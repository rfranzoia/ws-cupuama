package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.fr.cupuama.entity.Movimento;
import br.com.fr.cupuama.exception.MovimentoException;

public interface MovimentoRepository extends CrudRepository<Movimento, Integer> {

	@Query(nativeQuery = true, 
			value = "select m.* " +
					"from movimento m " +
					"order by m.dt_movimento, m.id")
	public List<Movimento> findAllOrderByDtMovimento() throws MovimentoException;
	
}
