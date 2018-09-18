package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Nivel;
import Modelos.Clase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Clase extends DB {
	
	@Autowired
	private Serv_Materia serv_Materia;
	@Autowired
	private Serv_Profesor serv_Profesor;
	@Autowired
	private Serv_Horario serv_Horario;

	@Autowired
	private Serv_Curso serv_Curso;
	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Clase>{
		@Override
		public Clase mapRow(ResultSet rs, int rows) throws SQLException {
			Clase us = new Clase();
			
			us.setCodclase(rs.getString("codclase"));
			us.setCodmateria(rs.getString("codmateria"));
			us.setCodprofesor(rs.getString("codprofesor"));
			us.setCodcurso(rs.getString("codcurso"));			
			us.setNombre(rs.getString("nombre"));		
			us.setEstado(rs.getInt("estado"));
			

			try {
				us.setCurso(serv_Curso.obtener_por_Codcurso(rs.getString("codcurso")));
			} catch (Exception e) {
				us.setCurso(null);
			}
			
			return us;
		}
	}
	public void adicionar(Clase us){
		us.setCodclase(generar_Codigo());
		String sql = "INSERT INTO clase(codclase,codmateria,codprofesor,codcurso,nombre) VALUES(?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodclase(),us.getCodmateria(),us.getCodprofesor(),us.getCodcurso(),us.getNombre()});
	}
	
	private String generar_Codigo(){
		int num=db.queryForObject("select count (*) from clase ", Integer.class);
		return "CLAC0001-"+num;
	}
	
	public Clase obtener_por_Codclase(String codclase){
		String sql = "SELECT * FROM clase WHERE codclase=?";
		return db.queryForObject(sql, new to_Object(),codclase);
	}
	

	public List<Clase> listar(){
		String sql = "SELECT * FROM clase ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Clase> listar_estado( int estado){
		String sql = "SELECT * FROM clase where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	
	public List<Clase> listar_clases_de_profesor( String codprofesor){
		String sql = "SELECT * FROM clase where codprofesor=?;";
		return db.query(sql, new to_Object(),codprofesor);
	}

	public List<Clase> listaractivos(){
		String sql = "SELECT * FROM clase where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Clase> listainactivos(){
		String sql = "SELECT * FROM clase where estado=0;";
		return db.query(sql, new to_Object());
	}
	public void modificar(Clase us){
		
		String sql = "UPDATE clase SET nombre=?,codmateria=?,codprofesor=?,codcurso=? WHERE codclase=?;";
		db.update(sql, new Object[]{us.getNombre(),us.getCodmateria(),us.getCodprofesor(),us.getCodcurso(),us.getCodclase()});
	}
	
	public void borrado_log(String codclase){
		String sql = "UPDATE clase SET estado=0 WHERE codclase=?;";
		db.update(sql, codclase);
	}
	
	public void activar(String codclase){
		String sql = "UPDATE clase SET estado=1 WHERE codclase=?;";
		db.update(sql, codclase);
	}
	
	public boolean existe_clase(String codclase){
		String sql="SELECT count(*) FROM clase WHERE codclase=?";
		if(db.queryForObject(sql, Integer.class,codclase)>0)return true;
		return false; 	
		
	}
	

	
}