package Controladores;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import Modelos.Datos;
import Modelos.Usuario;
import Servicios.Serv_Datos;
import Servicios.Serv_Usuario;



@Controller
@RequestMapping("/principal/*")
public class principal {
	
	@Autowired
	private Serv_Usuario serv_Usuario;
	
	@Autowired
	private Serv_Datos serv_datos;
	
	@RequestMapping("login")
	public String login(){
		
		return "principal/login";
	}
	
	
	
	@RequestMapping("recuperar_contracena")
	public String recuperar_contracena(HttpServletRequest request, Model model, String ci){
		
	
			
			
			try {
				model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
				
				model.addAttribute("dt", serv_datos.obteber_por_Ci(ci));
				return "principal/recuperar_contracena";
			} catch (Exception e) {
				model.addAttribute("error2", "Verifique sus datos");
				return "principal/login";
			}
			
			
			
			
			
			
			
			
			
			
		
	}
	
	
	@RequestMapping("validar2")
	public String validar2(HttpServletRequest request, Model model, String ci ,String resp){
	
			
			Datos datos=serv_datos.obteber_por_Ci(ci);
			System.out.println( datos.getCi());
			System.out.println("respuesta en el html"+resp);
			
			System.out.println( "respuesta en la bd"+datos.getResp());
	String x=datos.getResp();
			if(x.equals(resp)){
				
				System.out.println("entra al verdadero");
			Usuario user = serv_Usuario.iniciar_secion_con_pregunta(datos);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			model.addAttribute("msg", "Bienvenido "+user.getNombre());
			model.addAttribute("url", "principal/post_validar");
						
			return "principal/mensajes";
			
			
			
			
			
			
		} else
			
		{
			model.addAttribute("error", "respuesta incorecta ..vuelva a ingresar");
			model.addAttribute("us", serv_Usuario.obtener_por_Ci(ci));
		
		model.addAttribute("dt", serv_datos.obteber_por_Ci(ci));
			return "principal/recuperar_contracena";
			}
		}


	
	

	@RequestMapping("validar")
	public String validar(HttpServletRequest request, Model model, @ModelAttribute("dt") Datos dt){
		try {
			Usuario user = serv_Usuario.iniciar_sesion(dt);
			HttpSession session = request.getSession();
			session.setAttribute("user", user);
			model.addAttribute("msg", "Bienvenido "+user.toString());
			model.addAttribute("url", "principal/post_validar");
						
			return "principal/mensajes";
		} catch (Exception e) {
			model.addAttribute("error", "Verifique sus datos");
			return "principal/login";
		}
	}
	
	
	
	
	
	@RequestMapping("post_validar")
	public String post_validar(HttpServletRequest request, Model model){
		Usuario user = (Usuario)request.getSession().getAttribute("user");
		if(user!=null){
	
		
			
			
			model.addAttribute("user", user);
			model.addAttribute("roles", serv_Usuario.obtener_por_Ci(user.getCi()).getRoles());
			return "principal/post_validar";
		}
		else{
			model.addAttribute("msg", "Debe inicar sesión..!");
			model.addAttribute("url", "principal/login");
			return "principal/mensajes";
		}
	}
	
	@RequestMapping("salir")
	public String salir(HttpServletRequest request, HttpServletResponse response){
		try {
			Cookie[] cookie = request.getCookies();
			for(Cookie i: cookie){
				i.setPath(request.getContextPath());
		    	i.setMaxAge(0);
		    	i.setValue(null);
		    	response.addCookie(i);
			}
		} catch (Exception e) {}
		HttpSession session = request.getSession(false);
		if(session != null) session.invalidate();
		return "redirect:../principal/login";
	}
	
	
}
