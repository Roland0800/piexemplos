package pi.luis.eventos.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
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
	public String form(Evento evento) {
		return ("eventos/formEvento");
	}

	@PostMapping
	public String salvar(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			return form(evento);
		}
		
		System.out.println(evento.toString());
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Evento salvo com sucesso");
		return "redirect:/eventos";
	}

	@GetMapping
	public ModelAndView listar() {

		List<Evento> eventos = er.findAll();
		ModelAndView mv = new ModelAndView("eventos/lista");
		mv.addObject("eventos", eventos);
		return mv;
	}

	@GetMapping("/{id}")
	public ModelAndView detalhar(@PathVariable Long id, Convidado convidado) {
		ModelAndView md = new ModelAndView();
		Optional<Evento> opt = er.findById(id);
		if (opt.isEmpty()) {
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
	public ModelAndView salvarConvidado(@PathVariable Long idEvento, @Valid Convidado convidado, BindingResult result, RedirectAttributes attributes) {

		System.out.println("id do evento " + idEvento);
		System.out.println(convidado);

		Optional<Evento> opt = er.findById(idEvento);
		ModelAndView md = new ModelAndView();
		if (opt.isEmpty()) {
			md.setViewName("redirect:/eventos");
			return md;
		}
		if (result.hasErrors()) {
			return detalhar(idEvento ,convidado);
		}
		
		Evento evento = opt.get();
		convidado.setEvento(evento);
		cr.save(convidado);
		attributes.addFlashAttribute("mensagem", "Convidado salvo com sucesso");
		md.setViewName("redirect:/eventos/{idEvento}");
		
		return md;
	}

	@GetMapping("/{id}/selecionar")
	public ModelAndView selecionarEvento(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Evento> opt = er.findById(id);
		if (opt.isEmpty()) {
			md.setViewName("redirect:/eventos");
			return md;
		}

		Evento evento = opt.get();
		md.setViewName("eventos/formEvento");
		md.addObject("evento", evento);
		return md;
	}

	@GetMapping("/{idEvento}/selecionar/{id}")
	public ModelAndView selecionarConvidado(@PathVariable Long id, @PathVariable Long idEvento) {
		ModelAndView md = new ModelAndView();
		Optional<Evento> optE = er.findById(idEvento);
		Optional<Convidado> opt = cr.findById(id);
		if(opt.isEmpty() || optE.isEmpty()) {
			md.setViewName("redirect:/eventos");
			return md;
		}
		
		Evento evento = optE.get();
		Convidado convidado = opt.get();
		
		if(evento.getId() != convidado.getEvento().getId()) {
			md.setViewName("redirect:/eventos");
			return md;
		}
		md.setViewName("eventos/detalhes");
		md.addObject("convidado", convidado);
		md.addObject("evento", evento);
		md.addObject("convidados", cr.findByEvento(evento));
		return md;
	}
	
	@GetMapping("/{id}/remover")
	public String apagarEvento(@PathVariable Long id, RedirectAttributes attributes) {

		Optional<Evento> opt = er.findById(id);

		if (!opt.isEmpty()) {
			List<Convidado> convidados = cr.findByEvento(opt.get());
			cr.deleteAll(convidados);
			er.delete(opt.get());
			attributes.addFlashAttribute("mensagem", "Evento removido com sucesso");
		}

		return "redirect:/eventos";
	}

	@GetMapping("/{idEvento}/remover/convidado/{id}")
	public String apagarConvidado(@PathVariable Long id, @PathVariable Long idEvento, RedirectAttributes attributes) {
		
		Optional<Evento> optE = er.findById(idEvento);
		Optional<Convidado> opt = cr.findById(id);

		if (!opt.isEmpty() && !optE.isEmpty()) {
			cr.delete(opt.get());
			attributes.addFlashAttribute("mensagem", "Convidado apagado com sucesso");
		}
		
		return "redirect:/eventos/{idEvento}";

	}
}
