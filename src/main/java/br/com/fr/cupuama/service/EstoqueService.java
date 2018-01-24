package br.com.fr.cupuama.service;

import java.util.Date;
import java.util.List;

import javax.persistence.RollbackException;
import javax.transaction.Transactional;
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
import br.com.fr.cupuama.entity.dto.ProdutoFrutaDTO;
import br.com.fr.cupuama.entity.key.EstoqueKey;
import br.com.fr.cupuama.exception.EstoqueException;
import br.com.fr.cupuama.exception.FrutaException;
import br.com.fr.cupuama.exception.LocalEstoqueException;
import br.com.fr.cupuama.exception.ProdutoException;
import br.com.fr.cupuama.exception.ProdutoFrutaException;
import br.com.fr.cupuama.repository.EstoqueRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class EstoqueService {

	private final Logger logger = Logger.getLogger(getClass());
	public static final String REGEX = "([0-9]{4})([0-9]{2})";
	
	@Autowired
	EstoqueRepository repository;
	
	@Autowired
	ProdutoService produtoService;
	
	@Autowired
	FrutaService frutaService;
	
	@Autowired
	LocalEstoqueService localEstoqueService;
	
	@Autowired
	ProdutoFrutaService produtoFrutaService;

	// recupera um registro de estoque atravez do DTO informado 
	public EstoqueDTO get(EstoqueDTO dto) throws EstoqueException {
		
		EstoqueKey key = getEstoqueKey(dto.getKeyProdutoId(), dto.getKeyFrutaId(), dto.getKeyLocalEstoqueId());
		key.setAnoMes(dto.getKeyAnoMes());

		return get(key);
	}
	
	// recupera um registro de estoque atravez de uma chave informada
	public EstoqueDTO get(EstoqueKey key) throws EstoqueException {
		Estoque estoque = repository.findOne(key);
		
		if (estoque == null) {
			throw new NotFoundException();
		}
		
		return Util.buildDTO(estoque, EstoqueDTO.class);
	}

	// cria um novo registro de estoque (vazio) utilizando a chave informada
	private EstoqueDTO save(EstoqueKey key) throws EstoqueException {
		try {
			
			Estoque estoque = new Estoque();
			estoque.setKey(key);
			estoque.setQtEntradas(0.0f);
			estoque.setQtSaidas(0.0f);
			estoque.setQtSaldoAnterior(0.0f);
			estoque.setQtSaldoAtual(0.0f);
			
			repository.save(estoque);
			return Util.buildDTO(estoque, EstoqueDTO.class);
			
		} catch (RollbackException rex) {
			logger.error("save(key)", rex);
			throw rex;
			
		}
	}
	
	//cria um novo registro de estoque utilizando os dados do DTO informado
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
	
	// remove um registro de estoque com a chave informada
	public void delete(EstoqueKey key) throws EstoqueException {
		Estoque estoque = repository.findOne(key);

		if (estoque == null) {
			throw new NotFoundException();
		}
		
		delete(estoque);
	}

	// remove um registro de estoque pelos dados do registro
	private void delete(Estoque estoque) throws EstoqueException {
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

	// atualiza um registro de estoque com a chave informada e os dados do DTO
	// tambem atualiza o saldo deste registro
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
	
	// processa um registro de estoque adicionando uma quantidade para o tipo E/S informado
	@Transactional
	public EstoqueDTO processSaldo(EstoqueKey key, Character tipoEntradaSaida, Float quantidade) throws EstoqueException {

		if (key.getLocalEstoque().getGuardaEstoque().equals(Constantes.NAO)) {
			return null;
		}
		
		EstoqueDTO dto = null;
		
		try {
			dto = get(key);
			
		} catch (NotFoundException nfex) {
			try {
				dto = save(key);
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
	
	// atualiza os saldos dos registros de estoque que iniciam em AnoMes até o AnoMes da data corrente
	public void updateFromAnoMesToToday(String anoMes, Integer produtoId, Integer frutaId, Integer localEstoqueId) throws EstoqueException {

		// identifica o ano/mes atual
		String anoMesAtual = Util.DATE_FORMAT_ANOMES.format(new Date());
		
		// não processa nada se o ano/mes informado é posterior ao atual
		if (Integer.valueOf(anoMes) > Integer.valueOf(anoMesAtual)) {
			return;
		}
		
		EstoqueKey key = getEstoqueKey(produtoId, frutaId, localEstoqueId);
		key.setAnoMes(anoMes);
		
		EstoqueDTO dto = null;
		
		// recupera ou cria o registro do Ano/Mes desejado
		try {
			dto = get(key);
		} catch (NotFoundException ex) {
			dto = save(key);
		}
		
		Float saldoAnterior = -1f;

		// atualiza o saldo
		if (Integer.valueOf(anoMes) == Integer.valueOf(anoMesAtual)) {
			// se o AnoMes desejado é igual ao atual, apenas atualiza o saldo
			saldoAnterior = Float.max(dto.getQtSaldoAnterior(), 0);
			saldoAnterior = (saldoAnterior < 0)?
								0f:
								saldoAnterior;
			
			// atualiza o saldo
			updateSaldoAtual(key, dto, saldoAnterior);
			
		} else {		
			// atualiza os registros de saldo posteriores ao informado
			saldoAnterior = dto.getQtSaldoAtual();

			// identifica o Ano/Mes
			String[] inicio = anoMes
					.replaceAll(REGEX, "$1/$2")
					.split("[/]");
			
			int anoInicio = Integer.valueOf(inicio[0]);
			int mesInicio = Integer.valueOf(inicio[1]);
	
			do {
				mesInicio++;
				if (mesInicio > 12) {
					anoInicio++;
					mesInicio = 1;
				}
				
				anoMes = String.format("%04d", anoInicio) + String.format("%02d", mesInicio);
				key.setAnoMes(dto.getKeyAnoMes());
				
				// recupera ou cria o registro de estoque para o AnoMes calculado
				try {
					dto = get(key);
				} catch (NotFoundException ex) {
					dto = save(key);
				}
				
				// atualiza o saldo
				updateSaldoAtual(key, dto, saldoAnterior);
	
				saldoAnterior = dto.getQtSaldoAtual();
	
			} while (Integer.valueOf(anoMes) < Integer.valueOf(anoMesAtual));
		}
		
	}

	// cria os registros de Estoque do AnoMes informado ate data atual, para todos os Produdos/Frutas
	// se o local de estoque for informado, cria apenas para este, senão cria para todos os locais cadastrados na base
	public void createEntries(String anoMesInicio, LocalEstoqueDTO localEstoque) throws EstoqueException, ProdutoFrutaException, LocalEstoqueException {
		List<ProdutoFrutaDTO> lstProdutoFruta = produtoFrutaService.listAllOrderByProdutoFruta();
		List<LocalEstoqueDTO> lstLocalEstoque = localEstoqueService.listAllOrderByNome();
		
		for (ProdutoFrutaDTO pf : lstProdutoFruta) {
			if (localEstoque != null) {
				updateFromAnoMesToToday(anoMesInicio, pf.getKeyProdutoId(), pf.getKeyFrutaId(), localEstoque.getId());
				
			} else {
				for (LocalEstoqueDTO le : lstLocalEstoque) {
					updateFromAnoMesToToday(anoMesInicio, pf.getKeyProdutoId(), pf.getKeyFrutaId(), le.getId());
				}
			}
		}
	}

	// lista todos os registros de estoque existentes
	public List<EstoqueDTO> listAll() throws EstoqueException {
		List<Estoque> list = Lists.newArrayList(repository.findAll());
		return initializeList(list);
	}
	
	// lista os registros de estoque pelo AnoMes, Produto, Fruta e Local de Estoque. Apenas o AnoMes é obrigatorio
	public List<EstoqueDTO> listByAnoMesAndProdutoOrFrutaOrLocalEstoque(String anoMes, Integer produtoId, Integer frutaId, Integer localEstoqueId) throws EstoqueException {
		try {
			// se o AnoMes nao for informado gera um erro de registro nao encontrado
			if (anoMes == null || anoMes.trim().isEmpty()) {
				throw new NotFoundException();
			}
			
			// Verifica se os IDs correspondem a registros existentes
			if (produtoId > 0) produtoService.get(produtoId);
			if (frutaId > 0) frutaService.get(frutaId);
			if (localEstoqueId > 0) localEstoqueService.get(localEstoqueId);
			
			// gera a lista
			List<Estoque> list = repository.findByAnoMesProdutoFrutaLocalEstoque(anoMes, produtoId, frutaId, localEstoqueId);
			return initializeList(list);
		} catch (ProdutoException | FrutaException | LocalEstoqueException pfleex) {
			logger.error("listByAnoMesAndProdutoOrFrutaOrLocalEstoque()", pfleex);
			throw new NotFoundException();
		}
		
	}

	// inicializa uma lista de registros e gera a lista de DTO correspondente
	private List<EstoqueDTO> initializeList(List<Estoque> list) {
		for (Estoque e : list) {
			Hibernate.initialize(e.getKey().getProduto());
			Hibernate.initialize(e.getKey().getFruta());
			Hibernate.initialize(e.getKey().getLocalEstoque());
		}
		return Util.buildListDTO(list, EstoqueDTO.class);
	}
	
	// transfere os dados do DTO para o registro atualizando o saldo
	private void buildEstoque(EstoqueDTO dto, Estoque estoque) {
		estoque.setQtEntradas(dto.getQtEntradas());
		estoque.setQtSaidas(dto.getQtSaidas());
		estoque.setQtSaldoAnterior(dto.getQtSaldoAnterior());
		
		Float saldoAtual = dto.getQtSaldoAnterior() + dto.getQtEntradas() - dto.getQtSaidas();
		estoque.setQtSaldoAtual(saldoAtual);
	}
	
	// atualiza o saldo de um registro especificado
	private void updateSaldoAtual(EstoqueKey key, EstoqueDTO dto, Float saldoAnterior) throws EstoqueException {
		Float saldoAtual = saldoAnterior + dto.getQtEntradas() - dto.getQtSaidas();
		dto.setQtSaldoAtual(saldoAtual);
		update(key, dto);
	}

	// recupera uma chava de registro de estoque pelo Produto, Fruta e Local de Estoque informados
	public EstoqueKey getEstoqueKey(Integer produtoId, Integer frutaId, Integer localEstoqueId) throws NotFoundException {
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
}
