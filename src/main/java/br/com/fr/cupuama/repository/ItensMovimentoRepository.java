package br.com.fr.cupuama.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.fr.cupuama.entity.ItensMovimento;
import br.com.fr.cupuama.exception.ItensMovimentoException;

public interface ItensMovimentoRepository extends CrudRepository<ItensMovimento, Integer> {

	@Query(nativeQuery = true, 
			value = "select im.* " +  
					"from itens_movimento im " +
					"inner join produto p on p.id = im.produto_id and p.situacao = 'A' " +
					"inner join fruta f on f.id = im.fruta_id and f.situacao = 'A' " +
					"inner join local_estoque le on le.id = im.local_estoque_id and le.situacao = 'A' " +
					"where im.movimento_id = :movimentoId " +
					"order by im.id")
	public List<ItensMovimento> findByMovimento(@Param("movimentoId") Integer movimentoId) throws ItensMovimentoException;

	@Query(nativeQuery = true, 
			value = "select im.* " + 
					"from itens_movimento im " +
                    "inner join movimento m on m.id = im.movimento_id " +
                    "inner join produto p on p.id = im.produto_id " +
                    "inner join fruta f on f.id = im.fruta_id " +
                    "inner join local_estoque le on le.id = im.local_estoque_id " +
                    "left join cliente_fornecedor cf on cf.id = m.cliente_fornecedor_id " +
                    "where m.dt_movimento between :inicio and :fim " +
                    "and (im.produto_id = :produtoId or -1 = :produtoId) " +
                    "and (im.fruta_id = :frutaId or -1 = :frutaId) " +
                    "and (im.local_estoque_id = :localEstoqueId or -1 = :localEstoqueId) " +
                    "order by m.dt_movimento, im.tipo_entrada_saida")
	public List<ItensMovimento> findByEstoque(@Param("inicio") Date inicio, @Param("fim") Date fim, 
										@Param("produtoId") Integer produtoId, 
										@Param("frutaId") Integer frutaId, 
										@Param("localEstoqueId") Integer localEstoqueId) throws ItensMovimentoException;
}
