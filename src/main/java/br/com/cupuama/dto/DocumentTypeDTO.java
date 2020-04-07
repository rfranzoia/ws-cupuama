package br.com.cupuama.dto;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DocumentTypeDTO {
	
	private Long id;

	@NotNull(message = "Name cannot be null!")
	private String name;

	private DocumentTypeDTO() {
	}

	private DocumentTypeDTO(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static DocumentTypeDTOBuilder newBuilder() {
		return new DocumentTypeDTOBuilder();
	}

	@JsonProperty
	public Long getId() {
		return id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public static class DocumentTypeDTOBuilder {
		private Long id;
		private String name;

		public DocumentTypeDTOBuilder setId(Long id) {
			this.id = id;
			return this;
		}

		public DocumentTypeDTOBuilder setName(String name) {
			this.name = name;
			return this;
		}

		public DocumentTypeDTO createDocumentTypeDTO() {
			return new DocumentTypeDTO(id, name);
		}
	}
}
