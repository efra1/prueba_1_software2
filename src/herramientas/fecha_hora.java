package herramientas;

import java.util.Date;
import java.util.StringTokenizer;

@SuppressWarnings({ "deprecation" })
public class fecha_hora {

	public Date fecha(String f) {
		StringTokenizer token;
		if(f.indexOf('/')>-1)token= new StringTokenizer(f, "/");
		else token= new StringTokenizer(f, "-");
		return new Date(Integer.parseInt(token.nextToken()) - 1900, Integer.parseInt(token.nextToken()) - 1, Integer.parseInt(token.nextToken()));
	}

	public Date hora(String h) {
		StringTokenizer token = new StringTokenizer(h, ":");
		Date hr = new Date();
		hr.setHours(Integer.parseInt(token.nextToken()));
		hr.setMinutes(Integer.parseInt(token.nextToken()));
		hr.setSeconds(0);
		return hr;
	}
}