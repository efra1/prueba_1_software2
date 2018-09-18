package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Nivel;
import Modelos.Alumno;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fecha_hora;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Alumno extends DB {
	

fecha_hora f=new fecha_hora();
	private class to_Object implements ParameterizedRowMapper<Alumno>{
		@Override
		public Alumno mapRow(ResultSet rs, int rows) throws SQLException {
			Alumno us = new Alumno();
			
			us.setCodalumno(rs.getString("codalumno"));
			us.setCelular(rs.getString("celular"));
			us.setFechanac(rs.getString("fechanac"));
			us.setObservacion(rs.getString("observacion"));
			us.setEstado(rs.getInt("estado"));
			us.setRuestu(rs.getString("ruestu"));
			if (us.getFoto() == null) us.setFoto("usuario.png");
			us.setNombre(rs.getString("nombre"));
			us.setAp(rs.getString("ap"));
			us.setAm(rs.getString("am"));
			us.setCi_alum(rs.getString("ci_alum"));
			
			
			
			
			us.setTipo_doc(rs.getString("tipo_doc"));
			us.setPais(rs.getString("pais"));
			us.setDpto(rs.getString("dpto"));
			us.setProvincia(rs.getString("provincia"));
			us.setLocalidad(rs.getString("localidad"));
			us.setOficialia(rs.getString("oficialia"));
			us.setLibro(rs.getString("libro"));
			us.setPartida(rs.getString("partida"));
			
			us.setFolio(rs.getString("folio"));
			us.setSexo(rs.getString("sexo"));
				
			return us;
		}
	}
	public void adicionar(Alumno us){
		String sql = "INSERT INTO alumno(codalumno,celular,fechanac ,observacion,foto,fotito,nombre,ap,am,ci_alum,ruestu,tipo_doc,pais,dpto,provincia,localidad,oficialia,libro,partida,folio,sexo) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodalumno(),us.getCelular(), f.fecha(us.getFechanac()),us.getObservacion(),us.getFoto(),us.getFotito(),us.getNombre(),us.getAp(),us.getAm(),us.getCi_alum(),us.getRuestu(),us.getTipo_doc(),us.getPais(),us.getDpto(),us.getProvincia(),us.getLocalidad(),us.getOficialia(),us.getLibro(),us.getPartida()	,us.getFolio(),us.getSexo()});
	}
	
	public String generar_Codigo(){
		int num=db.queryForObject("select count (*) from alumno ", Integer.class);
		return "ALUMN0001-"+num;
	}
	
	public Alumno obtener_por_codalumno(String codalumno){
		String sql = "SELECT * FROM alumno WHERE codalumno=?";
		return db.queryForObject(sql, new to_Object(),codalumno);
	}
	
	public Alumno obtener_por_rud(String rue){
		String sql = "SELECT * FROM alumno WHERE ruestu=?";
		return db.queryForObject(sql, new to_Object(),rue);
	}

	public List<Alumno> listar(){
		String sql = "SELECT * FROM alumno ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Alumno> listar_por_curso(String codcurso){
		String sql = "SELECT alumno.* FROM matricula  m JOIN alumno on alumno.codalumno=m.codalumno  where m.codcurso=?  ORDER BY alumno.ap ASC";
		return db.query(sql, new to_Object(),codcurso);
	}
	
	
	
	public List<Alumno> listar_estado( int estado){
		String sql = "SELECT * FROM alumno where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}

	public List<Alumno> listaractivos(){
		String sql = "SELECT * FROM alumno where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Alumno> listainactivos(){
		String sql = "SELECT * FROM codalumno where estado=0;";
		return db.query(sql, new to_Object());
	}
	public void modificar(Alumno us){
		
		String sql = "UPDATE alumno SET celular=?,fechanac=?,observacion=?,estado=?,foto=?,fotito=?,nombre=?,ap=?,am=?,ci_alum=?,tipo_doc=?,pais=?,dpto=?,provincia=?,localidad=?,oficialia=?,libro=?,partida=?,folio=?,sexo=? WHERE codalumno=?;";
		db.update(sql, new Object[]{us.getCelular(),f.fecha(us.getFechanac()),us.getObservacion(),us.getEstado(),us.getFoto(),us.getFotito(),us.getNombre(),us.getAp(),us.getAm(),us.getCi_alum(),us.getTipo_doc(),us.getPais(),us.getDpto(),us.getProvincia(),us.getLocalidad(),us.getOficialia(),us.getLibro(),us.getPartida(),us.getFolio(),us.getSexo(),us.getCodalumno()});
	}
	
	public void borrado_log(String codalumno){
		String sql = "UPDATE alumno SET estado=0 WHERE codalumno=?;";
		db.update(sql, codalumno);
	}
	
	public void activar(String codclase){
		String sql = "UPDATE alumno SET estado=1 WHERE codalumno=?;";
		db.update(sql, codclase);
	}
	
	public boolean existe_alu(String codalumno){
		String sql="SELECT count(*) FROM alumno WHERE ci_alum=?";
		if(db.queryForObject(sql, Integer.class,codalumno)>0)return true;
		return false; 	
		
	}
	public boolean existe_alu_rue(String codalumno){
		String sql="SELECT count(*) FROM alumno WHERE ruestu=?";
		if(db.queryForObject(sql, Integer.class,codalumno)>0)return true;
		return false; 	
		
	}

	
}