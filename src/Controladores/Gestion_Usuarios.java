package Controladores;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import Utiles.encriptar;
import Modelos.Datos;

import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import herramientas.GeneradorReportes;
import Servicios.Serv_Datos;
import Servicios.Serv_Rol;
import Servicios.Serv_Usuario;

@Controller
@RequestMapping("/usuarios/*")
public class Gestion_Usuarios {
	
	@Autowired
	private Serv_Usuario serv_Usuario;
	@Autowired
	private Serv_Datos serv_Datos;
	
	@Autowired
	private Serv_Rol serv_Rol;
	
	
	
	Date date=new Date();
		
	
	
	
	@RequestMapping("gestion_usuarios")
	public String gestion_usuarios(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("usuarios", serv_Usuario.listar());
			model.addAttribute("logi",user.getCi() );
			model.addAttribute("estado", " EN GENERAL");
			return "usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("inactivos")
	public String inactivos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
		
			
			model.addAttribute("usuarios", serv_Usuario.listainactivos());
			model.addAttribute("estado", "INACTIVOS");
			return "usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("activos")
	public String activos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			model.addAttribute("estado", "ACTIVOS");
			
			model.addAttribute("usuarios", serv_Usuario.listaractivos());
			return "usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("adicionar_usuario")
	public String adicionar_usuario(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		
			
		if(user!=null){
			return "usuarios/adicionar_usuario";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("guardar_usuario")
	public String guardar_usuario(HttpServletRequest request, Model model, @ModelAttribute("us") Usuario us, @RequestParam("file") MultipartFile file) {		
		Usuario user = (Usuario) request.getSession().getAttribute("user");
		if(user!=null && serv_Usuario.existe_usuario(us.getCi())==false){
			try {	
				if (!file.isEmpty()) {		            
	                byte[] bytes = file.getBytes();
	                us.setFotito(bytes);	                 
	                String   rs1 = request.getSession().getServletContext().getRealPath("/fotos");
	                
	                System.out.println("ruta modificada ahora: "+rs1);
	                
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(rs1 +"/usuarios/"+ us.getCi()+file.getOriginalFilename())));
	                stream.write(bytes);
	                stream.close();
	                us.setFoto(us.getCi()+file.getOriginalFilename().toString());	 
		        } else {
		        	System.out.println("error al subir foto guardar usuario");	
		        	if (us.getFoto() == null) {
		        		us.setFoto("usuario.png");
					}
		        }
			}catch (Exception e){
				System.out.println("error: "+e.getMessage());
			}
			
			if (us.getFoto() == null) us.setFoto("usuario.png");
			
			System.out.println("Persona: "+us);			
			
			serv_Usuario.adicionar(us);
			
			
			model.addAttribute("msg", "Usuario Guardado Correctamente..!");
			
			
			return "redirect:../usuarios/gestion_usuarios";
		}
		
		
		else{
			model.addAttribute("msg", "el ci "+us.getCi()+"  ya esiste introdusca uno nuevo");
			
			model.addAttribute("url", "usuarios/adicionar_usuario");
			
			return "principal/mensajes";
		}	
	
	}
	

	

	@RequestMapping("modificar_usuario")
	public String modificar_usuario(HttpServletRequest request, Model model, String ci) {
		Usuario user = (Usuario) request.getSession().getAttribute("user");
		if(user!=null){
			
			System.out.println("Cedula en Modificar Usuario: "+ci);
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
			return "usuarios/modificar_usuario";	
		}else{
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("actualizar_usuario")
	public String actualizar_usuario(HttpServletRequest request, Model model, @ModelAttribute("persona") Usuario us, @RequestParam("file") MultipartFile file,String fotito) {
		Usuario user = (Usuario) request.getSession().getAttribute("user");
		if(user!=null){
			try {	
				if (!file.isEmpty()) {		            
	                byte[] bytes = file.getBytes();
	                us.setFotito(bytes);
	                String   rs1 = request.getSession().getServletContext().getRealPath("/fotos");
	                
	                System.out.println("ruta modificada ahora: "+rs1 +" "+file.getOriginalFilename());
	                
	                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(rs1 +"/usuarios/"+ us.getCi()+file.getOriginalFilename())));
	                stream.write(bytes);
	                stream.close();
	                us.setFoto(us.getCi()+file.getOriginalFilename().toString());	 
		        } else {
		        	System.out.println("error al subir foto guardar usuario");		        	
		        	if (fotito != null) {
		        		us.setFoto(fotito);
					}else {
						us.setFoto("usuario.png");
					}
		        }
			}catch (Exception e){
				System.out.println("error: "+e.getMessage());
			}
			
			System.out.println("Cedula en guardar Modificar Usuario: "+us+" "+us.getFoto()+" "+fotito+" fotitoto "+us.getFotito());
			
			serv_Usuario.modificar(us);
			
			
			
			return "redirect:../usuarios/gestion_usuarios";
			
			
		}else{
			model.addAttribute("msg", "Error.. Debe iniciar sesión o Tener permisos..!");
			
			return "redirect:../usuarios/gestion_usuarios";
		}
	}
	
	
	
	
	
	@RequestMapping("eliminar_usuario")
	public String eliminar_usuario(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Usuario.borrado_log(ci);
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("activar_usuario")
	public String activar_usuario(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Usuario.activar(ci);
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("ver_usuario")
	public String ver_usuario(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
			return "usuarios/ver_usuario";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("asignar_roles")
	public String asignar_roles(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
			model.addAttribute("roles", serv_Rol.roles_sin_asignar_a_Usuario(ci));
			return "usuarios/asignar_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_roles")
	public String guardar_roles(HttpServletRequest request, Model model, String ci, Integer[] codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Usuario.eliminar_usurol(ci);
			if(codr!=null){
				for(int i: codr) serv_Usuario.asignar_roles_a_Usuario(ci, i);
			}
			return "redirect:../usuarios/gestion_usuarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("adicionar_datos")
	public String adicionar_datos(HttpServletRequest request, Model model, String ci){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
			return "usuarios/adicionar_datos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_datos")
	public String guardar_datos(HttpServletRequest request, Model model, @ModelAttribute("dt") Datos dt){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			serv_Datos.adicionar_dato(dt);
				return "redirect:../usuarios/gestion_usuarios";
			
			
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_datos")
	public String modificar_datos(HttpServletRequest request, Model model, String ci) throws Exception{
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			Datos g=serv_Datos.obteber_por_Ci(ci);
			encriptar f=new encriptar();
			
			
	
			g.setClave(f.Desencriptar(g.getClave()));
			
			
			model.addAttribute("dt",g);
			model.addAttribute("ci",ci);
			model.addAttribute("us",serv_Usuario.obtener(ci));
			return "usuarios/modificar_datos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_datos")
	public String actualizar_datos(HttpServletRequest request, Model model,String ci,String login,String clave,String preg,String resp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
					System.out.println(login);
					System.out.println(clave);
					System.out.println(ci);
					
					Datos dt=new Datos();
						dt.setCi(ci);
					dt.setLogin(login);
					dt.setClave(clave);
					
					dt.setPreg(preg);
					dt.setResp(resp);;
			serv_Datos.modificar_dato(dt);
			return "redirect:../usuarios/gestion_usuarios";
		
			
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@Autowired
	DataSource dataSource;
	
	@RequestMapping("ver_usuarios")
	public void ver_usuarios(HttpServletRequest request,HttpServletResponse response,String ci)throws IOException{
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		Map<String, Object> parametros=new HashMap<String, Object>();
		String reportUrl="/Reportes/ver_usuario.jasper";
		parametros.put("ci", ci);
		GeneradorReportes rep=new GeneradorReportes();		
		try{		
			rep.generarReporte(response, getClass().getResource(reportUrl), "pdf", parametros, dataSource.getConnection(), "ver_usuario", "inline");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping("prueba")
	@ResponseBody
	public String desactivar(@RequestParam String ci){
		System.out.println("llega "+ci);
		try {
			boolean existe=serv_Usuario.existe_usuario(ci);
			System.out.println("estad"+existe);
			if(existe==false)
				return "200";
			else
				return "300";
		} catch (Exception e) {
			return "500";
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

	
}