package Modelos;
import java.util.List;
public class Detalle {
	
private String coddetalle,codnota,nombre;

public String getCoddetalle() {
	return coddetalle;
}

public void setCoddetalle(String coddetalle) {
	this.coddetalle = coddetalle;
}

public String getCodnota() {
	return codnota;
}

public void setCodnota(String codnota) {
	this.codnota = codnota;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}

public int getSer() {
	return ser;
}

public void setSer(int ser) {
	this.ser = ser;
}

public int getSaber() {
	return saber;
}

public void setSaber(int saber) {
	this.saber = saber;
}

public int getHacer() {
	return hacer;
}

public void setHacer(int hacer) {
	this.hacer = hacer;
}

public int getDecidir() {
	return decidir;
}

public void setDecidir(int decidir) {
	this.decidir = decidir;
}

public int getPromedio() {
	return promedio;
}

public void setPromedio(int promedio) {
	this.promedio = promedio;
}

public int getEstado() {
	return estado;
}

public void setEstado(int estado) {
	this.estado = estado;
}

private int ser,saber,hacer,decidir,promedio,estado;
}