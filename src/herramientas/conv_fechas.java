package herramientas;



import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
@SuppressWarnings("deprecation")
public class conv_fechas{
	public String obtenerFecha(Date fecha,String formato){
		SimpleDateFormat forma=new SimpleDateFormat(formato);
		return forma.format(fecha); 
	}
	public String obtenerFechaActualEspaniol(){
		Calendar f=Calendar.getInstance();
		String di[]={"","domingo","lunes","martes","miercoles","jueves","viernes","sabado"};
		String me[]={"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
		int A=f.get(Calendar.YEAR);
		int M=f.get(Calendar.MONTH);
		int D=f.get(Calendar.DAY_OF_MONTH);
		int S=f.get(Calendar.DAY_OF_WEEK);
		return di[S]+" "+D+" de "+me[M]+" del "+A;
	}
	public Time ConvertirHora(String hora){
		if(hora.equals(""))return null;
		return Time.valueOf(hora+":00");
	}
	public String obtenerFechaActual(String formato){
		SimpleDateFormat format = new java.text.SimpleDateFormat(formato, new Locale("es", "ES"));
		return format.format(new Date());
	}
	@SuppressWarnings("static-access")
	public static int obtener_dia_semana_anio(Date d){
		Calendar calendar=Calendar.getInstance();
		calendar.setFirstDayOfWeek(calendar.MONDAY);
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setTime(d);
		return calendar.get(calendar.WEEK_OF_YEAR);
	}
	public String obtenerDiaSemanaLiteral(Date d){
	      String[] dias={"Domingo","Lunes","Martes", "Miércoles","Jueves","Viernes","Sábado"};
	      int numeroDia=0;
	      Calendar cal= Calendar.getInstance();
	      cal.setTime(d);
	      numeroDia=cal.get(Calendar.DAY_OF_WEEK);
	      return dias[numeroDia - 1];
	}
	public static int obtenerDiaSemanaFecha(Date d){
		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(d);
		return cal.get(Calendar.DAY_OF_WEEK);
	}
	public static String ObtenerFechaLiteral(Date f){
		return f.getDate()+" DE "+obtenerMesLiteral(f.getMonth()+1)+" DE "+(f.getYear()+1900);
	}
	public Date convertirStringDate(String fec, String formato){
		if(fec==null)return null;
		SimpleDateFormat format = new java.text.SimpleDateFormat(formato, new Locale("es", "ES"));
		Date fecha=null;
		try {
		    fecha = new java.sql.Date(format.parse(fec).getTime());
		} catch (Exception ex) {ex.printStackTrace();}
		return fecha;
	}
	public static String obtenerMesLiteral(int num){	
		switch (num) {
		case 1 :
			return "Enero";
		case 2 :
			return "Febrero";
		case 3 :
			return "Marzo";
		case 4 :
			return "Abril";
		case 5 :
			return "Mayo";
		case 6 :
			return "Junio";
		case 7 :
			return "Julio";
		case 8 :
			return "Agosto";
		case 9 :
			return "Septiembre";
		case 10 :
			return "Octubre";
		case 11 :
			return "Noviembre";
		case 12 :
			return "Diciembre";
		default:
			return null;
		}
		
	}
	public Date sumarRestarDiasFecha(Date fecha,int dias){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.DAY_OF_YEAR, dias);
		return calendar.getTime();
	}
	public Date sumarRestarHorasFecha(Date fecha,int horas){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(fecha);
		calendar.add(Calendar.HOUR, horas);
		return calendar.getTime();
	}
	public static long DiferenciaFechas(String vinicio, String vfinal){
        Date dinicio = null, dfinal = null;
        long milis1, milis2, diff;
        SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy");
        try {
                // PARSEO STRING A DATE
                dinicio = sdf.parse(vinicio);
                dfinal = sdf.parse(vfinal);                    
               
        } catch (ParseException e) {

                System.out.println("Se ha producido un error en el parseo");
        }
        //INSTANCIA DEL CALENDARIO GREGORIANO
        Calendar cinicio = Calendar.getInstance();
        Calendar cfinal = Calendar.getInstance();
        //ESTABLECEMOS LA FECHA DEL CALENDARIO CON EL DATE GENERADO ANTERIORMENTE
         cinicio.setTime(dinicio);
         cfinal.setTime(dfinal);
         milis1 = cinicio.getTimeInMillis();
         milis2 = cfinal.getTimeInMillis();
         diff = milis2-milis1;
         // calcular la diferencia en dias
         long diffdias = Math.abs ( diff / (24 * 60 * 60 * 1000) );
         return diffdias+1;
	}

	public static String ultimodiaMes(int mes, int año){
		 switch(mes){
		 	case 0:  // Enero
		 	case 2:  // Marzo
		 	case 4:  // Mayo
		 	case 6:  // Julio
		 	case 7:  // Agosto
		 	case 9:  // Octubre
		 	case 11: // Diciembre
		 		return "31";
		 	case 3:  // Abril
		 	case 5:  // Junio
		 	case 8:  // Septiembre
		 	case 10: // Noviembre
		 		return "30";
		 	case 1:  // Febrero
		 		if ( ((año%100 == 0) && (año%400 == 0)) || ((año%100 != 0) && (año%  4 == 0))   )
		 		return "29";  // Año Bisiesto
		   else
			   return "28";
		  default:
		 }
		 return "0";
	}	
}