/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.fr.cupuama.entity.ProdutoFruta;
import br.com.fr.cupuama.entity.key.ProdutoFrutaKey;
import br.com.fr.cupuama.exception.ProdutoFrutaException;


/**
 *
 * @author p001269
 */
public interface ProdutoFrutaRepository extends CrudRepository<ProdutoFruta, ProdutoFrutaKey> {

	@Query(nativeQuery = true, value = "select pf.* " +
			"from produto_fruta pf " +
            "inner join fruta f on f.id = pf.fruta_id and f.situacao = 'A' " +
            "inner join produto p on p.id = pf.produto_id and p.situacao = 'A' " +
			"where pf.produto_id = :produtoId " +
			"and pf.fruta_id = :frutaId")
	public ProdutoFruta getByProdutoAndFruta(@Param("produtoId") Integer produtoId, @Param("frutaId")Long frutaId) throws ProdutoFrutaException;
	
	@Query(nativeQuery = true, value = "select pf.* " +
            "from produto_fruta pf " +
            "inner join fruta f on f.id = pf.fruta_id and f.situacao = 'A' " +
            "inner join produto p on p.id = pf.produto_id and p.situacao = 'A' " +
            "where pf.produto_id = :produtoId " + 
            "order by p.nome asc")
	public List<ProdutoFruta> findByProduto(@Param("produtoId") Integer produtoId) throws ProdutoFrutaException;
    
	@Query(nativeQuery = true, value = "select pf.* " +
            "from produto_fruta pf " +
            "inner join fruta f on f.id = pf.fruta_id and f.situacao = 'A' " +
            "inner join produto p on p.id = pf.produto_id and p.situacao = 'A' " +
            "where pf.fruta_id = :frutaId " + 
            "order by f.nome asc")
    public List<ProdutoFruta> findByFruta(@Param("frutaId") Integer frutaId) throws ProdutoFrutaException;
    
	@Query(nativeQuery = true, value = "select pf.* " +
			"from produto_fruta pf " +
			"inner join fruta f on f.id = pf.fruta_id and f.situacao = 'A' " +
            "inner join produto p on p.id = pf.produto_id and p.situacao = 'A' " +
            "order by p.nome, f.nome")
    public List<ProdutoFruta> findOrderByProdutoAndFruta() throws ProdutoFrutaException;
}
