package pi.luis.eventos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import pi.luis.eventos.models.Convidado;
import pi.luis.eventos.models.Evento;

public interface convidadoRepository extends JpaRepository<Convidado, Long>{

	List<Convidado> findByEvento(Evento evento); 
	
}
