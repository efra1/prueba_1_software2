package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Especialidad;
import Modelos.Rol;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Especialidad;
import Servicios.Serv_Proceso;
import Servicios.Serv_Rol;
@Controller
@RequestMapping("/especialidad/*")
public class Gestion_Especialidad {
	
	@Autowired
	private Serv_Especialidad serv_Especialidad;

	@RequestMapping("gestion_especialidad")
	public String gestion_especialidad(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("especialidades", serv_Especialidad.listar());
			return "especialidad/gestion_especialidad";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_especialidad")
	public String adicionar_especialidad(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "especialidad/adicionar_especialidad";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_especialidad")
	@SuppressWarnings("deprecation")
	public String guardar_especialidad(HttpServletRequest request, Model model, @ModelAttribute("especialidad") Especialidad especialidad){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Especialidad.adicionar(especialidad);
				model.addAttribute("especialidades", serv_Especialidad.listar_estado(1));
			return "redirect:../especialidad/gestion_especialidad";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_especialidad")
	public String modificar_especialidad(HttpServletRequest request, Model model, String codespecialidad){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("especialidad", serv_Especialidad.obtener_por_Codespecialidad(codespecialidad));
			return "especialidad/modificar_especialidad";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_especialidad")
	@SuppressWarnings("deprecation")
	public String actualizar_especialidad(HttpServletRequest request, Model model, @ModelAttribute("especialidad") Especialidad especialidad){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Especialidad.modificar(especialidad);
				model.addAttribute("especialidades", serv_Especialidad.listar_estado(1));
			return "redirect:../especialidad/gestion_especialidad";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_especialidad")
	@SuppressWarnings("deprecation")
	public String eliminar_rol(HttpServletRequest request, Model model, String codespecialidad){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Especialidad.borrado_log(codespecialidad);
			
			model.addAttribute("especialidades", serv_Especialidad.listar());
			return "redirect:../especialidad/gestion_especialidad";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("activar_especialidad")
	@SuppressWarnings("deprecation")
	public String activar_especialidad(HttpServletRequest request, Model model, String codespecialidad){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Especialidad.activar(codespecialidad);
			
			model.addAttribute("especialidades", serv_Especialidad.listar());
			return "redirect:../especialidad/gestion_especialidad";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
	@RequestMapping("ver_especialidad")
	public String ver_especialidad(HttpServletRequest request, Model model, String codespecialidad){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("especialidad", serv_Especialidad.obtener_por_Codespecialidad(codespecialidad));
			return "especialidad/ver_especialidad";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
}