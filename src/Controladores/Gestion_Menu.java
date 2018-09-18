package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Menu;
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
@RequestMapping("/menus/*")
public class Gestion_Menu {
	
	@Autowired
	private Serv_Menu serv_Menu;
	
	
	@RequestMapping("gestion_menus")
	public String gestion_menus(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			model.addAttribute("menus", serv_Menu.listar());
			return "menus/gestion_menus";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("adicionar_menu")
	public String adicionar_menu(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "menus/adicionar_menu";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_menu")
	@SuppressWarnings("deprecation")
	public String guardar_menu(HttpServletRequest request, Model model, @ModelAttribute("pr") Menu pr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
				
				serv_Menu.adicionar(pr);
			
			return "redirect:../menus/gestion_menus";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_menu")
	public String modificar_menu(HttpServletRequest request, Model model, Integer codmenu){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("pr", serv_Menu.obtener_por_Codmenu(codmenu));
			return "menus/modificar_menu";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_menu")
	@SuppressWarnings("deprecation")
	public String actualizar_menu(HttpServletRequest request, Model model, @ModelAttribute("pr") Menu pr){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
				serv_Menu.modificar(pr);
			
			return "redirect:../menus/gestion_menus";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_menu")
	@SuppressWarnings("deprecation")
	public String eliminar_menu(HttpServletRequest request, Model model, Integer codmenu){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Menu.dar_de_baja(codmenu);
			return "redirect:../menus/gestion_menus";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("activar_menu")
	@SuppressWarnings("deprecation")
	public String activar_menu(HttpServletRequest request, Model model, Integer codmenu){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Menu.dar_de_alta(codmenu);
			return "redirect:../menus/gestion_menus";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("ver_menu")
	public String ver_menu(HttpServletRequest request, Model model, Integer codmenu){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("pr", serv_Menu.obtener_por_Codmenu(codmenu));
			return "menus/ver_menu";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
}