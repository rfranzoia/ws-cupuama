/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.cupuama.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import br.com.fr.cupuama.entity.ClienteFornecedorFruta;
import br.com.fr.cupuama.entity.key.ClienteFornecedorFrutaKey;
import br.com.fr.cupuama.exception.ClienteFornecedorFrutaException;


/**
 *
 * @author p001269
 */
public interface ClienteFornecedorFrutaRepository extends CrudRepository<ClienteFornecedorFruta, ClienteFornecedorFrutaKey> {

	@Query(nativeQuery = true, value = "select cff.* " +
			"from cliente_fornecedor_fruta cff " +
            "inner join fruta f on f.id = cff.fruta_id and f.situacao = 'A' " +
            "inner join cliente_fornecedor cf on cf.id = cff.cliente_fornecedor_id and cf.situacao = 'A' " +
			"where cff.cliente_fornecedor_id = :clienteFornecedorId " +
			"and cff.fruta_id = :frutaId")
	public ClienteFornecedorFruta getByClienteFornecedorAndFruta(@Param("clienteFornecedorId") Integer clienteFornecedorId, @Param("frutaId")Long frutaId) throws ClienteFornecedorFrutaException;
	
	@Query(nativeQuery = true, value = "select cff.* " +
            "from cliente_fornecedor_fruta cff " +
            "inner join fruta f on f.id = cff.fruta_id and f.situacao = 'A' " +
            "inner join cliente_fornecedor cf on cf.id = cff.cliente_fornecedor_id and cf.situacao = 'A' " +
            "where cff.cliente_fornecedor_id = :clienteFornecedorId " + 
            "order by cf.nome asc")
	public List<ClienteFornecedorFruta> findByClienteFornecedor(@Param("clienteFornecedorId") Integer clienteFornecedorId) throws ClienteFornecedorFrutaException;
    
	@Query(nativeQuery = true, value = "select cff.* " +
            "from cliente_fornecedor_fruta cff " +
            "inner join cliente_fornecedor cf on cf.id = cff.cliente_fornecedor_id and cf.situacao = 'A' " +
            "inner join fruta f on f.id = cff.fruta_id and f.situacao = 'A' " +
            "where cff.fruta_id = :frutaId " + 
            "order by f.nome asc")
    public List<ClienteFornecedorFruta> findByFruta(@Param("frutaId") Integer frutaId) throws ClienteFornecedorFrutaException;
    
	@Query(nativeQuery = true, value = "select cff.* " +
			"from cliente_fornecedor_fruta cff " +
            "inner join fruta f on f.id = cff.fruta_id and f.situacao = 'A'" +
            "inner join cliente_fornecedor cf on cf.id = cff.cliente_fornecedor_id and cf.situacao = 'A' " +
            "order by cf.nome, f.nome")
    public List<ClienteFornecedorFruta> findOrderByClienteFornecedorAndFruta() throws ClienteFornecedorFrutaException;
}
