package pi.luis.eventos.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pi.luis.eventos.models.Evento;

@Controller
public class eventosController {
	
	@RequestMapping("/eventos/form")
	public String form(){
		return("formEvento");
	}
	
	@RequestMapping("/eventos/respForm")
	public String respForm(Evento res){
		System.out.println("respForm" + res.getNome() + res.getLocal() + res.getData() + res.getHorario());
		return "respForm";
	}
}
