package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.fr.cupuama.entity.LocalEstoque;

public interface LocalEstoqueRepository extends CrudRepository<LocalEstoque, Integer> {
	
	@Query(nativeQuery = true, 
			value = "select le.* " +  
					"from local_estoque le " + 
					"where le.situacao = 'A' " +
					"order by le.nome")
	public List<LocalEstoque> findAllOrderByNome(); 

}
