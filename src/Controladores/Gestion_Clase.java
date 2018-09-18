package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Clase;
import Modelos.Curso;
import Modelos.Especialidad;
import Modelos.Item;
import Modelos.Horario;
import Modelos.Rol;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Clase;
import Servicios.Serv_Curso;
import Servicios.Serv_Especialidad;
import Servicios.Serv_Grado;
import Servicios.Serv_Horario;
import Servicios.Serv_Materia;
import Servicios.Serv_Nivel;
import Servicios.Serv_Paralelo;
import Servicios.Serv_Proceso;
import Servicios.Serv_Profesor;
import Servicios.Serv_Rol;
import Servicios.Serv_Turno;
@Controller
@RequestMapping("/clases/*")
public class Gestion_Clase {
	
	@Autowired
	private Serv_Clase serv_clase;
	
	@Autowired
	private Serv_Curso serv_Curso;
	@Autowired
	private Serv_Materia serv_Materia;
	@Autowired
	private Serv_Horario serv_Horario;
	@Autowired
	private Serv_Profesor serv_Profesor;
	@RequestMapping("gestion_clases")
	public String gestion_clases(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("clases",serv_clase.listar());
			return "clases/gestion_clases";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("adicionar_clase")
	public String adicionar_clase(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			model.addAttribute("materias", serv_Materia.listar_estado(1));
			model.addAttribute("cursos", serv_Curso.listar_estado(1));
			model.addAttribute("profesores", serv_Profesor.listar_estado(1));
			model.addAttribute("horarios", serv_Horario.listar_estado(1));
			return "clases/adicionar_clase";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_clase")
	@SuppressWarnings("deprecation")
	public String guardar_clase(HttpServletRequest request, Model model, @ModelAttribute("clases") Clase clase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_clase.adicionar(clase);
				model.addAttribute("clases", serv_clase.listar_estado(1));
			return "redirect:../clases/gestion_clases";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

	@RequestMapping("modificar_clase")
	public String modificar_clase(HttpServletRequest request, Model model, String codclase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			model.addAttribute("materias", serv_Materia.listar_estado(1));
			model.addAttribute("cursos", serv_Curso.listar_estado(1));
			model.addAttribute("profesores", serv_Profesor.listar_estado(1));

			model.addAttribute("clase", serv_clase.obtener_por_Codclase(codclase));
			model.addAttribute("horarios", serv_Horario.listar_estado(1));
			return "clases/modificar_clase";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_clase")
	@SuppressWarnings("deprecation")
	public String actualizar_clase(HttpServletRequest request, Model model, @ModelAttribute("clase") Clase clase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_clase.modificar(clase);
				
				model.addAttribute("clases",serv_clase.listar_estado(1));
				return "clases/gestion_clases";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_clase")
	@SuppressWarnings("deprecation")
	public String eliminar_clase(HttpServletRequest request, Model model, String codclase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_clase.borrado_log(codclase);
			
			model.addAttribute("clases", serv_clase.listar());
			return "redirect:../clases/gestion_clases";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("activar_clase")
	@SuppressWarnings("deprecation")
	public String activar_clase(HttpServletRequest request, Model model, String codclase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_clase.activar(codclase);
			
			model.addAttribute("clases", serv_clase.listar());
			return "redirect:../clases/gestion_clases";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("ver_clase")
	public String ver_clase(HttpServletRequest request, Model model, String codclase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("materias", serv_Materia.listar_estado(1));
			model.addAttribute("cursos", serv_Curso.listar_estado(1));
			model.addAttribute("profesores", serv_Profesor.listar_estado(1));

			model.addAttribute("clase", serv_clase.obtener_por_Codclase(codclase));
			model.addAttribute("horarios", serv_Horario.listar_estado(1));
		
			return "clases/ver_clase";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("asignar_horarios")
	public String asignar_horarios(HttpServletRequest request, Model model, String codclase,String codprofesor){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("codclase",codclase);
			model.addAttribute("codprofesor",codprofesor);
			model.addAttribute("horarios",serv_Horario.listar_por_clase(codclase));
			return "clases/asignar_horarios";
		}
		
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("guardar_horarios")
	public String guardar_horarios(HttpServletRequest request, Model model, String codclase,String codprofesor,String dia,String hora_inicio,String hora_fin) throws DataAccessException, ParseException{
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		System.out.println(hora_inicio+""+hora_fin);
		if(user!=null&& serv_Horario.existe_horario(codprofesor,dia,hora_inicio,hora_fin)==false ){
			System.out.println(hora_inicio+"    "+hora_fin);
			Horario h=new Horario();
			h.setCodhorario(serv_Horario.generar_Codigo());
			h.setCodclase(codclase);
			h.setDia(dia);
			h.setHora_inicio(hora_inicio);
			h.setHora_fin(hora_fin);
			h.setCodprofesor(codprofesor);
			serv_Horario.adicionar(h);
			
			model.addAttribute("codclase",codclase);	
			model.addAttribute("codprofesor",codprofesor);
			model.addAttribute("horarios",serv_Horario.listar_por_clase(codclase));
			return "clases/asignar_horarios";
		}
		
		else {
			model.addAttribute("msg", "el docente tiene choque de horarios seleccione otro horario");
			model.addAttribute("url", "clases/gestion_clases");
			
			return "principal/mensajes";
		}
	}
	
	
	
	
	
}