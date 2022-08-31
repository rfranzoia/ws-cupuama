package br.com.cupuama.controller.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.controller.processing.dto.ProcessingDetailDTO;
import br.com.cupuama.controller.products.mapper.FruitsMapper;
import br.com.cupuama.controller.products.mapper.ProductsMapper;
import br.com.cupuama.controller.stock.mapper.DepotMapper;
import br.com.cupuama.domain.processing.ProcessingDetail;


public class ProcessingDetailMapper {
	public static ProcessingDetail makeProcessingDetail(final ProcessingDetailDTO dto) {
		return new ProcessingDetail(dto.getId(), ProcessingMapper.makeProcessing(dto.getProcessing()), 
				ProductsMapper.makeProduct(dto.getProduct()), FruitsMapper.makeFruit(dto.getFruit()), DepotMapper.makeDepot(dto.getDepot()),
				dto.getStocktakeInOut(), dto.getAmount(), dto.getPrice(), dto.getDiscount());
	}

	public static ProcessingDetailDTO makeDTO(final ProcessingDetail processingDetail) {
		ProcessingDetailDTO.ProcessingDetailDTOBuilder builder = ProcessingDetailDTO.newBuilder()
				.setId(processingDetail.getId())
				.setProcessing(ProcessingMapper.makeDTO(processingDetail.getProcessing()))
				.setProduct(ProductsMapper.makeDTO(processingDetail.getProduct()))
				.setFruit(FruitsMapper.makeDTO(processingDetail.getFruit()))
				.setDepot(DepotMapper.makeDTO(processingDetail.getDepot()))
				.setStocktakeInOut(processingDetail.getStocktakeInOut())
				.setAmount(processingDetail.getAmount())
				.setPrice(processingDetail.getPrice())
				.setDiscount(processingDetail.getDiscount());

		return builder.createDTO();
	}

	public static List<ProcessingDetailDTO> makeListDTO(final Collection<ProcessingDetail> processingDetails) {
		return processingDetails.stream()
				.map(ProcessingDetailMapper::makeDTO)
				.collect(Collectors.toList());
	}
	
	public static List<ProcessingDetail> makeList(final Collection<ProcessingDetailDTO> dtos) {
		return dtos.stream()
				.map(ProcessingDetailMapper::makeProcessingDetail)
				.collect(Collectors.toList());
	}

}
