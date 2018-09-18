package herramientas;


public class FirtsInCapitals {
	
	public String Mayuscula(String str){
		String correct = "";
		if (!str.isEmpty() && !str.equals("")) {
			for (String string : str.toLowerCase().split(" ")) {
				correct += string.toUpperCase().charAt(0);
				for(int i = 0; i < string.length(); i++){
					if(i > 0) correct += string.charAt(i);
				}
				correct += " ";
			}
		}
		return correct.trim();
	}
}