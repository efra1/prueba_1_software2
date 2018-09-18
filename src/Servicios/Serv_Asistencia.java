package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Asistencia;
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
public class Serv_Asistencia extends DB {
	
	
	fechas conv = new fechas();

	fecha_hora f = new fecha_hora();
	private class to_Object implements ParameterizedRowMapper<Asistencia>{
		@Override
		public Asistencia mapRow(ResultSet rs, int rows) throws SQLException {
			Asistencia us = new Asistencia();
			
			
			us.setCodasistencia(rs.getString("codasistencia"));
			us.setCodalumno(rs.getString("codalumno"));
			us.setCodclase(rs.getString("codclase"));
			us.setFecha(rs.getString("fecha"));
			us.setAsistencia(rs.getString("asistencia"));
			us.setEstado(rs.getInt("estado"));
							
			return us;
		}
	}
	
	public void adicionar(Asistencia us){
		
		
		String sql = "INSERT INTO asistencia(codasistencia,codalumno,codclase,fecha,asistencia) VALUES(?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodasistencia(),us.getCodalumno(),us.getCodclase(),f.fecha(us.getFecha()),us.getAsistencia()});
	}
	
	public String generar_Codigo(){
		int num=db.queryForObject("select count (*) from asistencia ", Integer.class);
		return "ASST-"+num;
	}
	
	
	public List<Asistencia> listar_estado( int estado){
		String sql = "SELECT * FROM asistencia where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Asistencia> listar( ){
		String sql = "SELECT * FROM asistencia ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Asistencia> listaractivos(){
		String sql = "SELECT * FROM asistencia where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Asistencia> listainactivos(){
		String sql = "SELECT * FROM asistencia where estado=0;";
		return db.query(sql, new to_Object());
	}
	public List<Asistencia> listar_asistencia_alumno_periodo(String codalumno,String codclase,String fini,String ffin){
		String sql = "select * FROM asistencia   WHERE codalumno=? and codclase=?  and fecha BETWEEN ?  and ?;";
		return db.query(sql, new to_Object(),codalumno,codclase, f.fecha(fini),f.fecha(ffin));
	}
	
	public int obtenertotal_asistenci( String codalumno,String codclase,String fini,String ffin){
		return db.queryForObject("select count(fecha) FROM asistencia   WHERE codalumno=? and codclase=?  and fecha BETWEEN ?  and ? ", Integer.class,codalumno,codclase, f.fecha(fini),f.fecha(ffin));
	}
	
	public int obtenerpresentes( String codalumno,String codclase,String fini,String ffin){
		return db.queryForObject("select count(fecha) FROM asistencia   WHERE codalumno=? and codclase=? and asistencia='P' and fecha BETWEEN ?  and ? ", Integer.class,codalumno,codclase, f.fecha(fini),f.fecha(ffin));
	}
	public int obtenerfaltas( String codalumno,String codclase,String fini,String ffin){
		return db.queryForObject("select count(fecha) FROM asistencia   WHERE codalumno=? and codclase=? and asistencia='F' and fecha BETWEEN ?  and ? ", Integer.class,codalumno,codclase, f.fecha(fini),f.fecha(ffin));
	}
	
	public int obtenerretrasos( String codalumno,String codclase,String fini,String ffin){
		return db.queryForObject("select count(fecha) FROM asistencia   WHERE codalumno=? and codclase=? and asistencia='R' and fecha BETWEEN ?  and ? ", Integer.class,codalumno,codclase, f.fecha(fini),f.fecha(ffin));
	}
	public int obtenerpersmisos( String codalumno,String codclase,String fini,String ffin){
		return db.queryForObject("select count(fecha) FROM asistencia   WHERE codalumno=? and codclase=? and asistencia='C' and fecha BETWEEN ?  and ? ", Integer.class,codalumno,codclase, f.fecha(fini),f.fecha(ffin));
	}
	
	public void borrado_log(String codgestion){
		String sql = "UPDATE asistencia SET estado=0 WHERE codasistencia=?;";
		db.update(sql, codgestion);
	}
	
	public void activar(String codgestion){
		String sql = "UPDATE asistencia SET estado=1 WHERE codasistencia=?;";
		db.update(sql, codgestion);
	}
	
	

	
}