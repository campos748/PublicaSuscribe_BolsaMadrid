package Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server{
   public static void main(String args[]) {
      InputStreamReader is = new InputStreamReader(System.in);
      BufferedReader br = new BufferedReader(is);
      String portNum, registryURL;
      try{     
         System.out.println("Enter the RMIregistry port number:");
         portNum = (br.readLine()).trim();
         int RMIPortNum = Integer.parseInt(portNum);
         startRegistry(RMIPortNum);
         ServerImpl exportedObj = new ServerImpl();
         registryURL = "rmi://localhost:" + portNum + "/bolsa";
         Naming.rebind(registryURL, exportedObj);
         System.out.println("Server registered.  Registry currently contains:");
         listRegistry(registryURL);
         System.out.println("Server ready.");
      }
      catch (Exception re) {
         System.out.println("Exception in Server.main: " + re);
      }

  }

   // This method starts a RMI registry on the local host, if it
   // does not already exists at the specified port number.
   private static void startRegistry(int RMIPortNum)
      throws RemoteException{
      try {
         Registry registry = LocateRegistry.getRegistry(RMIPortNum);
         registry.list( );  // This call will throw an exception
                            // if the registry does not already exist
      }
      catch (RemoteException e) { 
         // No valid registry at that port.
         System.out.println("RMI registry cannot be located at port " + RMIPortNum);
         Registry registry = LocateRegistry.createRegistry(RMIPortNum);
         System.out.println("RMI registry created at port " + RMIPortNum);
      }
   } // end startRegistry

  // This method lists the names registered with a Registry object
  private static void listRegistry(String registryURL)
     throws RemoteException, MalformedURLException {
       System.out.println("Registry " + registryURL + " contains: ");
       String [ ] names = Naming.list(registryURL);
       for (int i=0; i < names.length; i++)
          System.out.println(names[i]);
  } //end listRegistry
     
} // end class
