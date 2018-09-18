package Modelos;
import java.util.List;
public class Clase {
private String codclase,nombre,codmateria,codprofesor,codcurso;

private int estado;
private Curso curso;

public String getCodclase() {
	return codclase;
}

public void setCodclase(String codclase) {
	this.codclase = codclase;
}

public String getNombre() {
	return nombre;
}

public void setNombre(String nombre) {
	this.nombre = nombre;
}



public String getCodmateria() {
	return codmateria;
}

public void setCodmateria(String codmateria) {
	this.codmateria = codmateria;
}

public String getCodprofesor() {
	return codprofesor;
}

public void setCodprofesor(String codprofesor) {
	this.codprofesor = codprofesor;
}



public String getCodcurso() {
	return codcurso;
}

public void setCodcurso(String codcurso) {
	this.codcurso = codcurso;
}

public int getEstado() {
	return estado;
}

public void setEstado(int estado) {
	this.estado = estado;
}

public Curso getCurso() {
	return curso;
}

public void setCurso(Curso curso) {
	this.curso = curso;
}


}
