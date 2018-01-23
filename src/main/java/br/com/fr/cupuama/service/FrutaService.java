package br.com.fr.cupuama.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.RollbackException;
import javax.ws.rs.NotFoundException;

import org.apache.log4j.Logger;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.Constantes;
import br.com.fr.cupuama.entity.Fruta;
import br.com.fr.cupuama.entity.dto.FrutaDTO;
import br.com.fr.cupuama.exception.FrutaException;
import br.com.fr.cupuama.repository.FrutaRepository;
import br.com.fr.cupuama.util.Util;
import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class FrutaService {

	private final Logger logger = Logger.getLogger(getClass());

	@Autowired
	FrutaRepository repository;

	public FrutaDTO get(Integer id) throws FrutaException {
		try {
			Fruta fruta = repository.findOne(id);
			
			if (fruta == null) {
				throw new NotFoundException("nenhum registro encontrado com o ID espcificado: " + id);
			}
			
			return Util.buildDTO(fruta, FrutaDTO.class);
		} catch (Exception ex) {
			logger.error(ex);
			throw new NotFoundException();
		}
	}

	public FrutaDTO save(FrutaDTO dto) throws FrutaException {

		try {
			Fruta fruta = new Fruta();

			buildFruta(dto, fruta);
			repository.save(fruta);

			return Util.buildDTO(fruta, FrutaDTO.class);
		} catch (RollbackException rex) {
			logger.error("save()", rex);
			throw new FrutaException((Exception) rex.getCause());
		} catch (Exception ex) {
			logger.error("save()", ex);
			throw new FrutaException(ex);
		}

	}

	public void delete(Integer frutaId) throws FrutaException {
		Fruta fruta = repository.findOne(frutaId);

		if (fruta == null) {
			throw new NotFoundException();
		}
		
		delete(fruta);
	}

	public void delete(Fruta fruta) throws FrutaException {
		try {
			repository.delete(fruta);
		} catch (ConstraintViolationException cvex) {
			logger.error("delete():constraint", cvex);
			
			try {
				fruta.setSituacao(Constantes.SITUACAO_INATIVO);
				repository.save(fruta);
			} catch (Exception ex) {
				throw new FrutaException((Exception) cvex.getCause());
			}
			
		} catch (RollbackException rex) {
			logger.error("delete():rollback", rex);
			throw new FrutaException((Exception) rex.getCause());
			
		} catch (Exception ex) {
			logger.error("delete()", ex);
			throw new FrutaException(ex);
		}
	}

	public FrutaDTO update(Integer frutaId, FrutaDTO dto) throws FrutaException {
		try {

			Fruta fruta = repository.findOne(frutaId);

			if (fruta == null) {
				throw new NotFoundException();
			}

			buildFruta(dto, fruta);
			repository.save(fruta);

			return Util.buildDTO(fruta, FrutaDTO.class);

		} catch (RollbackException rex) {
			logger.error("update()", rex);
			throw rex;

		} catch (Exception ex) {
			logger.error("update()", ex);
			throw ex;
		}
	}

	public List<FrutaDTO> listAll() throws FrutaException {
		List<Fruta> list = Lists.newArrayList(repository.findAll());
		return Util.buildListDTO(list, FrutaDTO.class);
	}
	
	public List<FrutaDTO> listAllOrderByNome() throws FrutaException {
		List<Fruta> list = repository.findAllOrderByNome();
		return Util.buildListDTO(list, FrutaDTO.class);
	}
	
	public List<FrutaDTO> listBySafraOrderByNome(String safra) throws FrutaException {
		List<Fruta> list = repository.findBySafraOrderByNome(safra);
		return Util.buildListDTO(list, FrutaDTO.class);
	}
	
	public List<String> listSafras() throws FrutaException {
		List<FrutaDTO> list = listAll();
		
		if (list == null || list.isEmpty()) {
			throw new NotFoundException();
		}
		
		List<String> safras = new ArrayList<>();
		list.stream().forEach(f -> {
			if (!f.getSafra().trim().isEmpty() && !safras.contains(f.getSafra())) {
				safras.add(f.getSafra());
			}
		});
		
		if (safras.isEmpty()) {
			throw new NotFoundException();
		}
		
		return safras;
	}

	private void buildFruta(FrutaDTO dto, Fruta fruta) {
		fruta.setNome(dto.getNome());
		fruta.setSafra(dto.getSafra());
		fruta.setSigla(dto.getSigla());
	}
}
