package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Clase;
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
import Servicios.Serv_Especialidad;
import Servicios.Serv_Grado;
import Servicios.Serv_Horario;
import Servicios.Serv_Proceso;
import Servicios.Serv_Profesor;
import Servicios.Serv_Rol;
import herramientas.fecha_hora;
@Controller
@RequestMapping("/horarios/*")
public class Gestion_Horario {
	
	@Autowired
	private Serv_Horario serv_Horario;

	@Autowired
	private Serv_Profesor serv_Profesor;
	@Autowired
	private Serv_Clase serv_Clase;
	fecha_hora f =new fecha_hora();
	
	@RequestMapping("gestion_horarios")
	public String gestion_horarios(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("horarios", serv_Horario.listar());
			return "horarios/gestion_horarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_horario")
	public String adicionar_horario(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			model.addAttribute("clases", serv_Clase.listar());
			
			model.addAttribute("profesores", serv_Profesor.listar());
			return "horarios/adicionar_horario";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_horario")
	@SuppressWarnings("deprecation")
	public String guardar_horario(HttpServletRequest request, Model model, @ModelAttribute("horario") Horario horario) throws DataAccessException, ParseException{
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			if(serv_Horario.existe_horario(horario.getCodprofesor(),horario.getDia(),horario.getHora_inicio(),horario.getHora_fin())==true){
						
			model.addAttribute("msg", "existe choque de horario par el profesor ");
			model.addAttribute("url", "horarios/gestion_horarios");
				
			return "principal/mensajes";
			}
			else {
				Clase c= serv_Clase.obtener_por_Codclase(horario.getCodclase());
				horario.setCodprofesor(c.getCodprofesor());
				serv_Horario.adicionar(horario);;
			model.addAttribute("horarios", serv_Horario.listar());
			return "redirect:../horarios/gestion_horarios";
			}
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

	@RequestMapping("modificar_horario")
	public String modificar_horario(HttpServletRequest request, Model model, String codhorario){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			Horario hora =serv_Horario.obtener_por_Codhorario2(codhorario);
			
			
			model.addAttribute("horario",hora);
			return "horarios/modificar_horario";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_horario")
	@SuppressWarnings("deprecation")
	public String actualizar_horario(HttpServletRequest request, Model model, @ModelAttribute("horario") Horario horario){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null && serv_Horario.existe_horario(horario.getCodprofesor(),horario.getDia(),horario.getHora_inicio(),horario.getHora_fin())==false){
		
				serv_Horario.modificar(horario);
				model.addAttribute("horarios", serv_Horario.listar());
			return "redirect:../horarios/gestion_horarios";
		}
		else {
			model.addAttribute("msg", "el docente tiene choque de horarios seleccione otro horario");
			model.addAttribute("url", "clases/gestion_clases");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_horario")
	@SuppressWarnings("deprecation")
	public String eliminar_horario(HttpServletRequest request, Model model, String codhorario){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Horario.borrado_log(codhorario);
			
			model.addAttribute("horarios",serv_Horario.listar());
			return "redirect:../horarios/gestion_horarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("activar_horario")
	@SuppressWarnings("deprecation")
	public String activar_horario(HttpServletRequest request, Model model, String codhorario){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Horario.activar(codhorario);;
			
			model.addAttribute("horarios",serv_Horario.listar());
			return "redirect:../horarios/gestion_horarios";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("ver_horario")
	public String ver_grado(HttpServletRequest request, Model model, String codhorario){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("horario", serv_Horario.obtener_por_Codhorario(codhorario));
			return "horarios/ver_horario";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
}