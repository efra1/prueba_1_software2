package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Proceso;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Menu;
import Servicios.Serv_Proceso;

@Controller
@RequestMapping("/procesos/*")
public class Gestion_Procesos {
	
	@Autowired
	private Serv_Proceso serv_Proceso;
	@Autowired
	private Serv_Menu serv_Menu;
	
	@RequestMapping("gestion_procesos")
	public String gestion_procesos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("procesos", serv_Proceso.listar());
			return "procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("gestion_procesos_est")
	public String gestion_procesos_est(HttpServletRequest request, Model model,int est){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("procesos", serv_Proceso.listar_estado(est));
			return "procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("adicionar_proceso")
	public String adicionar_proceso(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "procesos/adicionar_proceso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_proceso")
	@SuppressWarnings("deprecation")
	public String guardar_proceso(HttpServletRequest request, Model model, @ModelAttribute("pr") Proceso pr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
				serv_Proceso.adicionar(pr);
				model.addAttribute("procesos", serv_Proceso.listar_estado(1));
				return "procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_proceso")
	public String modificar_proceso(HttpServletRequest request, Model model, Integer codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("pr", serv_Proceso.obtener_por_Codp(codp));
			return "procesos/modificar_proceso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_proceso")
	@SuppressWarnings("deprecation")
	public String actualizar_proceso(HttpServletRequest request, Model model, @ModelAttribute("pr") Proceso pr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
				serv_Proceso.modificar(pr);
			
				model.addAttribute("procesos", serv_Proceso.listar());
				return "procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_proceso")
	@SuppressWarnings("deprecation")
	public String eliminar_proceso(HttpServletRequest request, Model model, Integer codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
				serv_Proceso.dar_de_baja(codp);
				model.addAttribute("procesos", serv_Proceso.listar());
				return "procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("activar_proceso")
	@SuppressWarnings("deprecation")
	public String activar_proceso(HttpServletRequest request, Model model, Integer codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
				serv_Proceso.dar_de_alta(codp);
				model.addAttribute("procesos", serv_Proceso.listar_estado(1));
				return "procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("ver_proceso")
	public String ver_proceso(HttpServletRequest request, Model model, Integer codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("pr", serv_Proceso.obtener_por_Codp(codp));
			return "procesos/ver_proceso";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("asignar_menus")
	public String asignar_menus(HttpServletRequest request, Model model, Integer codp){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("proceso", serv_Proceso.obtener_por_Codp(codp));
			model.addAttribute("menus", serv_Menu.menus_sin_asignar_a_proceso(codp));
			return "procesos/asignar_menus";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_menus")
	public String guardar_menus(HttpServletRequest request, Model model, Integer codp, Integer[] codmenu){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Proceso.eliminar_de_proces_menu(codp);
			if(codmenu!=null){
				for(int i: codmenu) serv_Proceso.asignar_menus_a_proceso(codp, i);
			}
			return "redirect:../procesos/gestion_procesos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
}