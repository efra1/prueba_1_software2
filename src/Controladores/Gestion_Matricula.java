package Controladores;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import Modelos.Alumno;
import Modelos.Apoderado;
import Modelos.Aspectos_sociales;
import Modelos.Clase;
import Modelos.Curso;
import Modelos.Direccion;
import Modelos.Especialidad;
import Modelos.Item;
import Modelos.Matricula;
import Modelos.Opciones;
import Modelos.Parentesco;
import Modelos.Rol;
import Modelos.Usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import Servicios.Serv_Alumno;
import Servicios.Serv_Apoderado;
import Servicios.Serv_Aspectos_Sociales;
import Servicios.Serv_Clase;
import Servicios.Serv_Curso;
import Servicios.Serv_Direccion;
import Servicios.Serv_Especialidad;
import Servicios.Serv_Gestion;
import Servicios.Serv_Grado;
import Servicios.Serv_Horario;
import Servicios.Serv_Materia;
import Servicios.Serv_Matricula;
import Servicios.Serv_Nivel;
import Servicios.Serv_Opciones;
import Servicios.Serv_Paralelo;
import Servicios.Serv_Parentesco;
import Servicios.Serv_Proceso;
import Servicios.Serv_Profesor;
import Servicios.Serv_Rol;
import Servicios.Serv_Turno;
@Controller
@RequestMapping("/matriculas/*")
public class Gestion_Matricula {
	
	@Autowired
	private Serv_Matricula serv_Matricula;
	
	@Autowired
	private Serv_Curso serv_Curso;
	
	@Autowired
	private Serv_Alumno serv_Alumno;
	
	@Autowired
	private Serv_Gestion serv_Gestion;
	
	@Autowired
	private Serv_Parentesco serv_Parentesco;
	
	@Autowired
	private Serv_Aspectos_Sociales serv_Aspectos_Sociales;
	
