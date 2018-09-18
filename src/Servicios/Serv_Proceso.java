package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modelos.Proceso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import coneccion.DB;

@Service
@SuppressWarnings("deprecation")
public class Serv_Proceso extends DB {
	
	
	
	@Autowired
	private Serv_Menu serv_Menu;
	
	private class to_Object implements ParameterizedRowMapper<Proceso>{
		@Override
		public Proceso mapRow(ResultSet rs, int rows) throws SQLException {
			Proceso pr = new Proceso();
			pr.setCodp(rs.getInt("codp"));
			pr.setNombre(rs.getString("nombre"));
		
			pr.setEstado(rs.getInt("estado"));
			try {
				pr.setMenus(serv_Menu.menus_de_proceso(rs.getInt("codp")));
			} catch (Exception e) {
				pr.setMenus(null);
			}
			
			
			return pr;
		}
	}
	
	private Integer generar_Codp(){
		try {
			String sql = "SELECT MAX(codp) FROM proceso;";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Proceso obtener_por_Codp(Integer codp){
		String sql = "SELECT * FROM proceso WHERE codp=?;";
		return db.queryForObject(sql, new to_Object(), codp);
	}
	
	public List<Proceso> listar(){
		String sql = "SELECT * FROM proceso ORDER BY codp ASC;";
		return db.query(sql, new to_Object());
	}
	
	public List<Proceso> listar_estado(int est){
		String sql = "SELECT * FROM proceso where estado=? ORDER BY codp ASC;";
		return db.query(sql, new to_Object(),est);
	}
	
	public List<Proceso> procesos_de_Rol(Integer codr){
		String sql = "SELECT proceso.* FROM proceso INNER JOIN rolpro ON proceso.codp = rolpro.codp WHERE rolpro.codr = ?;";
		return db.query(sql, new to_Object(), codr);
	}
	
	public List<Proceso> procesos_sin_asignar_a_Rol(Integer codr){
		String sql = "SELECT proceso.* FROM proceso " +
				"WHERE proceso.codp NOT IN (SELECT codp FROM rolpro WHERE rolpro.codr=?);";
		return db.query(sql, new to_Object(), codr);
	}
	
	public void adicionar(Proceso pr){
		pr.setCodp(generar_Codp());
		String sql = "INSERT INTO proceso(codp, nombre) VALUES(?,?);";
		db.update(sql, new Object[]{pr.getCodp(), pr.getNombre()});
	}
	
	public void modificar(Proceso pr){
		String sql = "UPDATE proceso SET nombre=?,estado=1 WHERE codp=?;";
		db.update(sql, new Object[]{pr.getNombre(),pr.getCodp()});
	}
	
	public void eliminar(Integer codp){
		eliminar_de_rolpro(codp);
		String sql = "DELETE FROM proceso WHERE codp=?;";
		db.update(sql, codp);
	}
	
	public void dar_de_baja(Integer codp){
		
		String sql = "UPDATE proceso SET estado=0 WHERE codp=?;";
		db.update(sql, codp);
	}
public void dar_de_alta(Integer codp){
		
		String sql = "UPDATE proceso SET estado=1 WHERE codp=?;";
		db.update(sql, codp);
	}
	
	
	public void eliminar_de_rolpro(Integer codp){
		String sql = "DELETE FROM rolpro WHERE codp=?;";
		db.update(sql, codp);
	}
	
	public void eliminar_de_proces_menu(Integer codp){
		String sql = "DELETE FROM proces_menu WHERE codp=?;";
		db.update(sql, codp);
	}
	
	
	public void asignar_menus_a_proceso(Integer codp, Integer codmenu){
		String sql = "INSERT INTO proces_menu(codp, codmenu) VALUES(?,?);";
		db.update(sql, new Object[]{codp, codmenu});
	}
}