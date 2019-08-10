import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Registrador extends UnicastRemoteObject implements InterfazRegistrador{
  private Registry registro;
  public Registrador(Registry registro) throws java.rmi.RemoteException{
    super();
    this.registro=registro;
  }

  public void registrarEstacion(Interfaz estacion,String urlRegistro)throws java.rmi.RemoteException{
    System.setSecurityManager(new RMISecurityManager());
    try{
      registro.rebind(urlRegistro,(Remote) estacion);
    }catch(RemoteException e){
      System.out.println("error al rebind estacion");
      throw e;
    }
  }
}
