package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.entity.ClienteFornecedor;
import br.com.fr.cupuama.entity.dto.ClienteFornecedorDTO;
import br.com.fr.cupuama.exception.ClienteFornecedorException;
import br.com.fr.cupuama.repository.ClienteFornecedorRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class ClienteFornecedorService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	ClienteFornecedorRepository repository;

	public ClienteFornecedorDTO get(Integer id) throws ClienteFornecedorException {
		ClienteFornecedor clienteFornecedor = repository.findOne(id);
		
		if (clienteFornecedor == null) {
			throw new NotFoundException("nenhum registro encontrado com o ID espcificado: " + id);
		}
		
		return Util.buildDTO(clienteFornecedor, ClienteFornecedorDTO.class);
	}

	public ClienteFornecedorDTO save(ClienteFornecedorDTO dto) throws ClienteFornecedorException {

		try {
			ClienteFornecedor clienteFornecedor = new ClienteFornecedor();

			buildClienteFornecedor(dto, clienteFornecedor);
			repository.save(clienteFornecedor);

			return Util.buildDTO(clienteFornecedor, ClienteFornecedorDTO.class);
		} catch (RollbackException rex) {
			logger.error("save()", rex);
			throw new ClienteFornecedorException((Exception) rex.getCause());
		} catch (Exception ex) {
			logger.error("save()", ex);
			throw new ClienteFornecedorException(ex);
		}

	}

	public void delete(Integer clienteFornecedorId) throws ClienteFornecedorException {
		ClienteFornecedor clienteFornecedor = repository.findOne(clienteFornecedorId);

		if (clienteFornecedor == null) {
			throw new NotFoundException();
		}
		
		delete(clienteFornecedor);
	}

	public void delete(ClienteFornecedor clienteFornecedor) throws ClienteFornecedorException {
		try {
			repository.delete(clienteFornecedor);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			
			try {
				clienteFornecedor.setSituacao('I');
				repository.save(clienteFornecedor);
			} catch (Exception ex) {
				logger.error("delete():update", ex);
				throw new ClienteFornecedorException((Exception) cvex.getCause());
			}
			
		} catch (RollbackException rex) {
			logger.error("delete():rollback", rex);
			throw new ClienteFornecedorException((Exception) rex.getCause());
			
		} catch (Exception ex) {
			logger.error("delete()", ex);
			throw new ClienteFornecedorException(ex);
		}
	}

	public ClienteFornecedorDTO update(Integer clienteFornecedorId, ClienteFornecedorDTO dto) throws ClienteFornecedorException {
		try {

			ClienteFornecedor clienteFornecedor = repository.findOne(clienteFornecedorId);

			if (clienteFornecedor == null) {
				throw new NotFoundException();
			}

			buildClienteFornecedor(dto, clienteFornecedor);
			repository.save(clienteFornecedor);

			return dto;

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public List<ClienteFornecedorDTO> listAll() throws ClienteFornecedorException {
		List<ClienteFornecedor> list = Lists.newArrayList(repository.findAll());
		return Util.buildListDTO(list, ClienteFornecedorDTO.class);
	}
	
	public List<ClienteFornecedorDTO> listAllOrderByNome() throws ClienteFornecedorException {
		List<ClienteFornecedor> list = repository.findAllOrderByNome();
		return Util.buildListDTO(list, ClienteFornecedorDTO.class);
	}
	
	public List<ClienteFornecedorDTO> listByTipoOrderByNome(Character tipo) throws ClienteFornecedorException {
		List<ClienteFornecedor> list = repository.findByTipoOrderByNome(tipo);
		return Util.buildListDTO(list, ClienteFornecedorDTO.class);
	}
	
	private void buildClienteFornecedor(ClienteFornecedorDTO dto, ClienteFornecedor clienteFornecedor) {
		clienteFornecedor.setNome(dto.getNome());
		clienteFornecedor.setTipo(dto.getTipo());
		clienteFornecedor.setCpfCnpj(dto.getCpfCnpj());
	}
}
