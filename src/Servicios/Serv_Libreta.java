package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Libreta;
import Modelos.Matricula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fecha_hora;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Libreta extends DB {
	

	fechas conv = new fechas();
	fecha_hora f =new fecha_hora();

	private class to_Object implements ParameterizedRowMapper<Libreta>{
		@Override
		public Libreta mapRow(ResultSet rs, int rows) throws SQLException {
			Libreta us = new Libreta();
			
			us.setCodlibreta(rs.getString("codlibreta"));
			
			
			us.setCodalumno(rs.getString("codalumno"));
			us.setCodcurso(rs.getString("codcurso"));
			us.setCalificacion(rs.getString("calificacion"));
			
			us.setGestion(rs.getInt("gestion"));
			us.setNumero(rs.getInt("numero"));
			us.setEstado(rs.getBoolean("estado"));
			
			
			
			
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from libreta ", Integer.class);
		return "LBRT0001-"+num;
	}
	
	public int generar_numero(){
		int num=db.queryForObject("select count (*) from libreta ", Integer.class);
		return 10000+num;
	}
	
	
	public int verificar_promedio(String codcurso,String codalumno){
		int num=db.queryForObject("SELECT avg(nota) from notas join clase  c on c.codclase=notas.codclase and c.codcurso=? WHERE codalumno=? ", Integer.class,codcurso,codalumno);
		return num;
	}
	public Libreta obtener_por_Codlibreta(String codlibreta){
		String sql = "SELECT * FROM libreta WHERE codlibreta=?";
		return db.queryForObject(sql, new to_Object(),codlibreta);
	}
	
	public Libreta obtener_por_alumno_gestion(String codalumno,String codgestion){
		String sql = "SELECT * FROM libreta WHERE codalumno=? and codgestion=?";
		return db.queryForObject(sql, new to_Object(),codalumno,codgestion);
	}
	
	public List<Libreta> listar_estado( int estado){
		String sql = "SELECT * FROM libreta where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Libreta> listar( ){
		String sql = "SELECT * FROM libreta ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Libreta> listaractivos(){
		String sql = "SELECT * FROM libreta where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Libreta> listainactivos(){
		String sql = "SELECT * FROM libreta where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Libreta us){
		us.setCodlibreta(generar_Cod());
		String sql = "INSERT INTO libreta(codlibreta,codalumno,codcurso,gestion,calificacion,numero) VALUES(?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodlibreta(),us.getCodalumno(), us.getCodcurso(),us.getGestion(),us.getCalificacion(),us.getNumero()});
	}
	
	public void modificar(Libreta us){
		
		String sql = "UPDATE libreta SET codalumno=?,codcurso=?,gestion=?,calificacion=?,numero=? WHERE codlibreta=?;";
		db.update(sql, new Object[]{us.getCodalumno(),us.getCodcurso(), us.getGestion(),us.getCalificacion(),us.getNumero(),us.getCodlibreta()});
	}
	

	
	
	public void borrado_log(String codmatricula){
		String sql = "UPDATE libreta SET estado=0 WHERE codlibreta=?;";
		db.update(sql, codmatricula);
	}
	
	public void activar(String codmatricula){
		String sql = "UPDATE libreta SET estado=1 WHERE codlibreta=?;";
		db.update(sql, codmatricula);
	}
	
	public boolean existe_libreta(String codalumno,String codcurso,int gestion){
		String sql="SELECT count(*) FROM libreta WHERE codalumno=?,codcurso=?,gestion=?";
		if(db.queryForObject(sql, Integer.class,codalumno,codcurso,gestion)>0)return true;
		return false; 	
		
	}
	
	
	public boolean existe_libreta(String codalumno,String codcurso){
		String sql="SELECT count(*) FROM libreta WHERE codalumno=? and codcurso=?";
		if(db.queryForObject(sql, Integer.class,codalumno,codcurso)>0)return true;
		return false; 	
		
	}

	
}