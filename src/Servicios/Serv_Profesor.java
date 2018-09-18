package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Profesor;
import Modelos.Rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Profesor extends DB {
	
	@Autowired
	private Serv_Usuario serv_Usuario;
	
	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Profesor>{
		@Override
		public Profesor mapRow(ResultSet rs, int rows) throws SQLException {
			Profesor us = new Profesor();
			
			us.setCodprofesor(rs.getString("codprofesor"));			
			us.setCodespecialidad(rs.getString("codespecialidad"));
			us.setRud(rs.getString("rud"));
			us.setCodusuario(rs.getString("codusuario"));
			us.setLugar_nac(rs.getString("lugar_nac"));			
			us.setDireccion(rs.getString("direccion"));			
			us.setEstado(rs.getInt("estado"));
			us.setDistrito(rs.getString("distrito"));
			
			try {
				us.setUsuario(serv_Usuario.obtener_por_Ci(rs.getString("codusuario")));
			} catch (Exception e) {
				us.setUsuario(null);
			}
			
			return us;
		}
	}
	
	public void adicionar(Profesor us){
		us.setCodprofesor(generar_Codigo());
		String sql = "INSERT INTO profesor(codprofesor,codespecialidad,rud,codusuario,lugar_nac,direccion,distrito) VALUES(?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodprofesor(), us.getCodespecialidad(),us.getRud(),us.getCodusuario(),us.getLugar_nac(),us.getDireccion(),us.getDistrito()});
	}
	
public void modificar(Profesor us){
		
		String sql = "UPDATE profesor SET codespecialidad=?,rud=?,codusuario=?,lugar_nac=?,direccion=?,distrito=? WHERE codprofesor=?;";
		db.update(sql, new Object[]{us.getCodespecialidad(),us.getRud(),us.getCodusuario(),us.getLugar_nac(),us.getDireccion(),us.getDistrito(),us.getCodprofesor()});
	}
	
	private String generar_Codigo(){
		int num=db.queryForObject("select count (*) from profesor ", Integer.class);
		return "PROFE-"+num;
	}
	
	public Profesor obtener_por_Codprofesor(String codprofesor){
		String sql = "SELECT * FROM profesor WHERE codprofesor=?";
		return db.queryForObject(sql, new to_Object(),codprofesor);
	}
	
	public Profesor obtener_por_rud(String rud){
		String sql = "SELECT * FROM profesor WHERE rud=?";
		return db.queryForObject(sql, new to_Object(),rud);
	}
	
	
	public List<Profesor> listar_estado( int estado){
		String sql = "SELECT * FROM profesor where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Profesor> listar( ){
		String sql = "SELECT * FROM profesor ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Profesor> listaractivos(){
		String sql = "SELECT * FROM profesor where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Profesor> listainactivos(){
		String sql = "SELECT * FROM profesor where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void borrado_log(String codgrado){
		String sql = "UPDATE profesor SET estado=0 WHERE codprofesor=?;";
		db.update(sql, codgrado);
	}
	
	public void activar(String codgrado){
		String sql = "UPDATE profesor SET estado=1 WHERE codprofesor=?;";
		db.update(sql, codgrado);
	}
	
	public boolean existe_prof(String codgrado){
		String sql="SELECT count(*) FROM profesor WHERE codusuario=?";
		if(db.queryForObject(sql, Integer.class,codgrado)>0)return true;
		return false; 	
		
	}
	
	public boolean existe_prof_RUD(String codgrado){
		String sql="SELECT count(*) FROM profesor WHERE rud=?";
		if(db.queryForObject(sql, Integer.class,codgrado)>0)return true;
		return false; 	
		
	}
	
	public List<Profesor> tutores_de_curso(String codcurso){
		String sql = "SELECT profesor.* FROM profesor INNER JOIN tutoria ON profesor.codprofesor = tutoria.codprofesor WHERE tutoria.codcurso = ?;";
		return db.query(sql, new to_Object(), codcurso);
	}
	
	
	public List<Profesor> tutores_sin_curso(String codcurso){
		String sql = "SELECT profesor.* FROM profesor WHERE profesor.codprofesor NOT IN (SELECT codprofesor FROM tutoria WHERE tutoria.codcurso=?)";
		return db.query(sql, new to_Object(), codcurso);
	}
	
	public void eliminar_usurol(String codcurso){
		String sql = "DELETE FROM tutoria WHERE codcurso=?;";
		db.update(sql, codcurso);
	}
	
}