package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.fr.cupuama.entity.Produto;

public interface ProdutoRepository extends CrudRepository<Produto, Integer> {
	
	@Query(nativeQuery = true, 
			value = "select p.* " +  
					"from produto p " + 
					"where p.situacao = 'A' " +
					"order by p.nome")
	public List<Produto> findAllOrderByNome(); 

}
