package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Apoderado;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Apoderado extends DB {
	
	
	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Apoderado>{
		@Override
		public Apoderado mapRow(ResultSet rs, int rows) throws SQLException {
			Apoderado us = new Apoderado();
			
			us.setCodapoderado(rs.getString("codapoderado"));			
			us.setIdioma(rs.getString("idioma"));
			us.setOcupacion(rs.getString("ocupacion"));
			us.setGradoins(rs.getString("gradoins"));
			us.setParentesco(rs.getString("parentesco"));			
			us.setTelefono_apo(rs.getString("telefono_apo"));	
			us.setNombre_apo(rs.getString("nombre_apo"));	
			us.setCi_apo(rs.getString("ci_apo"));	
			us.setAp_apo(rs.getString("ap_apo"));	
			
			us.setAm_apo(rs.getString("am_apo"));	
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	public void adicionar(Apoderado us){
		String sql = "INSERT INTO apoderado(codapoderado,idioma,ocupacion,gradoins,parentesco,telefono_apo,nombre_apo,ci_apo,ap_apo,am_apo) VALUES(?,?,?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodapoderado(), us.getIdioma(),us.getOcupacion(),us.getGradoins(),us.getParentesco(),us.getTelefono_apo(),us.getNombre_apo(),us.getCi_apo(),us.getAp_apo(),us.getAm_apo()});
	}
	
public void modificar(Apoderado us){
		
		String sql = "UPDATE apoderado SET idioma=?,ocupacion=?,gradoins=?,parentesco=?,telefono_apo=?,nombre_apo=?,ci_apo=?,ap_apo=?,am_apo=? WHERE codapoderado=?;";
		db.update(sql, new Object[]{ us.getIdioma(),us.getOcupacion(),us.getGradoins(),us.getParentesco(),us.getTelefono_apo(),us.getNombre_apo(),us.getCi_apo(),us.getAp_apo(),us.getAm_apo(),us.getCodapoderado()});
	}
	
public String generar_Codigo(){
		int num=db.queryForObject("select count (*) from apoderado ", Integer.class);
		return "APODE-"+num;
	}
	
	public Apoderado obtener_por_Codprofesor(String codapoderado){
		String sql = "SELECT * FROM apoderado WHERE codapoderado=?";
		return db.queryForObject(sql, new to_Object(),codapoderado);
	}
	
	public List<Apoderado> listar_estado( int estado){
		String sql = "SELECT * FROM apoderado where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Apoderado> listar( ){
		String sql = "SELECT * FROM apoderado ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Apoderado> listaractivos(){
		String sql = "SELECT * FROM apoderado where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Apoderado> listainactivos(){
		String sql = "SELECT * FROM apoderado where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void borrado_log(String codapoderado){
		String sql = "UPDATE apoderado SET estado=0 WHERE codapoderado=?;";
		db.update(sql, codapoderado);
	}
	
	public void activar(String codapoderado){
		String sql = "UPDATE apoderado SET estado=1 WHERE codapoderado=?;";
		db.update(sql, codapoderado);
	}
	
	public boolean existe_grado(String codapoderado){
		String sql="SELECT count(*) FROM apoderado WHERE codapoderado=?";
		if(db.queryForObject(sql, Integer.class,codapoderado)>0)return true;
		return false; 	
		
	}
	

	
}