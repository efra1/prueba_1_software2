package Modelos;
import java.util.List;
public class Libreta {
private String  codlibreta,codalumno,codcurso,calificacion;




public String getCodlibreta() {
	return codlibreta;
}

public void setCodlibreta(String codlibreta) {
	this.codlibreta = codlibreta;
}

public String getCodalumno() {
	return codalumno;
}

public void setCodalumno(String codalumno) {
	this.codalumno = codalumno;
}

public String getCodcurso() {
	return codcurso;
}

public void setCodcurso(String codcurso) {
	this.codcurso = codcurso;
}

public String getCalificacion() {
	return calificacion;
}

public void setCalificacion(String calificacion) {
	this.calificacion = calificacion;
}

public int getGestion() {
	return gestion;
}

public void setGestion(int gestion) {
	this.gestion = gestion;
}

public int getNumero() {
	return numero;
}

public void setNumero(int numero) {
	this.numero = numero;
}

public boolean isEstado() {
	return estado;
}

public void setEstado(boolean estado) {
	this.estado = estado;
}

private int gestion,numero;

private boolean estado;

}
