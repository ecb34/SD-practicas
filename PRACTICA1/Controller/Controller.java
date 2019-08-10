
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Controller{
  //lee los datos que llegan del servidor
  public String leeSocket(Socket sk, String datos){
    try{
      InputStream is = sk.getInputStream();
      DataInputStream flujo = new DataInputStream(is);
      datos = new String();
      datos = flujo.readUTF();
    }catch(Exception e){
      System.err.println("Error al leer datos del socket");
    }
    return datos;
  }
  //escribe datos para servidor
  public void escribeSocket (Socket p_sk, String p_Datos)
	{
		try
		{
      PrintWriter out = new PrintWriter(p_sk.getOutputStream());
      out.println(p_Datos);
      out.flush();
      out.close();
		}
		catch (Exception e)
		{
			System.out.println("Error: " + e.toString());
		}
	}

private String pedirDatos(String host, int puerto, String datos){
    Interfaz objetoRemoto = null;
    String res = new String();
    String[] parametros_validos = new String[]{"todo","temperatura","humedad","luminosidad", "pantalla"};
    String servidor = "rmi://" + host +":"+puerto;
    String[] name=null;
    System.setSecurityManager(new RMISecurityManager());
    try{
      Registry registry = LocateRegistry.getRegistry(host,puerto);
      name = registry.list();

      if(!datos.contains("?")){
        if(datos.equals("index")){
            res = "<!DOCTYPE html>\n<html> \n"
                        + "    <head>\n"
                        + "        <meta charset=\"utf-8\">\n"
                        + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                        + "        <title>CENTRO DE CONTROL</title>\n"
                        + "        <meta name=\"description\" content=\"Un centro para el control de estaciones meteorológicas\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <h1>BIENVENIDO A EL CENTRO DE CONTROL DE ESTACIONES METEOROLÓGICAS</h1>\n"
                        + "        <a href=\"/index.html\">Inicio</a>\n";
          
          
          for(int i=0;i<name.length;i++){
            System.out.println(name[i]);
            if(name[i].contains("Estacion")){
              String[] aux = name[i].split("_"); //para tener en aux[1] Estacion_2
              try{
                objetoRemoto = (Interfaz) registry.lookup(name[i]);
                objetoRemoto.getId();
                res += "<br><a href=\"/controladorSD/todo?station=" + aux[1] + "\" post >Estacion " + aux[1] + "</a> \n";
              }catch(Exception e){

              }        
            }
          }
          res+= "</body> \n </html>";
        }
        else{
           res = "<!DOCTYPE html>\n<html> \n"
                        + "    <head>\n"
                        + "        <meta charset=\"utf-8\">\n"
                        + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                        + "        <title>CENTRO DE CONTROL</title>\n"
                        + "        <meta name=\"description\" content=\"error404\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + " <h1> ERROR 404 VARIABLE NO VÁLIDA"
                        + "</body> \n </html> \n";
        }
      } else{
        String[] aux = datos.split("\\?");
        String parametro = aux[0];
        String estacion = aux[1];
        if(estacion.contains("station")){//comprobamos que existe station en la peticion
          aux = estacion.split("=");
          String numero = aux[1];//cogemos numero de estacion
          String valorset = "";
          Boolean valido=false;
          if(datos.contains("&")){
            String[] aux2=datos.split("&");
            String[] aux3 = aux2[0].split("=");
            numero = aux3[1];
            if(aux2[1].contains("set")){
              aux2=aux2[1].split("=");
              valorset = aux2[1];
            }
          }
          for(int i=0;i<parametros_validos.length;i++){
            if(parametros_validos[i].contains(parametro))
              valido=true;
          }
          if(!valido){
           res = "<!DOCTYPE html>\n<html> \n"
                        + "    <head>\n"
                        + "        <meta charset=\"utf-8\">\n"
                        + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                        + "        <title>ERROR 404</title>\n"
                        + "        <meta name=\"description\" content=\"error404\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + " <h1> ERROR 404 VARIABLE NO VÁLIDA</h1>\n"
                        + "</body> \n </html> \n";
          }
          else{
            String servidorConcreto = servidor.concat("/Estacion_"+ numero);
            System.out.println("Servidor: "+ servidorConcreto);
            try{
              System.setSecurityManager(new RMISecurityManager());
              res = "<!DOCTYPE html>\n<html> \n"
                      + "    <head>\n"
                      + "        <meta charset=\"utf-8\">\n"
                      + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                      + "        <title>CENTRO DE CONTROL</title>\n"
                      + "        <meta name=\"description\" content=\"Un centro para el control de estaciones meteorológicas\">\n"
                      + "    </head>\n"
                      + "    <body>\n";
              objetoRemoto = (Interfaz) registry.lookup(servidorConcreto);
              System.out.println(objetoRemoto.getId());
                switch(parametro){
                  case "temperatura": if(valorset==""){
                                  res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                      res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                      res += "<br> Temperatura: "+objetoRemoto.getTemp();
                                       res+= "        <FORM method=get action=\"temperatura\">";
                                        res+= "        Introduce el nuevo valor de la temperatura:";
                                        res+= "        <INPUT type=\"hidden\" name=\"station\" value=\""+objetoRemoto.getId()+"\">";
                                        res+= "        <br><INPUT type=\"number\" min=\"-30\" max=\"50\" name=\"set\">";
                                        res+= "        <br><INPUT type=\"submit\" value=\"Enviar\"> ";
                                        res+= "        </FORM>";
                                        }else{
                                            objetoRemoto.compruebaTemp(Integer.parseInt(valorset));
                                             res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                      res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                        res+=" <br>Se ha cambiado correctamente el valor de la temperatura.";
                                        }
                                        res+= "</body> \n </html>\n";
                  
                  break;
                  case "humedad": if(valorset==""){
                    res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                   res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                  res += "<br> Humedad: "+objetoRemoto.getHum();
                                   res+= "        <FORM method=get action=\"humedad\">";
                                        res+= "        Introduce el nuevo valor de la humedad:";
                                        res+= "        <INPUT type=\"hidden\" name=\"station\" value=\""+objetoRemoto.getId()+"\">";
                                        res+= "        <br><INPUT type=\"number\" min=\"0\" max=\"100\" name=\"set\">";
                                        res+= "        <br><INPUT type=\"submit\" value=\"Enviar\"> ";
                                        res+= "        </FORM>";
                                  }else{
                                    objetoRemoto.compruebaHum(Integer.parseInt(valorset));
                                     res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                      res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                        res+=" <br>Se ha cambiado correctamente el valor de la humedad.";
                                  } 
                                  res+= "</body> \n </html>\n";
                  break;
                  case "luminosidad": if(valorset==""){
                   res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                      res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                      res += "<br> Luminosidad: "+objetoRemoto.getLum() ;
                                       res+= "        <FORM method=get action=\"luminosidad\">";
                                        res+= "        Introduce el nuevo valor de la luminosidad:";
                                        res+= "        <INPUT type=\"hidden\" name=\"station\" value=\""+objetoRemoto.getId()+"\">";
                                        res+= "        <br><INPUT type=\"number\" min=\"0\" max=\"800\" name=\"set\">";
                                        res+= "        <br><INPUT type=\"submit\" value=\"Enviar\"> ";
                                        res+= "        </FORM>";
                  }else{
                       objetoRemoto.compruebaLum(Integer.parseInt(valorset));
                                     res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                      res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                        res+=" <br>Se ha cambiado correctamente el valor de la luminosidad.";
                  }
                  res+= "</body> \n </html>\n";
                  break;
                  case "pantalla":  if(valorset==""){
                                    res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                      res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                      res += "<br> Pantalla: "+String.valueOf(objetoRemoto.getLCD());
                                      
                                        res+= "        <FORM method=get action=\"pantalla\">";
                                        res+= "        Introduce el nuevo valor de la pantalla:";
                                        res+= "        <INPUT type=\"hidden\" name=\"station\" value=\""+objetoRemoto.getId()+"\">";
                                        res+= "        <br><INPUT type=text name=\"set\">";
                                        res+= "        <br><INPUT type=\"submit\" value=\"Enviar\"> ";
                                        res+= "        </FORM>";
                                      }
                                      else{
                                        valorset=valorset.replace('+',' ');
                                          objetoRemoto.setLCD(valorset);
                                           res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                      res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                        res+=" <br>Se ha cambiado correctamente el valor de la pantalla.";
                                      }
                                       res+= "</body> \n </html>\n";
                  break;
                  case "todo":  res += "<h1> Estación "+objetoRemoto.getId()+"<h1>\n";
                                res += "<a href=\"/controladorSD/index\"> Estaciones </a>\n";
                                res += "<br><a href=\"/controladorSD/temperatura?station="+objetoRemoto.getId()+"\">Temperatura</a>";
                                res += "<br><a href=\"/controladorSD/luminosidad?station="+objetoRemoto.getId()+"\">Luminosidad</a>";
                                res += "<br><a href=\"/controladorSD/humedad?station="+objetoRemoto.getId()+"\">Humedad</a>";
                                res += "<br><a href=\"/controladorSD/pantalla?station="+objetoRemoto.getId()+"\">Pantalla</a>";
                                res+= "</body> \n </html>";
                  break;
                }
                
              }catch(Exception e){
               res = "<!DOCTYPE html>\n<html> \n"
                        + "    <head>\n"
                        + "        <meta charset=\"utf-8\">\n"
                        + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                        + "        <title>ERROR500</title>\n"
                        + "        <meta name=\"description\" content=\"error500\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + " <h1> ERROR 404 ESTACIÓN NO ENCONTRADA </h1>\n"
                        + "</body> \n </html> \n";
              }
          }
        }else
          res = "<!DOCTYPE html>\n<html> \n"
                        + "    <head>\n"
                        + "        <meta charset=\"utf-8\">\n"
                        + "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
                        + "        <title>ERROR 404L</title>\n"
                        + "        <meta name=\"description\" content=\"error404\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + " <h1> ERROR 404 ESTACIÓN NO VÁLIDA </h1>\n"
                        + "</body> \n </html> \n";
      }
    }catch(Exception e){
      System.out.println("Error rmi");
    }
    return res;
  }
  public static void main(String[] args){
    String hostRMI;
    int portRMI,portHTTP;
    Controller cr = new Controller();
    hostRMI = args[0];
    portRMI = Integer.parseInt(args[1]);
    portHTTP = Integer.parseInt(args[2]);
    String op = null;
    while(true){
      try{
        ServerSocket skserver = new ServerSocket(portHTTP);
        System.out.println("Escucho a HTTPServer desde el puerto " + portHTTP);
        for (;;) {
            /*
             * Se espera un cliente que quiera conectarse
             */
            Socket skCliente = skserver.accept(); // Crea objeto
            System.out.println("Sirviendo cliente...");
            op = cr.leeSocket(skCliente, op);
            System.out.println(op);//imprimir el string pedido

            op = cr.pedirDatos(hostRMI, portRMI, op);
            cr.escribeSocket(skCliente, op);

            skCliente.close();//Debemos cerrar siempre el cliente.
            skserver.close();
        }
      } catch (Exception e) {
        System.out.println("Error: " + e.toString());
    	}
     }
   }
}
