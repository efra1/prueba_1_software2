package Controladores;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Curso;
import Modelos.Datos;
import Modelos.Especialidad;
import Modelos.Item;
import Modelos.Profesor;
import Modelos.Rol;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Curso;
import Servicios.Serv_Datos;
import Servicios.Serv_Especialidad;
import Servicios.Serv_Grado;
import Servicios.Serv_Nivel;
import Servicios.Serv_Paralelo;
import Servicios.Serv_Proceso;
import Servicios.Serv_Profesor;
import Servicios.Serv_Rol;
import Servicios.Serv_Turno;
import Servicios.Serv_Usuario;
import Utiles.encriptar;
@Controller
@RequestMapping("/profesores/*")
public class Gestion_Profesores {
	
	@Autowired
	private Serv_Profesor serv_Profesor;
	
	@Autowired
	private Serv_Usuario serv_Usuario;
	
	@Autowired
	private Serv_Especialidad serv_Especialidad;
	
	@Autowired
	private Serv_Datos serv_Datos;
	
	@RequestMapping("gestion_profesores")
	public String gestion_profesores(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("profesores", serv_Profesor.listar());
			return "profesores/gestion_profesores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("adicionar_profesor")
	public String adicionar_profesor(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("especialiades", serv_Especialidad.listar_estado(1));
			return "profesores/adicionar_profesor";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}


	@RequestMapping("guardar_profesor")
	public String guardar_profesor(HttpServletRequest request, Model model, @ModelAttribute("us") Usuario us, @RequestParam("file") MultipartFile file,String rud,String lugar_nac,String direccion,String distrito,String codespecialidad,String login,String clave,String preg,String resp) {		
		Usuario user = (Usuario) request.getSession().getAttribute("user");
		if(user!=null){
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
			
			Profesor prof=new Profesor();
			prof.setCodusuario(us.getCi());
			prof.setCodespecialidad(codespecialidad);
			prof.setDireccion(direccion);
			prof.setDistrito(distrito);
			prof.setLugar_nac(lugar_nac);
			prof.setRud(rud);
			serv_Profesor.adicionar(prof);
			
			
			Datos dt=new Datos();
			dt.setCi(us.getCi());
			dt.setClave(clave);
			dt.setLogin(login);
			dt.setPreg(preg);
			dt.setResp(resp);
			serv_Datos.adicionar_dato(dt);
			
			model.addAttribute("msg", "Usuario Guardado Correctamente..!");
			
			
			return "redirect:../profesores/gestion_profesores";
		}
		
		
		else{
			model.addAttribute("msg", "el ci "+us.getCi()+"  ya esiste introdusca uno nuevo");
			
			model.addAttribute("url", "profesores/gestion_profesores");
			
			return "principal/mensajes";
		}	
	
	}

	@RequestMapping("modificar_profesor")
	public String modificar_profesor(HttpServletRequest request, Model model, String codprofesor) throws Exception{
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			
			
			Profesor pr=serv_Profesor.obtener_por_Codprofesor(codprofesor);
			model.addAttribute("especialiades", serv_Especialidad.listar_estado(1));
			
			model.addAttribute("profesor",pr);
			model.addAttribute("usuario", serv_Usuario.obtener(pr.getCodusuario()));
			
			Datos g=serv_Datos.obteber_por_Ci(pr.getCodusuario());
			encriptar f=new encriptar();
			
			
			
			g.setClave(f.Desencriptar(g.getClave()));
			
			
			
			model.addAttribute("datos",g);
			
			return "profesores/modificar_profesor";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("actualizar_profesor")
	public String actualizar_profesor(HttpServletRequest request, Model model, @ModelAttribute("us") Usuario us, @RequestParam("file") MultipartFile file,String rud,String lugar_nac,String direccion,String distrito,String codespecialidad,String login,String clave,String preg,String resp,String codprofesor) {		
		Usuario user = (Usuario) request.getSession().getAttribute("user");
		if(user!=null){
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
			
			serv_Usuario.modificar(us);
			
			Profesor prof=new Profesor();
			prof.setCodprofesor(codprofesor);
			prof.setCodusuario(us.getCi());
			prof.setCodespecialidad(codespecialidad);
			prof.setDireccion(direccion);
			prof.setDistrito(distrito);
			prof.setLugar_nac(lugar_nac);
			prof.setRud(rud);
			serv_Profesor.modificar(prof);
			
			
			Datos dt=new Datos();
			dt.setCi(us.getCi());
			dt.setClave(clave);
			dt.setLogin(login);
			dt.setPreg(preg);
			dt.setResp(resp);
			serv_Datos.modificar_dato(dt);
			
			model.addAttribute("msg", "Usuario modificado Correctamente..!");
			
			
			return "redirect:../profesores/gestion_profesores";
		}
		
		
		else{
			model.addAttribute("msg", "el ci "+us.getCi()+"  ya esiste introdusca uno nuevo");
			
			model.addAttribute("url", "profesores/gestion_profesores");
			
			return "principal/mensajes";
		}	
	
	}
	/*
	@RequestMapping("actualizar_curso")
	@SuppressWarnings("deprecation")
	public String actualizar_curso(HttpServletRequest request, Model model, @ModelAttribute("curso") Curso curso){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Curso.modificar(curso);;
				model.addAttribute("grados", serv_Grado.listar_estado(1));
			return "redirect:../cursos/gestion_cursos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_curso")
	@SuppressWarnings("deprecation")
	public String eliminar_curso(HttpServletRequest request, Model model, String codcurso){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Curso.borrado_log(codcurso);;
			
			model.addAttribute("cursos", serv_Curso.listar_estado(1));
			return "redirect:../cursos/gestion_cursos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
	
	@RequestMapping("ver_curso")
	public String ver_curso(HttpServletRequest request, Model model, String codcurso){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("curso", serv_Curso.obtener_por_Codcurso(codcurso));
			return "cursos/ver_curso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	*/
	
	
	
	@RequestMapping("eliminar_profesor")
	@SuppressWarnings("deprecation")
	public String eliminar_curso(HttpServletRequest request, Model model, String codprofesor){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Profesor.borrado_log(codprofesor);;

			model.addAttribute("profesores", serv_Profesor.listar());
			return "redirect:../profesores/gestion_profesores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("activar_profesor")
	@SuppressWarnings("deprecation")
	public String activar_profesor(HttpServletRequest request, Model model, String codprofesor){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Profesor.activar(codprofesor);;

			model.addAttribute("profesores", serv_Profesor.listar());
			return "redirect:../profesores/gestion_profesores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("prueba2p")
	@ResponseBody
	public String desactivar(@RequestParam String ci){
		System.out.println("llega "+ci);
		try {
			boolean existe=serv_Profesor.existe_prof(ci);
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