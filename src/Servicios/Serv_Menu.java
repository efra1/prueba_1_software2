package Servicios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Modelos.Menu;
import Modelos.Proceso;

import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.stereotype.Service;

import coneccion.DB;

@Service
@SuppressWarnings("deprecation")
public class Serv_Menu extends DB {
	
	private class to_Object implements ParameterizedRowMapper<Menu>{
		@Override
		public Menu mapRow(ResultSet rs, int rows) throws SQLException {
			Menu pr = new Menu();
			pr.setCodmenu(rs.getInt("codmenu"));
			pr.setNombre(rs.getString("nombre"));
			pr.setEnlace(rs.getString("enlace"));
			pr.setDescrip(rs.getString("descrip"));
			
			pr.setEstado(rs.getInt("estado"));
			return pr;
		}
	}
	
	private Integer generar_Codmenu(){
		try {
			String sql = "SELECT MAX(codmenu) FROM menu;";
			return db.queryForInt(sql)+1;
		} catch (Exception e) {
			return 0;
		}
	}
	
	public Menu obtener_por_Codmenu(Integer codmenu){
		String sql = "SELECT * FROM menu WHERE codmenu=?;";
		return db.queryForObject(sql, new to_Object(), codmenu);
	}
	
	public List<Menu> listar(){
		String sql = "SELECT * FROM menu;";
		return db.query(sql, new to_Object());
	}
	
	
	public void adicionar(Menu pr){
		pr.setCodmenu(generar_Codmenu());
		String sql = "INSERT INTO menu(codmenu, nombre, enlace, descrip) VALUES(?,?,?,?);";
		db.update(sql, new Object[]{pr.getCodmenu(), pr.getNombre(), pr.getEnlace(), pr.getDescrip()});
	}
	
	public void modificar(Menu pr){
		String sql = "UPDATE menu SET nombre=?, enlace=?, descrip=?,  estado=1 WHERE codmenu=?;";
		db.update(sql, new Object[]{pr.getNombre(), pr.getEnlace(), pr.getDescrip(), pr.getCodmenu()});
	}
	/*
	public void eliminar(Integer codmenu){
		
		String sql = "update  menu SET estado=? where codmenu=?;";
		db.update(sql, codmenu);
	}*/
	
	public List<Menu> menus_de_proceso(Integer codp){
		String sql = "SELECT menu.* FROM menu INNER JOIN proces_menu ON menu.codmenu = proces_menu.codmenu WHERE proces_menu.codp = ?;";
		return db.query(sql, new to_Object(), codp);
	}
	
	public List<Menu> menus_sin_asignar_a_proceso(Integer codp){
		String sql = "SELECT menu.* FROM menu " +
				"WHERE menu.codmenu NOT IN (SELECT codmenu FROM proces_menu WHERE proces_menu.codp=?);";
		return db.query(sql, new to_Object(), codp);
	}
	
	public void eliminar2(Integer codmenu){
		eliminar_de_proces_menu(codmenu);
		String sql = "DELETE FROM menu WHERE codmenu=?;";
		db.update(sql, codmenu);
	}
	
	public void eliminar_de_proces_menu(Integer codmenu){
		String sql = "DELETE FROM proces_menu WHERE codmenu=?;";
		db.update(sql, codmenu);
	}
	
	
public void dar_de_baja(Integer codmenu){
		
		String sql = "UPDATE menu SET estado=0 WHERE codmenu=?;";
		db.update(sql, codmenu);
	}
public void dar_de_alta(Integer codmenu){
		
		String sql = "UPDATE menu SET estado=1 WHERE codmenu=?;";
		db.update(sql, codmenu);
	}
	
}