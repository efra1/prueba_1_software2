package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Materia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Materia extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Materia>{
		
		@Override
		public Materia mapRow(ResultSet rs, int rows) throws SQLException {
			Materia us = new Materia();
			
			us.setCodmateria(rs.getString("codmateria"));
			us.setNombre(rs.getString("nombre"));
			us.setSigla(rs.getString("sigla"));
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from materia ", Integer.class);
		return "MTRIAO0001-"+num;
	}
	
	public Materia obtener_por_codmateria(String codmateria){
		String sql = "SELECT * FROM materia WHERE codmateria=?";
		return db.queryForObject(sql, new to_Object(),codmateria);
	}
	
	public List<Materia> listar_estado( int estado){
		String sql = "SELECT * FROM materia where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Materia> listar( ){
		String sql = "SELECT * FROM materia ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Materia> listaractivos(){
		String sql = "SELECT * FROM materia where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Materia> listainactivos(){
		String sql = "SELECT * FROM materia where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Materia us){
		us.setCodmateria(generar_Cod());
		String sql = "INSERT INTO materia(codmateria,nombre,sigla) VALUES(?,?,?);";
		db.update(sql, new Object[]{us.getCodmateria(), us.getNombre(),us.getSigla()});
	}
	
	public void modificar(Materia us){
		
		String sql = "UPDATE materia SET nombre=?,sigla=? WHERE codmateria=?;";
		db.update(sql, new Object[]{us.getNombre(),us.getSigla(),us.getCodmateria()});
	}
	
	public void borrado_log(String codmateria){
		String sql = "UPDATE materia SET estado=0 WHERE codmateria=?;";
		db.update(sql, codmateria);
	}
	
	public void activar(String codmateria){
		String sql = "UPDATE materia SET estado=1 WHERE codmateria=?;";
		db.update(sql, codmateria);
	}
	
	public boolean existe_grado(String codmateria){
		String sql="SELECT count(*) FROM materia WHERE codmateria=?";
		if(db.queryForObject(sql, Integer.class,codmateria)>0)return true;
		return false; 	
		
	}
	

	
}