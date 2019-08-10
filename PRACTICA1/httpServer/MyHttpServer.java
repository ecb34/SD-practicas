
import java.io.IOException;
import java.net.*;


public class MyHttpServer{
  private static int num_sockets=0;

    public static void abreServidor(int puerto,String hostController,int puertoController, int num_conexiones){
    	ServerSocket servidor = null;
      try{
        servidor= new ServerSocket(puerto); // escucho peticiones
        System.out.println("escuchando el puerto "+ puerto );
        for(;;){
          if(num_conexiones > Thread.activeCount()){
            Socket cliente = servidor.accept();//acepta peticion proveniente del cliente
            Thread t = new HiloServidor(cliente,hostController,puertoController);// creo el hilo para el cliente
            t.start();
          }
        }
      }catch(Exception e){
        System.err.println("No se ha podido abrir el servidor");
      }finally {
    	  try {
			servidor.close();
		} catch (IOException e) {
			System.out.println("error al cerrar servidor");
		}
      }

    }

    public static void main(String[] args){
      MyHttpServer miniHttp = new MyHttpServer();
      if(args.length == 4){
        int puerto = Integer.parseInt(args[0]); //puerto web
        String hostController = args[1];// nombre host(local host)
        int puertoController = Integer.parseInt(args[2]); // puerto serv controller)
        int num_conexiones = Integer.parseInt(args[3]);//max conexiones
        miniHttp.abreServidor(puerto,hostController,puertoController,num_conexiones);
      }else{
        System.err.println("faltan argumentos");
      }
    }

}
