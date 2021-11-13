package Server;

import Cliente.Interface;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;


public class Impl extends UnicastRemoteObject implements Interface {
  
   public Impl() throws RemoteException {
      super( );
   }
  
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
} // end class
