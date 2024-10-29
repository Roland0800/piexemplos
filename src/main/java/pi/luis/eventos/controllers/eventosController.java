package pi.luis.eventos.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pi.luis.eventos.models.Evento;
import pi.luis.eventos.repositories.eventoRepository;

@Controller
public class eventosController {
	
	@Autowired
	private eventoRepository er;
	
	@RequestMapping("/eventos/form")
	public String form(){
		return("formEvento");
	}
	
	@PostMapping("/eventos/respForm")
	public String respForm(Evento evento){
		System.out.println(evento.toString());
		er.save(evento);
		return "respForm";
	}
}
