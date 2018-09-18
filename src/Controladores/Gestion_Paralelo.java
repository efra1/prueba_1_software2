package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Especialidad;
import Modelos.Item;
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
import Servicios.Serv_Paralelo;
import Servicios.Serv_Proceso;
import Servicios.Serv_Rol;
@Controller
@RequestMapping("/paralelos/*")
public class Gestion_Paralelo {
	
	@Autowired
	private Serv_Paralelo serv_Paralelo;

	@RequestMapping("gestion_paralelos")
	public String gestion_paralelos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("paralelos", serv_Paralelo.listar());
			return "paralelos/gestion_paralelos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_paralelo")
	public String adicionar_paralelo(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "paralelos/adicionar_paralelo";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_paralelo")
	@SuppressWarnings("deprecation")
	public String guardar_paralelo(HttpServletRequest request, Model model, @ModelAttribute("paralelo") Paralelo paralelo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Paralelo.adicionar(paralelo);;
				model.addAttribute("paralelos", serv_Paralelo.listar_estado(1));
			return "redirect:../paralelos/gestion_paralelos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_paralelo")
	public String modificar_paralelo(HttpServletRequest request, Model model, String codparalelo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("paralelo", serv_Paralelo.obtener_por_Codparalelo(codparalelo));
			return "paralelos/modificar_paralelo";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_paralelo")
	@SuppressWarnings("deprecation")
	public String actualizar_paralelo(HttpServletRequest request, Model model, @ModelAttribute("paralelo") Paralelo paralelo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Paralelo.modificar(paralelo);;
				model.addAttribute("paralelos", serv_Paralelo.listar_estado(1));
			return "redirect:../paralelos/gestion_paralelos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_paralelo")
	@SuppressWarnings("deprecation")
	public String eliminar_paralelo(HttpServletRequest request, Model model, String codparalelo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Paralelo.borrado_log(codparalelo);;
			
			model.addAttribute("paralelos", serv_Paralelo.listar());
			return "redirect:../paralelos/gestion_paralelos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("activar_paralelo")
	@SuppressWarnings("deprecation")
	public String activar_paralelo(HttpServletRequest request, Model model, String codparalelo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Paralelo.activar(codparalelo);
			
			model.addAttribute("paralelos", serv_Paralelo.listar());
			return "redirect:../paralelos/gestion_paralelos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("ver_paralelo")
	public String ver_paralelo(HttpServletRequest request, Model model, String codparalelo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("paralelo", serv_Paralelo.obtener_por_Codparalelo(codparalelo));
			return "paralelos/ver_paralelo";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
}