


package Controladores;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modelos.Alumno;
import Modelos.Asistencia;
import Modelos.Clase;
import Modelos.Detalle_nota;
import Modelos.Notas;
import Modelos.Periodo;
import Modelos.Profesor;
import Modelos.Sub_Detalle;
import Modelos.Usuario;

import org.apache.velocity.runtime.directive.Parse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import Servicios.Serv_Alumno;
import Servicios.Serv_Asistencia;
import Servicios.Serv_Clase;
import Servicios.Serv_Curso;
import Servicios.Serv_Detalle;
import Servicios.Serv_Detalle_Nota;
import Servicios.Serv_Notas;
import Servicios.Serv_Periodo;
import Servicios.Serv_Profesor;
import Servicios.Serv_Sub_Detalle;
import herramientas.GeneradorReportes;
@Controller
@RequestMapping("/asistencias/*")
public class Gestion_Asistencia {
	
	@Autowired
	private Serv_Notas serv_Notas;
	@Autowired
	private Serv_Clase serv_Clase;
	@Autowired
	private Serv_Profesor serv_Profesor;
	
	@Autowired
	private Serv_Alumno serv_Alumno;
	@Autowired
	private Serv_Detalle_Nota serv_Detalle_Nota;
	@Autowired
	private Serv_Asistencia serv_Asistencia;
	
	
	@Autowired
	private Serv_Sub_Detalle serv_Sub_Detalle;
	
	
	@Autowired
	private Serv_Curso serv_Curso;
	
	@Autowired
	private Serv_Periodo serv_periodo;
	
	@RequestMapping("entra_profesor")
	public String entra_profesor(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("profesores",serv_Profesor.listar_estado(1) );
			return "asistencias/entra_profesor";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("cursos_profesor")
	public String cursos_profesor(HttpServletRequest request, Model model,String rud){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			if(serv_Profesor.existe_prof_RUD(rud)==true){	
			Profesor p=serv_Profesor.obtener_por_rud(rud);
			
			model.addAttribute("clases", serv_Clase.listar_clases_de_profesor(p.getCodprofesor()));
			return "asistencias/cursos_profesor";
			}
			else {

				model.addAttribute("error", " RUD INEXISTENTE, VERIFIQUE SUS DATOS");
				
				return "asistencias/entra_profesor";
			}
			
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("lista_clase")
	public String lista_clase(HttpServletRequest request, Model model,String codclase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			System.out.println(codclase);
			
			Clase cl=new Clase();
			cl=serv_Clase.obtener_por_Codclase(codclase);
			
			model.addAttribute("alumnos", serv_Alumno.listar_por_curso(cl.getCodcurso()));
			model.addAttribute("codclase",codclase);
			
			return "asistencias/lista_clase";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
		

	@RequestMapping("guardar_asistencia")
	public String guardar_asistencia(HttpServletRequest request, Model model,String codclase,String asistencia[],String codalumno[],String fecha){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			System.out.println(codclase);
			System.out.println(fecha);
			
			
			for (int i = 0; i < codalumno.length; i++) {
				System.out.println(codalumno[i]);
				System.out.println(asistencia[i]);
				
			Asistencia as=new Asistencia();
				String cod=serv_Asistencia.generar_Codigo();
				as.setCodalumno(codalumno[i]);
				as.setAsistencia(asistencia[i]);
			as.setCodasistencia(cod);
			as.setCodclase(codclase);
			as.setFecha(fecha);
		
			serv_Asistencia.adicionar(as);
			
				
			}
			
			model.addAttribute("msg", "assitencias adicionadas exitosamente");
			model.addAttribute("url", "asistencias/entra_profesor");
			return "principal/mensajes";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("detalle_asistencia")
	public String detalle_asistencia(HttpServletRequest request, Model model,String codclase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
		
			Clase cl=new Clase();
			cl=serv_Clase.obtener_por_Codclase(codclase);
			
			model.addAttribute("alumnos", serv_Alumno.listar_por_curso(cl.getCodcurso()));
			model.addAttribute("periodos", serv_periodo.listaractivos());
			
			model.addAttribute("codclase",codclase);
		
			return "asistencias/detalle_asistencias";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	@RequestMapping("ver_detalle")
	public String ver_detalle(HttpServletRequest request, Model model,String codclase,String codperiodo,String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			Periodo p=serv_periodo.obtener_por_Codperiodo(codperiodo);
			
			
		
			Clase s=serv_Clase.obtener_por_Codclase(codclase);
			model.addAttribute("asistencias", serv_Asistencia.listar_asistencia_alumno_periodo(codalumno, codclase, p.getFecha_inicio(),p.getFecha_fin()));
			model.addAttribute("faltas",serv_Asistencia.obtenerfaltas(codalumno, codclase, p.getFecha_inicio(),p.getFecha_fin()) );
			model.addAttribute("total",serv_Asistencia.obtenertotal_asistenci(codalumno, codclase, p.getFecha_inicio(),p.getFecha_fin()) );
			model.addAttribute("presentes",serv_Asistencia.obtenerpresentes(codalumno, codclase, p.getFecha_inicio(),p.getFecha_fin()) );
			model.addAttribute("restrasos",serv_Asistencia.obtenerretrasos(codalumno, codclase, p.getFecha_inicio(),p.getFecha_fin()) );
			model.addAttribute("permisos",serv_Asistencia.obtenerpersmisos(codalumno, codclase, p.getFecha_inicio(),p.getFecha_fin()) );
			model.addAttribute("clase",s);
			model.addAttribute("codclase",codclase);
			model.addAttribute("alumno",serv_Alumno.obtener_por_codalumno(codalumno));
			return "asistencias/ver_detalle";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
}