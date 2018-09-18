package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Rol;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Proceso;
import Servicios.Serv_Rol;
@Controller
@RequestMapping("/roles/*")
public class Gestion_Roles {
	
	@Autowired
	private Serv_Rol serv_Rol;
	@Autowired
	private Serv_Proceso serv_Proceso;
	
	@RequestMapping("gestion_roles")
	public String gestion_roles(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("roles", serv_Rol.listar());
			return "roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("gestion_roles_est")
	public String gestion_roles_est(HttpServletRequest request, Model model,int est){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			
			model.addAttribute("roles", serv_Rol.listar_estado(est));
			return "roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("adicionar_rol")
	public String adicionar_rol(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "roles/adicionar_rol";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_rol")
	@SuppressWarnings("deprecation")
	public String guardar_rol(HttpServletRequest request, Model model, @ModelAttribute("rol") Rol rol){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Rol.adicionar(rol);
				model.addAttribute("roles", serv_Rol.listar_estado(1));
			return "redirect:../roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_rol")
	public String modificar_rol(HttpServletRequest request, Model model, Integer codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("rol", serv_Rol.obtener_por_Codr(codr));
			return "roles/modificar_rol";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_rol")
	@SuppressWarnings("deprecation")
	public String actualizar_rol(HttpServletRequest request, Model model, @ModelAttribute("rol") Rol rol){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Rol.modificar(rol);
				model.addAttribute("roles", serv_Rol.listar_estado(1));
			return "redirect:../roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_rol")
	@SuppressWarnings("deprecation")
	public String eliminar_rol(HttpServletRequest request, Model model, Integer codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Rol.eliminar(codr);
			
			model.addAttribute("roles", serv_Rol.listar_estado(1));
			return "redirect:../roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
	
	@RequestMapping("ver_rol")
	public String ver_rol(HttpServletRequest request, Model model, Integer codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("rol", serv_Rol.obtener_por_Codr(codr));
			return "roles/ver_rol";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("asignar_procesos")
	public String asignar_procesos(HttpServletRequest request, Model model, Integer codr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("rol", serv_Rol.obtener_por_Codr(codr));
			model.addAttribute("procesos", serv_Proceso.procesos_sin_asignar_a_Rol(codr));
			return "roles/asignar_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_procesos")
	public String guardar_procesos(HttpServletRequest request, Model model, Integer codr, Integer[] codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Rol.eliminar_de_rolpro(codr);
			if(codp!=null){
				for(int i: codp) serv_Rol.asignar_procesos_a_Rol(codr, i);
			}
			return "redirect:../roles/gestion_roles";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
}