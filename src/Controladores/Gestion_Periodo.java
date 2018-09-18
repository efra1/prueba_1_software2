package Controladores;



import javax.servlet.http.HttpServletRequest;

import Modelos.Periodo;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


import Servicios.Serv_Periodo;

@Controller
@RequestMapping("/periodos/*")
public class Gestion_Periodo {
	
	@Autowired
	private Serv_Periodo serv_Periodo;

	@RequestMapping("gestion_periodos")
	public String gestion_periodos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("periodos", serv_Periodo.listar());
			return "periodos/gestion_periodos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_periodo")
	public String adicionar_turno(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "periodos/adicionar_periodo";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_periodo")
	@SuppressWarnings("deprecation")
	public String guardar_periodo(HttpServletRequest request, Model model, @ModelAttribute("periodo") Periodo periodo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			System.out.println("fechas"+periodo.getFecha_fin());
			
				serv_Periodo.adicionar(periodo);
				model.addAttribute("periodos", serv_Periodo.listar_estado(1));
			return "redirect:../periodos/gestion_periodos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_periodo")
	public String modificar_periodo(HttpServletRequest request, Model model, String codperiodo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("periodo", serv_Periodo.obtener_por_Codperiodo(codperiodo));
			return "periodos/modificar_periodo";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_periodo")
	@SuppressWarnings("deprecation")
	public String actualizar_periodo(HttpServletRequest request, Model model, @ModelAttribute("periodo") Periodo periodo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Periodo.modificar(periodo);
				model.addAttribute("periodos", serv_Periodo.listar_estado(1));
			return "redirect:../periodos/gestion_periodos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_periodo")
	@SuppressWarnings("deprecation")
	public String eliminar_periodo(HttpServletRequest request, Model model, String codperiodo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Periodo.borrado_log(codperiodo);
			
			model.addAttribute("periodos", serv_Periodo.listar());
			return "redirect:../periodos/gestion_periodos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("activar_periodo")
	@SuppressWarnings("deprecation")
	public String activar_periodo(HttpServletRequest request, Model model, String codperiodo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Periodo.activar(codperiodo);
			
			model.addAttribute("periodos", serv_Periodo.listar());
			return "redirect:../periodos/gestion_periodos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("ver_periodo")
	public String ver_periodo(HttpServletRequest request, Model model, String codperiodo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("periodo", serv_Periodo.obtener_por_Codperiodo(codperiodo));
			return "periodos/ver_periodo";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

}