package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Gestion;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fecha_hora;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Gestion extends DB {
	
	
	fechas conv = new fechas();

	fecha_hora f = new fecha_hora();
	private class to_Object implements ParameterizedRowMapper<Gestion>{
		@Override
		public Gestion mapRow(ResultSet rs, int rows) throws SQLException {
			Gestion us = new Gestion();
			
			us.setCodgestion(rs.getString("codgestion"));
			
			us.setFecha_inicio(rs.getString("fecha_inicio"));
			us.setFecha_fin(rs.getString("fecha_fin"));
			us.setGestion(rs.getInt("gestion"));
			
			us.setEstado(rs.getInt("estado"));
							
			return us;
		}
	}
	
	public void adicionar(Gestion us){
		
		us.setCodgestion(generar_Codigo());
		String sql = "INSERT INTO gestion(codgestion,fecha_inicio,fecha_fin,gestion) VALUES(?,?,?,?);";
		db.update(sql, new Object[]{us.getCodgestion(),f.fecha(us.getFecha_inicio()),f.fecha(us.getFecha_fin()),us.getGestion()});
	}
	
	private String generar_Codigo(){
		int num=db.queryForObject("select count (*) from gestion ", Integer.class);
		return "GSTN-"+num;
	}
	
	public Gestion obtener_por_codgestion(String codgestion){
		String sql = "SELECT * FROM gestion WHERE codgestion=?";
		return db.queryForObject(sql, new to_Object(),codgestion);
	}
	
	public List<Gestion> listar_estado( int estado){
		String sql = "SELECT * FROM gestion where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Gestion> listar( ){
		String sql = "SELECT * FROM gestion ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Gestion> listaractivos(){
		String sql = "SELECT * FROM gestion where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Gestion> listainactivos(){
		String sql = "SELECT * FROM gestion where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	
	public void modificar(Gestion us){
		
		String sql = "UPDATE gestion SET fecha_inicio=?,fecha_fin=?,gestion=? WHERE codgestion=?;";
		db.update(sql, new Object[]{f.fecha(us.getFecha_inicio()),f.fecha(us.getFecha_fin()),us.getGestion(),us.getCodgestion()});
	}
	
	public void borrado_log(String codgestion){
		String sql = "UPDATE gestion SET estado=0 WHERE codgestion=?;";
		db.update(sql, codgestion);
	}
	
	public void activar(String codgestion){
		String sql = "UPDATE gestion SET estado=1 WHERE codgestion=?;";
		db.update(sql, codgestion);
	}
	
	public boolean existe_gestion(String codgestion){
		String sql="SELECT count(*) FROM gestion WHERE codgestion=?";
		if(db.queryForObject(sql, Integer.class,codgestion)>0)return true;
		return false; 	
		
	}

	
}