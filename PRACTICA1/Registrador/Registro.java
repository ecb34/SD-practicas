
import java.rmi.*;
import java.net.*;
import java.io.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Registro{
public static void main(String args[]){
    String URLRegistro;
    if(args.length <1){
      System.out.println("error");
      System.exit(1);
    }
    else{
      try{
            String hostRMI = args[0];
            int portRMI = Integer.parseInt(args[1]);
            final Registry r = LocateRegistry.createRegistry(portRMI);
            System.setSecurityManager(new RMISecurityManager());
            Registrador registrador = new Registrador(r);
            URLRegistro = "rmi://"+hostRMI+":1099/Registrador";
            r.rebind(URLRegistro,(Remote) registrador);
            System.out.println("registrador ya esta listo.");
          }catch (Exception ex){
              System.out.println(ex);
          }
        }
  }
}
