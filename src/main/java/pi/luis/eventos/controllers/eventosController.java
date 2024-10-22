package pi.luis.eventos.controllers;




import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class eventosController {
	
	@RequestMapping("/eventos/form")
	public String form(){
		return("formEvento");
		
	}
	
	@RequestMapping("/eventos/respForm")
	public String respForm(){
		String nome;
		String local;
		String data;
		String horario;
		System.out.println(nome);
		System.out.println(local);
		System.out.println(data);
		System.out.println(horario);
		return("respForm");
	}
}
