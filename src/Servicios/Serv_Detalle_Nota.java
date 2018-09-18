
package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Notas;
import Modelos.Detalle_nota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Detalle_Nota extends DB {
	@Autowired
	private Serv_Sub_Detalle serv_Sub_Detalle;

	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Detalle_nota>{
		@Override
		public Detalle_nota mapRow(ResultSet rs, int rows) throws SQLException {
			Detalle_nota us = new Detalle_nota();
			
			us.setCodnota(rs.getString("codnota"));
			us.setCoddetnot(rs.getString("coddetnot"));
			us.setItem(rs.getString("item"));
			us.setEstado(rs.getInt("estado"));
			us.setNota(rs.getInt("nota"));
			
			
			
			try {
				us.setSub_detalle(serv_Sub_Detalle.listar_x_codigo_detalle(rs.getString("coddetnot")));
			} catch (Exception e) {
				us.setSub_detalle(null);
			}
			
			return us;
		}
	}
	
	public String generar_Cod(){
		int num=db.queryForObject("select count (*) from detalle_nota ", Integer.class);
		return "DET-NOT-"+num;
	}
	
	public Detalle_nota obtener_por_nota_y_detalle(String codnota,String coddetnot){
		String sql = "SELECT * FROM detalle_nota WHERE codnota=? and coddetnot=?";
		return db.queryForObject(sql, new to_Object(),codnota,coddetnot);
	}
	
	public List<Detalle_nota> listar_estado( int estado){
		String sql = "SELECT * FROM detalle_nota where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Detalle_nota> listar( ){
		String sql = "SELECT * FROM detalle_nota ;";
		return db.query(sql, new to_Object());
	}
	public List<Detalle_nota> listar_x_codigo_nota(String codnota ){
		String sql = "SELECT * FROM detalle_nota where codnota=?;";
		return db.query(sql, new to_Object(),codnota);
	}
	
	
	
	
	public void adicionar(Detalle_nota us){
		String sql = "INSERT INTO detalle_nota(codnota,coddetnot,item,nota) VALUES(?,?,?,?);";
		db.update(sql, new Object[]{us.getCodnota(), us.getCoddetnot(),us.getItem(),us.getNota()});
	}
	

	
	public void borrado_log(String codnota,String coddetnot){
		String sql = "UPDATE detalle_nota SET estado=0 WHERE codnota=? and coddetnot=?;";
		db.update(sql, codnota,coddetnot);
	}
	
	public void activar(String codnota,String coddetnot){
		String sql = "UPDATE detalle_nota SET estado=1 WHERE codnota=? and coddetnot=?;";
		db.update(sql, codnota,coddetnot);
	}
	
}