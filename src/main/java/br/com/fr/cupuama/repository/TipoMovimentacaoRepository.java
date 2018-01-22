package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.fr.cupuama.entity.TipoMovimentacao;

public interface TipoMovimentacaoRepository extends CrudRepository<TipoMovimentacao, Integer> {
	
	@Query(nativeQuery = true, 
			value = "select tm.* " +  
					"from tipo_movimentacao tm " +
					"where tm.situacao = 'A' " +
					"order by tm.nome")
	public List<TipoMovimentacao> findAllOrderByNome(); 

}
