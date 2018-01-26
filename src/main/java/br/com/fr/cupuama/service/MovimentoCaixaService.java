package br.com.fr.cupuama.service;

import java.util.Date;
import java.util.List;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.entity.MovimentoCaixa;
import br.com.fr.cupuama.entity.dto.MovimentoCaixaDTO;
import br.com.fr.cupuama.exception.CaixaException;
import br.com.fr.cupuama.exception.ItensMovimentoException;
import br.com.fr.cupuama.exception.MovimentoCaixaException;
import br.com.fr.cupuama.repository.MovimentoCaixaRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class MovimentoCaixaService {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	MovimentoCaixaRepository repository;
	
	@Autowired
	CaixaService caixaService;
	
	public MovimentoCaixaDTO get(Integer movimentoCaixaId) throws MovimentoCaixaException {
		MovimentoCaixa mc = repository.findOne(movimentoCaixaId);
		
		if (mc == null) {
			throw new NotFoundException("Nenhum movimento foi encontrado!");
		}
		 
		return Util.buildDTO(mc, MovimentoCaixaDTO.class);
	}
	
	@Transactional
	public MovimentoCaixaDTO save(MovimentoCaixaDTO dto) throws MovimentoCaixaException {
		try {
			MovimentoCaixa movimentoCaixa = new MovimentoCaixa();

			buildMovimentoCaixa(dto, movimentoCaixa);
			repository.save(movimentoCaixa);

			return Util.buildDTO(movimentoCaixa, MovimentoCaixaDTO.class);
			
		} catch (RollbackException rex) {
			logger.error("save()", rex);
			throw new MovimentoCaixaException((Exception) rex.getCause());
		}
	}
	
	@Transactional
	public MovimentoCaixaDTO update(Integer movimentoCaixaId, MovimentoCaixaDTO dto) throws MovimentoCaixaException {
		try {

			MovimentoCaixa movimentoCaixa = repository.findOne(movimentoCaixaId);

			if (movimentoCaixa == null) {
				throw new NotFoundException();
			}

			buildMovimentoCaixa(dto, movimentoCaixa);
			repository.save(movimentoCaixa);

			return Util.buildDTO(movimentoCaixa, MovimentoCaixaDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw new MovimentoCaixaException(ex);
		}
	}
	
	@Transactional
	public void delete(Integer movimentoId) throws MovimentoCaixaException {
		MovimentoCaixa movimentoCaixa = repository.findOne(movimentoId);

		if (movimentoCaixa == null) {
			throw new NotFoundException();
		}
		
		delete(movimentoId);
	}

	@Transactional
	public void delete(MovimentoCaixa movimentoCaixa) throws MovimentoCaixaException {
		try {
			repository.delete(movimentoCaixa);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			throw new MovimentoCaixaException((Exception) cvex.getCause());
			
		} catch (RollbackException rex) {
			logger.error("delete():rollback", rex);
			throw new MovimentoCaixaException((Exception) rex.getCause());
			
		} catch (Exception ex) {
			logger.error("delete()", ex);
			throw new MovimentoCaixaException(ex);
		}
	}
	
	@Transactional
	public MovimentoCaixaDTO addMovimentoCaixaAndUpdateCaixa(MovimentoCaixaDTO movimentoCaixaDTO) throws MovimentoCaixaException, CaixaException {
		logger.warn("addMovimentoCaixaAndUpdateCaixa()");
		
		if (movimentoCaixaDTO == null) {
			throw new NotFoundException("Nenhum informação de movimento foi encontrada!");
		}
		
		movimentoCaixaDTO = save(movimentoCaixaDTO);
		
		caixaService.processSaldo(Util.DATE_FORMAT_ANOMES.format(movimentoCaixaDTO.getDtMovimento()), movimentoCaixaDTO.getTipo(), movimentoCaixaDTO.getVlMovimento());
		
		return movimentoCaixaDTO;
	}
	
	@Transactional
	public MovimentoCaixaDTO updateMovimentoCaixaAndUpdateCaixa(Integer movimentoCaixaId, MovimentoCaixaDTO dto) throws MovimentoCaixaException, CaixaException {
		logger.warn("updateMovimentoCaixaAndUpdateCaixa()");
		
		MovimentoCaixaDTO movimentoCaixaDTO = get(movimentoCaixaId);
		
		if (movimentoCaixaDTO == null) {
			throw new NotFoundException("Nenhum informação de movimento foi encontrada!");
		}
		
		caixaService.processSaldo(Util.DATE_FORMAT_ANOMES.format(movimentoCaixaDTO.getDtMovimento()), movimentoCaixaDTO.getTipo(), (movimentoCaixaDTO.getVlMovimento() * -1.0));
		
		dto = update(movimentoCaixaId, dto);
		
		caixaService.processSaldo(Util.DATE_FORMAT_ANOMES.format(dto.getDtMovimento()), dto.getTipo(), dto.getVlMovimento());
		
		return dto;
	}
	
	@Transactional
	public void removeMovimentoCaixaAndUpdateCaixa(Integer movimentoCaixaId) throws MovimentoCaixaException, CaixaException {
		logger.warn("removeMovimentoCaixaAndUpdateCaixa()");
		
		MovimentoCaixaDTO movimentoCaixaDTO = get(movimentoCaixaId);
		
		if (movimentoCaixaDTO == null) {
			throw new NotFoundException("Nenhum informação de movimento foi encontrada!");
		}
		
		caixaService.processSaldo(Util.DATE_FORMAT_ANOMES.format(movimentoCaixaDTO.getDtMovimento()), movimentoCaixaDTO.getTipo(), (movimentoCaixaDTO.getVlMovimento() * -1.0));
		
		delete(movimentoCaixaId);
		
	}
	
	public List<MovimentoCaixaDTO> listAll() throws ItensMovimentoException {
		List<MovimentoCaixa> list = Lists.newArrayList(repository.findAll());
		return Util.buildListDTO(list, MovimentoCaixaDTO.class);
	}
	
	public List<MovimentoCaixaDTO> listByPeriodo(Date inicio, Date fim) throws MovimentoCaixaException {
		logger.warn("listByPeriodo()");
		List<MovimentoCaixa> list = repository.findByPeriodo(inicio, fim);
		return Util.buildListDTO(list, MovimentoCaixaDTO.class);
	}
	
	private void buildMovimentoCaixa(MovimentoCaixaDTO dto, MovimentoCaixa movimentoCaixa) throws MovimentoCaixaException {
		movimentoCaixa.setDtMovimento(dto.getDtMovimento());
		movimentoCaixa.setHistorico(dto.getHistorico());
		movimentoCaixa.setTipo(dto.getTipo());
		movimentoCaixa.setVlMovimento(dto.getVlMovimento());
	}
}
