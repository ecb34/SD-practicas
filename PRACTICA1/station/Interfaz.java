import java.rmi.Remote;


public interface Interfaz extends Remote{
  public void compruebaLum(int l)throws java.rmi.RemoteException;
  public void compruebaTemp(int t)throws java.rmi.RemoteException;
  public void compruebaHum(int h)throws java.rmi.RemoteException;
  public void setLCD(String lcd)throws java.rmi.RemoteException;
  public int getLum() throws java.rmi.RemoteException;
  public int getTemp() throws java.rmi.RemoteException;
  public int getHum() throws java.rmi.RemoteException;
  public int getId() throws java.rmi.RemoteException;
  public char[] getLCD() throws java.rmi.RemoteException;
  public void getEstacion(String estacion) throws java.rmi.RemoteException;
  public void setEstacion(String estacion) throws java.rmi.RemoteException;
}
