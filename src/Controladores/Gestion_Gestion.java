package Controladores;



import javax.servlet.http.HttpServletRequest;

import Modelos.Gestion;
import Modelos.Periodo;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import Servicios.Serv_Gestion;
import Servicios.Serv_Periodo;

@Controller
@RequestMapping("/gestiones/*")
public class Gestion_Gestion {
	
	@Autowired
	private Serv_Gestion serv_Gestion;

	@RequestMapping("gestion_gestiones")
	public String gestion_periodos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("gestiones", serv_Gestion.listar());
			return "gestiones/gestion_gestiones";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_gestion")
	public String adicionar_turno(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "gestiones/adicionar_gestion";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_gestion")
	@SuppressWarnings("deprecation")
	public String guardar_gestion(HttpServletRequest request, Model model, @ModelAttribute("gestion") Gestion gestion){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			serv_Gestion.adicionar(gestion);
				model.addAttribute("gestiones", serv_Gestion.listar_estado(1));
			return "redirect:../gestiones/gestion_gestiones";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_gestion")
	public String modificar_gestion(HttpServletRequest request, Model model, String codgestion){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("gestiones", serv_Gestion.obtener_por_codgestion(codgestion));
			return "gestiones/modificar_gestion";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_gestion")
	@SuppressWarnings("deprecation")
	public String actualizar_gestion(HttpServletRequest request, Model model, @ModelAttribute("gestion") Gestion gestion){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Gestion.modificar(gestion);
				model.addAttribute("gestiones", serv_Gestion.listar_estado(1));
			return "redirect:../gestiones/gestion_gestiones";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_gestion")
	@SuppressWarnings("deprecation")
	public String eliminar_gestion(HttpServletRequest request, Model model, String codgestion){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Gestion.borrado_log(codgestion);
			
			model.addAttribute("gestiones", serv_Gestion.listar());
			return "redirect:../gestiones/gestion_gestiones";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("activar_gestion")
	@SuppressWarnings("deprecation")
	public String activar_gestion(HttpServletRequest request, Model model, String codgestion){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Gestion.activar(codgestion);
			
			model.addAttribute("gestiones", serv_Gestion.listar());
			return "redirect:../gestiones/gestion_gestiones";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("ver_gestion")
	public String ver_gestion(HttpServletRequest request, Model model, String codgestion){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("gestiones", serv_Gestion.obtener_por_codgestion(codgestion));
			return "gestiones/ver_gestion";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

}