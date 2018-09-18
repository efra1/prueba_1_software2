
package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Direccion;
import Modelos.Especialidad;
import Modelos.Horario;
import Modelos.Item;
import Modelos.Items;
import Modelos.Opciones;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Opciones extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Opciones>{
		@Override
		public Opciones mapRow(ResultSet rs, int rows) throws SQLException {
			Opciones us = new Opciones();
			
			us.setCodopcion(rs.getString("codopcion"));
			us.setCodaspectos(rs.getString("codaspectos"));
			us.setTipo(rs.getString("tipo"));
			us.setOpcion(rs.getString("opcion"));
			
			return us;
		}
	}
	
	public String generar_Cod(){
		int num=db.queryForObject("select count (*) from opciones ", Integer.class);
		return "OPCION-"+num;
	}
	
	public void adicionar(String codopcion,String codaspectos,String tipo,String opcion){
		String sql = "INSERT INTO opciones(codopcion,codaspectos,tipo,opcion) VALUES(?,?,?,?);";
		db.update(sql, new Object[]{codopcion,codaspectos,tipo,opcion});
	}
	
	
	public List<Opciones> listar_por_aspecto( String codaspectos ,String tipo){
		String sql = "SELECT * FROM opciones WHERE codaspectos=? and tipo=?;";
		return db.query(sql, new to_Object(),codaspectos,tipo);
	}
	public List<Opciones> listar( ){
		String sql = "SELECT * FROM opciones ;";
		return db.query(sql, new to_Object());
	}
	
	
	
	public void eliminar_de_opciones(String codaspectos){
		String sql = "DELETE FROM opciones WHERE codaspectos=? ;";
		db.update(sql, codaspectos);
	}
	
	
}