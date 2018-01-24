package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.ItensMovimento;
import br.com.fr.cupuama.entity.LocalEstoque;
import br.com.fr.cupuama.entity.Movimento;
import br.com.fr.cupuama.entity.Produto;
import br.com.fr.cupuama.entity.dto.FrutaDTO;
import br.com.fr.cupuama.entity.dto.ItensMovimentoDTO;
import br.com.fr.cupuama.entity.dto.LocalEstoqueDTO;
import br.com.fr.cupuama.entity.dto.MovimentoDTO;
import br.com.fr.cupuama.entity.dto.ProdutoDTO;
import br.com.fr.cupuama.exception.ItensMovimentoException;
import br.com.fr.cupuama.repository.ItensMovimentoRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class ItensMovimentoService {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	ItensMovimentoRepository repository;
	
	@Autowired
	MovimentoService movimentoService;
	
	@Autowired
	FrutaService frutaService;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	LocalEstoqueService localEstoqueService;

	public ItensMovimentoDTO get(Integer itensItensMovimentoId) throws ItensMovimentoException {
		try {
			ItensMovimento m = repository.findOne(itensItensMovimentoId);
			
			if (m == null) {
				throw new NotFoundException("nenhum registro foi encontrado com o ID especificado!");
			}
			 
			return Util.buildDTO(m, ItensMovimentoDTO.class);
		} catch (Exception ex) {
			logger.error("", ex);
			throw new NotFoundException(ex);
		}
	}
	
	public ItensMovimentoDTO save(ItensMovimentoDTO dto) throws ItensMovimentoException {
		try {
			ItensMovimento itensItensMovimento = new ItensMovimento();

			buildItensMovimento(dto, itensItensMovimento);
			repository.save(itensItensMovimento);

			return Util.buildDTO(itensItensMovimento, ItensMovimentoDTO.class);
		} catch (RollbackException rex) {
			logger.error("save()", rex);
			throw new ItensMovimentoException((Exception) rex.getCause());
		} catch (Exception ex) {
			logger.error("save()", ex);
			throw new ItensMovimentoException(ex);
		}
	}
	
	public void delete(Integer itensMovimentoId) throws ItensMovimentoException {
		ItensMovimento itensMovimento = repository.findOne(itensMovimentoId);

		if (itensMovimento == null) {
			throw new NotFoundException();
		}
		
		delete(itensMovimento);
	}
	
	public void delete(ItensMovimento itensMovimento) throws ItensMovimentoException {
		try {
			repository.delete(itensMovimento);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			throw new ItensMovimentoException((Exception) cvex.getCause());
			
		} catch (RollbackException rex) {
			logger.error("delete():rollback", rex);
			throw new ItensMovimentoException((Exception) rex.getCause());
			
		} catch (Exception ex) {
			logger.error("delete()", ex);
			throw new ItensMovimentoException(ex);
		}
	}

	public List<ItensMovimentoDTO> listAll() throws ItensMovimentoException {
		List<ItensMovimento> list = Lists.newArrayList(repository.findAll());
		return initializeList(list);
	}
	
	public List<ItensMovimentoDTO> listByMovimento(Integer movimentoId) throws ItensMovimentoException {
		logger.warn("listByMovimento()");
		List<ItensMovimento> list = repository.findByMovimento(movimentoId);
		return initializeList(list);
	}
	
	
	// inicializa uma lista de registros e gera a lista de DTO correspondente
	private List<ItensMovimentoDTO> initializeList(List<ItensMovimento> list) {
		for (ItensMovimento im : list) {
			Hibernate.initialize(im.getFruta());
			Hibernate.initialize(im.getProduto());
			Hibernate.initialize(im.getLocalEstoque());
		}
		return Util.buildListDTO(list, ItensMovimentoDTO.class);
	}
	
	private void buildItensMovimento(ItensMovimentoDTO dto, ItensMovimento itensMovimento) throws ItensMovimentoException {
		try {
			MovimentoDTO movimento = movimentoService.get(dto.getMovimentodId());
			FrutaDTO fruta = frutaService.get(dto.getFrutaId());
			ProdutoDTO produto = produtoService.get(dto.getProdutoIid());
			LocalEstoqueDTO localEstoque = localEstoqueService.get(dto.getLocalEstoqueId());
			
			itensMovimento.setFruta(Util.buildDTO(fruta, Fruta.class));
			itensMovimento.setLocalEstoque(Util.buildDTO(localEstoque, LocalEstoque.class));
			itensMovimento.setMovimento(Util.buildDTO(movimento, Movimento.class));
			itensMovimento.setProduto(Util.buildDTO(produto, Produto.class));
			itensMovimento.setQtMovimento(dto.getQtMovimento());
			itensMovimento.setTipoEntradaSaida(dto.getTipoEntradaSaida());
			itensMovimento.setVlDesconto(dto.getVlDesconto());
			itensMovimento.setVlMovimento(dto.getVlMovimento());
		} catch (Exception ex) {
			logger.error("", ex);
			throw new ItensMovimentoException(ex);
		}
	}
}
