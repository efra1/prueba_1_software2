package Modelos;

import java.sql.Date;
import java.util.List;

public class Profesor {

	private String codprofesor,codespecialidad,rud,codusuario,lugar_nac,direccion,distrito;
	private Usuario usuario;
	
	
	
	
	public String getCodprofesor() {
		return codprofesor;
	}
	public void setCodprofesor(String codprofesor) {
		this.codprofesor = codprofesor;
	}
	public String getCodespecialidad() {
		return codespecialidad;
	}
	public void setCodespecialidad(String codespecialidad) {
		this.codespecialidad = codespecialidad;
	}
	public String getRud() {
		return rud;
	}
	public void setRud(String rud) {
		this.rud = rud;
	}
	public String getCodusuario() {
		return codusuario;
	}
	public void setCodusuario(String codusuario) {
		this.codusuario = codusuario;
	}
	public String getLugar_nac() {
		return lugar_nac;
	}
	public void setLugar_nac(String lugar_nac) {
		this.lugar_nac = lugar_nac;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getDistrito() {
		return distrito;
	}
	public void setDistrito(String distrito) {
		this.distrito = distrito;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	private Integer estado;

	
	
}
