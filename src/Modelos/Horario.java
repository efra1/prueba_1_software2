package Modelos;

import java.util.List;
public class Horario {
	private String codhorario,nombre,dia,hora_inicio,hora_fin,codclase,codprofesor;
	private Integer estado;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodhorario() {
		return codhorario;
	}
	public void setCodhorario(String codhorario) {
		this.codhorario = codhorario;
	}
	public String getHora_inicio() {
		return hora_inicio;
	}
	public void setHora_inicio(String hora_inicio) {
		this.hora_inicio = hora_inicio;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getHora_fin() {
		return hora_fin;
	}
	public void setHora_fin(String hora_fin) {
		this.hora_fin = hora_fin;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getCodclase() {
		return codclase;
	}
	public void setCodclase(String codclase) {
		this.codclase = codclase;
	}
	public String getCodprofesor() {
		return codprofesor;
	}
	public void setCodprofesor(String codprofesor) {
		this.codprofesor = codprofesor;
	}
}
