package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Especialidad;
import Modelos.Item;
import Modelos.Materia;
import Modelos.Paralelo;
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
import Servicios.Serv_Grado;
import Servicios.Serv_Materia;
import Servicios.Serv_Paralelo;
import Servicios.Serv_Proceso;
import Servicios.Serv_Rol;
@Controller
@RequestMapping("/materias/*")
public class Gestion_Materia {
	
	@Autowired
	private Serv_Materia serv_Materia;

	@RequestMapping("gestion_materias")
	public String gestion_materias(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("materias", serv_Materia.listar());
			return "materias/gestion_materias";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_materia")
	public String adicionar_materia(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "materias/adicionar_materia";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_materia")
	@SuppressWarnings("deprecation")
	public String guardar_materia(HttpServletRequest request, Model model, @ModelAttribute("materia") Materia materia){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Materia.adicionar(materia);;
				model.addAttribute("materias", serv_Materia.listar_estado(1));
			return "redirect:../materias/gestion_materias";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_materia")
	public String modificar_materia(HttpServletRequest request, Model model, String codmateria){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("materia", serv_Materia.obtener_por_codmateria(codmateria));
			return "materias/modificar_materia";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_materia")
	@SuppressWarnings("deprecation")
	public String actualizar_materia(HttpServletRequest request, Model model, @ModelAttribute("materia") Materia materia){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Materia.modificar(materia);;;
				model.addAttribute("materias", serv_Materia.listar());
			return "redirect:../materias/gestion_materias";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_materia")
	@SuppressWarnings("deprecation")
	public String eliminar_materia(HttpServletRequest request, Model model, String codmateria){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Materia.borrado_log(codmateria);
			
			model.addAttribute("materias", serv_Materia.listar());
			return "redirect:../materias/gestion_materias";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("activar_materia")
	public String activar_materia(HttpServletRequest request, Model model, String codmateria){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Materia.activar(codmateria);
			return "redirect:../materias/gestion_materias";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("ver_materia")
	public String ver_materia(HttpServletRequest request, Model model, String codmateria){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("materia", serv_Materia.obtener_por_codmateria(codmateria));
			return "materias/ver_materia";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
}