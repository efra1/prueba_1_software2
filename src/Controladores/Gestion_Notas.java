package Controladores;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Modelos.Alumno;
import Modelos.Clase;
import Modelos.Detalle_nota;
import Modelos.Notas;
import Modelos.Profesor;
import Modelos.Sub_Detalle;
import Modelos.Usuario;

import org.apache.velocity.runtime.directive.Parse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import Servicios.Serv_Alumno;
import Servicios.Serv_Clase;
import Servicios.Serv_Curso;
import Servicios.Serv_Detalle;
import Servicios.Serv_Detalle_Nota;
import Servicios.Serv_Item;
import Servicios.Serv_Notas;
import Servicios.Serv_Periodo;
import Servicios.Serv_Profesor;
import Servicios.Serv_Sub_Detalle;
import herramientas.GeneradorReportes;
@Controller
@RequestMapping("/notas/*")
public class Gestion_Notas {
	
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
	private Serv_Item Serv_Item;
	@Autowired
	private Serv_Sub_Detalle serv_Sub_Detalle;
	
	
	@Autowired
	private Serv_Curso serv_Curso;
	
	@Autowired
	private Serv_Periodo serv_periodo;
	
	@RequestMapping("notas")
	public String notas(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			model.addAttribute("profesores",serv_Profesor.listar_estado(1) );
			return "notas/notas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("notas_profesor")
	public String notas_profesor(HttpServletRequest request, Model model,String rud){
		Usuario user = (Usuario)request.getSession().getAttribute("user");

		if(user!=null){
			
			if(serv_Profesor.existe_prof_RUD(rud)==true){	
				Profesor p=serv_Profesor.obtener_por_rud(rud);
			model.addAttribute("clases", serv_Clase.listar_clases_de_profesor(p.getCodprofesor()));
			return "notas/profesores_cursos";
			
			}
			
			else {

				model.addAttribute("error", " RUD INEXISTENTE, VERIFIQUE SUS DATOS");
				
				return "notas/notas";
			}
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("ver_lista_clase")
	public String ver_lista_clase(HttpServletRequest request, Model model,String codclase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			System.out.println(codclase);
			
			Clase cl=new Clase();
			cl=serv_Clase.obtener_por_Codclase(codclase);
			
			model.addAttribute("alumnos", serv_Alumno.listar_por_curso(cl.getCodcurso()));
			model.addAttribute("codclase",codclase);
			
			return "notas/ver_lista_clase";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	@RequestMapping("cargar_notas_alumno")
	public String cargar_notas_alumno(HttpServletRequest request, Model model,String codclase,String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			System.out.println("codigo de clsesentrante a metodo"+codclase);

			System.out.println("codigo de alumno a metodo"+codalumno);
			
			Clase cl1=new Clase();
			cl1=serv_Clase.obtener_por_Codclase(codclase);
			
			
			model.addAttribute("alumno", serv_Alumno.obtener_por_codalumno(codalumno));
			model.addAttribute("curso", serv_Curso.obtener_por_Codcurso(cl1.getCodcurso()));
			model.addAttribute("periodos",serv_periodo.listaractivos());
			model.addAttribute("items",Serv_Item.listar_estado(1));
			
			
			
			model.addAttribute("clase", cl1);
			return "notas/cargar_notas";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("prueba1")
	public String prueba1(HttpServletRequest request, Model model,String codclase,String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			System.out.println("codigo de clsesentrante a metodo"+codclase);

			System.out.println("codigo de alumno a metodo"+codalumno);
			
			Clase cl1=new Clase();
			cl1=serv_Clase.obtener_por_Codclase(codclase);
			
			
			
			model.addAttribute("notas",serv_Notas.listar_nota(codalumno, codclase));
			
			
			model.addAttribute("clase", cl1);
			return "notas/cargar_notas2";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	
	
	/*
	@RequestMapping("ver_detalle_clase")
	public String ver_detalle_clase(HttpServletRequest request, Model model,String codclase){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			System.out.println(codclase);
			
			Clase cl=new Clase();
			cl=serv_Clase.obtener_por_Codclase(codclase);
			
			model.addAttribute("alumnos", serv_Alumno.listar_por_curso(cl.getCodcurso()));
			model.addAttribute("codclase",codclase);
			model.addAttribute("periodos",serv_periodo.listar());
			
			return "notas/ver_detalle_clase";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	*/
	@RequestMapping("guardar_notas_alumnos")
	public String guardar_notas_alumnos(HttpServletRequest request, Model model,String codclase,int ser[],int saber[],int decidir[],int hacer[],String codalumno[],String codperiodo){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			for (int i = 0; i < hacer.length; i++) {
				
			Notas n=new Notas();
			String cod=serv_Notas.generar_Cod();
				n.setCodnota(cod);
				n.setCodalumno(codalumno[i]);
				n.setCodclase(codclase);
				
				n.setNota((ser[i]+saber[i]+hacer[i]+decidir[i])/4);
				n.setCodperiodo(codperiodo);
			serv_Notas.adicionar(n);
			
				
			}
			
			model.addAttribute("msg", "notas adicionadas exitosamente");
			model.addAttribute("url", "notas/notas");
			return "principal/mensajes";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
	
	@RequestMapping("notas_alumno")
	public String notas_alumno(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			return "notas/notas_alumno";
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}
	
@RequestMapping("ver_notas_alumno")
	public String ver_notas_alumno(HttpServletRequest request, Model model,String codalumno){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
			
			
			if(serv_Alumno.existe_alu_rue(codalumno)==true){
				Alumno al=serv_Alumno.obtener_por_rud(codalumno);
			
			model.addAttribute("cursos", serv_Curso.listar_cursos_x_codalumno(al.getCodalumno()));
			model.addAttribute("alumno", serv_Alumno.obtener_por_codalumno(al.getCodalumno()));
			
			return "notas/ver_notas_alumno";
		}
			
			else {
				model.addAttribute("error","El RUE no existe , verifique sus datos");
				
				return "notas/notas_alumno";
			}
			
		}
		else {
			model.addAttribute("msg", "Debe inciar sesión..!");
			return "principal/mensajes";
		}
	}

































@RequestMapping("guardar_notas_alumno_clase")
public String guardar_notas_alumno_clase(HttpServletRequest request, Model model,float ser, float saber,float decidir,float haser,int detalle_ser[],int detalle_haser[],int detalle_decidir[],int detalle_saber[],int TOTAL,int ser_est,int decidir_est,String codclase,String codalumno,String codperiodo,String nombre_saber[],String nombre_ser[],String nombre_haser[],String nombre_decidir[]){
	Usuario user = (Usuario)request.getSession().getAttribute("user");
	if(user!=null){
		
		String codnota=serv_Notas.generar_Cod();
		
		Notas nt=new Notas();
		nt.setCodalumno(codalumno);
		nt.setCodclase(codclase);
		nt.setCodnota(codnota);
		nt.setCodperiodo(codperiodo);
		nt.setNota(TOTAL);
		nt.setNombre("nota cargada");
		 serv_Notas.adicionar(nt);

		 //adicona detalle de nota 
		 Detalle_nota dt1=new Detalle_nota();
		 String codigodt1=serv_Detalle_Nota.generar_Cod();
		 dt1.setCodnota(codnota);
		 dt1.setCoddetnot(codigodt1);
		 dt1.setItem("SER");
		 dt1.setNota(Math.round(ser));
		 serv_Detalle_Nota.adicionar(dt1);
		 
		System.out.println("nota de ser "+ser);
		
		//adiciona subdetalle
		for (int i = 0; i < detalle_ser.length; i++) {
		Sub_Detalle sdet1=new Sub_Detalle();
		
		sdet1.setCoddetnot(codigodt1);
		sdet1.setCodsubdet(serv_Sub_Detalle.generar_Cod());
		sdet1.setItem(nombre_ser[i]);
		sdet1.setNota(detalle_ser[i]);
		serv_Sub_Detalle.adicionar(sdet1);
			System.out.println("detalle numero "+i+"--->"+detalle_ser[i]);
		}
		
		 //adicona detalle de nota 
		 Detalle_nota dt2=new Detalle_nota();
		 String codigodt2=serv_Detalle_Nota.generar_Cod();
		 dt2.setCodnota(codnota);
		 dt2.setCoddetnot(codigodt2);
		 dt2.setItem("SABER");
		 dt2.setNota(Math.round(saber));
		 serv_Detalle_Nota.adicionar(dt2);
		 
		System.out.println("nota de ser "+saber);
		
		//adiciona subdetalle
		for (int i = 0; i < detalle_saber.length; i++) {
		Sub_Detalle sdet1=new Sub_Detalle();
		
		sdet1.setCoddetnot(codigodt2);
		sdet1.setCodsubdet(serv_Sub_Detalle.generar_Cod());
		sdet1.setItem(nombre_saber[i]);
		sdet1.setNota(detalle_saber[i]);
		serv_Sub_Detalle.adicionar(sdet1);
		}
		
		//adicona detalle de nota 
		 Detalle_nota dt3=new Detalle_nota();
		 String codigodt3=serv_Detalle_Nota.generar_Cod();
		 dt3.setCodnota(codnota);
		 dt3.setCoddetnot(codigodt3);
		 dt3.setItem("HASER");
		 dt3.setNota(Math.round(haser));
		 serv_Detalle_Nota.adicionar(dt3);
		 
		System.out.println("nota de ser "+saber);
		
		//adiciona subdetalle
		for (int i = 0; i < detalle_haser.length; i++) {
		Sub_Detalle sdet1=new Sub_Detalle();
		
		sdet1.setCoddetnot(codigodt3);
		sdet1.setCodsubdet(serv_Sub_Detalle.generar_Cod());
		sdet1.setItem(nombre_haser[i]);
		sdet1.setNota(detalle_haser[i]);
		serv_Sub_Detalle.adicionar(sdet1);
		}
		
		
		//adicona detalle de nota 
		 Detalle_nota dt4=new Detalle_nota();
		 String codigodt4=serv_Detalle_Nota.generar_Cod();
		 dt4.setCodnota(codnota);
		 dt4.setCoddetnot(codigodt4);
		 dt4.setItem("DECIDIR");
		 dt4.setNota(Math.round(decidir));
		 serv_Detalle_Nota.adicionar(dt4);
		 
		System.out.println("nota de ser "+decidir);
		
		//adiciona subdetalle
		for (int i = 0; i < detalle_decidir.length; i++) {
		Sub_Detalle sdet1=new Sub_Detalle();
		
		sdet1.setCoddetnot(codigodt4);
		sdet1.setCodsubdet(serv_Sub_Detalle.generar_Cod());
		sdet1.setItem(nombre_decidir[i]);
		sdet1.setNota(detalle_decidir[i]);
		serv_Sub_Detalle.adicionar(sdet1);
		}
		
		Detalle_nota dtSER=new Detalle_nota();
		 String codigodtSER=serv_Detalle_Nota.generar_Cod();
		 dtSER.setCodnota(codnota);
		 dtSER.setCoddetnot(codigodtSER);
		 dtSER.setItem("AUTOEVALUACION SER");
		 
		 float d1=(float) (ser_est);
		 dtSER.setNota(Math.round(d1));
		 serv_Detalle_Nota.adicionar(dtSER);
		
		 Detalle_nota dtDEC=new Detalle_nota();
		 String codigodtDEC=serv_Detalle_Nota.generar_Cod();
		 dtDEC.setCodnota(codnota);
		 dtDEC.setCoddetnot(codigodtDEC);
		 dtDEC.setItem("AUTOEVALUACION DECIDIR");
		 
		 float d=(float) (decidir_est);
		 dtDEC.setNota(Math.round(d));
		 serv_Detalle_Nota.adicionar(dtDEC);
		
		
		model.addAttribute("msg", "notas adicionadas exitosamente");
		model.addAttribute("url", "notas/notas");
		return "principal/mensajes";
	}
	else {
		model.addAttribute("msg", "Debe inciar sesión..!");
		return "principal/mensajes";
	}
}
@RequestMapping("listar_cursos")
public String listar_cursos(HttpServletRequest request, Model model){
	Usuario user = (Usuario)request.getSession().getAttribute("user");
	if(user!=null){
		
		model.addAttribute("cursos", serv_Curso.listar_estado(1));
		
		return "notas/cursos";
	}

	else {
		model.addAttribute("msg", "Debe inciar sesión..!");
		return "principal/mensajes";
	}
}

@RequestMapping("lista_alumnos_clase")
public String lista_alumnos_clase(HttpServletRequest request, Model model,String codcurso){
	Usuario user = (Usuario)request.getSession().getAttribute("user");
	if(user!=null){
		
		
		
		model.addAttribute("alumnos", serv_Alumno.listar_por_curso(codcurso));
		model.addAttribute("curso", serv_Curso.obtener_por_Codcurso(codcurso));
		return "notas/lista_alumnos_clase";
	}

	else {
		model.addAttribute("msg", "Debe inciar sesión..!");
		return "principal/mensajes";
	}
}
}