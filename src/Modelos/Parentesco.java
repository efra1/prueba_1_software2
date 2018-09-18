package Modelos;

public class Parentesco {
	
	public String getCodparentesco() {
		return codparentesco;
	}
	public void setCodparentesco(String codparentesco) {
		this.codparentesco = codparentesco;
	}
	public String getCodapoderado() {
		return codapoderado;
	}
	public void setCodapoderado(String codapoderado) {
		this.codapoderado = codapoderado;
	}
	public String getCodalumno() {
		return codalumno;
	}
	public void setCodalumno(String codalumno) {
		this.codalumno = codalumno;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	private String codparentesco,codapoderado,codalumno;
	private int estado;

}
