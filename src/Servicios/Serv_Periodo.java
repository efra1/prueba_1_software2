package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Periodo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fecha_hora;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Periodo extends DB {
	
	
	fechas conv = new fechas();

	fecha_hora f = new fecha_hora();
	private class to_Object implements ParameterizedRowMapper<Periodo>{
		@Override
		public Periodo mapRow(ResultSet rs, int rows) throws SQLException {
			Periodo us = new Periodo();
			
			us.setCodperiodo(rs.getString("codperiodo"));
			
		
			us.setNombre(rs.getString("nombre"));
			us.setDescripcion(rs.getString("descripcion"));
			us.setFecha_inicio(rs.getString("fecha_inicio"));
			us.setFecha_fin(rs.getString("fecha_fin"));
			us.setEstado(rs.getInt("estado"));
							
			return us;
		}
	}
	
	public void adicionar(Periodo us){
		
		us.setCodperiodo(generar_Codigo());
		String sql = "INSERT INTO periodo(codperiodo,nombre,descripcion,fecha_inicio,fecha_fin) VALUES(?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodperiodo(), us.getNombre(),us.getDescripcion(),f.fecha(us.getFecha_inicio()),f.fecha(us.getFecha_fin())});
	}
	
	private String generar_Codigo(){
		int num=db.queryForObject("select count (*) from periodo ", Integer.class);
		return "HRA0001-"+num;
	}
	
	public Periodo obtener_por_Codperiodo(String codperiodo){
		String sql = "SELECT * FROM periodo WHERE codperiodo=?";
		return db.queryForObject(sql, new to_Object(),codperiodo);
	}
	
	public List<Periodo> listar_estado( int estado){
		String sql = "SELECT * FROM periodo where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Periodo> listar( ){
		String sql = "SELECT * FROM periodo ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Periodo> listaractivos(){
		String sql = "SELECT * FROM periodo where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Periodo> listainactivos(){
		String sql = "SELECT * FROM periodo where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	
	public void modificar(Periodo us){
		
		String sql = "UPDATE periodo SET nombre=?,descripcion=?,fecha_inicio=?,fecha_fin=? WHERE codperiodo=?;";
		db.update(sql, new Object[]{us.getNombre(),us.getDescripcion(),f.fecha(us.getFecha_inicio()),f.fecha(us.getFecha_fin()),us.getCodperiodo()});
	}
	
	public void borrado_log(String codperiodo){
		String sql = "UPDATE periodo SET estado=0 WHERE codperiodo=?;";
		db.update(sql, codperiodo);
	}
	
	public void activar(String codperiodo){
		String sql = "UPDATE periodo SET estado=1 WHERE codperiodo=?;";
		db.update(sql, codperiodo);
	}
	
	public boolean existe_periodo(String codperiodo){
		String sql="SELECT count(*) FROM periodo WHERE codperiodo=?";
		if(db.queryForObject(sql, Integer.class,codperiodo)>0)return true;
		return false; 	
		
	}

	
}