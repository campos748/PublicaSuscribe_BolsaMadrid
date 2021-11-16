package Server;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import Cliente.ServerInterface;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.Vector;
import java.util.Timer;


public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
  
   private ArrayList<Alerta> alertas;
   private HashMap<String,Float> data;
   
   public ServerImpl() throws RemoteException {
      super( );
      alertas = new ArrayList<>();
      data = new HashMap<>();
   } 
   
   
   @Override
    public synchronized void registerForCallback(ClientInterface callbackClientObject,String empresa,Float precio,int tipo) throws java.rmi.RemoteException {
        Alerta alerta = new Alerta(empresa,precio,tipo,callbackClientObject);
        this.alertas.add(alerta);
    }

// This remote method allows an object client to 
// cancel its registration for callback
// @param id is an ID for the client; to be used by
// the server to uniquely identify the registered client.
   @Override
    public synchronized void unregisterForCallback(ClientInterface callbackClientObject) throws java.rmi.RemoteException {
        System.out.println("Funcion de desrregistro");
        for (int i = 0; i < alertas.size(); i++) {
           if(alertas.get(i).getCliente()== callbackClientObject){
               
               alertas.remove(i);
               System.out.println("Unregistered client ");
           }
       }
    }
   
   
    private synchronized void doCallbacks() throws java.rmi.RemoteException {
        // make callback to each registered client
        
        System.out.println("Numero alertas antes callbacks: " + alertas.size());
        System.out.println("**************************************\n" + "Callbacks initiated ---");
        for (int i = 0; i < alertas.size() ; i++) {
            
            
            if (alertas.get(i).getTipo() == 0) { // Si es una alerta de compra
                if (this.data.get(alertas.get(i).getEmpresa()) <= alertas.get(i).getPrecio()) {
                    ClientInterface nextClient = (ClientInterface) alertas.get(i).getCliente();
                    // invoke the callback method
                    nextClient.notifyMe("Ha saltado una alerta de Compra");
                    nextClient.actualizarVentana(alertas.get(i).getEmpresa(),alertas.get(i).getPrecio(),0);                                        
                }
            } 
            else {                            // Si es una alerta de venta
                if (this.data.get(alertas.get(i).getEmpresa()) >= alertas.get(i).getPrecio()) {
                    ClientInterface nextClient = (ClientInterface) alertas.get(i).getCliente();
                    // invoke the callback method
                    nextClient.notifyMe("Ha saltado una alerta de Venta");
                    nextClient.actualizarVentana(alertas.get(i).getEmpresa(),alertas.get(i).getPrecio(),1);    
                }
            }

        }// end for
        
        System.out.println("********************************\n" + "Server completed callbacks ---");
    } // doCallbacks
    
    
   
   @Override
   public HashMap<String,Float> getData() throws RemoteException {
      HashMap<String,Float> data = new HashMap<>();    //HashMap que va a almacenar todos los valores de la bolsa

      Document doc = null;
      try {
         doc = Jsoup.connect("https://www.bolsamadrid.es/esp/aspx/Mercados/Precios.aspx?indice=ESI100000000&punto=indice").get();
      } catch (IOException e) {
         e.printStackTrace();
      }


      Elements table = doc.select("#ctl00_Contenido_tblAcciones"); //select the first table.
      Elements rows = table.select("tr");
      Float valor = new Float(0);
      String textValor;
      
      Element row;
      Elements cols;
              
      for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
         row = rows.get(i);                             
         cols = row.select("td");
         textValor = cols.get(1).text();                //Obtención del valor
         textValor = textValor.replace(',', '.');       //Es neccesario cambiar el formato de , a . para poder procesar el número
         
         valor = Float.valueOf(textValor);
         
         data.put(cols.get(0).text(),valor);            //Guardo en el hashMap empresa-valor

      }

      return data;                                      //Devuelvo el hashMap
   }
   
   
   public void observar() throws InterruptedException, RemoteException{
       while(true){
           
           sleep(60000);    //Espero 1 min
           
           System.out.println("*********** Consulata de la bolsa *************");
           this.data = this.getData();  //actualizo el hashMap

           for (String empresa : this.data.keySet()) {
               System.out.println(empresa + ": " + this.data.get(empresa));
           }
           
           this.doCallbacks();
           
          
       }
           
  }
   
    
} // end class
