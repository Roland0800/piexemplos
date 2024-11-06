package pi.luis.eventos.controllers;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		
		List<Evento> eventos = er.findAll();
		ModelAndView mv = new ModelAndView("eventos/lista");
		mv.addObject("eventos", eventos);
		return mv;
	}
	
	@GetMapping("/{id}")
	public ModelAndView detalhar(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Evento> opt = er.findById(id);
		if(opt.isEmpty()) {
			md.setViewName("redirect:/eventos");
			return md;
		}
		md.setViewName("eventos/detalhes");
		Evento evento = opt.get();
		
		md.addObject("evento", evento);
		
		return md;
	}
}
