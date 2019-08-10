import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.*;
import java.rmi.server.*;
public class EstacionMain{
public static void main(String[] args)throws Exception{//recibe el host de registro y la id de la estacion a registrar
    final Registry registro = LocateRegistry.getRegistry(args[0],Registry.REGISTRY_PORT);
    int id = Integer.parseInt(args[2]);
    int portRMI = Integer.parseInt(args[1]);
    String URLRegistro = "rmi://"+args[0]+":"+portRMI+"/Registrador";
    InterfazRegistrador r =null;
    try{
      System.setSecurityManager(new RMISecurityManager());
      Estacion e = new Estacion(id);
      r = (InterfazRegistrador) registro.lookup(URLRegistro);
      System.out.println("registrador encontrado");
      String url = "rmi://"+args[0]+":1099/Estacion_"+id;
      r.registrarEstacion(e,url);
      System.out.println("Estacion "+id+" registrada");
    }catch(Exception e){
      System.out.println(e);
    }
  }
}
