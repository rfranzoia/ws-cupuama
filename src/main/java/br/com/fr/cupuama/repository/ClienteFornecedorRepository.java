package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.fr.cupuama.entity.ClienteFornecedor;

public interface ClienteFornecedorRepository extends CrudRepository<ClienteFornecedor, Integer> {
	
	@Query(nativeQuery = true, 
			value = "select cf.* " +  
					"from cliente_fornecedor cf " +
					"where cf.situacao = 'A' " +
					"order by cf.nome")
	public List<ClienteFornecedor> findAllOrderByNome();
	
	@Query(nativeQuery = true, 
			value = "select cf.* " +  
					"from cliente_fornecedor cf " +
					"where cf.tipo = :tipo " +
					"and cf.situacao = 'A' " +
					"order by cf.nome")
	public List<ClienteFornecedor> findByTipoOrderByNome(@Param("tipo") Character tipo);

}
