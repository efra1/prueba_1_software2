


package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modelos.Sub_Detalle;
import Modelos.Detalle_nota;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;


import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Sub_Detalle extends DB {
	

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Sub_Detalle>{
		@Override
		public Sub_Detalle mapRow(ResultSet rs, int rows) throws SQLException {
			Sub_Detalle us = new Sub_Detalle();
			
			
			us.setCodsubdet(rs.getString("codsubdet"));
			us.setCoddetnot(rs.getString("coddetnot"));
			us.setItem(rs.getString("item"));
			us.setEstado(rs.getInt("estado"));
			us.setNota(rs.getInt("nota"));
			
			return us;
		}
	}
	
	public String generar_Cod(){
		int num=db.queryForObject("select count (*) from sub_detalle ", Integer.class);
		return "SUB-DET-NOT-"+num;
	}
	
	public Sub_Detalle obtener_por_detalle_subdetalle(String coddetnot,String codsubnot){
		String sql = "SELECT * FROM sub_detalle WHERE coddetnot=? and codsubnot=?";
		return db.queryForObject(sql, new to_Object(),coddetnot,codsubnot);
	}
	
	public List<Sub_Detalle> listar_estado( int estado){
		String sql = "SELECT * FROM sub_detalle where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Sub_Detalle> listar( ){
		String sql = "SELECT * FROM sub_detalle ;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Sub_Detalle us){
		String sql = "INSERT INTO sub_detalle(coddetnot,codsubdet,item,nota) VALUES(?,?,?,?);";
		db.update(sql, new Object[]{us.getCoddetnot(),us.getCodsubdet(),us.getItem(),us.getNota()});
	}
		
	public void borrado_log(String coddetnot,String codsubdet){
		String sql = "UPDATE sub_detalle SET estado=0 WHERE coddetnot=? and codsubdet=?;";
		db.update(sql, coddetnot,codsubdet);
	}
	
	public void activar(String coddetnot,String codsubdet){
		String sql = "UPDATE sub_detalle SET estado=1 WHERE coddetnot=? and codsubdet=?;";
		db.update(sql,coddetnot,codsubdet);
	}
	
	
	public List<Sub_Detalle> listar_x_codigo_detalle( String coddetnot){
		String sql = "SELECT * FROM sub_detalle where coddetnot=?;";
		return db.query(sql, new to_Object(),coddetnot);
	}
	
}