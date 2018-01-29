package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.Constantes;
import br.com.fr.cupuama.entity.TipoDocumento;
import br.com.fr.cupuama.entity.dto.TipoDocumentoDTO;
import br.com.fr.cupuama.exception.TipoDocumentoException;
import br.com.fr.cupuama.repository.TipoDocumentoRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class TipoDocumentoService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	TipoDocumentoRepository repository;

	public TipoDocumentoDTO get(Integer id) throws TipoDocumentoException {
		TipoDocumento tipoDocumento = repository.findOne(id);
		
		if (tipoDocumento == null) {
			throw new NotFoundException("TipoDocumento não encontrado!");
		}
		
		return Util.buildDTO(tipoDocumento, TipoDocumentoDTO.class);
	}

	public TipoDocumentoDTO save(TipoDocumentoDTO dto) throws TipoDocumentoException {

		try {
			TipoDocumento tipoDocumento = new TipoDocumento();

			buildTipoDocumento(dto, tipoDocumento);
			tipoDocumento.setSituacao(Constantes.SITUACAO_ATIVO);
			repository.save(tipoDocumento);

			return Util.buildDTO(tipoDocumento, TipoDocumentoDTO.class);
		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}

	}

	public void delete(Integer tipoDocumentoId) throws TipoDocumentoException {
		TipoDocumento tipoDocumento = repository.findOne(tipoDocumentoId);

		if (tipoDocumento == null) {
			throw new NotFoundException();
		}
		
		delete(tipoDocumento);

	}

	public void delete(TipoDocumento tipoDocumento) throws TipoDocumentoException {
		try {
			repository.delete(tipoDocumento);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			
			try {
				tipoDocumento.setSituacao(Constantes.SITUACAO_INATIVO);
				repository.save(tipoDocumento);
			} catch (Exception ex) {
				throw new TipoDocumentoException((Exception) cvex.getCause());
			}
		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public TipoDocumentoDTO update(Integer tipoDocumentoId, TipoDocumentoDTO dto) throws TipoDocumentoException {
		try {

			TipoDocumento tipoDocumento = repository.findOne(tipoDocumentoId);
			
			if (tipoDocumento == null) {
				throw new NotFoundException();
			}

			buildTipoDocumento(dto, tipoDocumento);
			repository.save(tipoDocumento);

			return Util.buildDTO(tipoDocumento, TipoDocumentoDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public List<TipoDocumentoDTO> listAll() throws TipoDocumentoException {
		List<TipoDocumento> list = Lists.newArrayList(repository.findAll());
		return Util.buildListDTO(list, TipoDocumentoDTO.class);
	}
	
	public List<TipoDocumentoDTO> listAllOrderByNome() throws TipoDocumentoException {
		List<TipoDocumento> list = repository.findAllOrderByNome();
		return Util.buildListDTO(list, TipoDocumentoDTO.class);
	}

	private void buildTipoDocumento(TipoDocumentoDTO dto, TipoDocumento tipoDocumento) {
		tipoDocumento.setNome(dto.getNome());
	}
}
