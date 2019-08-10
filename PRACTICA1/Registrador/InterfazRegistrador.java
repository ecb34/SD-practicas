import java.rmi.Remote;
public interface InterfazRegistrador extends Remote{
  public void registrarEstacion(Interfaz estacion,String URLRegistro) throws java.rmi.RemoteException;
}
