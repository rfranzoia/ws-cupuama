package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MovimentoCaixaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private Date dtMovimento;
	
	private String documento;
	
	private Integer tipoDocumentoId;
	private String tipoDocumentoNome;
	private Character tipoDocumentoSituacao;
	
	private String historico;
	private Character tipo;
	private Double vlMovimento;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtMovimento() {
		return dtMovimento;
	}

	public void setDtMovimento(Date dtMovimento) {
		this.dtMovimento = dtMovimento;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getTipoDocumentoId() {
		return tipoDocumentoId;
	}

	public void setTipoDocumentoId(Integer tipoDocumentoId) {
		this.tipoDocumentoId = tipoDocumentoId;
	}

	public String getTipoDocumentoNome() {
		return tipoDocumentoNome;
	}

	public void setTipoDocumentoNome(String tipoDocumentoNome) {
		this.tipoDocumentoNome = tipoDocumentoNome;
	}

	public Character getTipoDocumentoSituacao() {
		return tipoDocumentoSituacao;
	}

	public void setTipoDocumentoSituacao(Character tipoDocumentoSituacao) {
		this.tipoDocumentoSituacao = tipoDocumentoSituacao;
	}
	
	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public Character getTipo() {
		return tipo;
	}

	public void setTipo(Character tipo) {
		this.tipo = tipo;
	}

	public Double getVlMovimento() {
		return vlMovimento;
	}

	public void setVlMovimento(Double vlMovimento) {
		this.vlMovimento = vlMovimento;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MovimentoCaixaDTO other = (MovimentoCaixaDTO) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MovimentoCaixaDTO [id=" + id + ", dtMovimento=" + dtMovimento + ", documento=" + documento
				+ ", tipoDocumentoId=" + tipoDocumentoId + ", tipoDocumentoNome=" + tipoDocumentoNome
				+ ", tipoDocumentoSituacao=" + tipoDocumentoSituacao + ", historico=" + historico + ", tipo=" + tipo
				+ ", vlMovimento=" + vlMovimento + "]";
	}

}