	@Autowired
	private Serv_Opciones serv_Opciones;
	
	
	@Autowired
	private Serv_Direccion serv_Direccion;
	@Autowired
	private Serv_Apoderado serv_Apoderado;
	@RequestMapping("gestion_matriculas")
	public String gestion_matriculas(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			model.addAttribute("gestiones",serv_Gestion.listar());
			model.addAttribute("matriculas",serv_Matricula.listar());
			return "matriculas/gestion_matriculas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("adicionar_matricula")
	public String adicionar_matricula(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			model.addAttribute("cursos",serv_Curso.listar_estado(1));
			model.addAttribute("gestiones",serv_Gestion.listar());
	  
      
      
     return "matriculas/adicionar_matricula";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("guardar_matricula")
	@SuppressWarnings("deprecation")
	public String guardar_matricula(HttpServletRequest request, Model model, @ModelAttribute("alumno") Alumno alumno, Model model2, @ModelAttribute("matricula") Matricula mat, Model model3, @ModelAttribute("direccion") Direccion dir, Model model4, @ModelAttribute("aspectos_sociales") Aspectos_sociales asp,String serv_basicos[],String idiomas[],String acceso_medios[], Model model5, @ModelAttribute("apoderado") Apoderado apo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			
			
			String codigoalumno=serv_Alumno.generar_Codigo();
				
			alumno.setCodalumno(codigoalumno);
			serv_Alumno.adicionar(alumno);
				
				
				
				mat.setMonto(0);
				mat.setCodalumno(alumno.getCodalumno());
				mat.setCi(user.getCi());
				serv_Matricula.adicionar(mat);
				
						
				
				dir.setCodalumno(codigoalumno);
				serv_Direccion.adicionar(dir);
						
				
				asp.setCodalumno(codigoalumno);
				
				String codasp=serv_Aspectos_Sociales.generar_Cod();
				asp.setCodaspectos(codasp);
				serv_Aspectos_Sociales.adicionar(asp);
				
				for (int i = 0; i < acceso_medios.length; i++) {
					serv_Opciones.adicionar(serv_Opciones.generar_Cod(),codasp,"ACCESO A MEDIOS",acceso_medios[i]);
				}
				
				
				for (int i = 0; i < idiomas.length; i++) {
					serv_Opciones.adicionar(serv_Opciones.generar_Cod(),codasp,"IDIOMAS",idiomas[i]);
				}
				
				
				
				for (int i = 0; i < serv_basicos.length; i++) {
					serv_Opciones.adicionar(serv_Opciones.generar_Cod(),codasp,"SERV_BASICOS",serv_basicos[i]);
					
				}	

				
				
				String codapoderado1=serv_Apoderado.generar_Codigo();
				apo.setCodapoderado(codapoderado1);
				serv_Apoderado.adicionar(apo);
				
				Parentesco p=new Parentesco();
					
					p.setCodalumno(codigoalumno);
				p.setCodapoderado(codapoderado1);
				
				p.setCodparentesco(serv_Parentesco.generar_Codigo());
				
				serv_Parentesco.adicionar(p);
				
				
				model.addAttribute("matriculas",serv_Matricula.listar_estado(1));
				model.addAttribute("gestiones",serv_Gestion.listar());
			return "redirect:../matriculas/gestion_matriculas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("modificar_matricula")
	public String modificar_curso(HttpServletRequest request, Model model, String codmatricula){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			System.out.println("codigo de matricula es "+ codmatricula);
			Matricula mat=serv_Matricula.obtener_por_Codgrado(codmatricula);
			Alumno al=serv_Alumno.obtener_por_codalumno(mat.getCodalumno());
			model.addAttribute("gestiones",serv_Gestion.listar());
			System.out.println("alumno es  "+ mat.getCodalumno());
			model.addAttribute("cursos",serv_Curso.listar_estado(1));
			model.addAttribute("alumno", al);
			Direccion dir=serv_Direccion.obtener_por_alumono(al.getCodalumno());
			model.addAttribute("direccion",dir);
			model.addAttribute("matricula", mat);
			System.out.println("direccion es "+ dir.getDepartamento());
			
			Aspectos_sociales as=serv_Aspectos_Sociales.obtener_por_alumono(al.getCodalumno());
			model.addAttribute("aspectos",as);
		
			
			System.out.println("uno de los aspectos sociales son ;"+ as.getEtimologia());
			
		Parentesco p =serv_Parentesco.obtener_por_codalumno(mat.getCodalumno());
		model.addAttribute("apoderado", serv_Apoderado.obtener_por_Codprofesor(p.getCodapoderado()));
			
		return "matriculas/modificar_matricula";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("actualizar_matricula")
	@SuppressWarnings("deprecation")
	public String actualizar_matricula(HttpServletRequest request, Model model, @ModelAttribute("alumno") Alumno alumno, Model model2, @ModelAttribute("matricula") Matricula mat, Model model3, @ModelAttribute("direccion") Direccion dir, Model model4, @ModelAttribute("aspectos_sociales") Aspectos_sociales asp, Model model5, @ModelAttribute("apoderado") Apoderado apo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		alumno.setEstado(1);
				serv_Alumno.modificar(alumno);
				
				mat.setCodalumno(alumno.getCodalumno());
				serv_Matricula.modificar(mat);
					
				
				serv_Apoderado.modificar(apo);
				
				dir.setCodalumno(alumno.getCodalumno());
				serv_Direccion.modificar(dir);
				asp.setCodalumno(alumno.getCodalumno());
				serv_Aspectos_Sociales.modificar(asp);
				
				model.addAttribute("gestiones",serv_Gestion.listar());
				model.addAttribute("matriculas",serv_Matricula.listar());
			return "redirect:../matriculas/gestion_matriculas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
	
	
	@RequestMapping("eliminar_matricula")
	@SuppressWarnings("deprecation")
	public String eliminar_matricula(HttpServletRequest request, Model model, String codmatricula){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Matricula.borrado_log(codmatricula);
			
			model.addAttribute("matriculas", serv_Matricula.listar());
			return "redirect:../matriculas/gestion_matriculas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	

	@RequestMapping("activar_matricula")
	@SuppressWarnings("deprecation")
	public String activar_matricula(HttpServletRequest request, Model model, String codmatricula){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			serv_Matricula.activar(codmatricula);
			
			model.addAttribute("matriculas", serv_Matricula.listar());
			return "redirect:../matriculas/gestion_matriculas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("reinscribir_alumno")
	@SuppressWarnings("deprecation")
	public String reinscribir_alumno(HttpServletRequest request, Model model, String rude,String codgestion){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			System.out.println(rude);
			System.out.println(codgestion);
			Alumno al=serv_Alumno.obtener_por_rud(rude);
			
			//recuperando exmatricula
			System.out.println(al.getCodalumno());
			Matricula Mat =serv_Matricula.obtener_por_alumno_gestion(al.getCodalumno(), codgestion);
			
			
			model.addAttribute("alumno",al);
			model.addAttribute("matricula",Mat);
			
			Direccion dir=serv_Direccion.obtener_por_alumono(al.getCodalumno());
			model.addAttribute("direccion",dir);
			
			Aspectos_sociales as=serv_Aspectos_Sociales.obtener_por_alumono(al.getCodalumno());
			model.addAttribute("aspectos",as);
			
			Parentesco p =serv_Parentesco.obtener_por_codalumno(Mat.getCodalumno());
			model.addAttribute("apoderado", serv_Apoderado.obtener_por_Codprofesor(p.getCodapoderado()));
			
			model.addAttribute("gestiones",serv_Gestion.listar());
			model.addAttribute("cursos",serv_Curso.listar_estado(1));
			
			
			return "matriculas/reinscribir_alumno";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	

	@RequestMapping("reinscribir_matricula")
	@SuppressWarnings("deprecation")
	public String reinscribir_matricula(HttpServletRequest request, Model model, @ModelAttribute("matricula") Matricula mat,String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
		
			
				
				
				
				mat.setMonto(0);
				mat.setCodalumno(codalumno);
				mat.setCi(user.getCi());
				serv_Matricula.adicionar(mat);
				
				model.addAttribute("gestiones",serv_Gestion.listar());
				model.addAttribute("matriculas",serv_Matricula.listar_estado(1));
			return "redirect:../matriculas/gestion_matriculas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	
	@RequestMapping("ver_matricula")
	public String ver_curso(HttpServletRequest request, Model model, String codmatricula){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			Matricula mat=serv_Matricula.obtener_por_Codgrado(codmatricula);
			
			model.addAttribute("cursos",serv_Curso.listar_estado(1));
			model.addAttribute("alumno", serv_Alumno.obtener_por_codalumno(mat.getCodalumno()));
		model.addAttribute("matricula", mat);
		
		Parentesco p =serv_Parentesco.obtener_por_codalumno(mat.getCodalumno());
		model.addAttribute("apoderado", serv_Apoderado.obtener_por_Codprofesor(p.getCodapoderado()));
			
		return "matriculas/ver_matricula";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("prueba")
	@ResponseBody
	public String desactivar(@RequestParam String ci){
		System.out.println("llega "+ci);
		try {
			boolean existe=serv_Alumno.existe_alu(ci);
			System.out.println("estad"+existe);
			if(existe==false)
				return "200";
			else
				return "300";
		} catch (Exception e) {
			return "500";
		}
		
	}
}