package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.fr.cupuama.entity.ProcessoMovimentacao;
import br.com.fr.cupuama.entity.key.ProcessoMovimentacaoKey;
import br.com.fr.cupuama.exception.ProcessoMovimentacaoException;

public interface ProcessoMovimentacaoRepository extends CrudRepository<ProcessoMovimentacao, ProcessoMovimentacaoKey> {

	@Query(nativeQuery = true, value = 
			"select pm.* " +
			"from processo_movimentacao pm " +
			"inner join produto p on p.id = pm.produto_id and p.situacao = 'A' " +
			"inner join tipo_movimentacao tm on tm.id = pm.tipo_movimentacao_id and tm.situacao = 'A' " +
			"inner join local_estoque le on le.id = pm.local_estoque_id and le.situacao = 'A' " +
			"order by tm.nome, p.nome, pm.tipo_entrada_saida")
	public List<ProcessoMovimentacao> findAllOrderByTipoMovimentacaoProdutoTipoEntradaSaida() throws ProcessoMovimentacaoException;
	
	@Query(nativeQuery = true, value = 
			"select pm.* " + 
			"from processo_movimentacao pm " +
			"inner join produto p on p.id = pm.produto_id and p.situacao = 'A' " +
			"inner join tipo_movimentacao tm on tm.id = pm.tipo_movimentacao_id and tm.situacao = 'A' " +
			"inner join local_estoque le on le.id = pm.local_estoque_id and le.situacao = 'A' " +
			"where pm.tipo_entrada_saida = :tipoEntradaSaida")
	public List<ProcessoMovimentacao> findByTipoEntradaSaidaOrderByTipoMovimentacaoProduto(@Param("tipoEntradaSaida") Character tipoEntadaSaida) throws ProcessoMovimentacaoException;
	
	@Query(nativeQuery = true, value = "select pm.* " + 
			"from processo_movimentacao pm " +
			"inner join produto p on p.id = pm.produto_id and p.situacao = 'A' " +
			"inner join tipo_movimentacao tm on tm.id = pm.tipo_movimentacao_id and tm.situacao = 'A' " +
			"inner join local_estoque le on le.id = pm.local_estoque_id and le.situacao = 'A' " +
			"where pm.produto_id = :produtoId " +
			"order by tm.nome, pm.tipo_entrada_saida")
	public List<ProcessoMovimentacao> findByProdutoOrderByTipoMovimentacao(@Param("produtoId") Integer produtoId) throws ProcessoMovimentacaoException;
	
	@Query(nativeQuery = true, value = "select pm.* " + 
			"from processo_movimentacao pm " +
			"inner join produto p on p.id = pm.produto_id and p.situacao = 'A' " +
			"inner join tipo_movimentacao tm on tm.id = pm.tipo_movimentacao_id and tm.situacao = 'A' " +
			"inner join local_estoque le on le.id = pm.local_estoque_id and le.situacao = 'A' " +
			"where pm.tipo_movimentacao_id = :tipoMovimentacaoId " +
			"order by p.nome, pm.tipo_entrada_saida")
	public List<ProcessoMovimentacao> findByTipoMovimentacaoOrderByProduto(@Param("tipoMovimentacaoId") Integer tipoMovimentacaoId) throws ProcessoMovimentacaoException;
	
	@Query(nativeQuery = true, value = "select pm.* " + 
			"from processo_movimentacao pm " +
			"inner join produto p on p.id = pm.produto_id and p.situacao = 'A' " +
			"inner join tipo_movimentacao tm on tm.id = pm.tipo_movimentacao_id and tm.situacao = 'A' " +
			"inner join local_estoque le on le.id = pm.local_estoque_id and le.situacao = 'A' " +
			"where pm.local_estoque_id = :localEstoqueId " +
			"order by tm.nome, p.nome, pm.tipo_entrada_saida")
	public List<ProcessoMovimentacao> findByLocalEstoqueOrderByTipoMovimentacaoProdutoTipoEntradaSaida(@Param("localEstoqueId") Integer localEstoqueId) throws ProcessoMovimentacaoException;
	
}
