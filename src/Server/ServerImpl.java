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
import java.util.Vector;


public class ServerImpl extends UnicastRemoteObject implements ServerInterface {
  
   private Vector clientList;
    
   public ServerImpl() throws RemoteException {
      super( );
      clientList = new Vector();
   }
  
   @Override
    public synchronized void registerForCallback(ClientInterface callbackClientObject) throws java.rmi.RemoteException {
        // store the callback object into the vector
        if (!(clientList.contains(callbackClientObject))) {
            clientList.addElement(callbackClientObject);
            System.out.println("Registered new client ");
            doCallbacks();
        } // end if
    }

// This remote method allows an object client to 
// cancel its registration for callback
// @param id is an ID for the client; to be used by
// the server to uniquely identify the registered client.
   @Override
    public synchronized void unregisterForCallback(ClientInterface callbackClientObject) throws java.rmi.RemoteException {
        if (clientList.removeElement(callbackClientObject)) {
            System.out.println("Unregistered client ");
        } else {
            System.out.println("unregister: clientwasn't registered.");
        }
    }
   
   
    private synchronized void doCallbacks() throws java.rmi.RemoteException {
        // make callback to each registered client
        System.out.println("**************************************\n" + "Callbacks initiated ---");
        for (int i = 0; i < clientList.size(); i++) {
            System.out.println("doing " + i + "-th callback\n");
            // convert the vector object to a callback object
            ClientInterface nextClient = (ClientInterface) clientList.elementAt(i);
            // invoke the callback method
            nextClient.notifyMe("Number of registered clients="+ clientList.size());
        }// end for
        System.out.println("********************************\n" +  "Server completed callbacks ---");
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

    @Override
    public boolean alertaCompra(String empresa, Float precio) throws RemoteException {
        
        ObserverAlerta alert = new ObserverAlerta(empresa, precio, 0);
        alert.start();
        
        return true;
    }

    @Override
    public boolean alertaVenta(String empresa, Float precio) throws RemoteException {
        
        ObserverAlerta alert = new ObserverAlerta(empresa, precio, 1);
        alert.start();
        
        return true;
    }
} // end class
