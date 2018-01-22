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
 * @author p001269
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
        try {
        	ProcessoMovimentacao pm = repository.findOne(key);
        	
    		Hibernate.initialize(pm.getKey().getProduto());
    		Hibernate.initialize(pm.getKey().getTipoMovimentacao());
    		
            return Util.buildDTO(pm, ProcessoMovimentacaoDTO.class);
        } catch (Exception ex) {
            logger.error("get()", ex);
            throw ex;
        }
    }
    
    public void save(ProcessoMovimentacaoDTO dto) throws ProcessoMovimentacaoException, LocalEstoqueException, TipoMovimentacaoException, ProdutoException {
        try {
    		Produto p = Util.buildDTO(produtoService.get(dto.getKeyProdutoId()), Produto.class);
    		TipoMovimentacao tm = Util.buildDTO(tipoMovimentacaoService.get(dto.getKeyTipoMovimentacaoId()), TipoMovimentacao.class);
    		LocalEstoque le = Util.buildDTO(localEstoqueService.get(dto.getLocalEstoqueId()), LocalEstoque.class);
    		
    		ProcessoMovimentacaoKey key = new ProcessoMovimentacaoKey();
    		key.setProduto(p);
    		key.setTipoMovimentacao(tm);
    		key.setTipoEntradaSaida(dto.getKeyTipoEntradaSaida());
        	
        	ProcessoMovimentacao processoMovimentacao = new ProcessoMovimentacao();
        	processoMovimentacao.setKey(key);
        	processoMovimentacao.setLocalEstoque(le);
        	
            repository.save(processoMovimentacao);
            
        } catch (ProdutoException | TipoMovimentacaoException | LocalEstoqueException ptex) {
        	logger.error("save()", ptex);
            throw ptex;
            
        } catch (RollbackException rex) {
            logger.error("save()", rex);
            throw rex;
            
        } catch (Exception ex) {
            logger.error("save()", ex);
            throw ex;
        }
    }
    
    public void update(ProcessoMovimentacaoDTO dto) throws ProcessoMovimentacaoException, LocalEstoqueException, TipoMovimentacaoException, ProdutoException {
        try {
        	
        	Produto p = Util.buildDTO(produtoService.get(dto.getKeyProdutoId()), Produto.class);
    		TipoMovimentacao tm = Util.buildDTO(tipoMovimentacaoService.get(dto.getKeyTipoMovimentacaoId()), TipoMovimentacao.class);
    		LocalEstoque le = Util.buildDTO(localEstoqueService.get(dto.getLocalEstoqueId()), LocalEstoque.class);
    		
    		ProcessoMovimentacaoKey key = new ProcessoMovimentacaoKey();
    		key.setProduto(p);
    		key.setTipoMovimentacao(tm);
    		key.setTipoEntradaSaida(dto.getKeyTipoEntradaSaida());
        	
        	ProcessoMovimentacao processoMovimentacao = repository.findOne(key);
        	
        	if (processoMovimentacao == null) {
        		throw new NotFoundException();
        	}
        	
        	processoMovimentacao.setLocalEstoque(le);
        	
            repository.save(processoMovimentacao);
            
        } catch (ProdutoException | TipoMovimentacaoException | LocalEstoqueException ptex) {
        	logger.error("save()", ptex);
            throw ptex;
            
        } catch (NotFoundException nfex) {
        	logger.error("save()", nfex);
            throw nfex;
            
        } catch (RollbackException rex) {
            logger.error("save()", rex);
            throw rex;
            
        } catch (Exception ex) {
            logger.error("save()", ex);
            throw ex;
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
            throw ex;
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
            throw ex;
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

	public List<ProcessoMovimentacaoDTO> listByProdutoOrderByTipoMovimentacao(Integer produtoId) throws ProcessoMovimentacaoException, ProdutoException {
		ProdutoDTO p = produtoService.get(produtoId);
		
		if (p == null) {
			throw new NotFoundException();
		}
		
		List<ProcessoMovimentacao> list = repository.findByProdutoOrderByTipoMovimentacao(produtoId);
		
		return initializeList(list);
	}
	
	public List<ProcessoMovimentacaoDTO> listByTipoMovimentacaoOrderByProduto(Integer tipoMovimentacaoId) throws ProcessoMovimentacaoException, TipoMovimentacaoException {
		TipoMovimentacaoDTO tm = tipoMovimentacaoService.get(tipoMovimentacaoId);
		if (tm == null) {
			throw new NotFoundException();
		}
		List<ProcessoMovimentacao> list = repository.findByTipoMovimentacaoOrderByProduto(tipoMovimentacaoId);
		return initializeList(list);
	}
	
	public List<ProcessoMovimentacaoDTO> listByLocalEstoqueOrderByTipoMovimentacaoProdutoTipoEntradaSaida(Integer localEstoqueId) throws ProcessoMovimentacaoException, LocalEstoqueException {
		LocalEstoqueDTO le = localEstoqueService.get(localEstoqueId);
		if (le == null) {
			throw new NotFoundException();
		}
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
