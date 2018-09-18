package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Curso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Curso extends DB {
	
	@Autowired
	private Serv_Grado serv_Grado;
	@Autowired
	private Serv_Nivel serv_Nivel;
	@Autowired
	private Serv_Paralelo serv_Paralelo;
	@Autowired
	private Serv_Turno serv_Turno;
	@Autowired
	private Serv_Profesor serv_Profesor;
	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Curso>{
		@Override
		public Curso mapRow(ResultSet rs, int rows) throws SQLException {
			Curso us = new Curso();
			
			us.setCodcurso(rs.getString("codcurso"));
			us.setCodgrado(rs.getString("codgrado"));
			us.setCodnivel(rs.getString("codnivel"));
			us.setCodparalelo(rs.getString("codparalelo"));
			us.setCodturno(rs.getString("codturno"));
			
			us.setNombre(rs.getString("nombre"));
			
			us.setEstado(rs.getInt("estado"));
			
			
			try {
				us.setGrado(serv_Grado.obtener_por_Codgrado(rs.getString("codgrado")));
			} catch (Exception e) {
				us.setGrado(null);
			}
			try {
				us.setNivel(serv_Nivel.obtener_por_codnivel(rs.getString("codnivel")));
			} catch (Exception e) {
				us.setNivel(null);
			}
			
			try {
				us.setParalelo(serv_Paralelo.obtener_por_Codparalelo(rs.getString("codparalelo")));
			} catch (Exception e) {
				us.setParalelo(null);
			}
			
			try {
				us.setTurno(serv_Turno.obtener_por_Codturno(rs.getString("codturno")));
			} catch (Exception e) {
				us.setTurno(null);
			}
			
			try {
				us.setTutores(serv_Profesor.tutores_de_curso(rs.getString("codcurso")));
			} catch (Exception e) {
				us.setTutores(null);
			}
			
			return us;
		}
	}
	public void adicionar(Curso us){
		us.setCodcurso(generar_Codigo());
		String sql = "INSERT INTO curso(codcurso,codgrado,codnivel,codparalelo,codturno,nombre) VALUES(?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodcurso(), us.getCodgrado(),us.getCodnivel(),us.getCodparalelo(),us.getCodturno(),us.getNombre()});
	}
	
	private String generar_Codigo(){
		int num=db.queryForObject("select count (*) from curso ", Integer.class);
		return "CUR0001-"+num;
	}
	
	public Curso obtener_por_Codcurso(String codcurso){
		String sql = "SELECT * FROM curso WHERE codcurso=?";
		return db.queryForObject(sql, new to_Object(),codcurso);
	}
	
	public List<Curso> listar_estado( int estado){
		String sql = "SELECT * FROM curso where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Curso> listar( ){
		String sql = "SELECT * FROM curso ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Curso> listaractivos(){
		String sql = "SELECT * FROM curso where estado=1;";
		return db.query(sql, new to_Object());
	}
	
	public List<Curso> listar_cursos_x_codalumno(String codalumno){
		String sql = "select c.* from matricula m join curso c on c.codcurso=m.codcurso where m.codalumno=?";
		return db.query(sql, new to_Object(),codalumno);
	}
	public List<Curso> listainactivos(){
		String sql = "SELECT * FROM curso where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	
	public void modificar(Curso us){
		
		String sql = "UPDATE curso SET codgrado=?,codnivel=?,codparalelo=?,codturno=?,nombre=? WHERE codcurso=?;";
		db.update(sql, new Object[]{us.getCodgrado(),us.getCodnivel(),us.getCodparalelo(),us.getCodturno(),us.getNombre(),us.getCodcurso()});
	}
	
	public void borrado_log(String codgrado){
		String sql = "UPDATE curso SET estado=0 WHERE codcurso=?;";
		db.update(sql, codgrado);
	}
	
	public void activar(String codgrado){
		String sql = "UPDATE curso SET estado=1 WHERE codcurso=?;";
		db.update(sql, codgrado);
	}
	
	public boolean existe_grado(String codgrado){
		String sql="SELECT count(*) FROM grado WHERE codgrado=?";
		if(db.queryForObject(sql, Integer.class,codgrado)>0)return true;
		return false; 	
		
	}
	

	
}