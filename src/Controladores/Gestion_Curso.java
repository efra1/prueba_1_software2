package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Curso;
import Modelos.Especialidad;
import Modelos.Item;
import Modelos.Rol;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Curso;
import Servicios.Serv_Especialidad;
import Servicios.Serv_Grado;
import Servicios.Serv_Nivel;
import Servicios.Serv_Paralelo;
import Servicios.Serv_Proceso;
import Servicios.Serv_Rol;
import Servicios.Serv_Turno;
@Controller
@RequestMapping("/cursos/*")
public class Gestion_Curso {
	
	@Autowired
	private Serv_Curso serv_Curso;
	@Autowired
	private Serv_Grado serv_Grado;
	@Autowired
	private Serv_Paralelo serv_Paralelo;
	@Autowired
	private Serv_Nivel serv_Nivel;
	@Autowired
	private Serv_Turno serv_Turno;

	@RequestMapping("gestion_cursos")
	public String gestion_cursos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("cursos", serv_Curso.listar_estado(1));
			return "cursos/gestion_cursos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("adicionar_curso")
	public String adicionar_curso(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			model.addAttribute("turnos", serv_Turno.listar_estado(1));
			model.addAttribute("paralelos", serv_Paralelo.listar_estado(1));
			model.addAttribute("niveles", serv_Nivel.listar_estado(1));
			model.addAttribute("grados", serv_Grado.listar_estado(1));
			return "cursos/adicionar_curso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_curso")
	@SuppressWarnings("deprecation")
	public String guardar_curso(HttpServletRequest request, Model model, @ModelAttribute("curso") Curso curso){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Curso.adicionar(curso);
				model.addAttribute("cursos", serv_Curso.listar_estado(1));
			return "redirect:../cursos/gestion_cursos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

	@RequestMapping("modificar_curso")
	public String modificar_curso(HttpServletRequest request, Model model, String codcurso){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			model.addAttribute("turnos", serv_Turno.listar_estado(1));
			model.addAttribute("paralelos", serv_Paralelo.listar_estado(1));
			model.addAttribute("niveles", serv_Nivel.listar_estado(1));
			model.addAttribute("grados", serv_Grado.listar_estado(1));
			model.addAttribute("curso", serv_Curso.obtener_por_Codcurso(codcurso));
			return "cursos/modificar_curso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
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
			
			model.addAttribute("cursos", serv_Curso.listar());
			return "redirect:../cursos/gestion_cursos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("activar_curso")
	@SuppressWarnings("deprecation")
	public String activar_curso(HttpServletRequest request, Model model, String codcurso){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Curso.activar(codcurso);;
			
			model.addAttribute("cursos", serv_Curso.listar());
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
	
	
}