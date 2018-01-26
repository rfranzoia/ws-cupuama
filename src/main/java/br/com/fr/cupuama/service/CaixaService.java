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

import br.com.fr.cupuama.entity.Caixa;
import br.com.fr.cupuama.entity.dto.CaixaDTO;
import br.com.fr.cupuama.exception.CaixaException;
import br.com.fr.cupuama.repository.CaixaRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class CaixaService {

	private final Logger logger = Logger.getLogger(getClass());
	public static final String REGEX = "([0-9]{4})([0-9]{2})";
	
	@Autowired
	CaixaRepository repository;
	
	// recupera um registro de caixa atravez de uma chave informada
	public CaixaDTO get(String anoMes) throws CaixaException {
		Caixa caixa = repository.findOne(anoMes);

		if (caixa == null) {
			throw new NotFoundException();
		}
		
		return Util.buildDTO(caixa, CaixaDTO.class);
	}

	// cria um novo registro de caixa (vazio) utilizando a chave informada
	private CaixaDTO save(String anoMes) throws CaixaException {
		try {
			
			Caixa caixa = new Caixa();
			caixa.setAnoMes(anoMes);
			caixa.setVlEntradas(0.0);
			caixa.setVlSaidas(0.0);
			caixa.setVlSaldoAnterior(0.0);
			caixa.setVlSaldoAtual(0.0);
			
			repository.save(caixa);
			return Util.buildDTO(caixa, CaixaDTO.class);
			
		} catch (RollbackException rex) {
			logger.error("save(anoMes)", rex);
			throw rex;
			
		}
	}
	
	//cria um novo registro de caixa utilizando os dados do DTO informado
	public CaixaDTO save(CaixaDTO dto) throws CaixaException {

		try {
			Caixa caixa = new Caixa();
			
			caixa.setAnoMes(dto.getAnoMes());
			caixa.setVlEntradas(dto.getVlEntradas());
			caixa.setVlSaidas(dto.getVlSaidas());
			caixa.setVlSaldoAnterior(dto.getVlSaldoAnterior());
			caixa.setVlSaldoAtual(dto.getVlSaldoAtual());

			buildCaixa(dto, caixa);
			repository.save(caixa);

			return Util.buildDTO(caixa, CaixaDTO.class);
			
		} catch (RollbackException rex) {
			logger.error("save()", rex);
			throw new CaixaException((Exception) rex.getCause());
			
		}

	}
	
	// remove um registro de caixa com a chave informada
	public void delete(String anoMes) throws CaixaException {
		Caixa caixa = repository.findOne(anoMes);

		if (caixa == null) {
			throw new NotFoundException();
		}
		
		delete(caixa);
	}

	// remove um registro de caixa pelos dados do registro
	private void delete(Caixa caixa) throws CaixaException {
		try {
			repository.delete(caixa);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			throw new CaixaException((Exception) cvex.getCause());
			
		} catch (RollbackException rex) {
			logger.error("delete():rollback", rex);
			throw new CaixaException((Exception) rex.getCause());
			
		}
	}

	// atualiza um registro de caixa com a chave informada e os dados do DTO
	// tambem atualiza o saldo deste registro
	public CaixaDTO update(String anoMes, CaixaDTO dto) throws CaixaException {
		try {

			Caixa caixa = repository.findOne(anoMes);

			if (caixa == null) {
				throw new NotFoundException("Registro de Caixa não foi encontrado!");
			}

			buildCaixa(dto, caixa);
			repository.save(caixa);

			return Util.buildDTO(caixa, CaixaDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}
	
	// processa um registro de caixa adicionando uma quantidade para o tipo E/S informado
	@Transactional
	public CaixaDTO processSaldo(String anoMes, Character tipoEntradaSaida, Double valor) throws CaixaException {

		CaixaDTO dto = null;
		
		try {
			dto = get(anoMes);
			
		} catch (NotFoundException nfex) {
			try {
				dto = save(anoMes);
			} catch (Exception ex) {
				logger.error("updateSaldo()", ex);
				throw ex;
			}
		}
		
		Double entradas = dto.getVlEntradas();
		Double saidas = dto.getVlSaidas();
		
		switch (tipoEntradaSaida) {
			case 'E':
				entradas += valor;
				break;
			case 'S':
				saidas += valor;
				break;
			case 'Z':
				saidas += valor;
				entradas += valor;
		}
		
		dto.setVlEntradas(entradas);
		dto.setVlSaidas(saidas);
		
		dto = update(anoMes, dto);
		
		// identifica o ano/mes informado é menor que o atual
		String anoMesAtual = Util.DATE_FORMAT_ANOMES.format(new Date());
		
		// não processa nada se o ano/mes informado é posterior ao atual
		if (Integer.valueOf(anoMes) < Integer.valueOf(anoMesAtual)) {
			updateFromAnoMesToToday(anoMes);
		}
		
		return dto;
	}
	
	// atualiza os saldos dos registros de caixa que iniciam em AnoMes até o AnoMes da data corrente
	@Transactional
	public void updateFromAnoMesToToday(String anoMes) throws CaixaException {

		// identifica o ano/mes atual
		String anoMesAtual = Util.DATE_FORMAT_ANOMES.format(new Date());
		
		// não processa nada se o ano/mes informado é posterior ao atual
		if (Integer.valueOf(anoMes) > Integer.valueOf(anoMesAtual)) {
			return;
		}
		
		CaixaDTO dto = null;
		
		// recupera ou cria o registro do Ano/Mes desejado
		try {
			dto = get(anoMes);
		} catch (NotFoundException ex) {
			dto = save(anoMes);
		}
		
		Double saldoAnterior = -1.0;

		// atualiza o saldo
		if (Integer.valueOf(anoMes) == Integer.valueOf(anoMesAtual)) {
			// se o AnoMes desejado é igual ao atual, apenas atualiza o saldo
			saldoAnterior = Double.max(dto.getVlSaldoAnterior(), 0.0);
			saldoAnterior = (saldoAnterior < 0)?
								0.0:
								saldoAnterior;
			
			// atualiza o saldo
			updateSaldoAtual(anoMes, dto, saldoAnterior);
			
		} else {		
			// atualiza os registros de saldo posteriores ao informado
			saldoAnterior = dto.getVlSaldoAtual();

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
				
				// recupera ou cria o registro de caixa para o AnoMes calculado
				try {
					dto = get(anoMes);
				} catch (NotFoundException ex) {
					dto = save(anoMes);
				}
				
				dto.setVlSaldoAnterior(saldoAnterior);
				
				// atualiza o saldo
				dto = update(anoMes, dto);
	
				saldoAnterior = dto.getVlSaldoAtual();
	
			} while (Integer.valueOf(anoMes) < Integer.valueOf(anoMesAtual));
		}
		
	}

	// lista todos os registros de caixa existentes
	public List<CaixaDTO> listAll() throws CaixaException {
		List<Caixa> list = Lists.newArrayList(repository.findAll());
		return Util.buildListDTO(list, CaixaDTO.class);
	}
	
	// transfere os dados do DTO para o registro atualizando o saldo
	private void buildCaixa(CaixaDTO dto, Caixa caixa) {
		caixa.setVlEntradas(dto.getVlEntradas());
		caixa.setVlSaidas(dto.getVlSaidas());
		caixa.setVlSaldoAnterior(dto.getVlSaldoAnterior());
		
		Double saldoAtual = dto.getVlSaldoAnterior() + dto.getVlEntradas() - dto.getVlSaidas();
		caixa.setVlSaldoAtual(saldoAtual);
	}
	
	// atualiza o saldo de um registro especificado
	private void updateSaldoAtual(String anoMes, CaixaDTO dto, Double saldoAnterior) throws CaixaException {
		Double saldoAtual = saldoAnterior + dto.getVlEntradas() - dto.getVlSaidas();
		
		dto.setVlSaldoAtual(saldoAtual);
		update(anoMes, dto);
	}

}
