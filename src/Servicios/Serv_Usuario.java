package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import Modelos.Datos;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fecha_hora;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Usuario extends DB {
	
	@Autowired
	private Serv_Datos serv_Datos;
	@Autowired
	private Serv_Rol serv_Rol;
	
	fecha_hora f =new fecha_hora();
	
	
	private class to_Object implements ParameterizedRowMapper<Usuario>{
		@Override
		public Usuario mapRow(ResultSet rs, int rows) throws SQLException {
			Usuario us = new Usuario();
			us.setCi(rs.getString("ci"));
			us.setNombre(rs.getString("nombre"));
			us.setAp(rs.getString("ap"));
			us.setAm(rs.getString("am"));
			us.setSexo(rs.getString("sexo"));
			us.setFechanac(rs.getDate("fechanac").toString());
			us.setEcivil(rs.getString("ecivil"));
			us.setFoto(rs.getString("foto"));
			
			if (us.getFoto() == null) us.setFoto("usuario.png");
			
			us.setEmail(rs.getString("email"));
			us.setEstado(rs.getInt("estado"));
			us.setTelefono(rs.getString("telefono"));
			try {
				us.setDatos(serv_Datos.obteber_por_Ci(rs.getString("ci")));
			} catch (Exception e) {
				us.setDatos(null);
			}
			try {
				us.setRoles(serv_Rol.roles_de_Usuario(rs.getString("ci")));
			} catch (Exception e) {
				us.setRoles(null);
			}
			return us;
		}
	}
	
	public Usuario obtener_por_Ci(String ci){
		String sql = "SELECT * FROM usuario WHERE ci=?;";
		return db.queryForObject(sql, new to_Object(), ci);
	}
	
	public List<Usuario> listar_estado( int estado){
		String sql = "SELECT * FROM usuario where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Usuario> listar( ){
		String sql = "SELECT * FROM usuario ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Usuario> listaractivos(){
		String sql = "SELECT * FROM usuario where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Usuario> listainactivos(){
		String sql = "SELECT * FROM usuario where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	
	
	public Usuario iniciar_sesion(Datos dt){
		try {
			encriptar e=new encriptar();
			String log=dt.getLogin();
			String cla=e.Encriptar(dt.getClave());
			
			
			String sql = "SELECT usuario.* FROM usuario INNER JOIN datos ON usuario.ci = datos.ci " +
				"WHERE datos.login = ? AND datos.clave = ?;";
		return db.queryForObject(sql, new to_Object(), new Object[]{log,cla});
	} catch (Exception e) {
		System.out.println(e.toString());
		return null;
	}
	}
	
	

	public Usuario iniciar_secion_con_pregunta(Datos dt){
		try {
			
			String preg=dt.getPreg();
			String resp=dt.getResp();
			
			
			String sql = "SELECT usuario.* FROM usuario INNER JOIN datos ON usuario.ci = datos.ci " +
				"WHERE datos.preg = ? AND datos.resp = ?;";
		return db.queryForObject(sql, new to_Object(), new Object[]{preg,resp});
	} catch (Exception e) {
		System.out.println(e.toString());
		return null;
	}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public void adicionar(Usuario us){
		
		String sql = "INSERT INTO usuario(ci, nombre, ap, am, sexo, fechanac, ecivil, foto, email, telefono,fotito) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCi(), us.getNombre(), us.getAp(), us.getAm(), us.getSexo(), f.fecha(us.getFechanac()), us.getEcivil(), us.getFoto(), us.getEmail(), us.getTelefono(),us.getFotito()});
	}
	
	public void modificar(Usuario us){
		
		String sql = "UPDATE usuario SET nombre=?, ap=?, am=?, sexo=?, fechanac=?, ecivil=?, foto=?, email=?, telefono=?,fotito=?, estado=1 WHERE ci=?;";
		db.update(sql, new Object[]{us.getNombre(), us.getAp(), us.getAm(), us.getSexo(), f.fecha(us.getFechanac()), us.getEcivil(), us.getFoto(), us.getEmail(), us.getTelefono(),us.getFotito(), us.getCi()});
	}
	
	public void borrado_log(String ci){
		serv_Datos.borrado_log(ci);
		String sql = "UPDATE usuario SET estado=0 WHERE ci=?;";
		db.update(sql, ci);
	}
	
	public void activar(String ci){
		serv_Datos.activar(ci);
		String sql = "UPDATE usuario SET estado=1 WHERE ci=?;";
		db.update(sql, ci);
	}
	
	
	
	public void asignar_roles_a_Usuario(String ci, Integer codr){
		String sql = "INSERT INTO usurol(ci, codr) VALUES(?,?);";
		db.update(sql, new Object[]{ci, codr});
	}
	
	public void eliminar_usurol(String ci){
		String sql = "DELETE FROM usurol WHERE ci=?;";
		db.update(sql, ci);
	}
	
	public boolean existe_usuario(String ci){
		
		
		
		String sql="SELECT count(*) FROM usuario WHERE ci=?";
		
		if(db.queryForObject(sql, Integer.class,ci)>0)return true;
		
		return false; 	
		
	}
	public Map<String, Object> obtener(String ci){
		try {
			return db.queryForMap("select * from usuario where ci=?",ci);
		} catch (Exception e) {
			System.out.println("error obtenerUsuario="+e.toString());
			return null;
		}
	}

	
}