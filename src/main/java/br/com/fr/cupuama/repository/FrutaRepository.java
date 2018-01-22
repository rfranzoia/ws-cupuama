package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.fr.cupuama.entity.Fruta;

public interface FrutaRepository extends CrudRepository<Fruta, Integer> {
	
	@Query(nativeQuery = true, 
			value = "select f.* " +  
					"from fruta f " + 
					"where f.situacao = 'A' " +
					"order by f.nome")
	public List<Fruta> findAllOrderByNome();
	
	@Query(nativeQuery = true, 
			value = "select f.* " +  
					"from fruta f " +
					"where upper(f.safra) = upper(:safra) " +
					"and f.situacao = 'A' " +
					"order by f.nome")
	public List<Fruta> findBySafraOrderByNome(@Param("safra") String safra);

}
