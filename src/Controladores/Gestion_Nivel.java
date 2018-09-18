package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Especialidad;
import Modelos.Item;
import Modelos.Nivel;
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
import Servicios.Serv_Nivel;
import Servicios.Serv_Proceso;
import Servicios.Serv_Rol;
@Controller
@RequestMapping("/niveles/*")
public class Gestion_Nivel {
	
	@Autowired
	private Serv_Nivel serv_Nivelo;

	@RequestMapping("gestion_niveles")
	public String gestion_niveles(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("niveles", serv_Nivelo.listar());
			return "niveles/gestion_niveles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_nivel")
	public String adicionar_nivel(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "niveles/adicionar_nivel";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_nivel")
	@SuppressWarnings("deprecation")
	public String guardar_nivel(HttpServletRequest request, Model model, @ModelAttribute("nivel") Nivel nivel){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Nivelo.adicionar(nivel);;
				model.addAttribute("niveles", serv_Nivelo.listar_estado(1));
			return "redirect:../niveles/gestion_niveles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_nivel")
	public String modificar_nivel(HttpServletRequest request, Model model, String codnivel){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("nivel", serv_Nivelo.obtener_por_codnivel(codnivel));
			return "niveles/modificar_nivel";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_nivel")
	@SuppressWarnings("deprecation")
	public String actualizar_nivel(HttpServletRequest request, Model model, @ModelAttribute("nivel") Nivel nivel){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Nivelo.modificar(nivel);
				model.addAttribute("niveles", serv_Nivelo.listar_estado(1));
			return "redirect:../niveles/gestion_niveles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_nivel")
	@SuppressWarnings("deprecation")
	public String eliminar_nivel(HttpServletRequest request, Model model, String codnivel){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Nivelo.borrado_log(codnivel);;
			
			model.addAttribute("niveles", serv_Nivelo.listar());
			return "redirect:../niveles/gestion_niveles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("activar_nivel")
	@SuppressWarnings("deprecation")
	public String activar_nivel(HttpServletRequest request, Model model, String codnivel){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Nivelo.activar(codnivel);;
			
			model.addAttribute("niveles", serv_Nivelo.listar());
			return "redirect:../niveles/gestion_niveles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("ver_nivel")
	public String ver_nivel(HttpServletRequest request, Model model, String codnivel){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("nivel", serv_Nivelo.obtener_por_codnivel(codnivel));
			return "niveles/ver_nivel";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
}