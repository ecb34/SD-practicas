package estacion;
import java.io.BufferedReader;
import java.io.File;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;


public class Estacion{
   private int temperatura;
   private int humedad;
   private int luminosidad;
   private String lcd;
   private final int id;

   public Estacion(){
     this.id = 5;
     String nombreFichero = "./Estacion"+id+".txt";
     File f = new File(nombreFichero);
     if(f.exists()){
       getEstacion(nombreFichero);
     }else{
       this.temperatura = 30;
       this.luminosidad = 450;
       this.humedad = 90;
       String aux="Hola, esta es la practica de SD";
       this.lcd=aux;
       setEstacion(nombreFichero);
     }
   }

   public void setLog(String logLine) {
	   String ficheroLog = "log.txt";
	   FileWriter fichero = null;
	   PrintWriter pw = null;
	   try {
		   fichero = new FileWriter(ficheroLog,true);
		   pw = new PrintWriter(fichero);
		   pw.println(logLine);
		   
	   }catch(Exception e) {
		   e.printStackTrace();
	   }finally {
		   try {
			   if(null !=fichero)
				   fichero.close();
		   }catch(Exception e2) {
			   e2.printStackTrace();
		   }
	   }
   }
   
   public String getId(){
	     return encryptAndEncode(Integer.toString(id));
   }

   public String getTemp(){
     try{
       getEstacion("./Estacion"+id+".txt");
     }catch(Exception e){
   }
     return encryptAndEncode(Integer.toString(temperatura));
   }

   public String getHum(){
     try{
       getEstacion("./Estacion"+id+".txt");
     }catch(Exception e){
    }

     return encryptAndEncode(Integer.toString(humedad));
   }

   public String getLum(){
     try{
       getEstacion("./Estacion"+id+".txt");
     }catch(Exception e){

     }
     return encryptAndEncode(Integer.toString(luminosidad));
   }


   public String getLCD(){
     try{
       getEstacion("./Estacion"+id+".txt");
     }catch(Exception e){
     }
     return encryptAndEncode(lcd.toString());
   }


   public void setTemp(String encriptado){
	 try {  
		 String temp = decodeAndDecrypt(encriptado);
		 int t = Integer.parseInt(temp);
	     if(t >= -30 && 51 > t)
	       temperatura = t;
	     else
	       temperatura = 30;
	     setEstacion("./Estacion"+id+".txt");
	 }catch(Exception e) {}
   }


   public void setHum(String encriptado){
     try {
	   String hum = decodeAndDecrypt(encriptado);
	   int h = Integer.parseInt(hum);
       if(h >= 0 && 101 > h)
	       humedad = h;
	   else
	       humedad = 90;
	   setEstacion("./Estacion"+id+".txt");
     }catch(Exception e) {}
   }


   public void setLum(String encriptado){
     try {
    	 String lum = decodeAndDecrypt(encriptado);
  	   	 int l = Integer.parseInt(lum);
    	 if(l >= 0 && 801 > l)
	       luminosidad = l;
	     else
	       luminosidad = 450;
	     setEstacion("./Estacion"+id+".txt");
     }catch(Exception e) {}
   }


   public void setLCD(String encriptado){
      try {
    	  String lcd = decodeAndDecrypt(encriptado);
    	  this.lcd = lcd;
    	  setEstacion("./Estacion"+id+".txt");
      }catch(Exception e) {}
      
   }
   private void setTemp(int t){
	     if(t >= -30 && 51 > t)
	       temperatura = t;
	     else
	       temperatura = 30;
	   }


	   private void setHum(int h){
	     if(h >= 0 && 101 > h)
	       humedad = h;
	     else
	       humedad = 90;
	   }


	   private void setLum(int l){
	     if(l >= 0 && 801 > l)
	       luminosidad = l;
	     else
	       luminosidad = 450;
	   }


	   private void setPantalla(String lcd){
	      this.lcd = lcd;
	      setEstacion("./Estacion"+id+".txt");
	   }


   private void getEstacion(String estacion) {
     File archivo = new File(estacion);
     try{
       FileReader fr = new FileReader(archivo);
       BufferedReader br = new BufferedReader(fr);
       String linea = br.readLine();
       for(int i = 0; i<4;i++){
         String dividir[] = linea.split("=");
         switch(i){
           case 0: setTemp(Integer.parseInt(dividir[1]));
           break;
           case 1: setHum(Integer.parseInt(dividir[1]));
           break;
           case 2: setLum(Integer.parseInt(dividir[1]));
           break;
           case 3: setPantalla(dividir[1]);
           break;
         }

         linea = br.readLine();
      }
      br.close();
      fr.close();
      setEstacion(estacion);
    }catch(Exception e){
    }
  }


  private void setEstacion(String estacion){

  OutputStream os = null;
    try{
      os = new FileOutputStream(new File(estacion));
      String texto = "Temperatura="+temperatura+"\n"+"Humedad="+humedad+"\n"+"Luminosidad="+luminosidad+"\n"+"Pantalla="+String.valueOf(lcd)+"\n";
      os.write(texto.getBytes(),0,texto.length());
    }catch (Exception ex) {
  }finally{
      try{
        os.close();
      }catch(Exception e){
        System.out.println(e);
      }
    }
  }
  
  private static String IV = "IV_VALUE_16_BYTE"; 
  private static String PASSWORD = "PASSWORD_VALUE"; 
  private static String SALT = "SALT_VALUE"; 

  private String encryptAndEncode(String raw) {
      try {
          Cipher c = getCipher(Cipher.ENCRYPT_MODE);
          byte[] encryptedVal = c.doFinal(getBytes(raw));
          String s = getString(Base64.encodeBase64(encryptedVal));
          return s;
      } catch (Throwable t) {
          throw new RuntimeException(t);
      }
  }

  private String decodeAndDecrypt(String encrypted) throws Exception {
      byte[] decodedValue = Base64.decodeBase64(getBytes(encrypted));
      Cipher c = getCipher(Cipher.DECRYPT_MODE);
      byte[] decValue = c.doFinal(decodedValue);
      return new String(decValue);
  }

  private String getString(byte[] bytes) throws UnsupportedEncodingException {
      return new String(bytes, "UTF-8");
  }

  private byte[] getBytes(String str) throws UnsupportedEncodingException {
      return str.getBytes("UTF-8");
  }

  private Cipher getCipher(int mode) throws Exception {
      Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
      byte[] iv = getBytes(IV);
      c.init(mode, generateKey(), new IvParameterSpec(iv));
      return c;
  }

  private Key generateKey() throws Exception {
      SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
      char[] password = PASSWORD.toCharArray();
      byte[] salt = getBytes(SALT);

      KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
      SecretKey tmp = factory.generateSecret(spec);
      byte[] encoded = tmp.getEncoded();
      return new SecretKeySpec(encoded, "AES");
  }


  
}
