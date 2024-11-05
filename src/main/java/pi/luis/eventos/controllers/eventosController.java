package pi.luis.eventos.controllers;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pi.luis.eventos.models.Evento;
import pi.luis.eventos.repositories.eventoRepository;

@Controller
@RequestMapping("/eventos")
public class eventosController {
	
	@Autowired
	private eventoRepository er;
	
	@GetMapping("/form")
	public String form(){
		return("eventos/formEvento");
	}
	
	@PostMapping("/respForm")
	public String respForm(Evento evento){
		System.out.println(evento.toString());
		er.save(evento);
		return "eventos/respForm";
	}
	
	@GetMapping
	public ModelAndView listar() {
		
		List<Evento> Eventos = er.findAll();
		ModelAndView mv = new ModelAndView("eventos/lista");
		mv.addObject("eventos", Eventos);
		return mv;
	}
}
