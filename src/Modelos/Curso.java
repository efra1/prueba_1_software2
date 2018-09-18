package Modelos;
import java.util.List;
public class Curso {

	private String codcurso,nombre,codnivel,codparalelo,codturno,codgrado;
	private Integer estado;
	private List<Profesor> tutores;
	
	private Turno turno;

	public Item getGrado() {
		return grado;
	}
	public void setGrado(Item grado) {
		this.grado = grado;
	}
	public Paralelo getParalelo() {
		return paralelo;
	}
	public void setParalelo(Paralelo paralelo) {
		this.paralelo = paralelo;
	}
	public Nivel getNivel() {
		return nivel;
	}
	public void setNivel(Nivel nivel) {
		this.nivel = nivel;
	}
	private Item grado;
	private Paralelo paralelo;
	private Nivel nivel;
	
	
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getCodnivel() {
		return codnivel;
	}
	public void setCodnivel(String codnivel) {
		this.codnivel = codnivel;
	}
	public String getCodparalelo() {
		return codparalelo;
	}
	public void setCodparalelo(String codparalelo) {
		this.codparalelo = codparalelo;
	}
	public String getCodgrado() {
		return codgrado;
	}
	public void setCodgrado(String codgrado) {
		this.codgrado = codgrado;
	}
	public String getCodturno() {
		return codturno;
	}
	public void setCodturno(String codturno) {
		this.codturno = codturno;
	}
	public Integer getEstado() {
		return estado;
	}
	public void setEstado(Integer estado) {
		this.estado = estado;
	}
	public String getCodcurso() {
		return codcurso;
	}
	public void setCodcurso(String codcurso) {
		this.codcurso = codcurso;
	}
	public Turno getTurno() {
		return turno;
	}
	public void setTurno(Turno turno) {
		this.turno = turno;
	}
	public List<Profesor> getTutores() {
		return tutores;
	}
	public void setTutores(List<Profesor> tutores) {
		this.tutores = tutores;
	}
}
