package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Tutoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Tutoria extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Tutoria>{
		@Override
		public Tutoria mapRow(ResultSet rs, int rows) throws SQLException {
			Tutoria us = new Tutoria();
			
			
			us.setCodcurso(rs.getString("codcurso"));
			us.setCodprofesor(rs.getString("codprofesor"));
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	public String generar_Codigo(){
		int num=db.queryForObject("select count (*) from tutoria ", Integer.class);
		return "TTRIA-"+num;
	}
	
	public Tutoria obtener_por_Codtutoria(String codtutoria){
		String sql = "SELECT * FROM tutoria WHERE codgrado=?";
		return db.queryForObject(sql, new to_Object(),codtutoria);
	}
	
	public List<Tutoria> listar_estado( int estado){
		String sql = "SELECT * FROM tutoria where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Tutoria> listar( ){
		String sql = "SELECT * FROM tutoria ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Tutoria> listaractivos(){
		String sql = "SELECT * FROM tutoria where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Tutoria> listainactivos(){
		String sql = "SELECT * FROM tutoria where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Tutoria us){
		
		String sql = "INSERT INTO tutoria(codcurso,codprofesor) VALUES(?,?);";
		db.update(sql, new Object[]{us.getCodcurso(),us.getCodprofesor()});
	}
	

	
	public void borrado_log(String codtutoria){
		String sql = "UPDATE tutoria SET estado=0 WHERE codtutoria=?;";
		db.update(sql, codtutoria);
	}
	
	public void activar(String codtutoria){
		String sql = "UPDATE tutoria SET estado=1 WHERE codtutoria=?;";
		db.update(sql, codtutoria);
	}
	
	public boolean existe_grado(String codtutoria){
		String sql="SELECT count(*) FROM tutoria WHERE codtutoria=?";
		if(db.queryForObject(sql, Integer.class,codtutoria)>0)return true;
		return false; 	
		
	}
	

	
}