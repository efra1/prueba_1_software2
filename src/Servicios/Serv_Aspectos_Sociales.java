package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Apoderado;
import Modelos.Aspectos_sociales;
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
public class Serv_Aspectos_Sociales extends DB {
	

	fechas conv = new fechas();
	fecha_hora f =new fecha_hora();

	private class to_Object implements ParameterizedRowMapper<Aspectos_sociales>{
		@Override
		public Aspectos_sociales mapRow(ResultSet rs, int rows) throws SQLException {
			Aspectos_sociales us = new Aspectos_sociales();
			
			
			
			us.setCodaspectos(rs.getString("codaspectos"));
			us.setLengua_materna(rs.getString("lengua_materna"));
			us.setEtimologia(rs.getString("etimologia"));
			us.setSeguro(rs.getString("seguro"));
			
			us.setLugar_seguro(rs.getString("lugar_seguro"));
			us.setGrupo_sang(rs.getString("grupo_sang"));
			us.setDiscapacidad(rs.getString("discapacidad"));
			us.setTipo_discapacidad(rs.getString("tipo_discapacidad"));			
			us.setTrabaja(rs.getString("trabaja"));
			us.setTransporte(rs.getString("transporte"));			
			us.setDistancia(rs.getString("distancia"));			
			us.setTiempo(rs.getString("tiempo"));
			us.setCodalumno(rs.getString("codalumno"));
			
			
			
			
			
			
			
			return us;
		}
	}
	
	public String generar_Cod(){
		int num=db.queryForObject("select count (*) from aspectos_sociales ", Integer.class);
		return "ASPSOC0001-"+num;
	}
	

	
	public Aspectos_sociales obtener_por_alumono(String codalumno){
		String sql = "SELECT * FROM aspectos_sociales WHERE codalumno=?";
		return db.queryForObject(sql, new to_Object(),codalumno);
	}
	
	public void adicionar(Aspectos_sociales us){
		
		String sql = "INSERT INTO aspectos_sociales(codaspectos,lengua_materna,etimologia,seguro,lugar_seguro,grupo_sang,discapacidad,tipo_discapacidad,trabaja,transporte,distancia,tiempo,codalumno) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodaspectos(),us.getLengua_materna(),us.getEtimologia(),us.getSeguro(),us.getLugar_seguro(),us.getGrupo_sang(),us.getDiscapacidad(),us.getTipo_discapacidad(),us.getTrabaja(),us.getTransporte(),us.getDistancia(),us.getTiempo(),us.getCodalumno()});
	}
	
	
public void  modificar(Aspectos_sociales us){
		
		String sql = "UPDATE aspectos_sociales SET lengua_materna =?,etimologia=?,seguro=?,lugar_seguro=?,grupo_sang=?,discapacidad=?,tipo_discapacidad=?,trabaja=?,transporte=?,distancia=?,tiempo=?,codalumno=? WHERE codaspectos=?;";
		db.update(sql, new Object[]{us.getLengua_materna(),us.getEtimologia(),us.getSeguro(),us.getLugar_seguro(),us.getGrupo_sang(),us.getDiscapacidad(),us.getTipo_discapacidad(),us.getTrabaja(),us.getTransporte(),us.getDistancia(),us.getTiempo(),us.getCodalumno(),us.getCodaspectos()});
	}
	
	
}