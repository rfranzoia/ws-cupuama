package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.entity.ClienteFornecedor;
import br.com.fr.cupuama.entity.Movimento;
import br.com.fr.cupuama.entity.TipoDocumento;
import br.com.fr.cupuama.entity.TipoMovimentacao;
import br.com.fr.cupuama.entity.dto.ClienteFornecedorDTO;
import br.com.fr.cupuama.entity.dto.ItensMovimentoDTO;
import br.com.fr.cupuama.entity.dto.MovimentoDTO;
import br.com.fr.cupuama.entity.dto.TipoDocumentoDTO;
import br.com.fr.cupuama.entity.dto.TipoMovimentacaoDTO;
import br.com.fr.cupuama.entity.key.EstoqueKey;
import br.com.fr.cupuama.exception.ClienteFornecedorException;
import br.com.fr.cupuama.exception.EstoqueException;
import br.com.fr.cupuama.exception.ItensMovimentoException;
import br.com.fr.cupuama.exception.MovimentoException;
import br.com.fr.cupuama.exception.TipoDocumentoException;
import br.com.fr.cupuama.exception.TipoMovimentacaoException;
import br.com.fr.cupuama.repository.MovimentoRepository;
import br.com.fr.cupuama.util.Util;

@Service
public class MovimentoService {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	MovimentoRepository repository;
	
	@Autowired
	ItensMovimentoService itensMovimentoService;
	
	@Autowired
	ClienteFornecedorService clienteFornecedorService;
	
	@Autowired
	TipoMovimentacaoService tipoMovimentacaoService;
	
	@Autowired
	TipoDocumentoService tipoDocumentoService;
	
	@Autowired
	EstoqueService estoqueService;
	
	@Transactional
	public MovimentoDTO addMovimentoAndItensAndUpdateEstoque(MovimentoDTO movimentoDTO) throws MovimentoException {
		
		if (movimentoDTO == null) {
			throw new NotFoundException("Nenhum informação de movimento foi encontrada!");
		}

		// guarda a lista de itens para salvar posteriormente
		List<ItensMovimentoDTO> itens = movimentoDTO.getItensMovimento();
		
		// salva o movimento
		movimentoDTO = save(movimentoDTO);
		
		movimentoDTO.setItensMovimento(itens);

		try {
			// salva os itens
			addItensMovimento(movimentoDTO);	
		} catch (ItensMovimentoException | EstoqueException ex) {
			logger.error(ex);
			throw new MovimentoException(ex);
		}
		
		return movimentoDTO;
		
	}

	@Transactional
	public MovimentoDTO updateMovimentoItensAndEstoque(Integer movimentoId, MovimentoDTO movimentoDTO) throws MovimentoException {
		
		// recupera o movimento original
		MovimentoDTO movimento = getWithItensMovimento(movimentoId);

		try {
			// atualiza o movimento primeiro
			update(movimentoId, movimentoDTO);
			
			// remove todos os itens atualizando o estoque
			deleteItensMovimento(movimento);
			
			// salva os novos itens
			addItensMovimento(movimentoDTO);
		} catch (ItensMovimentoException | EstoqueException ex) {
			logger.error(ex);
			throw new MovimentoException(ex);
		}
		
		return movimento;
	}

	@Transactional
	public void deleteMovimentoAndUpdateEstoque(Integer movimentoId) throws MovimentoException {
		
		// recupera o movimento 
		MovimentoDTO movimento = getWithItensMovimento(movimentoId);
		
		try {
			// remove os itens atualizando o estoque
			deleteItensMovimento(movimento);
			
		} catch (ItensMovimentoException | EstoqueException ex) {
			logger.error(ex);
			throw new MovimentoException(ex);
		}
		
		// apaga o movimento
		delete(movimentoId);
			
	}
	
	@Transactional
	private void addItensMovimento(MovimentoDTO movimentoDTO) throws ItensMovimentoException, EstoqueException {
		
		// não faz nada se não houver itens para processar
		if (movimentoDTO.getItensMovimento() == null || movimentoDTO.getItensMovimento().isEmpty()) {
			return;
		}
		
		// adiciona o item e atualiza o estoque de acordo
		for (ItensMovimentoDTO itensDTO : movimentoDTO.getItensMovimento()) {
			itensDTO.setMovimentoId(movimentoDTO.getId());
			itensDTO = itensMovimentoService.save(itensDTO);
			
			// atualiza o estoque
			EstoqueKey estoqueKey = estoqueService.getEstoqueKey(itensDTO.getProdutoId(), itensDTO.getFrutaId(), itensDTO.getLocalEstoqueId());
			estoqueKey.setAnoMes(Util.DATE_FORMAT_ANOMES.format(movimentoDTO.getDtMovimento()));
			
			estoqueService.processSaldo(estoqueKey, itensDTO.getTipoEntradaSaida(), itensDTO.getQtMovimento());
		}
	}

	@Transactional
	private void deleteItensMovimento(MovimentoDTO movimento) throws ItensMovimentoException, EstoqueException {
		
		// não faz nada se não houver itens para processar
		if (movimento.getItensMovimento() == null || movimento.getItensMovimento().isEmpty()) {
			return;
		}
		
		// remove o item e atualiza o estoque de acordo
		for (ItensMovimentoDTO itensDTO : movimento.getItensMovimento()) {
			itensMovimentoService.delete(itensDTO.getId());
			
			// atualiza o estoque
			EstoqueKey estoqueKey = estoqueService.getEstoqueKey(itensDTO.getProdutoId(), itensDTO.getFrutaId(), itensDTO.getLocalEstoqueId());
			estoqueKey.setAnoMes(Util.DATE_FORMAT_ANOMES.format(movimento.getDtMovimento()));
			
			// a quantidade é negativa pois estamos removendo um registro anterior
			estoqueService.processSaldo(estoqueKey, itensDTO.getTipoEntradaSaida(), (itensDTO.getQtMovimento() * -1f));
		}
	}
	
