package Modelos;

public class Menu {
	
	private Integer codmenu, estado;
	private String nombre, descrip, enlace;
	
	
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	
	public String getEnlace() {
		return enlace;
	}
	public void setEnlace(String enlace) {
		this.enlace = enlace;
	}
	public Integer getCodmenu() {
		return codmenu;
	}
	public void setCodmenu(Integer codmenu) {
		this.codmenu = codmenu;
	}
}