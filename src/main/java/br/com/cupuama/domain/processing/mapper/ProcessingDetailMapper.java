package br.com.cupuama.domain.processing.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import br.com.cupuama.domain.processing.dto.ProcessingDetailDTO;
import br.com.cupuama.domain.processing.entity.ProcessingDetail;
import br.com.cupuama.domain.products.mapper.FruitMapper;
import br.com.cupuama.domain.products.mapper.ProductMapper;
import br.com.cupuama.domain.stock.mapper.DepotMapper;


public class ProcessingDetailMapper {
	public static ProcessingDetail makeProcessingDetail(final ProcessingDetailDTO dto) {
		return new ProcessingDetail(dto.getId(), ProcessingMapper.makeProcessing(dto.getProcessing()), 
				ProductMapper.makeProduct(dto.getProduct()), FruitMapper.makeFruit(dto.getFruit()), DepotMapper.makeDepot(dto.getDepot()),
				dto.getStocktakeInOut(), dto.getAmount(), dto.getPrice(), dto.getDiscount());
	}

	public static ProcessingDetailDTO makeProcessingDetailDTO(final ProcessingDetail processingDetail) {
		ProcessingDetailDTO.ProcessingDetailDTOBuilder builder = ProcessingDetailDTO.newBuilder()
				.setId(processingDetail.getId())
				.setProcessing(ProcessingMapper.makeProcessingDTO(processingDetail.getProcessing()))
				.setProduct(ProductMapper.makeProductDTO(processingDetail.getProduct()))
				.setFruit(FruitMapper.makeFruitDTO(processingDetail.getFruit()))
				.setDepot(DepotMapper.makeDepotDTO(processingDetail.getDepot()))
				.setStocktakeInOut(processingDetail.getStocktakeInOut())
				.setAmount(processingDetail.getAmount())
				.setPrice(processingDetail.getPrice())
				.setDiscount(processingDetail.getDiscount());

		return builder.createDTO();
	}

	public static List<ProcessingDetailDTO> makeProcessingDetailDTOList(final Collection<ProcessingDetail> processingDetails) {
		return processingDetails.stream()
				.map(ProcessingDetailMapper::makeProcessingDetailDTO)
				.collect(Collectors.toList());
	}
	
	public static List<ProcessingDetail> makeProcessingDetailList(final Collection<ProcessingDetailDTO> dtos) {
		return dtos.stream()
				.map(ProcessingDetailMapper::makeProcessingDetail)
				.collect(Collectors.toList());
	}

}
