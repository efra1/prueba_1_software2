package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modelos.Curso;
import Modelos.Detalle;
import Modelos.Notas;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;
import coneccion.DB;

@Service
public class Serv_Detalle extends DB {
	
	private class to_Object implements ParameterizedRowMapper<Detalle>{
		@Override
		public Detalle mapRow(ResultSet rs, int rows) throws SQLException {
			Detalle dt = new Detalle();
			dt.setCoddetalle(rs.getString("coddetalle"));
			dt.setCodnota(rs.getString("codnota"));
			dt.setSer(rs.getInt("ser"));
			dt.setSaber(rs.getInt("saber"));
			dt.setHacer(rs.getInt("hacer"));
			dt.setDecidir(rs.getInt("decidir"));
			dt.setPromedio(rs.getInt("promedio"));
			dt.setEstado(rs.getInt("estado"));
			return dt;
		}
	}
	
	
	
	public String generar_Codigo(){
		int num=db.queryForObject("select count (*) from detalle ", Integer.class);
		return "DETNOT0001-"+num;
	}
	
	public Detalle obtener_por_Coddetalle(String coddetalle){
		String sql = "SELECT * FROM detalle WHERE coddetalle=?";
		return db.queryForObject(sql, new to_Object(),coddetalle);
	}
	
	public List<Detalle> listar_estado( int estado){
		String sql = "SELECT * FROM detalle where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Detalle> listar( ){
		String sql = "SELECT * FROM detalle ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Detalle> listaractivos(){
		String sql = "SELECT * FROM detalle where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Detalle> listainactivos(){
		String sql = "SELECT * FROM detalle where estado=0;";
		return db.query(sql, new to_Object());
	}
	public void adicionar(Detalle us){
		String sql = "INSERT INTO detalle(coddetalle,codnota,nombre,ser,saber,decidir,hacer,promedio) VALUES(?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCoddetalle(),us.getCodnota(), us.getNombre(),us.getSer(),us.getSaber(),us.getDecidir(),us.getHacer(),us.getPromedio()});
	}
	
}