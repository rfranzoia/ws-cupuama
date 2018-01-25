package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.Constantes;
import br.com.fr.cupuama.entity.TipoMovimentacao;
import br.com.fr.cupuama.entity.dto.TipoMovimentacaoDTO;
import br.com.fr.cupuama.exception.TipoMovimentacaoException;
import br.com.fr.cupuama.repository.TipoMovimentacaoRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class TipoMovimentacaoService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	TipoMovimentacaoRepository repository;

	public TipoMovimentacaoDTO get(Integer id) throws TipoMovimentacaoException, NotFoundException {
		TipoMovimentacao tipoMovimentacao = repository.findOne(id);
		
		if (tipoMovimentacao == null) {
			throw new NotFoundException("TipoMovimentacao não encontrado!");
		}
		
		return Util.buildDTO(tipoMovimentacao, TipoMovimentacaoDTO.class);
	}

	public TipoMovimentacaoDTO save(TipoMovimentacaoDTO dto) throws TipoMovimentacaoException {

		try {
			TipoMovimentacao tipoMovimentacao = new TipoMovimentacao();

			buildTipoMovimentacao(dto, tipoMovimentacao);
			tipoMovimentacao.setSituacao(Constantes.SITUACAO_ATIVO);
			repository.save(tipoMovimentacao);

			return Util.buildDTO(tipoMovimentacao, TipoMovimentacaoDTO.class);
		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}

	}

	public void delete(Integer tipoMovimentacaoId) throws TipoMovimentacaoException {
		TipoMovimentacao tipoMovimentacao = repository.findOne(tipoMovimentacaoId);

		if (tipoMovimentacao == null) {
			throw new NotFoundException();
		}
		
		delete(tipoMovimentacao);

	}

	public void delete(TipoMovimentacao tipoMovimentacao) throws TipoMovimentacaoException {
		try {
			repository.delete(tipoMovimentacao);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			
			try {
				tipoMovimentacao.setSituacao(Constantes.SITUACAO_INATIVO);
				repository.save(tipoMovimentacao);
			} catch (Exception ex) {
				throw new TipoMovimentacaoException((Exception) cvex.getCause());
			}
		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public TipoMovimentacaoDTO update(Integer tipoMovimentacaoId, TipoMovimentacaoDTO dto) throws TipoMovimentacaoException {
		try {

			TipoMovimentacao tipoMovimentacao = repository.findOne(tipoMovimentacaoId);
			
			if (tipoMovimentacao == null) {
				throw new NotFoundException();
			}

			buildTipoMovimentacao(dto, tipoMovimentacao);
			repository.save(tipoMovimentacao);

			return Util.buildDTO(tipoMovimentacao, TipoMovimentacaoDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public List<TipoMovimentacaoDTO> listAll() throws TipoMovimentacaoException {
		List<TipoMovimentacao> list = Lists.newArrayList(repository.findAll());
		return Util.buildListDTO(list, TipoMovimentacaoDTO.class);
	}
	
	public List<TipoMovimentacaoDTO> listAllOrderByNome() throws TipoMovimentacaoException {
		List<TipoMovimentacao> list = repository.findAllOrderByNome();
		return Util.buildListDTO(list, TipoMovimentacaoDTO.class);
	}

	private void buildTipoMovimentacao(TipoMovimentacaoDTO dto, TipoMovimentacao tipoMovimentacao) {
		tipoMovimentacao.setNome(dto.getNome());
	}
}
