package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Notas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Notas extends DB {
	
	@Autowired
	private Serv_Detalle_Nota serv_Detalle_Nota;
	
	@Autowired
	private Serv_Periodo serv_Periodo;
	fechas conv = new fechas();

	private class to_Object implements ParameterizedRowMapper<Notas>{
		@Override
		public Notas mapRow(ResultSet rs, int rows) throws SQLException {
			Notas us = new Notas();
			
			
			us.setCodnota(rs.getString("codnota"));
			us.setNombre(rs.getString("nombre"));
			us.setCodalumno(rs.getString("codalumno"));
			us.setCodclase(rs.getString("codclase"));
			us.setCodperiodo(rs.getString("codperiodo"));
			us.setEstado(rs.getInt("estado"));
			us.setNota(rs.getInt("nota"));
			
			
			
			try {
				us.setDetalle(serv_Detalle_Nota.listar_x_codigo_nota(rs.getString("codnota")));
			} catch (Exception e) {
				us.setDetalle(null);
			}
			
			
			try {
				us.setPeriodo(serv_Periodo.obtener_por_Codperiodo(rs.getString("codperiodo")));
			} catch (Exception e) {
				us.setPeriodo(null);
			}
			
			return us;
		}
	}
	
	public String generar_Cod(){
		int num=db.queryForObject("select count (*) from notas ", Integer.class);
		return "NTA0001-"+num;
	}
	
	public Notas obtener_por_codnivel(String codnota){
		String sql = "SELECT * FROM notas WHERE codnota=?";
		return db.queryForObject(sql, new to_Object(),codnota);
	}
	
	public List<Notas> listar_estado( int estado){
		String sql = "SELECT * FROM notas where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Notas> listar( ){
		String sql = "SELECT * FROM notas ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Notas> listaractivos(){
		String sql = "SELECT * FROM notas where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Notas> listainactivos(){
		String sql = "SELECT * FROM notas where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Notas us){
		String sql = "INSERT INTO notas(codnota,nombre,codalumno,codclase,codperiodo,nota) VALUES(?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodnota(), us.getNombre(),us.getCodalumno(),us.getCodclase(),us.getCodperiodo(),us.getNota()});
	}
	
	public void modificar(Notas us){
		
		String sql = "UPDATE notas SET nombre=?,codalumno=?,codclase=?,codperiodo=?,nota=? WHERE codnota=?;";
		db.update(sql, new Object[]{ us.getNombre(),us.getCodalumno(),us.getCodclase(),us.getCodperiodo(),us.getNota(),us.getCodnota()});
	}
	
	public void borrado_log(String codnota){
		String sql = "UPDATE notas SET estado=0 WHERE codnota=?;";
		db.update(sql, codnota);
	}
	
	public void activar(String codnota){
		String sql = "UPDATE notas SET estado=1 WHERE codnota=?;";
		db.update(sql, codnota);
	}
	
	public boolean existe_nivel(String codnota){
		String sql="SELECT count(*) FROM notas WHERE codnota=?";
		if(db.queryForObject(sql, Integer.class,codnota)>0)return true;
		return false; 	
		
	}
	
	
	
	public List<Notas> listar_nota(String codalumno, String codclase, String codparalelo){
		String sql = "SELECT * from notas WHERE codalumno=? and codclase=? and codperiodo=?";
		return db.query(sql, new to_Object(),codalumno,codclase,codparalelo);
	}
	
	public List<Notas> listar_nota(String codalumno, String codclase){
		String sql = "SELECT * from notas WHERE codalumno=? and codclase=? ";
		return db.query(sql, new to_Object(),codalumno,codclase);
	}
	
}