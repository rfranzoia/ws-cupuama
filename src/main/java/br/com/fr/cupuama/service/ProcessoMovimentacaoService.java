/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.entity.LocalEstoque;
import br.com.fr.cupuama.entity.ProcessoMovimentacao;
import br.com.fr.cupuama.entity.Produto;
import br.com.fr.cupuama.entity.TipoMovimentacao;
import br.com.fr.cupuama.entity.dto.LocalEstoqueDTO;
import br.com.fr.cupuama.entity.dto.ProcessoMovimentacaoDTO;
import br.com.fr.cupuama.entity.dto.ProdutoDTO;
import br.com.fr.cupuama.entity.dto.TipoMovimentacaoDTO;
import br.com.fr.cupuama.entity.key.ProcessoMovimentacaoKey;
import br.com.fr.cupuama.exception.LocalEstoqueException;
import br.com.fr.cupuama.exception.ProcessoMovimentacaoException;
import br.com.fr.cupuama.exception.ProdutoException;
import br.com.fr.cupuama.exception.TipoMovimentacaoException;
import br.com.fr.cupuama.repository.ProcessoMovimentacaoRepository;
import br.com.fr.cupuama.util.Util;

/**
 *
 * @author Romeu Franzoia Jr
 */
@Service
public class ProcessoMovimentacaoService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	ProcessoMovimentacaoRepository repository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	TipoMovimentacaoService tipoMovimentacaoService;
	
	@Autowired
	LocalEstoqueService localEstoqueService;

    public ProcessoMovimentacaoDTO get(ProcessoMovimentacaoKey key) throws ProcessoMovimentacaoException {
		ProcessoMovimentacao pm = repository.findOne(key);
		
		if (pm == null) {
			throw new NotFoundException("ProcessoMovimentacao não encontrado!");
		}
		
		Hibernate.initialize(pm.getKey().getProduto());
		Hibernate.initialize(pm.getKey().getTipoMovimentacao());
		
	    return Util.buildDTO(pm, ProcessoMovimentacaoDTO.class);
    }
    
    public ProcessoMovimentacaoDTO save(ProcessoMovimentacaoDTO dto) throws ProcessoMovimentacaoException {
        try {
    		ProdutoDTO p = produtoService.get(dto.getKeyProdutoId());
    		TipoMovimentacaoDTO tm = tipoMovimentacaoService.get(dto.getKeyTipoMovimentacaoId());
    		LocalEstoqueDTO le = localEstoqueService.get(dto.getLocalEstoqueId());
    		
    		ProcessoMovimentacaoKey key = new ProcessoMovimentacaoKey();
    		key.setProduto(Util.buildDTO(p, Produto.class));
    		key.setTipoMovimentacao(Util.buildDTO(tm, TipoMovimentacao.class));
    		key.setTipoEntradaSaida(dto.getKeyTipoEntradaSaida());
        	
        	ProcessoMovimentacao processoMovimentacao = new ProcessoMovimentacao();
        	processoMovimentacao.setKey(key);
        	processoMovimentacao.setLocalEstoque(Util.buildDTO(le, LocalEstoque.class));
        	
            repository.save(processoMovimentacao);
            
            return Util.buildDTO(processoMovimentacao, ProcessoMovimentacaoDTO.class);

        } catch (NotFoundException nfex) {
        	logger.error("save()", nfex);
            throw nfex;
            
        } catch (ProdutoException | TipoMovimentacaoException | LocalEstoqueException ptex) {
        	logger.error("save()", ptex);
        	throw new ProcessoMovimentacaoException(ptex);
            
        } catch (RollbackException rex) {
            logger.error("save()", rex);
            throw new ProcessoMovimentacaoException(rex);
            
        } catch (Exception ex) {
            logger.error("save()", ex);
            throw new ProcessoMovimentacaoException(ex);
        }
    }
    
    public void update(ProcessoMovimentacaoDTO dto) throws ProcessoMovimentacaoException {
        try {
        	ProdutoDTO p = produtoService.get(dto.getKeyProdutoId());
    		TipoMovimentacaoDTO tm = tipoMovimentacaoService.get(dto.getKeyTipoMovimentacaoId());
    		
    		ProcessoMovimentacaoKey key = new ProcessoMovimentacaoKey();
    		key.setProduto(Util.buildDTO(p, Produto.class));
    		key.setTipoMovimentacao(Util.buildDTO(tm, TipoMovimentacao.class));
    		key.setTipoEntradaSaida(dto.getKeyTipoEntradaSaida());
        	
        	ProcessoMovimentacao processoMovimentacao = repository.findOne(key);
        	
        	if (processoMovimentacao == null) {
        		throw new NotFoundException("ProcessoMovimentacao não encontrado!");
        	}
        	
        	LocalEstoqueDTO le = localEstoqueService.get(dto.getLocalEstoqueId());
        	processoMovimentacao.setLocalEstoque(Util.buildDTO(le, LocalEstoque.class));
        	
            repository.save(processoMovimentacao);
            
        } catch (ProdutoException | TipoMovimentacaoException | LocalEstoqueException ptex) {
        	logger.error("update()", ptex);
        	throw new ProcessoMovimentacaoException(ptex);
            
        } catch (RollbackException rex) {
            logger.error("update()", rex);
            throw new ProcessoMovimentacaoException(rex);
            
        }
    }
    
	public void deleteByKey(ProcessoMovimentacaoKey key) throws ProcessoMovimentacaoException {
        try {
        	ProcessoMovimentacao pm = repository.findOne(key);
			
        	if (pm == null) {
				throw new NotFoundException();
			}
        	
            repository.delete(pm);
        } catch (Exception ex) {
            logger.error("deleteByKey()", ex);
            throw new ProcessoMovimentacaoException(ex);
        }
	}
	
	public void delete(ProcessoMovimentacao produtoTipoMovimentacao) throws ProcessoMovimentacaoException {
		try {
			repository.delete(produtoTipoMovimentacao);
        } catch (RollbackException rex) {
            logger.error("delete()", rex);
            throw rex;
            
        } catch (Exception ex) {
            logger.error("delete()", ex);
            throw new ProcessoMovimentacaoException(ex);
        }
	}
    
	public List<ProcessoMovimentacaoDTO> listAllOrderByTipoMovimentacaoProdutoTipoEntradaSaida()  throws ProcessoMovimentacaoException {
		List<ProcessoMovimentacao> list = repository.findAllOrderByTipoMovimentacaoProdutoTipoEntradaSaida();
		return initializeList(list);
	}
	
	public List<ProcessoMovimentacaoDTO> listByTipoEntradaSaidaOrderByTipoMovimentacaoProduto(Character tipoEntradaSaida) throws ProcessoMovimentacaoException {
		List<ProcessoMovimentacao> list = repository.findByTipoEntradaSaidaOrderByTipoMovimentacaoProduto(tipoEntradaSaida);
		return initializeList(list);
	}

	public List<ProcessoMovimentacaoDTO> listByProdutoOrderByTipoMovimentacao(Integer produtoId) throws ProcessoMovimentacaoException {
		List<ProcessoMovimentacao> list = repository.findByProdutoOrderByTipoMovimentacao(produtoId);
		return initializeList(list);
	}
	
	public List<ProcessoMovimentacaoDTO> listByTipoMovimentacaoOrderByProduto(Integer tipoMovimentacaoId) throws ProcessoMovimentacaoException {
		List<ProcessoMovimentacao> list = repository.findByTipoMovimentacaoOrderByProduto(tipoMovimentacaoId);
		return initializeList(list);
	}
	
	public List<ProcessoMovimentacaoDTO> listByLocalEstoqueOrderByTipoMovimentacaoProdutoTipoEntradaSaida(Integer localEstoqueId) throws ProcessoMovimentacaoException {
		List<ProcessoMovimentacao> list = repository.findByLocalEstoqueOrderByTipoMovimentacaoProdutoTipoEntradaSaida(localEstoqueId);
		return initializeList(list);
	}
	
	private List<ProcessoMovimentacaoDTO> initializeList(List<ProcessoMovimentacao> list) {
		for (ProcessoMovimentacao pm : list) {
			Hibernate.initialize(pm.getKey().getTipoMovimentacao());
			Hibernate.initialize(pm.getKey().getProduto());
			Hibernate.initialize(pm.getLocalEstoque());
		}
		return Util.buildListDTO(list, ProcessoMovimentacaoDTO.class);
	}
	
}
