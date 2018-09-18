
package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Item;
import Modelos.Items;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Item extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Items>{
		@Override
		public Items mapRow(ResultSet rs, int rows) throws SQLException {
			Items us = new Items();
			
			us.setCodigo(rs.getString("codigo"));
			us.setNombre(rs.getString("nombre"));
			us.setTipo(rs.getString("tipo"));
			us.setEstado(rs.getInt("estado"));
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from items ", Integer.class);
		return "ITM-"+num;
	}
	
	
	
	public List<Items> listar_estado( int estado){
		String sql = "SELECT * FROM items where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Items> listar( ){
		String sql = "SELECT * FROM items ;";
		return db.query(sql, new to_Object());
	}
	
	

	
}