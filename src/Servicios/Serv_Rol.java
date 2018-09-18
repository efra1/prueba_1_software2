package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modelos.Rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import coneccion.DB;

@Service
@SuppressWarnings("deprecation")
public class Serv_Rol extends DB {
	
	@Autowired
	private Serv_Proceso serv_Proceso;
	
	private class to_Object implements ParameterizedRowMapper<Rol>{
		@Override
		public Rol mapRow(ResultSet rs, int rows) throws SQLException {
			Rol rol = new Rol();
			rol.setCodr(rs.getInt("codr"));
			rol.setNombre(rs.getString("nombre"));
			rol.setDescrip(rs.getString("descrip"));
		
			rol.setEstado(rs.getInt("estado"));
			try {
				rol.setProcesos(serv_Proceso.procesos_de_Rol(rs.getInt("codr")));
			} catch (Exception e) {
				rol.setProcesos(null);
			}
			return rol;
		}
	}
	
	private Integer generar_Codr(){
		try {
			String sql = "SELECT MAX(codr) FROM rol;";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Rol obtener_por_Codr(Integer codr){
		String sql = "SELECT * FROM rol WHERE codr=?;";
		return db.queryForObject(sql, new to_Object(), codr);
	}
	
	public List<Rol> listar(){
		String sql = "SELECT * FROM rol;";
		return db.query(sql, new to_Object());
	}
	public List<Rol> listar_estado(int est){
		String sql = "SELECT * FROM rol where estado=?;";
		return db.query(sql, new to_Object(),est);
	}
	
	
	public List<Rol> roles_de_Usuario(String ci){
		String sql = "SELECT rol.* FROM rol INNER JOIN usurol ON rol.codr = usurol.codr WHERE usurol.ci = ?;";
		return db.query(sql, new to_Object(), ci);
	}
	
	public List<Rol> roles_sin_asignar_a_Usuario(String ci){
		String sql = "SELECT rol.* FROM rol WHERE rol.codr NOT IN (SELECT codr FROM usurol WHERE usurol.ci=?);";
		return db.query(sql, new to_Object(), ci);
	}
	
	public void asignar_procesos_a_Rol(Integer codr, Integer codp){
		String sql = "INSERT INTO rolpro(codr, codp) VALUES(?,?);";
		db.update(sql, new Object[]{codr, codp});
	}
	
	public void adicionar(Rol rol){
		rol.setCodr(generar_Codr());
		String sql = "INSERT INTO rol(codr, nombre, descrip) VALUES(?,?,?);";
		db.update(sql, new Object[]{rol.getCodr(), rol.getNombre(), rol.getDescrip()});
	}
	
	public void modificar(Rol rol){
		String sql = "UPDATE rol SET nombre=?, descrip=?, estado=1 WHERE codr=?;";
		db.update(sql, new Object[]{rol.getNombre(), rol.getDescrip(), rol.getCodr()});
	}
	
	public void eliminar(Integer codr){
		eliminar_de_rolpro(codr);
		eliminar_de_usurol(codr);
		String sql = "DELETE FROM rol WHERE codr=?;";
		db.update(sql, codr);
	}
	
	public void eliminar_de_usurol(Integer codr){
		String sql = "DELETE FROM usurol WHERE codr=?;";
		db.update(sql, codr);
	}
	
	public void eliminar_de_rolpro(Integer codr){
		String sql = "DELETE FROM rolpro WHERE codr=?;";
		db.update(sql, codr);
	}
}