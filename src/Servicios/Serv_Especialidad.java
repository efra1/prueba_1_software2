package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;


import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Especialidad extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Especialidad>{
		@Override
		public Especialidad mapRow(ResultSet rs, int rows) throws SQLException {
			Especialidad us = new Especialidad();
			
			us.setCodespecialidad(rs.getString("codespecialidad"));
			us.setNombre(rs.getString("nombre"));
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	private String generar_Codespecialidad(){
		int num=db.queryForObject("select count (*) from especialidad ", Integer.class);
		return "ESP0001-"+num;
	}
	
	public Especialidad obtener_por_Codespecialidad(String codespecialidad){
		String sql = "SELECT * FROM especialidad WHERE codespecialidad=?";
		return db.queryForObject(sql, new to_Object(),codespecialidad);
	}
	
	public List<Especialidad> listar_estado( int estado){
		String sql = "SELECT * FROM especialidad where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Especialidad> listar( ){
		String sql = "SELECT * FROM especialidad ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Especialidad> listaractivos(){
		String sql = "SELECT * FROM especialidad where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Especialidad> listainactivos(){
		String sql = "SELECT * FROM especialidad where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Especialidad us){
		us.setCodespecialidad(generar_Codespecialidad());
		String sql = "INSERT INTO especialidad(codespecialidad,nombre,estado) VALUES(?,?,?);";
		db.update(sql, new Object[]{us.getCodespecialidad(), us.getNombre(),us.getEstado()});
	}
	
	public void modificar(Especialidad us){
		
		String sql = "UPDATE especialidad SET nombre=? WHERE codespecialidad=?;";
		db.update(sql, new Object[]{us.getNombre(),us.getCodespecialidad()});
	}
	
	public void borrado_log(String codespecialidad){
		String sql = "UPDATE especialidad SET estado=0 WHERE codespecialidad=?;";
		db.update(sql, codespecialidad);
	}
	
	public void activar(String codespecialidad){
		String sql = "UPDATE especialidad SET estado=1 WHERE codespecialidad=?;";
		db.update(sql, codespecialidad);
	}
	
	public boolean existe_especialidad(String codespecialidad){
		String sql="SELECT count(*) FROM especialidad WHERE codespecialidad=?";
		if(db.queryForObject(sql, Integer.class,codespecialidad)>0)return true;
		return false; 	
		
	}
	public Map<String, Object> obtener(String codespecialidad){
		try {
			return db.queryForMap("select * from especialidad where codespecialidad=?",codespecialidad);
		} catch (Exception e) {
			System.out.println("error obtenerespecialidad="+e.toString());
			return null;
		}
	}

	
}