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

import pi.luis.eventos.models.Convidado;
import pi.luis.eventos.models.Evento;
import pi.luis.eventos.repositories.convidadoRepository;
import pi.luis.eventos.repositories.eventoRepository;

@Controller
@RequestMapping("/eventos")
public class eventosController {
	
	@Autowired
	private eventoRepository er;
	@Autowired
	private convidadoRepository cr;
	
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
		
		List<Convidado> convidados = cr.findByEvento(evento);
		md.addObject("convidados", convidados);
		
		return md;
	}
	
	@PostMapping("/{idEvento}")
	public String salvarConvidado(@PathVariable Long idEvento, Convidado convidado) {
		
		System.out.println("id do evento " + idEvento);
		System.out.println(convidado);
		
		Optional<Evento> opt = er.findById(idEvento);
		
		if(opt.isEmpty()) {
			return "redirect:/eventos";
		}
		Evento evento = opt.get();
		convidado.setEvento(evento);
		cr.save(convidado);
		
		return "redirect:/eventos/{idEvento}";
	}
	
	@GetMapping("/{id}/remover")
	public String apagarEvento(@PathVariable Long id) {
		
		Optional<Evento> opt = er.findById(id);
		
		if(!opt.isEmpty()) {
			List<Convidado> convidados = cr.findByEvento(opt.get());
			cr.deleteAll(convidados);
			er.delete(opt.get());
		}
		
		return "redirect:/eventos";
	}
	
	@GetMapping("/{idEvento}/remover/convidado/{id}")
	public String apagarConvidado(@PathVariable Long id, Long idEvento) {
		
		Optional<Convidado> opt = cr.findById(id);
		
		if(!opt.isEmpty()){
			cr.delete(opt.get());
		}
		
		return "redirect:/eventos/{idEvento}";
		
	}
}
