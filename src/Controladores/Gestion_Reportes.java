package Controladores;

import java.io.FileOutputStream;
import java.awt.datatransfer.FlavorTable;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.ParseException;

import javax.print.DocFlavor.STRING;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;



import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import org.apache.poi.ss.util.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;

import org.apache.poi.hssf.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import com.lowagie.text.Row;

import Modelos.Alumno;
import Modelos.Libreta;
import Modelos.Usuario;
import Servicios.Serv_Alumno;
import Servicios.Serv_Libreta;
import herramientas.GeneradorReportes;
import herramientas.fechas;



@Controller
@RequestMapping("/reportes/*")
public class Gestion_Reportes {

	
	@Autowired
	private Serv_Alumno serv_Alumno;
	
	
	@Autowired
	private Serv_Libreta serv_Libreta;
	@Autowired
	DataSource dataSource;
	
	@RequestMapping("boletin_notas")
	public void boletin_notas(HttpServletRequest request,HttpServletResponse response,String codalumno,String codcurso)throws IOException, ParseException{
		System.out.println(codalumno+"    "+ codcurso);
		Map<String, Object> parametros=new HashMap<String, Object>();
		
		String reportUrl="/Reportes/boletin_notas.jasper";
		parametros.put("codalumno", codalumno);
		parametros.put("codcurso", codcurso);
		parametros.put("codperiodo","HRA0001-0");
		parametros.put("codperiodo2","HRA0001-1");
		parametros.put("codperiodo3","HRA0001-2");
		GeneradorReportes rep=new GeneradorReportes();		
		try{		
			rep.generarReporte(response, getClass().getResource(reportUrl), "pdf", parametros, dataSource.getConnection(), "reporte_usuario", "inline");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@Autowired
	DataSource dataSource32;
	
	@RequestMapping("libreta")
	public void libreta(HttpServletRequest request,HttpServletResponse response,String codalumno,String codcurso)throws IOException, ParseException{
		System.out.println(codalumno+"    "+codcurso);
		
		
		if (serv_Libreta.existe_libreta(codalumno, codcurso)==false) {
			
			System.out.println("no existe libreta");
			Libreta lib= new Libreta();			
			lib.setCodalumno(codalumno);
			lib.setCodcurso(codcurso);
			lib.setGestion(2018);
			lib.setNumero(serv_Libreta.generar_numero());
			
			if(serv_Libreta.verificar_promedio(codcurso, codalumno)<51){
				lib.setCalificacion("REPROBADO");	
			}
			else{
				lib.setCalificacion("APROBADO");	
				
			}			
			serv_Libreta.adicionar(lib);
			
		}
		
			System.out.println("ya existe");
			
			
			
			
			Map<String, Object> parametros=new HashMap<String, Object>();
			
			String reportUrl="/Reportes/REPORTE_LIBRETA_1.jasper";
			parametros.put("codalumno", codalumno);
			parametros.put("codcurso", codcurso);
		
			parametros.put("t1","NOMBRA LAS CLASES DE HORACIONES Y LOS DIFERENTES SUSTANTIVOS PARA REALIZAR SUS TRABAJOS DE LENGUAJE CON RESPONSABILIDAD");
			parametros.put("t2","DESCRIBE EL SISTEMA OROGRAFICO Y LOS DIFERENTES CLIMAS DE BOLIVIA Y NOMBRA LAS ENFERMEDADES DEL APARATO CIRCULATORIO");
			parametros.put("t3","CONCEPTUALIZA LAS CLASES DE FRACCIONES CON DIREFENTES EMPLEOS Y REALIZA EN LENGUAJE CUENTOS Y LEYENDAS");
			
			parametros.put("texto_pie","LA/EL ESTUDIANTE SI A SIDO PROMOVIDO/A AL AÑO DE ESCOLARIDAD INMEDIATO SUPERIOR");
			GeneradorReportes rep=new GeneradorReportes();		
			try{		
				rep.generarReporte(response, getClass().getResource(reportUrl), "pdf", parametros, dataSource.getConnection(), "reporte_usuario", "inline");	
			} catch (Exception e) {
				e.printStackTrace();
			}			
			
			
	}
	
	

	@Autowired
	DataSource dataSource2;
	
	@RequestMapping("usuarios")
	public void usuarios(HttpServletRequest request,HttpServletResponse response,String codalumno,String codcurso)throws IOException, ParseException{
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		System.out.println(codalumno+"    "+ codcurso);
		Map<String, Object> parametros=new HashMap<String, Object>();
		String reportUrl="/Reportes/report1.jasper";
		
		GeneradorReportes rep=new GeneradorReportes();		
		try{		
			rep.generarReporte(response, getClass().getResource(reportUrl), "pdf", parametros, dataSource.getConnection(), "reporte_usuario", "inline");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Autowired
	DataSource dataSource3;
	
	@RequestMapping("profesores_clases")
	public void profesores_clases(HttpServletRequest request,HttpServletResponse response,String codprofesor)throws IOException, ParseException{
		Usuario user = (Usuario)request.getSession().getAttribute("user");
	
		Map<String, Object> parametros=new HashMap<String, Object>();
		String reportUrl="/Reportes/profesor_clases.jasper";
		parametros.put("codprofesor", codprofesor);
		GeneradorReportes rep=new GeneradorReportes();		
		try{		
			rep.generarReporte(response, getClass().getResource(reportUrl), "pdf", parametros, dataSource.getConnection(), "reporte_profesores_clases", "inline");	
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}	

