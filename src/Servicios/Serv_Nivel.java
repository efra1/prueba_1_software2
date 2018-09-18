package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Nivel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Nivel extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Nivel>{
		@Override
		public Nivel mapRow(ResultSet rs, int rows) throws SQLException {
			Nivel us = new Nivel();
			
			
			us.setCodnivel(rs.getString("codnivel"));
			us.setNombre(rs.getString("nombre"));
			us.setSigla(rs.getString("sigla"));
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from nivel ", Integer.class);
		return "NVL0001-"+num;
	}
	
	public Nivel obtener_por_codnivel(String codnivel){
		String sql = "SELECT * FROM nivel WHERE codnivel=?";
		return db.queryForObject(sql, new to_Object(),codnivel);
	}
	
	public List<Nivel> listar_estado( int estado){
		String sql = "SELECT * FROM nivel where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Nivel> listar( ){
		String sql = "SELECT * FROM nivel ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Nivel> listaractivos(){
		String sql = "SELECT * FROM nivel where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Nivel> listainactivos(){
		String sql = "SELECT * FROM nivel where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Nivel us){
		us.setCodnivel(generar_Cod());
		String sql = "INSERT INTO nivel(codnivel,nombre,sigla) VALUES(?,?,?);";
		db.update(sql, new Object[]{us.getCodnivel(), us.getNombre(),us.getSigla()});
	}
	
	public void modificar(Nivel us){
		
		String sql = "UPDATE nivel SET nombre=?,sigla=? WHERE codnivel=?;";
		db.update(sql, new Object[]{us.getNombre(),us.getSigla(),us.getCodnivel()});
	}
	
	public void borrado_log(String codnivel){
		String sql = "UPDATE nivel SET estado=0 WHERE codnivel=?;";
		db.update(sql, codnivel);
	}
	
	public void activar(String codnivel){
		String sql = "UPDATE nivel SET estado=1 WHERE codnivel=?;";
		db.update(sql, codnivel);
	}
	
	public boolean existe_nivel(String codnivel){
		String sql="SELECT count(*) FROM nivel WHERE codnivel=?";
		if(db.queryForObject(sql, Integer.class,codnivel)>0)return true;
		return false; 	
		
	}
	

	
}