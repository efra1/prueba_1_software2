package Modelos;

import java.util.List;

public class Usuario {
	
	private String ci, nombre, ap, am, sexo, fechanac, ecivil, foto, email, telefono;
	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	private Integer estado;
	
	private byte[] fotito;
	
	private Datos datos;
	private List<Rol> roles;
	
	
	
	

	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAp() {
		return ap;
	}

	public void setAp(String ap) {
		this.ap = ap;
	}

	public String getAm() {
		return am;
	}

	public void setAm(String am) {
		this.am = am;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getFechanac() {
		return fechanac;
	}

	public void setFechanac(String fechanac) {
		this.fechanac = fechanac;
	}

	public String getEcivil() {
		return ecivil;
	}

	public void setEcivil(String ecivil) {
		this.ecivil = ecivil;
	}

	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Integer getEstado() {
		return estado;
	}

	public void setEstado(Integer estado) {
		this.estado = estado;
	}

	public Datos getDatos() {
		return datos;
	}

	public void setDatos(Datos datos) {
		this.datos = datos;
	}
	
	public List<Rol> getRoles() {
		return roles;
	}
	
	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
	
	@Override
	public String toString() {
		return String.format("%s %s %s", nombre, ap, (am==null?"":am));
	}

	public byte[] getFotito() {
		return fotito;
	}

	public void setFotito(byte[] fotito) {
		this.fotito = fotito;
	}
}