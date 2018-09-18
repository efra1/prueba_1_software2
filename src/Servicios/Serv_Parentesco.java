package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Parentesco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Parentesco extends DB {
	
	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Parentesco>{
		@Override
		public Parentesco mapRow(ResultSet rs, int rows) throws SQLException {
			Parentesco us = new Parentesco();
			
			us.setCodparentesco(rs.getString("codparentesco"));
			us.setCodalumno(rs.getString("codalumno"));
			us.setCodapoderado(rs.getString("codapoderado"));
			us.setEstado(rs.getInt("estado"));
			return us;
		}
	}
	public void adicionar(Parentesco us){
		
		String sql = "INSERT INTO parentesco(codparentesco,codalumno,codapoderado) VALUES(?,?,?);";
		db.update(sql, new Object[]{us.getCodparentesco(), us.getCodalumno(),us.getCodapoderado()});
	}
	
	public String generar_Codigo(){
		int num=db.queryForObject("select count (*) from parentesco ", Integer.class);
		return "PRES0001-"+num;
	}
	
	public Parentesco obtener_por_codparentesco(String codparentesco){
		String sql = "SELECT * FROM parentesco WHERE codparentesco=?";
		return db.queryForObject(sql, new to_Object(),codparentesco);
	}
	
	public Parentesco obtener_por_codalumno(String codparentesco){
		String sql = "SELECT * FROM parentesco WHERE codalumno=?";
		return db.queryForObject(sql, new to_Object(),codparentesco);
	}
	
	
	public List<Parentesco> listar_estado( int estado){
		String sql = "SELECT * FROM parentesco where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Parentesco> listar( ){
		String sql = "SELECT * FROM parentesco ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Parentesco> listaractivos(){
		String sql = "SELECT * FROM parentesco where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Parentesco> listainactivos(){
		String sql = "SELECT * FROM parentesco where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	
	public void modificar(Parentesco us){
		
		String sql = "UPDATE parentesco SET codalumno=?,codapoderado=? WHERE codparentesco=?;";
		db.update(sql, new Object[]{us.getCodalumno(),us.getCodapoderado(),us.getCodparentesco()});
	}
	
	public void borrado_log(String codparentesco){
		String sql = "UPDATE parentesco SET estado=0 WHERE codparentesco=?;";
		db.update(sql, codparentesco);
	}
	
	public void activar(String codparentesco){
		String sql = "UPDATE parentesco SET estado=1 WHERE codparentesco=?;";
		db.update(sql, codparentesco);
	}
	
	public boolean existe_grado(String codparentesco){
		String sql="SELECT count(*) FROM parentesco WHERE codparentesco=?";
		if(db.queryForObject(sql, Integer.class,codparentesco)>0)return true;
		return false; 	
		
	}
	

	
}