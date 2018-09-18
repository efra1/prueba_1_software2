package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;

import Modelos.Datos;


import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;
import coneccion.DB;

@Service
public class Serv_Datos extends DB {
	
	private class to_Object implements ParameterizedRowMapper<Datos>{
		@Override
		public Datos mapRow(ResultSet rs, int rows) throws SQLException {
			Datos dt = new Datos();
			dt.setCi(rs.getString("ci"));
			dt.setLogin(rs.getString("login"));
			dt.setClave(rs.getString("clave"));
			dt.setEstado(rs.getInt("estado"));
			
			dt.setPreg(rs.getString("preg"));
			dt.setResp(rs.getString("resp"));
			return dt;
		}
	}
	
	public Datos obteber_por_Ci(String ci){
		String sql = "SELECT * FROM datos WHERE ci=?;";
		return db.queryForObject(sql, new to_Object(), ci);
	}
	

	
	public void adicionar_dato(Datos da){
		
		String sql="INSERT INTO datos(ci, login, clave,preg,resp) VALUES(?,?,?,?,?)";
		try {
			encriptar e=new encriptar();
			
			da.setClave(e.Encriptar(da.getClave()));
			db.update(sql,da.getCi(),da.getLogin(),da.getClave(),da.getPreg(),da.getResp());
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		
	}
	
	public void modificar_dato(Datos dt){
		encriptar e=new encriptar();
		dt.setClave(e.Encriptar(dt.getClave()));
		
			String sql = "UPDATE datos SET login=?, clave=?, estado=1,preg=?,resp=? WHERE ci=?;";
			
			
			
			db.update(sql, new Object[]{dt.getLogin(), dt.getClave(),dt.getPreg(),dt.getResp(), dt.getCi()});
		}
	
	
	
	
	
	
	
	
	public void borrado_log(String ci){
		String sql = "UPDATE datos SET estado=0 WHERE ci=?;";
		db.update(sql, ci);
	}
	public void activar(String ci){
		String sql = "UPDATE datos SET estado=1 WHERE ci=?;";
		db.update(sql, ci);
	}
}