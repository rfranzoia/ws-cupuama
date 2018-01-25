package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.Constantes;
import br.com.fr.cupuama.entity.LocalEstoque;
import br.com.fr.cupuama.entity.dto.LocalEstoqueDTO;
import br.com.fr.cupuama.exception.LocalEstoqueException;
import br.com.fr.cupuama.repository.LocalEstoqueRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class LocalEstoqueService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	LocalEstoqueRepository repository;

	public LocalEstoqueDTO get(Integer id) throws LocalEstoqueException {
		LocalEstoque localEstoque = repository.findOne(id);
		
		if (localEstoque == null) {
			throw new NotFoundException("LocalEstoque não encontrado!");
		}
		
		return Util.buildDTO(localEstoque, LocalEstoqueDTO.class);
	}

	public LocalEstoqueDTO save(LocalEstoqueDTO dto) throws LocalEstoqueException {

		try {
			LocalEstoque localEstoque = new LocalEstoque();

			buildLocalEstoque(dto, localEstoque);
			localEstoque.setSituacao(Constantes.SITUACAO_ATIVO);
			
			repository.save(localEstoque);

			return Util.buildDTO(localEstoque, LocalEstoqueDTO.class);
		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}

	}

	public void delete(Integer localEstoqueId) throws LocalEstoqueException {
		LocalEstoque localEstoque = repository.findOne(localEstoqueId);

		if (localEstoque == null) {
			throw new NotFoundException();
		}
		
		delete(localEstoque);

	}

	public void delete(LocalEstoque localEstoque) throws LocalEstoqueException {
		try {
			repository.delete(localEstoque);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			
			try {
				localEstoque.setSituacao(Constantes.SITUACAO_INATIVO);
				repository.save(localEstoque);
			} catch (Exception ex) {
				throw new LocalEstoqueException((Exception) cvex.getCause());
			}
		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public LocalEstoqueDTO update(Integer localEstoqueId, LocalEstoqueDTO dto) throws LocalEstoqueException {
		try {

			LocalEstoque localEstoque = repository.findOne(localEstoqueId);
			
			if (localEstoque == null) {
				throw new NotFoundException();
			}

			buildLocalEstoque(dto, localEstoque);
			repository.save(localEstoque);

			return Util.buildDTO(localEstoque, LocalEstoqueDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public List<LocalEstoqueDTO> listAll() throws LocalEstoqueException {
		List<LocalEstoque> list = Lists.newArrayList(repository.findAll());
		return Util.buildListDTO(list, LocalEstoqueDTO.class);
	}
	
	public List<LocalEstoqueDTO> listAllOrderByNome() throws LocalEstoqueException {
		List<LocalEstoque> list = repository.findAllOrderByNome();
		return Util.buildListDTO(list, LocalEstoqueDTO.class);
	}
	
	private void buildLocalEstoque(LocalEstoqueDTO dto, LocalEstoque localEstoque) {
		localEstoque.setNome(dto.getNome());
		localEstoque.setGuardaEstoque(dto.getGuardaEstoque());
	}
}
