package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.Constantes;
import br.com.fr.cupuama.entity.Produto;
import br.com.fr.cupuama.entity.dto.ProdutoDTO;
import br.com.fr.cupuama.exception.ProdutoException;
import br.com.fr.cupuama.repository.ProdutoRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class ProdutoService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	ProdutoRepository repository;

	public ProdutoDTO get(Integer id) throws ProdutoException {
		try {
			Produto produto = repository.findOne(id);
			if (produto == null) {
				throw new NotFoundException();
			}
			return Util.buildDTO(produto, ProdutoDTO.class);
		} catch (Exception ex) {
			logger.error(ex);
			throw new NotFoundException();
		}
	}

	public ProdutoDTO save(ProdutoDTO dto) throws ProdutoException {

		try {
			Produto produto = new Produto();

			buildProduto(dto, produto);
			produto.setSituacao(Constantes.SITUACAO_ATIVO);
			
			repository.save(produto);

			return Util.buildDTO(produto, ProdutoDTO.class);
		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}

	}

	public void delete(Integer produtoId) throws ProdutoException {
		Produto produto = repository.findOne(produtoId);

		if (produto == null) {
			throw new NotFoundException();
		}
		
		delete(produto);

	}

	public void delete(Produto produto) throws ProdutoException {
		try {
			repository.delete(produto);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			
			try {
				produto.setSituacao(Constantes.SITUACAO_INATIVO);
				repository.save(produto);
			} catch (Exception ex) {
				throw new ProdutoException((Exception) cvex.getCause());
			}
		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public ProdutoDTO update(Integer produtoId, ProdutoDTO dto) throws ProdutoException {
		try {

			Produto produto = repository.findOne(produtoId);
			
			if (produto == null) {
				throw new NotFoundException();
			}

			buildProduto(dto, produto);
			repository.save(produto);

			return Util.buildDTO(produto, ProdutoDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public List<ProdutoDTO> listAll() throws ProdutoException {
		List<Produto> list = Lists.newArrayList(repository.findAll());
		return Util.buildListDTO(list, ProdutoDTO.class);
	}
	
	public List<ProdutoDTO> listAllOrderByNome() throws ProdutoException {
		List<Produto> list = repository.findAllOrderByNome();
		return Util.buildListDTO(list, ProdutoDTO.class);
	}

	private void buildProduto(ProdutoDTO dto, Produto produto) {
		produto.setNome(dto.getNome());
		produto.setUnidade(dto.getUnidade());
	}
}
