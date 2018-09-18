package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Matricula;
import Modelos.Direccion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fecha_hora;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Direccion extends DB {
	

	fechas conv = new fechas();
	fecha_hora f =new fecha_hora();

	private class to_Object implements ParameterizedRowMapper<Direccion>{
		@Override
		public Direccion mapRow(ResultSet rs, int rows) throws SQLException {
			Direccion us = new Direccion();
			
			us.setCoddireccion(rs.getString("coddireccion"));
			
			us.setCodalumno(rs.getString("codalumno"));
			us.setDepartamento(rs.getString("departamento"));
			
			us.setProvincia_2(rs.getString("provincia_2"));
			us.setSeccion(rs.getString("seccion"));
			us.setCanton(rs.getString("canton"));
			us.setLocalidad_2(rs.getString("localidad_2"));
			us.setZona(rs.getString("zona"));
			us.setAvenida(rs.getString("avenida"));
			us.setNumero(rs.getString("numero"));
			us.setTelefono(rs.getString("telefono"));
			us.setEstado(rs.getBoolean("estado"));
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from direccion ", Integer.class);
		return "DIRECC0001-"+num;
	}
	
	
	
	public Direccion obtener_por_alumono(String codalumno){
		String sql = "SELECT * FROM direccion WHERE codalumno=?";
		return db.queryForObject(sql, new to_Object(),codalumno);
	}
	
	public void adicionar(Direccion us){
		us.setCoddireccion(generar_Cod());
		String sql = "INSERT INTO direccion(coddireccion,codalumno,departamento,provincia_2,seccion,canton,localidad_2,zona,avenida,numero,telefono) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCoddireccion(),us.getCodalumno(),us.getDepartamento(), us.getProvincia_2(),us.getSeccion(),us.getCanton(),us.getLocalidad_2(),us.getZona(),us.getAvenida(),us.getNumero(),us.getTelefono()});
	}
	
	
	
	public void modificar(Direccion us){
		String sql = "update  direccion set codalumno=?,departamento=?,provincia_2=?,seccion=?,canton=?,localidad_2=?,zona=?,avenida=?,numero=?,telefono=? where coddireccion=?";
		db.update(sql, new Object[]{us.getCodalumno(),us.getDepartamento(), us.getProvincia_2(),us.getSeccion(),us.getCanton(),us.getLocalidad_2(),us.getZona(),us.getAvenida(),us.getNumero(),us.getTelefono(),us.getCoddireccion()});
	}
	
	
	public void borrado_log(String codmatricula){
		String sql = "UPDATE direccion SET estado=false WHERE coddireccion=?;";
		db.update(sql, codmatricula);
	}
	
	public void activar(String codmatricula){
		String sql = "UPDATE matricula SET estado=true WHERE coddireccion=?;";
		db.update(sql, codmatricula);
	}
	
	
	

	
}