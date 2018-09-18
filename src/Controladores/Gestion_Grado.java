package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

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

import Servicios.Serv_Especialidad;
import Servicios.Serv_Grado;
import Servicios.Serv_Proceso;
import Servicios.Serv_Rol;
@Controller
@RequestMapping("/grados/*")
public class Gestion_Grado {
	
	@Autowired
	private Serv_Grado serv_Grado;

	@RequestMapping("gestion_grados")
	public String gestion_grados(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("grados", serv_Grado.listar());
			return "grados/gestion_grados";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_grado")
	public String adicionar_grado(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "grados/adicionar_grado";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_grado")
	@SuppressWarnings("deprecation")
	public String guardar_grado(HttpServletRequest request, Model model, @ModelAttribute("grado") Item grado){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Grado.adicionar(grado);
				model.addAttribute("grados", serv_Grado.listar_estado(1));
			return "redirect:../grados/gestion_grados";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_grado")
	public String modificar_grado(HttpServletRequest request, Model model, String codgrado){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("grado", serv_Grado.obtener_por_Codgrado(codgrado));
			return "grados/modificar_grado";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_grado")
	@SuppressWarnings("deprecation")
	public String actualizar_grado(HttpServletRequest request, Model model, @ModelAttribute("grado") Item grado){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Grado.modificar(grado);
				model.addAttribute("grados", serv_Grado.listar_estado(1));
			return "redirect:../grados/gestion_grados";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_grado")
	@SuppressWarnings("deprecation")
	public String eliminar_grado(HttpServletRequest request, Model model, String codgrado){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Grado.borrado_log(codgrado);
			
			model.addAttribute("grados", serv_Grado.listar());
			return "redirect:../grados/gestion_grados";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("activar_grado")
	@SuppressWarnings("deprecation")
	public String activar_grado(HttpServletRequest request, Model model, String codgrado){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Grado.activar(codgrado);
			
			model.addAttribute("grados", serv_Grado.listar());
			return "redirect:../grados/gestion_grados";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
	@RequestMapping("ver_grado")
	public String ver_grado(HttpServletRequest request, Model model, String codgrado){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("grado", serv_Grado.obtener_por_Codgrado(codgrado));
			return "grados/ver_grado";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
}