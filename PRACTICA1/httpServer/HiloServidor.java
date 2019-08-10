
import java.lang.Exception;
import java.net.Socket;
import java.io.*;


public class HiloServidor extends Thread{//la putita de httpserver

  private Socket cliente;
  private String hostController; //pues pa controlar, pa q va a ser xD
  private int puertoHilo;
  private Socket controlador;

  public HiloServidor(Socket cliente,String host, int puerto){
    this.cliente = cliente;
    hostController = host;
    puertoHilo = puerto;
  }
//lee los datos que le pide el cliente y los devuelve.
  public String leeSocket(Socket sk, String datos){
    try{
      InputStream aux = sk.getInputStream();
      BufferedReader flujo = new BufferedReader(new InputStreamReader(aux));
      datos = new String();
      while(flujo.ready()){
        datos = datos.concat(flujo.readLine() + "\n");
      }
    }catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
      return datos;
  }

  //para socket normal
  public void escribeSocket(Socket sk, String datos){
    try{
      OutputStream aux = sk.getOutputStream();
      DataOutputStream flujo= new DataOutputStream(aux);
			flujo.writeUTF(datos);
		}catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
  }

  public void escribeSocketWeb(Socket sk, String datos){
    try{
      PrintWriter out = new PrintWriter(sk.getOutputStream());
      out.println("HTTP/1.1 200 OK");
          //falta content-length
          out.println("Content-Type: text/html; charset=utf-8");
          out.println("Server: MyHTTPServer");
          out.println("");
          out.println(datos);
          out.flush();
          out.close();
    }catch(Exception e){
      System.out.println("ERROR SOCKET");
    }
  }
  //para socket conectado con navegador cuando el recurso es estático
  public void escribeSocketEstatico(Socket sk, String data){
    try{
      PrintWriter out = new PrintWriter(sk.getOutputStream());
      BufferedReader br;
      String url="";
      if(data.equals("/") || data.equals("/index.html"))
        url = "./index.html";
      else//corregir?
        url = "./error.html";

      br = new BufferedReader(new FileReader(url));
      if (url.equals("/index.html")) {
          out.println("HTTP/1.1 200 OK");
          //falta content-length
          out.println("Content-Type: text/html; charset=utf-8");
          out.println("Server: MyHTTPServer");
      } else {
          out.println("HTTP/1.1 405 Method Not Allowed");
          //falta content-length
          out.println("Content-Type: text/html; charset=utf-8");
          out.println("Server: MyHTTPServer");
      }
      out.println("");
      data = br.readLine();
      while(data!=null){
        out.println(data);
        data = br.readLine();//enviar a cliente
      }
      out.flush();
    //  out.close();
    }catch(Exception e){
      System.err.println("No se pudo escribir en el socket");
    }
  }

  public void run(){
    String cadena="", respuesta = "";
    try{
      do{
        cadena = leeSocket(cliente,cadena);
        if(!cadena.isEmpty()){
          String[] result = cadena.split("\\s");
          if(result[0].equals("GET")){
              String[] aux = cadena.split("/");
              if(aux[1].equals("controladorSD")){
                try{
                  aux = aux[2].split("\\s");
                  System.out.println(aux[0]);
                  controlador = new Socket(hostController,puertoHilo);

                  escribeSocket(controlador,aux[0]);
                  while(respuesta.isEmpty()){
                      respuesta = leeSocket(controlador, respuesta);
                  }
                  escribeSocketWeb(cliente,respuesta);  
                }catch(Exception e){
                  String respuestaAux =  "<!DOCTYPE html>\n<html> \n"
                        + "    <head>\n"
                        + "        <meta charset=\"utf-8\">\n"
                        + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                        + "        <title>ERROR 500</title>\n"
                        + "        <meta name=\"description\" content=\"error404\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + " <h1> ERROR 500 CONTROLADOR NO DISPONIBLE"
                        + "</body> \n </html> \n";
                  escribeSocketWeb(cliente,respuestaAux);
                }
              }else{
                escribeSocketEstatico(cliente,result[1]);
              }
          }else{
            System.err.println("Comando no soportado");
          }
        }
      }while(cadena.equals(""));
    }catch(Exception e){
      System.err.println("error al abrir servidor");
    }
      try {
        sleep(5000);
	      cliente.close();
	} catch (Exception e) {
		System.err.println("error al cerrar cliente");
	}

  }
}
