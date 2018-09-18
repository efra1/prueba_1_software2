package Modelos;

public class Direccion {

	
	private String coddireccion,codalumno,departamento,	provincia_2,seccion,canton,localidad_2,zona,avenida,numero,telefono;
	public String getCoddireccion() {
		return coddireccion;
	}
	public void setCoddireccion(String coddireccion) {
		this.coddireccion = coddireccion;
	}
	public String getCodalumno() {
		return codalumno;
	}
	public void setCodalumno(String codalumno) {
		this.codalumno = codalumno;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	
	public String getSeccion() {
		return seccion;
	}
	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}
	public String getCanton() {
		return canton;
	}
	public void setCanton(String canton) {
		this.canton = canton;
	}
	
		
		
	public String getZona() {
		return zona;
	}
	public void setZona(String zona) {
		this.zona = zona;
	}
	public String getAvenida() {
		return avenida;
	}
	public void setAvenida(String avenida) {
		this.avenida = avenida;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public String getProvincia_2() {
		return provincia_2;
	}
	public void setProvincia_2(String provincia_2) {
		this.provincia_2 = provincia_2;
	}
	public String getLocalidad_2() {
		return localidad_2;
	}
	public void setLocalidad_2(String localidad_2) {
		this.localidad_2 = localidad_2;
	}
	private boolean estado;

}
