/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.Produto;
import br.com.fr.cupuama.entity.ProdutoFruta;
import br.com.fr.cupuama.entity.dto.FrutaDTO;
import br.com.fr.cupuama.entity.dto.ProdutoDTO;
import br.com.fr.cupuama.entity.dto.ProdutoFrutaDTO;
import br.com.fr.cupuama.entity.key.ProdutoFrutaKey;
import br.com.fr.cupuama.exception.FrutaException;
import br.com.fr.cupuama.exception.ProdutoException;
import br.com.fr.cupuama.exception.ProdutoFrutaException;
import br.com.fr.cupuama.repository.ProdutoFrutaRepository;
import br.com.fr.cupuama.util.Util;

/**
 *
 * @author Romeu Franzoia Jr
 */
@SuppressWarnings("unused")
@Service
public class ProdutoFrutaService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	ProdutoFrutaRepository repository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	FrutaService frutaService;

    public ProdutoFrutaDTO get(ProdutoFrutaKey key) throws ProdutoFrutaException {
		ProdutoFruta pf = repository.findOne(key);
		
		if (pf == null) {
			throw new NotFoundException("Produto x Fruta não encontrado!");
		}
		
		Hibernate.initialize(pf.getKey().getProduto());
		Hibernate.initialize(pf.getKey().getFruta());
		
	    return Util.buildDTO(pf, ProdutoFrutaDTO.class);
    }
    
    public ProdutoFrutaDTO save(ProdutoFrutaDTO dto) throws ProdutoFrutaException, ProdutoException, FrutaException {
        try {
    		ProdutoDTO p = produtoService.get(dto.getKeyProdutoId());
    		FrutaDTO f = frutaService.get(dto.getKeyFrutaId());
    		
    		if (p == null || f == null) {
    			throw new NotFoundException("Produto e/ou Fruta não encontrado(s)!");
    		}
    		
    		ProdutoFrutaKey key = new ProdutoFrutaKey();
    		key.setProduto(Util.buildDTO(p, Produto.class));
    		key.setFruta(Util.buildDTO(f, Fruta.class));
        	
        	ProdutoFruta produtoFruta = new ProdutoFruta();
        	produtoFruta.setKey(key);
        	
            repository.save(produtoFruta);
            
            return Util.buildDTO(produtoFruta, ProdutoFrutaDTO.class);
            
        } catch (RollbackException rex) {
            logger.error("save()", rex);
            throw rex;
            
        }
    }
    
    @Transactional
	public void deleteByProduto(Integer produtoId) throws ProdutoFrutaException {
        try {
            List<ProdutoFruta> list = repository.findByProduto(produtoId);
            for (ProdutoFruta pf : list) {
            	delete(pf);
            }
        } catch (Exception ex) {
            logger.error("deleteByProduto()", ex);
            throw ex;
        }
	}

	@Transactional
	public void deleteByFruta(Integer frutaId) throws ProdutoFrutaException {
        try {
            List<ProdutoFruta> list = repository.findByFruta(frutaId);
            for (ProdutoFruta pf : list) {
            	delete(pf);
            }
        } catch (Exception ex) {
            logger.error("deleteByFruta()", ex);
            throw ex;
        }
	}
	
	public void deleteByKey(ProdutoFrutaKey key) throws ProdutoFrutaException {
        try {
            repository.delete(key);
        } catch (Exception ex) {
            logger.error("deleteByKey()", ex);
            throw ex;
        }
	}
	
	public void delete(ProdutoFruta produtoFruta) throws ProdutoFrutaException {
		try {
			repository.delete(produtoFruta);
        } catch (RollbackException rex) {
            logger.error("delete()", rex);
            throw rex;
            
        } catch (Exception ex) {
            logger.error("delete()", ex);
            throw ex;
        }
	}
    
	public List<ProdutoFrutaDTO> listAllOrderByProdutoFruta() throws ProdutoFrutaException {
		List<ProdutoFruta> list = repository.findOrderByProdutoAndFruta();
    	return initializeList(list);		
	}

    public List<ProdutoFrutaDTO> listByProduto(Integer produtoId) throws ProdutoFrutaException {
    	List<ProdutoFruta> list = repository.findByProduto(produtoId);
    	return initializeList(list);
    }
    
    
    public List<ProdutoFrutaDTO> listByFruta(Integer frutaId) throws ProdutoFrutaException {
    	List<ProdutoFruta> list = repository.findByFruta(frutaId);
    	return initializeList(list);
    }


    public void syncronizeProdutoFrutaByProduto(Integer produtoId, List<ProdutoFrutaDTO> produtoFrutaList) throws ProdutoFrutaException, ProdutoException, FrutaException {
    	ProdutoDTO p = produtoService.get(produtoId);
    	
    	if (p == null) {
    		throw new NotFoundException("Produto não encontrado!");
    	}
    	
    	deleteByProduto(produtoId);
    	
    	for (ProdutoFrutaDTO pf : produtoFrutaList) {
    		if (pf.getKeyProdutoId().equals(produtoId)) {
    			try {
					FrutaDTO f = frutaService.get(pf.getKeyFrutaId());
    				save(pf);	
				} catch (NotFoundException nfe) {
					logger.error(nfe);
				}
    		}
    	}
    }
    

    @Transactional
    public void syncronizeProdutoFrutaByFruta(Integer frutaId, List<ProdutoFrutaDTO> produtoFrutaList) throws ProdutoFrutaException, ProdutoException, FrutaException {
    	FrutaDTO f = frutaService.get(frutaId);
    	
    	if (f == null) {
    		throw new NotFoundException("Fruta não encontrada!");
    	}
    	
    	deleteByFruta(frutaId);
    	
    	for (ProdutoFrutaDTO pf : produtoFrutaList) {
    		if (pf.getKeyFrutaId().equals(frutaId)) {
    			try {
					ProdutoDTO p = produtoService.get(pf.getKeyProdutoId());
    				save(pf);	
				} catch (NotFoundException nfe) {
					logger.error(nfe);
				}	
    		}
    	}
    }
    
	private List<ProdutoFrutaDTO> initializeList(List<ProdutoFruta> list) {
		list.stream().forEach(pf -> {
    		Hibernate.initialize(pf.getKey().getProduto());
    		Hibernate.initialize(pf.getKey().getFruta());
    	});
    	return Util.buildListDTO(list, ProdutoFrutaDTO.class);
	}
	
}