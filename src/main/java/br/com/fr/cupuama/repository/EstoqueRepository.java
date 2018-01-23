package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.fr.cupuama.entity.Estoque;
import br.com.fr.cupuama.entity.key.EstoqueKey;
import br.com.fr.cupuama.exception.EstoqueException;

public interface EstoqueRepository extends CrudRepository<Estoque, EstoqueKey> {
	
	@Query(nativeQuery = true, 
			value = "select e.* " +  
					"from estoque e " +
					"inner join produto p on p.id = e.produto_id and p.situacao = 'A' " +
					"inner join fruta f on f.id = e.fruta_id and f.situacao = 'A' " +
					"inner join local_estoque le on le.id = e.local_estoque_id and le.situacao = 'A' " +
					"where (e.ano_mes = :anoMes or :anoMes = '000000') " + 
					"  and (e.produto_id = :produtoId or :produtoId = -1) " +
					"  and (e.fruta_id = :frutaId or :frutaId = -1) " +
					"  and (e.local_estoque_id = :localEstoqueId or :localEstoqueId = -1) " +
					"order by e.ano_mes, p.nome, f.nome, le.nome")
	public List<Estoque> findByAnoMesProdutoFrutaLocalEstoque(
			@Param("anoMes") String anomes, 
			@Param("produtoId") Integer produtoId, 
			@Param("frutaId") Integer frutaId, 
			@Param("localEstoqueId") Integer localEstoqueId) throws EstoqueException;

}
