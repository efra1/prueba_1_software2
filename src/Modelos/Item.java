package Modelos;
import java.util.List;
public class Item {
	private String  codgrado,nombre, sigla;
	private Integer estado;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getCodgrado() {
		return codgrado;
	}
	public void setCodgrado(String codgrado) {
		this.codgrado = codgrado;
	}

}
