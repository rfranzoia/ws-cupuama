package br.com.fr.cupuama.service;

import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.Constantes;
import br.com.fr.cupuama.entity.Estoque;
import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.LocalEstoque;
import br.com.fr.cupuama.entity.Produto;
import br.com.fr.cupuama.entity.dto.EstoqueDTO;
import br.com.fr.cupuama.entity.dto.FrutaDTO;
import br.com.fr.cupuama.entity.dto.LocalEstoqueDTO;
import br.com.fr.cupuama.entity.dto.ProdutoDTO;
import br.com.fr.cupuama.entity.key.EstoqueKey;
import br.com.fr.cupuama.exception.EstoqueException;
import br.com.fr.cupuama.exception.FrutaException;
import br.com.fr.cupuama.exception.LocalEstoqueException;
import br.com.fr.cupuama.exception.ProdutoException;
import br.com.fr.cupuama.repository.EstoqueRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class EstoqueService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	EstoqueRepository repository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	FrutaService frutaService;
	
	@Autowired
	LocalEstoqueService localEstoqueService;

	public EstoqueDTO get(EstoqueDTO dto) throws EstoqueException {
		
		EstoqueKey key = getEstoqueKey(dto.getKeyProdutoId(), dto.getKeyFrutaId(), dto.getKeyLocalEstoqueId());
		key.setAnoMes(dto.getKeyAnoMes());

		return get(key);
	}
	
	public EstoqueDTO get(EstoqueKey key) throws EstoqueException {
		Estoque estoque = repository.findOne(key);
		
		if (estoque == null) {
			throw new NotFoundException();
		}
		
		return Util.buildDTO(estoque, EstoqueDTO.class);
	}

	public EstoqueDTO save(EstoqueDTO dto) throws EstoqueException {

		try {
			Estoque estoque = new Estoque();
			
			EstoqueKey key = getEstoqueKey(dto.getKeyProdutoId(), dto.getKeyFrutaId(), dto.getKeyLocalEstoqueId());
			key.setAnoMes(dto.getKeyAnoMes());
			estoque.setKey(key);

			buildEstoque(dto, estoque);
			repository.save(estoque);

			return Util.buildDTO(estoque, EstoqueDTO.class);
			
		} catch (RollbackException rex) {
			logger.error("save()", rex);
			throw new EstoqueException((Exception) rex.getCause());
			
		}

	}
	
	public void delete(EstoqueKey key) throws EstoqueException {
		Estoque estoque = repository.findOne(key);

		if (estoque == null) {
			throw new NotFoundException();
		}
		
		delete(estoque);
	}

	public void delete(Estoque estoque) throws EstoqueException {
		try {
			repository.delete(estoque);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			throw new EstoqueException((Exception) cvex.getCause());
			
		} catch (RollbackException rex) {
			logger.error("delete():rollback", rex);
			throw new EstoqueException((Exception) rex.getCause());
			
		} catch (Exception ex) {
			logger.error("delete()", ex);
			throw new EstoqueException(ex);
		}
	}

	public EstoqueDTO update(EstoqueKey key, EstoqueDTO dto) throws EstoqueException {
		try {

			Estoque estoque = repository.findOne(key);

			if (estoque == null) {
				throw new NotFoundException();
			}

			buildEstoque(dto, estoque);
			repository.save(estoque);

			return Util.buildDTO(estoque, EstoqueDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}
	
	public EstoqueDTO updateSaldo(EstoqueKey key, Character tipoEntradaSaida, Float quantidade) throws EstoqueException {

		if (key.getLocalEstoque().getGuardaEstoque().equals(Constantes.NAO)) {
			return null;
		}
		
		EstoqueDTO dto = null;
		
		try {
			dto = get(key);
			
		} catch (NotFoundException nfex) {
			try {
				Estoque estoque = new Estoque();
				
				estoque.setKey(key);
				estoque.setQtEntradas(0.0f);
				estoque.setQtSaidas(0.0f);
				estoque.setQtSaldoAnterior(0.0f);
				estoque.setQtSaldoAtual(0.0f);
				
				repository.save(estoque);
				dto = get(key);
			} catch (Exception ex) {
				logger.error("updateSaldo()", ex);
				throw ex;
			}
		}
		
		Float entradas = dto.getQtEntradas();
		Float saidas = dto.getQtSaidas();
		
		switch (tipoEntradaSaida) {
			case 'E':
				entradas += quantidade;
				break;
			case 'S':
				saidas += quantidade;
				break;
		}
		
		dto.setQtEntradas(entradas);
		dto.setQtSaidas(saidas);
		
		update(key, dto);
		
		return dto;
	}
	
	public void updateFromAnoMesForward(String anoMes, Integer produtoId, Integer frutaId, Integer localEstoqueId) throws EstoqueException {

		EstoqueKey key = getEstoqueKey(produtoId, frutaId, localEstoqueId);

		List<EstoqueDTO> list = listByAnoMesAndProdutoOrFrutaOrLocalEstoque(anoMes, produtoId, frutaId, localEstoqueId);

		Float saldoAnterior = -1f;

		for (EstoqueDTO dto : list) {

			if (saldoAnterior > 0) {
				dto.setQtSaldoAnterior(saldoAnterior);
			}

			Float saldoAtual = dto.getQtSaldoAnterior() + dto.getQtEntradas() - dto.getQtSaidas();
			dto.setQtSaldoAtual(saldoAtual);

			key.setAnoMes(dto.getKeyAnoMes());

			update(key, dto);

			saldoAnterior = saldoAtual;

		}
	}

	private EstoqueKey getEstoqueKey(Integer produtoId, Integer frutaId, Integer localEstoqueId) throws NotFoundException {
		try {
			ProdutoDTO p = produtoService.get(produtoId);
			FrutaDTO f = frutaService.get(frutaId);
			LocalEstoqueDTO le = localEstoqueService.get(localEstoqueId);

			EstoqueKey key = new EstoqueKey();
			key.setProduto(Util.buildDTO(p, Produto.class));
			key.setFruta(Util.buildDTO(f, Fruta.class));
			key.setLocalEstoque(Util.buildDTO(le, LocalEstoque.class));
			return key;
		} catch (ProdutoException | FrutaException | LocalEstoqueException pfleex) {
			logger.error("save()", pfleex);
			throw new NotFoundException();
		}
	}
	
	public static final String REGEX = "([0-9]{4})([0-9]{2})";
	
	public void createEntries(String anoMesInicio, String anoMesFim) {

		String[] inicio = anoMesInicio
				.replaceAll(REGEX, "$1/$2")
				.split("[/]");
		
		try {
			int anoInicio = Integer.valueOf(inicio[0]);
			int mesInicio = Integer.valueOf(inicio[1]);

			do {

				anoMesInicio = String.format("%04d", anoInicio) + String.format("%02d", mesInicio);
				System.out.println(anoMesInicio);

				mesInicio++;
				if (mesInicio > 12) {
					anoInicio++;
					mesInicio = 1;
				}

			} while (Integer.valueOf(anoMesInicio) < Integer.valueOf(anoMesFim));
		} catch (Exception e) {
		}

	}

	public List<EstoqueDTO> listAll() throws EstoqueException {
		List<Estoque> list = Lists.newArrayList(repository.findAll());
		return initializeList(list);
	}
	
	public List<EstoqueDTO> listByAnoMesAndProdutoOrFrutaOrLocalEstoque(String anoMes, Integer produtoId, Integer frutaId, Integer localEstoqueId) throws EstoqueException {
		try {
			ProdutoDTO p = produtoService.get(produtoId);
			FrutaDTO f = frutaService.get(frutaId);
			LocalEstoqueDTO le = localEstoqueService.get(localEstoqueId);
			
			List<Estoque> list = repository.findByAnoMesProdutoFrutaLocalEstoque(anoMes, p.getId(), f.getId(), le.getId());
			return initializeList(list);
		} catch (ProdutoException | FrutaException | LocalEstoqueException pfleex) {
			logger.error("listByAnoMesAndProdutoOrFrutaOrLocalEstoque()", pfleex);
			throw new NotFoundException();
		}
		
	}

	private List<EstoqueDTO> initializeList(List<Estoque> list) {
		for (Estoque e : list) {
			Hibernate.initialize(e.getKey().getProduto());
			Hibernate.initialize(e.getKey().getFruta());
			Hibernate.initialize(e.getKey().getLocalEstoque());
		}
		return Util.buildListDTO(list, EstoqueDTO.class);
	}
	
	
	private void buildEstoque(EstoqueDTO dto, Estoque estoque) {
		estoque.setQtEntradas(dto.getQtEntradas());
		estoque.setQtSaidas(dto.getQtSaidas());
		estoque.setQtSaldoAnterior(dto.getQtSaldoAnterior());
		
		Float saldoAtual = dto.getQtSaldoAnterior() + dto.getQtEntradas() - dto.getQtSaidas();
		estoque.setQtSaldoAtual(saldoAtual);
	}
}
