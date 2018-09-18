package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Alumno;
import Modelos.Apoderado;
import Modelos.Clase;
import Modelos.Curso;
import Modelos.Especialidad;
import Modelos.Item;
import Modelos.Matricula;
import Modelos.Parentesco;
import Modelos.Rol;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Alumno;
import Servicios.Serv_Clase;
import Servicios.Serv_Curso;
import Servicios.Serv_Especialidad;
import Servicios.Serv_Grado;
import Servicios.Serv_Horario;
import Servicios.Serv_Materia;
import Servicios.Serv_Matricula;
import Servicios.Serv_Nivel;
import Servicios.Serv_Paralelo;
import Servicios.Serv_Proceso;
import Servicios.Serv_Profesor;
import Servicios.Serv_Rol;
import Servicios.Serv_Turno;
@Controller
@RequestMapping("/alumnos/*")
public class Gestion_Alumnos {
	
	@Autowired
	private Serv_Alumno serv_Alumno;
	
	@Autowired
	private Serv_Curso serv_Curso;
	@RequestMapping("gestion_alumnos")
	public String gestion_alumnos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("alumnos",serv_Alumno.listar());
			return "alumnos/gestion_alumnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_alumno")
	public String modificar_alumno(HttpServletRequest request, Model model, String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
		
			
			model.addAttribute("cursos",serv_Curso.listar_estado(1));
			model.addAttribute("alumno", serv_Alumno.obtener_por_codalumno(codalumno));
		
		
		return "alumnos/modificar_alumno";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_alumno")
	@SuppressWarnings("deprecation")
	public String actualizar_alumno(HttpServletRequest request, Model model, @ModelAttribute("alumno") Alumno alumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			alumno.setEstado(1);
				serv_Alumno.modificar(alumno);
				
				
			return "redirect:../alumnos/gestion_alumnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("ver_alumno")
	public String ver_alumno(HttpServletRequest request, Model model, String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
		
			model.addAttribute("cursos",serv_Curso.listar_estado(1));
			model.addAttribute("alumno", serv_Alumno.obtener_por_codalumno(codalumno));
		
		
		return "alumnos/ver_alumno";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	

	@RequestMapping("eliminar_alumno")
	@SuppressWarnings("deprecation")
	public String eliminar_alumno(HttpServletRequest request, Model model, String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Alumno.borrado_log(codalumno);
			
			model.addAttribute("alumnos", serv_Alumno.listar());
			return "redirect:../alumnos/gestion_alumnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("activar_alumno")
	@SuppressWarnings("deprecation")
	public String activar_alumno(HttpServletRequest request, Model model, String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Alumno.activar(codalumno);
			
			model.addAttribute("alumnos", serv_Alumno.listar());
			return "redirect:../alumnos/gestion_alumnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
}