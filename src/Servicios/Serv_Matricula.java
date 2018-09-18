package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import Modelos.Especialidad;
import Modelos.Matricula;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import Utiles.encriptar;

import coneccion.DB;
import herramientas.fecha_hora;
import herramientas.fechas;

@Service
@SuppressWarnings("deprecation")
public class Serv_Matricula extends DB {
	

	fechas conv = new fechas();
	fecha_hora f =new fecha_hora();

	private class to_Object implements ParameterizedRowMapper<Matricula>{
		@Override
		public Matricula mapRow(ResultSet rs, int rows) throws SQLException {
			Matricula us = new Matricula();
			
			us.setCodmatricula(rs.getString("codmatricula"));
			
			us.setFecha(rs.getString("fecha"));
			us.setMonto(rs.getFloat("monto"));
			us.setCodalumno(rs.getString("codalumno"));
			us.setCodcurso(rs.getString("codcurso"));
			us.setCodgestion(rs.getString("codgestion"));
			us.setCi(rs.getString("ci"));
			us.setEstado(rs.getInt("estado"));
			us.setInscripcion(rs.getString("inscripcion"));
			us.setExcol(rs.getString("excol"));
			us.setNom_excol(rs.getString("nom_excol"));
			
			us.setDoc_insc(rs.getString("doc_insc"));
			us.setLugar_insc(rs.getString("lugar_insc"));
			
			
			
			
			return us;
		}
	}
	
	private String generar_Cod(){
		int num=db.queryForObject("select count (*) from matricula ", Integer.class);
		return "MTLA0001-"+num;
	}
	
	public Matricula obtener_por_Codgrado(String codmatricula){
		String sql = "SELECT * FROM matricula WHERE codmatricula=?";
		return db.queryForObject(sql, new to_Object(),codmatricula);
	}
	
	public Matricula obtener_por_alumno_gestion(String codalumno,String codgestion){
		String sql = "SELECT * FROM matricula WHERE codalumno=? and codgestion=?";
		return db.queryForObject(sql, new to_Object(),codalumno,codgestion);
	}
	
	public List<Matricula> listar_estado( int estado){
		String sql = "SELECT * FROM matricula where estado=?;";
		return db.query(sql, new to_Object(),estado);
	}
	public List<Matricula> listar( ){
		String sql = "SELECT * FROM matricula ;";
		return db.query(sql, new to_Object());
	}
	
	public List<Matricula> listaractivos(){
		String sql = "SELECT * FROM matricula where estado=1;";
		return db.query(sql, new to_Object());
	}
	public List<Matricula> listainactivos(){
		String sql = "SELECT * FROM matricula where estado=0;";
		return db.query(sql, new to_Object());
	}
	
	public void adicionar(Matricula us){
		us.setCodmatricula(generar_Cod());
		String sql = "INSERT INTO matricula(codmatricula,monto,codalumno,codcurso,codgestion,ci,inscripcion,excol,nom_excol,doc_insc,lugar_insc) VALUES(?,?,?,?,?,?,?,?,?,?,?);";
		db.update(sql, new Object[]{us.getCodmatricula(),us.getMonto(),us.getCodalumno(), us.getCodcurso(),us.getCodgestion(),us.getCi(),us.getInscripcion(),us.getExcol(),us.getNom_excol(),us.getDoc_insc(),us.getLugar_insc()});
	}
	
	public void modificar(Matricula us){
		
		String sql = "UPDATE matricula SET monto=?,codalumno=?,codcurso=?,codgestion=?,excol=?,doc_insc=?,nom_excol=?,ci=? WHERE codmatricula=?;";
		db.update(sql, new Object[]{us.getMonto(),us.getCodalumno(), us.getCodcurso(),us.getCodgestion(),us.getExcol(),us.getDoc_insc(),us.getNom_excol(),us.getCi(),us.getCodmatricula()});
	}
	
public void modificar_texto(String codmatricula,String codigo1,String codigo2,String codigo3){
		
		String sql = "UPDATE matricula SET texto1=?,texto2=?,texto3=? WHERE codmatricula=?;";
		db.update(sql, new Object[]{codigo1,codigo2,codigo3,codmatricula});
	}
	
	
	public void borrado_log(String codmatricula){
		String sql = "UPDATE matricula SET estado=0 WHERE codmatricula=?;";
		db.update(sql, codmatricula);
	}
	
	public void activar(String codmatricula){
		String sql = "UPDATE matricula SET estado=1 WHERE codmatricula=?;";
		db.update(sql, codmatricula);
	}
	
	public boolean existe_grado(String codmatricula){
		String sql="SELECT count(*) FROM matricula WHERE codmatricula=?";
		if(db.queryForObject(sql, Integer.class,codmatricula)>0)return true;
		return false; 	
		
	}
	

	
}