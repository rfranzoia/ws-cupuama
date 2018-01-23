package br.com.fr.cupuama.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fr.cupuama.entity.Movimento;
import br.com.fr.cupuama.entity.dto.MovimentoDTO;
import br.com.fr.cupuama.exception.MovimentoException;
import br.com.fr.cupuama.repository.ItensMovimentoRepository;
import br.com.fr.cupuama.repository.MovimentoRepository;
import br.com.fr.cupuama.util.Util;

@Service
public class MovimentoService {

	private final Logger logger = Logger.getLogger(getClass());
	
	@Autowired
	MovimentoRepository repository;
	
	@Autowired
	ItensMovimentoRepository itensMovimentoRepository;
	
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
		}
		return Util.buildListDTO(list, MovimentoDTO.class);
	}
}
