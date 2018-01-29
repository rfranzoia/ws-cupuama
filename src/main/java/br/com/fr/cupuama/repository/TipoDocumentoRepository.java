package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.fr.cupuama.entity.TipoDocumento;

public interface TipoDocumentoRepository extends CrudRepository<TipoDocumento, Integer> {
	
	@Query(nativeQuery = true, 
			value = "select td.* " +  
					"from tipo_documento td " +
					"where td.situacao = 'A' " +
					"order by td.nome")
	public List<TipoDocumento> findAllOrderByNome(); 

}
