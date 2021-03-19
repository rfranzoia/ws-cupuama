package br.com.cupuama.controller.processing.mapper;

import br.com.cupuama.controller.processing.dto.ProcessFlowTypeKey;
import br.com.cupuama.controller.products.mapper.ProductsMapper;
import br.com.cupuama.domain.processing.ProcessFlowTypeId;


public class ProcessFlowTypeKeyMapper {
	public static ProcessFlowTypeId makeId(ProcessFlowTypeKey key) {
		return new ProcessFlowTypeId(ProcessTypeMapper.makeProcessType(key.getProcessType()), 
										ProductsMapper.makeProduct(key.getProduct()),
										key.getStocktakeInOut());
	}

	public static ProcessFlowTypeKey makeKey(ProcessFlowTypeId id) {
		return new ProcessFlowTypeKey(ProcessTypeMapper.makeDTO(id.getProcessType()), 
										ProductsMapper.makeDTO(id.getProduct()),
										id.getStocktakeInOut());
	}

}
