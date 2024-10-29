package pi.luis.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pi.luis.eventos.models.Evento;

public interface eventoRepository extends JpaRepository<Evento, Long>{

}
