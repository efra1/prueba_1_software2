package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import Modelos.Rol;
import Modelos.Tutoria;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Curso;
import Servicios.Serv_Parentesco;
import Servicios.Serv_Proceso;
import Servicios.Serv_Profesor;
import Servicios.Serv_Rol;
import Servicios.Serv_Tutoria;
@Controller
@RequestMapping("/tutores/*")
public class Gestion_Tutores {
	
	@Autowired
	private Serv_Profesor serv_Profesor;
	@Autowired
	private Serv_Curso serv_Curso;
	
	@Autowired
	private Serv_Tutoria serv_Tutoria;
	@RequestMapping("gestion_tutores")
	public String gestion_tutores(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("cursos", serv_Curso.listar_estado(1));
			return "tutores/gestion_tutores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("asignar_tutores")
	public String asignar_tutores(HttpServletRequest request, Model model,String codcurso){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("codcurso", codcurso);
			model.addAttribute("profesores", serv_Profesor.tutores_de_curso(codcurso));
			model.addAttribute("noprofesores",serv_Profesor.tutores_sin_curso(codcurso));
			
			return "tutores/profesores_tutores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_tutores")
	public String guardar_tutores(HttpServletRequest request, Model model,String codcurso, String[] codprofesor){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			serv_Profesor.eliminar_usurol(codcurso);
			
			for (int i = 0; i < codprofesor.length; i++) {
				
				Tutoria t=new Tutoria();
				
				t.setCodprofesor(codprofesor[i]);
				t.setCodcurso(codcurso);
				serv_Tutoria.adicionar(t);
				
			}
			
			
			return "redirect:../tutores/gestion_tutores";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
}