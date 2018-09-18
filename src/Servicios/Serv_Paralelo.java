package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Paralelo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Paralelo extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Paralelo>{
		
		@Override
		public Paralelo mapRow(ResultSet rs, int rows) throws SQLException {
			Paralelo us = new Paralelo();
			
			us.setCodparalelo(rs.getString("codparalelo"));
			us.setNombre(rs.getString("nombre"));
			us.setSigla(rs.getString("sigla"));
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from paralelo ", Integer.class);
		return "PLO0001-"+num;
	}
	
	public Paralelo obtener_por_Codparalelo(String codparalelo){
		String sql = "SELECT * FROM paralelo WHERE codparalelo=?";
		return db.queryForObject(sql, new to_Object(),codparalelo);
	}
	
	public List<Paralelo> listar_estado( int estado){
		String sql = "SELECT * FROM paralelo where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Paralelo> listar( ){
		String sql = "SELECT * FROM paralelo ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Paralelo> listaractivos(){
		String sql = "SELECT * FROM paralelo where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Paralelo> listainactivos(){
		String sql = "SELECT * FROM paralelo where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Paralelo us){
		us.setCodparalelo(generar_Cod());
		String sql = "INSERT INTO paralelo(codparalelo,nombre,sigla) VALUES(?,?,?);";
		db.update(sql, new Object[]{us.getCodparalelo(), us.getNombre(),us.getSigla()});
	}
	
	public void modificar(Paralelo us){
		
		String sql = "UPDATE paralelo SET nombre=?,sigla=? WHERE codparalelo=?;";
		db.update(sql, new Object[]{us.getNombre(),us.getSigla(),us.getCodparalelo()});
	}
	
	public void borrado_log(String codparalelo){
		String sql = "UPDATE paralelo SET estado=0 WHERE codparalelo=?;";
		db.update(sql, codparalelo);
	}
	
	public void activar(String codparalelo){
		String sql = "UPDATE paralelo SET estado=1 WHERE codparalelo=?;";
		db.update(sql, codparalelo);
	}
	
	public boolean existe_grado(String codparalelo){
		String sql="SELECT count(*) FROM paralelo WHERE codparalelo=?";
		if(db.queryForObject(sql, Integer.class,codparalelo)>0)return true;
		return false; 	
		
	}
	

	
}