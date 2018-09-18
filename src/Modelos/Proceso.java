package Modelos;

import java.util.List;

public class Proceso {
	
	private Integer codp, estado;
	private String nombre;
	private List<Menu> menus;
	public Integer getCodp() {
		return codp;
	}
	public void setCodp(Integer codp) {
		this.codp = codp;
	}
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
	
	public List<Menu> getMenus() {
		return menus;
	}
	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}
	
}