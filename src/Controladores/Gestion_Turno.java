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
import Modelos.Turno;
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
import Servicios.Serv_Turno;
@Controller
@RequestMapping("/turnos/*")
public class Gestion_Turno {
	
	@Autowired
	private Serv_Turno serv_Turno;

	@RequestMapping("gestion_turnos")
	public String gestion_turnos(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("turnos", serv_Turno.listar());
			return "turnos/gestion_turnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("adicionar_turno")
	public String adicionar_turno(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			return "turnos/adicionar_turno";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_turno")
	@SuppressWarnings("deprecation")
	public String guardar_turno(HttpServletRequest request, Model model, @ModelAttribute("turno") Turno turno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Turno.adicionar(turno);
				model.addAttribute("turnos", serv_Turno.listar_estado(1));
			return "redirect:../turnos/gestion_turnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_turno")
	public String modificar_turno(HttpServletRequest request, Model model, String codturno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("turno", serv_Turno.obtener_por_Codturno(codturno));
			return "turnos/modificar_turno";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_turno")
	@SuppressWarnings("deprecation")
	public String actualizar_turno(HttpServletRequest request, Model model, @ModelAttribute("turno") Turno turno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
				serv_Turno.modificar(turno);
				model.addAttribute("turnos", serv_Turno.listar_estado(1));
			return "redirect:../turnos/gestion_turnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("eliminar_turno")
	@SuppressWarnings("deprecation")
	public String eliminar_turno(HttpServletRequest request, Model model, String codturno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Turno.borrado_log(codturno);
			
			model.addAttribute("turnos", serv_Turno.listar());
			return "redirect:../turnos/gestion_turnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("activar_turno")
	@SuppressWarnings("deprecation")
	public String activar_turno(HttpServletRequest request, Model model, String codturno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Turno.activar(codturno);
			
			model.addAttribute("turnos", serv_Turno.listar());
			return "redirect:../turnos/gestion_turnos";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	@RequestMapping("ver_turno")
	public String ver_grado(HttpServletRequest request, Model model, String codturno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("turno", serv_Turno.obtener_por_Codturno(codturno));
			return "turnos/ver_turno";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
}