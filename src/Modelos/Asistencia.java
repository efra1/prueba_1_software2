package Modelos;

public class Asistencia {
	
	
	private String codalumno,codasistencia,fecha,codclase,asistencia;
	public String getCodalumno() {
		return codalumno;
	}
	public void setCodalumno(String codalumno) {
		this.codalumno = codalumno;
	}
	
	public String getFecha() {
		return fecha;
	}
	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	public String getCodclase() {
		return codclase;
	}
	public void setCodclase(String codclase) {
		this.codclase = codclase;
	}
	public String getAsistencia() {
		return asistencia;
	}
	public void setAsistencia(String asistencia) {
		this.asistencia = asistencia;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public String getCodasistencia() {
		return codasistencia;
	}
	public void setCodasistencia(String codasistencia) {
		this.codasistencia = codasistencia;
	}
	private int estado;

}
