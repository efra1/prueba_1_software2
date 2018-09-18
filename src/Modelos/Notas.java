package Modelos;

import java.util.List;

public class Notas {
	
	private String codnota,nombre,codclase,codalumno,codperiodo;
	private Integer nota,estado;
	
	private List<Detalle_nota> detalle;
	private Periodo periodo;
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
	public String getCodclase() {
		return codclase;
	}
	public void setCodclase(String codclase) {
		this.codclase = codclase;
	}
	public String getCodalumno() {
		return codalumno;
	}
	public void setCodalumno(String codalumno) {
		this.codalumno = codalumno;
	}
	public String getCodperiodo() {
		return codperiodo;
	}
	public void setCodperiodo(String codperiodo) {
		this.codperiodo = codperiodo;
	}
	public Integer getNota() {
		return nota;
	}
	public void setNota(Integer nota) {
		this.nota = nota;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public List<Detalle_nota> getDetalle() {
		return detalle;
	}
	public void setDetalle(List<Detalle_nota> detalle) {
		this.detalle = detalle;
	}
	public Periodo getPeriodo() {
		return periodo;
	}
	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

	

}
