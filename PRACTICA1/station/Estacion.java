
import java.io.Serializable;
import java.rmi.*;
import java.rmi.server.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.rmi.RemoteException;


public class Estacion extends UnicastRemoteObject implements Interfaz, Serializable{
   private int temperatura;
   private int humedad;
   private int luminosidad;
   private char[] lcd = new char[150];
   private final int id;

   public Estacion(int id) throws java.rmi.RemoteException{
     super();
     this.id = id;
     String nombreFichero = "./Estacion"+id+".txt";
     File f = new File(nombreFichero);
     if(f.exists()){
       getEstacion(nombreFichero);
     }else{
       this.temperatura = 30;
       this.luminosidad = 450;
       this.humedad = 90;
       String aux="Hola, esta es la practica de SD";
       this.lcd=aux.toCharArray();
       setEstacion(nombreFichero);
     }
   }

   public int getId(){
     return id;
   }

   public int getTemp() throws java.rmi.RemoteException{
     try{
       getEstacion("./Estacion"+id+".txt");
     }catch(Exception e){
       throw new java.rmi.RemoteException("No existe la estacion" + id);
     }
     return temperatura;
   }

   public int getHum() throws java.rmi.RemoteException{
     try{
       getEstacion("./Estacion"+id+".txt");
     }catch(Exception e){
       throw new java.rmi.RemoteException("No existe la estacion" + id);
     }
     return humedad;
   }

   public int getLum() throws java.rmi.RemoteException{
     try{
       getEstacion("./Estacion"+id+".txt");
     }catch(Exception e){
       throw new java.rmi.RemoteException("No existe la estacion" + id);
     }
     return luminosidad;
   }


   public char[] getLCD() throws java.rmi.RemoteException{
     try{
       getEstacion("./Estacion"+id+".txt");
     }catch(Exception e){
       throw new java.rmi.RemoteException("No existe la estacion" + id);
     }
     return lcd;
   }


   public void compruebaTemp(int t)throws java.rmi.RemoteException{
     if(t >= -30 && 51 > t)
       temperatura = t;
     else
       temperatura = 30;
      setEstacion("./Estacion"+id+".txt");
   }


   public void compruebaHum(int h)throws java.rmi.RemoteException{
     if(h >= 0 && 101 > h)
       humedad = h;
     else
       humedad = 90;
      setEstacion("./Estacion"+id+".txt");
   }


   public void compruebaLum(int l)throws java.rmi.RemoteException{
     if(l >= 0 && 801 > l)
       luminosidad = l;
     else
       luminosidad = 450;
      setEstacion("./Estacion"+id+".txt");
   }


   public void setLCD(String lcd) throws java.rmi.RemoteException{
      this.lcd = lcd.toCharArray();
      setEstacion("./Estacion"+id+".txt");
   }


   public void getEstacion(String estacion) throws java.rmi.RemoteException{
     File archivo = new File(estacion);
     try{
       FileReader fr = new FileReader(archivo);
       BufferedReader br = new BufferedReader(fr);
       String linea = br.readLine();
       for(int i = 0; i<4;i++){
         String dividir[] = linea.split("=");
         switch(i){
           case 0: compruebaTemp(Integer.parseInt(dividir[1]));
           break;
           case 1: compruebaHum(Integer.parseInt(dividir[1]));
           break;
           case 2: compruebaLum(Integer.parseInt(dividir[1]));
           break;
           case 3: setLCD(dividir[1]);
          // this.lcd = dividir[1].toCharArray();
          break;
         }

         linea = br.readLine();
      }
      br.close();
      fr.close();
      //setEstacion(estacion);
    }catch(Exception e){
      throw new java.rmi.RemoteException("No se ha podido leer el fichero"+ e.toString());
    }
  }


  public void setEstacion(String estacion)throws java.rmi.RemoteException{

  OutputStream os = null;
    try{
      os = new FileOutputStream(new File(estacion));
      String texto = "Temperatura="+temperatura+"\n"+"Humedad="+humedad+"\n"+"Luminosidad="+luminosidad+"\n"+"Pantalla="+String.valueOf(lcd)+"\n";
      os.write(texto.getBytes(),0,texto.length());
    }catch (Exception ex) {
      throw new java.rmi.RemoteException("error al crear/escribir en el archivo");
    }finally{
      try{
        os.close();
      }catch(Exception e){
        System.out.println(e);
      }
    }
  }


  
}
