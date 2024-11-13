package pi.luis.eventos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import pi.luis.eventos.models.Convidado;

public interface convidadoRepository extends JpaRepository<Convidado, Long>{

}
