package pi.luis.eventos.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pi.luis.eventos.models.Evento;

@Controller
public class eventosController {
	
	@RequestMapping("/eventos/form")
	public String form(){
		return("formEvento");
	}
	
	@PostMapping("/eventos/respForm")
	public String respForm(Evento res){
		System.out.println(res.toString());
		return "respForm";
	}
}
