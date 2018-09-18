package herramientas;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class fechas {

	public Date convertir_a_date(String fec){
		
		SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy", new Locale("es", "ES"));
		
		Date fecha=null;
		
		try {
		    
		    fecha = new java.sql.Date(format.parse(fec).getTime());
		   
		} catch (Exception ex) {}
		
		return fecha;
		
	}
	
public Date convertir_a_date_time(String fec){
		
		SimpleDateFormat format = new java.text.SimpleDateFormat("dd-MM-yyyy:HH.mm.ss", new Locale("es", "ES"));
		
		Date fecha=null;
		
		try {
		    
		    fecha = new java.sql.Date(format.parse(fec).getTime());
		   
		} catch (Exception ex) {}
		
		return fecha;
		
	}
	
	
	
	
	
	
	public String convertir_mes_numeral_a_mes_literal(String num){
		
		switch (Integer.parseInt(num)) {
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
}
