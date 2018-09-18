package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Grado extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Item>{
		@Override
		public Item mapRow(ResultSet rs, int rows) throws SQLException {
			Item us = new Item();
			
			us.setCodgrado(rs.getString("codgrado"));
			us.setNombre(rs.getString("nombre"));
			us.setSigla(rs.getString("sigla"));
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from grado ", Integer.class);
		return "GRD0001-"+num;
	}
	
	public Item obtener_por_Codgrado(String codgrado){
		String sql = "SELECT * FROM grado WHERE codgrado=?";
		return db.queryForObject(sql, new to_Object(),codgrado);
	}
	
	public List<Item> listar_estado( int estado){
		String sql = "SELECT * FROM grado where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Item> listar( ){
		String sql = "SELECT * FROM grado ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Item> listaractivos(){
		String sql = "SELECT * FROM grado where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Item> listainactivos(){
		String sql = "SELECT * FROM grado where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Item us){
		us.setCodgrado(generar_Cod());
		String sql = "INSERT INTO grado(codgrado,nombre,sigla) VALUES(?,?,?);";
		db.update(sql, new Object[]{us.getCodgrado(), us.getNombre(),us.getSigla()});
	}
	
	public void modificar(Item us){
		
		String sql = "UPDATE grado SET nombre=?,sigla=? WHERE codgrado=?;";
		db.update(sql, new Object[]{us.getNombre(),us.getSigla(),us.getCodgrado()});
	}
	
	public void borrado_log(String codgrado){
		String sql = "UPDATE grado SET estado=0 WHERE codgrado=?;";
		db.update(sql, codgrado);
	}
	
	public void activar(String codgrado){
		String sql = "UPDATE grado SET estado=1 WHERE codgrado=?;";
		db.update(sql, codgrado);
	}
	
	public boolean existe_grado(String codgrado){
		String sql="SELECT count(*) FROM grado WHERE codgrado=?";
		if(db.queryForObject(sql, Integer.class,codgrado)>0)return true;
		return false; 	
		
	}
	

	
}