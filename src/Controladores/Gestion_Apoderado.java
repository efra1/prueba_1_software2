package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Clase;
import Modelos.Curso;
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

import Servicios.Serv_Alumno;
import Servicios.Serv_Apoderado;
import Servicios.Serv_Clase;
import Servicios.Serv_Curso;
import Servicios.Serv_Especialidad;
import Servicios.Serv_Grado;
import Servicios.Serv_Horario;
import Servicios.Serv_Materia;
import Servicios.Serv_Matricula;
import Servicios.Serv_Nivel;
import Servicios.Serv_Paralelo;
import Servicios.Serv_Proceso;
import Servicios.Serv_Profesor;
import Servicios.Serv_Rol;
import Servicios.Serv_Turno;
@Controller
@RequestMapping("/apoderados/*")
public class Gestion_Apoderado {
	
	@Autowired
	private Serv_Apoderado serv_Apoderado;
	@RequestMapping("gestion_apoderados")
	public String gestion_apoderados(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("apoderados",serv_Apoderado.listar_estado(1));
			return "apoderados/gestion_apoderados";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("eliminar_apoderado")
	@SuppressWarnings("deprecation")
	public String eliminar_apoderado(HttpServletRequest request, Model model, String codapoderado){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Apoderado.borrado_log(codapoderado);
			
			model.addAttribute("apoderados", serv_Apoderado.listar());
			return "redirect:../apoderados/gestion_apoderados";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("activar_apoderado")
	@SuppressWarnings("deprecation")
	public String activar_apoderado(HttpServletRequest request, Model model, String codapoderado){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Apoderado.activar(codapoderado);
			
			model.addAttribute("apoderados", serv_Apoderado.listar());
			return "redirect:../apoderados/gestion_apoderados";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
}