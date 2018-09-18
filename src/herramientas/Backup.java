package herramientas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Backup {
	
	public void generar_backup(String ruta_postgres, String ruta_guardar,String IP,String username,String password,String DB ) throws IOException {
		
		   Runtime r =Runtime.getRuntime();
	
           Process p;
           ProcessBuilder pb;
               java.util.TimeZone zonah = java.util.TimeZone.getTimeZone("GMT+1");
               java.util.Calendar Calendario = java.util.GregorianCalendar.getInstance( zonah, new java.util.Locale("es"));
               java.text.SimpleDateFormat df = new java.text.SimpleDateFormat( "dd-MM-yyyy" );
               StringBuffer date = new StringBuffer();
               date.append(df.format(Calendario.getTime()));
              
           java.io.File file = new java.io.File(ruta_postgres);
           if(file.exists()){
               StringBuffer fechafile = new StringBuffer();
               fechafile.append(ruta_guardar);
               fechafile.append(date.toString());
               fechafile.append(".backup");
               java.io.File ficherofile = new java.io.File(fechafile.toString());
               //Probamos a ver si existe ese
              if(ficherofile.exists()){
                  //Lo Borramos
                  ficherofile.delete();
              }

               r =Runtime.getRuntime();
               System.out.println("llega aqui");
               
               	pb = new ProcessBuilder(ruta_postgres, "-i", "-h",IP, "-p", "5432","-U", username, "-F", "c", "-b", "-v" ,"-f",ficherofile.toString(),DB);    
   	        	pb.environment().put("PGPASSWORD", password);    
   	        	pb.redirectErrorStream(true);    
   	        	p = pb.start();  
               

               System.out.println("paso2");
               try{
                      InputStream is = p.getInputStream();
                      InputStreamReader isr = new InputStreamReader(is);
                      BufferedReader br = new BufferedReader(isr);
                      String ll;
                      while ((ll = br.readLine()) != null) {
                        System.out.println(ll);
                     }
               }
               catch (IOException e) {
                 System.out.println (e);
               }
           }
		
	}

}