	public MovimentoDTO getWithItensMovimento(Integer movimentoId) throws MovimentoException {
		logger.warn("getWithItensMovimento()");
		
		MovimentoDTO dto = get(movimentoId);
		
		try {
			List<ItensMovimentoDTO> itens = itensMovimentoService.listByMovimento(movimentoId);
			dto.setItensMovimento(itens);	
		} catch (ItensMovimentoException imex) {
			logger.error(imex);
		}
		
		return dto;
	}
	
	public MovimentoDTO get(Integer movimentoId) throws MovimentoException {
		Movimento m = repository.findOne(movimentoId);
		
		if (m == null) {
			throw new NotFoundException("Nenhum movimento foi encontrado!");
		}
		 
		return Util.buildDTO(m, MovimentoDTO.class);
	}
	
	@Transactional
	public MovimentoDTO save(MovimentoDTO dto) throws MovimentoException {
		try {
			Movimento movimento = new Movimento();

			buildMovimento(dto, movimento);
			repository.save(movimento);

			return Util.buildDTO(movimento, MovimentoDTO.class);
		} catch (TipoMovimentacaoException | ClienteFornecedorException | TipoDocumentoException ex) {
			logger.error("save()", ex);
			throw new MovimentoException((Exception) ex.getCause());
			
		} catch (RollbackException rex) {
			logger.error("save()", rex);
			throw new MovimentoException((Exception) rex.getCause());
		}
	}
	
	@Transactional
	public void delete(Integer movimentoId) throws MovimentoException {
		Movimento movimento = repository.findOne(movimentoId);

		if (movimento == null) {
			throw new NotFoundException();
		}
		
		delete(movimento);
	}

	@Transactional
	public void delete(Movimento movimento) throws MovimentoException {
		try {
			List<ItensMovimentoDTO> itens = itensMovimentoService.listByMovimento(movimento.getId());
			if (itens != null && !itens.isEmpty()) {
				for (ItensMovimentoDTO im : itens) {
					itensMovimentoService.delete(im.getId());
				}
			}
			
			repository.delete(movimento);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			throw new MovimentoException((Exception) cvex.getCause());
			
		} catch (RollbackException rex) {
			logger.error("delete():rollback", rex);
			throw new MovimentoException((Exception) rex.getCause());
			
		} catch (Exception ex) {
			logger.error("delete()", ex);
			throw new MovimentoException(ex);
		}
	}

	@Transactional
	public MovimentoDTO update(Integer movimentoId, MovimentoDTO dto) throws MovimentoException {
		try {

			Movimento movimento = repository.findOne(movimentoId);

			if (movimento == null) {
				throw new NotFoundException();
			}

			buildMovimento(dto, movimento);
			repository.save(movimento);

			return Util.buildDTO(movimento, MovimentoDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (TipoMovimentacaoException | ClienteFornecedorException | TipoDocumentoException ex) {
			logger.error("save()", ex);
			throw new MovimentoException((Exception) ex.getCause());
		}
	}
	
	public List<MovimentoDTO> listAllWithItensMovimento() throws MovimentoException {
		logger.warn("listAllWithItensMovimento()");
		List<MovimentoDTO> list = listAllOrderByDtMovimento();
		for (MovimentoDTO m : list) {
			try {
				List<ItensMovimentoDTO> itens = itensMovimentoService.listByMovimento(m.getId());
				m.setItensMovimento(itens);
			} catch (ItensMovimentoException imex) {
				logger.error(imex);
			}
		}
		return list;
	}
	
	// lista de todos os movimentos (não traz os itens)
	public List<MovimentoDTO> listAllOrderByDtMovimento() throws MovimentoException {
		logger.warn("listAllOrderByDtMovimento()");
		List<Movimento> list = repository.findAllOrderByDtMovimento();
		return initializeList(list);
	}
	
	// inicializa uma lista de registros e gera a lista de DTO correspondente
	private List<MovimentoDTO> initializeList(List<Movimento> list) {
		for (Movimento m : list) {
			Hibernate.initialize(m.getTipoMovimentacao());
			Hibernate.initialize(m.getClienteFornecedor());
			Hibernate.initialize(m.getTipoDocumento());
		}
		return Util.buildListDTO(list, MovimentoDTO.class);
	}
	
	private void buildMovimento(MovimentoDTO dto, Movimento movimento) throws MovimentoException, ClienteFornecedorException, TipoMovimentacaoException, TipoDocumentoException {
		try {
			ClienteFornecedorDTO cf = clienteFornecedorService.get(dto.getClienteFornecedorId());
			TipoMovimentacaoDTO tm = tipoMovimentacaoService.get(dto.getTipoMovimentacaoId());
			TipoDocumentoDTO td = tipoDocumentoService.get(dto.getTipoDocumentoId());
			
			movimento.setClienteFornecedor(Util.buildDTO(cf, ClienteFornecedor.class));
			movimento.setDocumento(dto.getDocumento());
			movimento.setTipoDocumento(Util.buildDTO(td, TipoDocumento.class));
			movimento.setDtMovimento(dto.getDtMovimento());
			movimento.setObservacao(dto.getObservacao());
			movimento.setTipoMovimentacao(Util.buildDTO(tm, TipoMovimentacao.class));
		} catch (NotFoundException nfe) {
			logger.error("", nfe);
			throw nfe;
			
		} catch (ClienteFornecedorException | TipoDocumentoException | TipoMovimentacaoException ex) {
			logger.error("", ex);
			throw ex;
		}
		
	}
}