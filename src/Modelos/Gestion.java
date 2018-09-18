package Modelos;

public class Gestion {
	
	private String codgestion,fecha_inicio,fecha_fin;
	public String getCodgestion() {
		return codgestion;
	}
	public void setCodgestion(String codgestion) {
		this.codgestion = codgestion;
	}
	public String getFecha_inicio() {
		return fecha_inicio;
	}
	public void setFecha_inicio(String fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}
	public String getFecha_fin() {
		return fecha_fin;
	}
	public void setFecha_fin(String fecha_fin) {
		this.fecha_fin = fecha_fin;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public int getGestion() {
		return gestion;
	}
	public void setGestion(int gestion) {
		this.gestion = gestion;
	}
	private int estado,gestion;

}
