package Servicios;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Horario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fecha_hora;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Horario extends DB {
	
	@Autowired
	private Serv_Grado serv_Grado;
	
	fecha_hora f =new fecha_hora();

	private class to_Object implements ParameterizedRowMapper<Horario>{
		@Override
		public Horario mapRow(ResultSet rs, int rows) throws SQLException {
			Horario us = new Horario();
			
			us.setCodhorario(rs.getString("codhorario"));
			us.setDia(rs.getString("dia"));
			us.setHora_fin(rs.getString("hora_fin"));
			us.setHora_inicio(rs.getString("hora_inicio"));
			us.setNombre(rs.getString("nombre"));
			us.setEstado(rs.getInt("estado"));
			us.setCodclase(rs.getString("codclase"));
			us.setCodprofesor(rs.getString("codprofesor"));
			
			
			return us;
		}
	}
	public void adicionar(Horario us){
		String sql = "INSERT INTO horario(codhorario,dia,hora_inicio,hora_fin,nombre,codclase,codprofesor) VALUES(?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodhorario(), us.getDia(), f.hora(us.getHora_inicio()),f.hora(us.getHora_fin()),us.getNombre(),us.getCodclase(),us.getCodprofesor()});
	}
	
	public String generar_Codigo(){
		int num=db.queryForObject("select count (*) from horario ", Integer.class);
		return "HRA0001-"+num;
	}
	public Horario obtener_por_Codhorario(String codhorario){
		String sql = "SELECT * FROM horario WHERE codhorario=?";
		return db.queryForObject(sql, new to_Object(),codhorario);
	}
	
	public Horario obtener_por_Codhorario2(String codhorario){
		String sql = "SELECT cast(to_char(hora_inicio,'hh:mm:ss') as varchar(25)) as hora_inicio,cast(to_char(hora_fin,'hh:mm:ss') as varchar(25)) as hora_fin,codhorario,nombre,dia,codclase,codprofesor,estado FROM horario WHERE codhorario=?";
		return db.queryForObject(sql, new to_Object(),codhorario);
	}
	public List<Horario> listar_estado( int estado){
		String sql = "SELECT * FROM horario where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	
	
	public List<Horario> listar_por_clase(String codclase){
		String sql = "SELECT * FROM horario where codclase=?;";
		return db.query(sql, new to_Object(),codclase);
	}
	public List<Horario> listar( ){
		String sql = "SELECT * FROM horario ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Horario> listaractivos(){
		String sql = "SELECT * FROM horario where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Horario> listainactivos(){
		String sql = "SELECT * FROM horario where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	
	public void modificar(Horario us){
		
		String sql = "UPDATE horario SET dia=?,hora_inicio=?,hora_fin=?,nombre=?,codclase=? WHERE codhorario=?;";
		db.update(sql, new Object[]{us.getDia(),f.hora(us.getHora_inicio()),f.hora(us.getHora_fin()),us.getNombre(),us.getCodclase(),us.getCodhorario()});
	}
	
	public void borrado_log(String codhorario){
		String sql = "UPDATE horario SET estado=0 WHERE codhorario=?;";
		db.update(sql, codhorario);
	}
	
	public void activar(String codhorario){
		String sql = "UPDATE horario SET estado=1 WHERE codhorario=?;";
		db.update(sql, codhorario);
	}
	
	public boolean existe_grado(String codhorario){
		String sql="SELECT count(*) FROM horario WHERE codhorario=?";
		if(db.queryForObject(sql, Integer.class,codhorario)>0)return true;
		return false; 	
		
	}
	
	public boolean existe_horario(String codprofesor,String dia,String hora_1,String hora_2 ){
		
		System.out.println(hora_1+"    "+hora_2);
		
		String sql="SELECT count(*) FROM horario WHERE codprofesor=? and dia=?  AND hora_fin>=? AND hora_inicio<=?";
		
		if(db.queryForObject(sql, Integer.class,codprofesor,dia,f.hora(hora_1),f.hora(hora_2))>0)return true;
		else
		return false; 	
		
	}
	
	
	
public boolean existe_horario2(String codprofesor,String dia){
		
		
		String sql="SELECT count(*) FROM horario WHERE codprofesor=? and dia=? ";
		
		if(db.queryForObject(sql, Integer.class,codprofesor,dia)>0)return true;
		
		return false; 	
		
	}
}