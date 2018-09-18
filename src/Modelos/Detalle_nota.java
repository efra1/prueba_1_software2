package Modelos;

import java.util.List;

public class Detalle_nota {
	
	
	
	private List<Sub_Detalle> sub_detalle;
	
	public String getCodnota() {
		return codnota;
	}
	public void setCodnota(String codnota) {
		this.codnota = codnota;
	}
	public String getCoddetnot() {
		return coddetnot;
	}
	public void setCoddetnot(String coddetnot) {
		this.coddetnot = coddetnot;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getNota() {
		return nota;
	}
	public void setNota(int nota) {
		this.nota = nota;
	}
	public int getEstado() {
		return estado;
	}
	public void setEstado(int estado) {
		this.estado = estado;
	}
	public List<Sub_Detalle> getSub_detalle() {
		return sub_detalle;
	}
	public void setSub_detalle(List<Sub_Detalle> sub_detalle) {
		this.sub_detalle = sub_detalle;
	}
	private String codnota,coddetnot,item;
	private int nota,estado;
	

}
