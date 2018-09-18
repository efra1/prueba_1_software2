package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Turno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Turno extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Turno>{
		@Override
		public Turno mapRow(ResultSet rs, int rows) throws SQLException {
			Turno us = new Turno();
			
			us.setCodturno(rs.getString("codturno"));
			us.setNombre(rs.getString("nombre"));
		
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from turno ", Integer.class);
		return "TRN0001-"+num;
	}
	
	public Turno obtener_por_Codturno(String codturno){
		String sql = "SELECT * FROM turno WHERE codturno=?";
		return db.queryForObject(sql, new to_Object(),codturno);
	}
	
	public List<Turno> listar_estado( int estado){
		String sql = "SELECT * FROM turno where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Turno> listar( ){
		String sql = "SELECT * FROM turno ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Turno> listaractivos(){
		String sql = "SELECT * FROM turno where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Turno> listainactivos(){
		String sql = "SELECT * FROM turno where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Turno us){
		us.setCodturno(generar_Cod());
		String sql = "INSERT INTO turno(codturno,nombre) VALUES(?,?);";
		db.update(sql, new Object[]{us.getCodturno(), us.getNombre()});
	}
	
	public void modificar(Turno us){
		
		String sql = "UPDATE turno SET nombre=? WHERE codturno=?;";
		db.update(sql, new Object[]{us.getNombre(),us.getCodturno()});
	}
	
	public void borrado_log(String codturno){
		String sql = "UPDATE turno SET estado=0 WHERE codturno=?;";
		db.update(sql, codturno);
	}
	
	public void activar(String codturno){
		String sql = "UPDATE turno SET estado=1 WHERE codturno=?;";
		db.update(sql, codturno);
	}
	
	public boolean existe_turno(String codturno){
		String sql="SELECT count(*) FROM turno WHERE codturno=?";
		if(db.queryForObject(sql, Integer.class,codturno)>0)return true;
		return false; 	
		
	}
	

	
}