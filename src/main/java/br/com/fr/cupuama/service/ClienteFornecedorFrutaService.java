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

import br.com.fr.cupuama.entity.ClienteFornecedor;
import br.com.fr.cupuama.entity.ClienteFornecedorFruta;
import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.dto.ClienteFornecedorDTO;
import br.com.fr.cupuama.entity.dto.ClienteFornecedorFrutaDTO;
import br.com.fr.cupuama.entity.dto.FrutaDTO;
import br.com.fr.cupuama.entity.key.ClienteFornecedorFrutaKey;
import br.com.fr.cupuama.exception.ClienteFornecedorException;
import br.com.fr.cupuama.exception.ClienteFornecedorFrutaException;
import br.com.fr.cupuama.exception.FrutaException;
import br.com.fr.cupuama.repository.ClienteFornecedorFrutaRepository;
import br.com.fr.cupuama.util.Util;

/**
 *
 * @author Romeu Franzoia Jr
 */
@SuppressWarnings("unused")
@Service
public class ClienteFornecedorFrutaService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	ClienteFornecedorFrutaRepository repository;
	
	@Autowired
	ClienteFornecedorService clienteFornecedorService;
	
	@Autowired
	FrutaService frutaService;

    public ClienteFornecedorFrutaDTO get(ClienteFornecedorFrutaKey key) throws ClienteFornecedorFrutaException {
        try {
        	ClienteFornecedorFruta cff = repository.findOne(key);
        	
        	if (cff == null) {
        		throw new NotFoundException("ClienteFornecedor x Fruta não encontrado!");
        	}
        	
    		Hibernate.initialize(cff.getKey().getClienteFornecedor());
    		Hibernate.initialize(cff.getKey().getFruta());
    		
            return Util.buildDTO(cff, ClienteFornecedorFrutaDTO.class);
        } catch (Exception ex) {
            logger.error("get()", ex);
            throw ex;
        }
    }
    
    public void save(ClienteFornecedorFrutaDTO dto) throws ClienteFornecedorFrutaException, ClienteFornecedorException, FrutaException {
        try {
    		ClienteFornecedorDTO cf = clienteFornecedorService.get(dto.getKeyClienteFornecedorId());
    		FrutaDTO f = frutaService.get(dto.getKeyFrutaId());
    		
    		if (cf == null || f == null) {
    			throw new NotFoundException("CllienteFornecedor e/ou Fruta não encontrado(s)!");
    		}
    		
    		ClienteFornecedorFrutaKey key = new ClienteFornecedorFrutaKey();
    		key.setClienteFornecedor(Util.buildDTO(cf, ClienteFornecedor.class));
    		key.setFruta(Util.buildDTO(f, Fruta.class));
        	
        	ClienteFornecedorFruta clienteFornecedorFruta = new ClienteFornecedorFruta();
        	clienteFornecedorFruta.setKey(key);
        	
            repository.save(clienteFornecedorFruta);
            
        } catch (ClienteFornecedorException | FrutaException | NotFoundException nfex) {
        	logger.error("save()", nfex);
            throw nfex;
            
        } catch (RollbackException rex) {
            logger.error("save()", rex);
            throw rex;
            
        }
    }
    
	public void deleteByClienteFornecedor(Integer clienteFornecedorId) throws ClienteFornecedorFrutaException {
        try {
            List<ClienteFornecedorFruta> list = repository.findByClienteFornecedor(clienteFornecedorId);
            for (ClienteFornecedorFruta cff : list) {
            	delete(cff);
            }
        } catch (Exception ex) {
            logger.error("deleteByClienteFornecedor()", ex);
            throw ex;
        }
	}

	public void deleteByFruta(Integer frutaId) throws ClienteFornecedorFrutaException {
        try {
            List<ClienteFornecedorFruta> list = repository.findByFruta(frutaId);
            for (ClienteFornecedorFruta cff : list) {
            	delete(cff);
            }
        } catch (Exception ex) {
            logger.error("deleteByFruta()", ex);
            throw ex;
        }
	}
	
	public void deleteByKey(ClienteFornecedorFrutaKey key) throws ClienteFornecedorFrutaException {
        try {
            repository.delete(key);
        } catch (Exception ex) {
            logger.error("deleteByKey()", ex);
            throw ex;
        }
	}
	
	public void delete(ClienteFornecedorFruta clienteFornecedorFruta) throws ClienteFornecedorFrutaException {
		try {
			repository.delete(clienteFornecedorFruta);
        } catch (RollbackException rex) {
            logger.error("delete()", rex);
            throw rex;
            
        } catch (Exception ex) {
            logger.error("delete()", ex);
            throw ex;
        }
	}
    
    public List<ClienteFornecedorFrutaDTO> listByClienteFornecedor(Integer clienteFornecedorId) throws ClienteFornecedorFrutaException, ClienteFornecedorException {
    	List<ClienteFornecedorFruta> list = repository.findByClienteFornecedor(clienteFornecedorId);
    	return initializeList(list);
    }

    public List<ClienteFornecedorFrutaDTO> listByFruta(Integer frutaId) throws ClienteFornecedorFrutaException, FrutaException {
    	List<ClienteFornecedorFruta> list = repository.findByFruta(frutaId);
    	return initializeList(list);
    }


    public void syncronizeClienteFornecedorFrutaByClienteFornecedor(Integer clienteFornecedorId, List<ClienteFornecedorFrutaDTO> clienteFornecedorFrutaList) throws ClienteFornecedorFrutaException, ClienteFornecedorException, FrutaException {
    	ClienteFornecedorDTO cf = clienteFornecedorService.get(clienteFornecedorId);
    	
    	if (cf == null) {
    		throw new NotFoundException("ClienteFornecedor não encontrado(s)!");
    	}
    	
    	deleteByClienteFornecedor(clienteFornecedorId);
    	
    	for (ClienteFornecedorFrutaDTO cff : clienteFornecedorFrutaList) {
    		if (cff.getKeyClienteFornecedorId().equals(clienteFornecedorId)) {
    			try {
    				FrutaDTO f = frutaService.get(cff.getKeyFrutaId());
        			save(cff);
				} catch (NotFoundException nfe) {
					logger.error(nfe);
				}
    		}
    	}
    }
    

    public void syncronizeClienteFornecedorFrutaByFruta(Integer frutaId, List<ClienteFornecedorFrutaDTO> clienteFornecedorFrutaList) throws ClienteFornecedorFrutaException, ClienteFornecedorException, FrutaException {
    	FrutaDTO f = frutaService.get(frutaId);
    	
    	if (f == null) {
    		throw new NotFoundException("Fruta não encontrada!");
    	}
    	
    	deleteByFruta(frutaId);
    	
    	for (ClienteFornecedorFrutaDTO cff : clienteFornecedorFrutaList) {
    		if (cff.getKeyFrutaId().equals(frutaId)) {
    			try {
					ClienteFornecedorDTO cf = clienteFornecedorService.get(cff.getKeyClienteFornecedorId());
    				save(cff);
				} catch (NotFoundException nfe) {
					logger.error(nfe);
				}
    		}
    	}
    }
    
	private List<ClienteFornecedorFrutaDTO> initializeList(List<ClienteFornecedorFruta> list) {
		list.stream().forEach(cff -> {
    		Hibernate.initialize(cff.getKey().getClienteFornecedor());
    		Hibernate.initialize(cff.getKey().getFruta());
    	});
    	return Util.buildListDTO(list, ClienteFornecedorFrutaDTO.class);
	}
	
}
