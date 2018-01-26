package br.com.fr.cupuama.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.fr.cupuama.entity.MovimentoCaixa;
import br.com.fr.cupuama.exception.MovimentoCaixaException;

public interface MovimentoCaixaRepository extends CrudRepository<MovimentoCaixa, Integer> {
	
	@Query(nativeQuery = true, 
			value = "select mc.* " + 
					"from movimento_caixa mc " +
                    "where mc.dt_movimento between :inicio and :fim " +
                    "order by mc.dt_movimento, mc.tipo")
	public List<MovimentoCaixa> findByPeriodo(@Param("inicio") Date inicio, @Param("fim") Date fim) throws MovimentoCaixaException;

}
