package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

public class MovimentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date dtMovimento;

	private Integer tipoMovimentacaoId;
	private String tipoMovimentacaoNome;
	private Character tipoMovimentacaoSituacao;

	private String documento;
	
	private Integer tipoDocumentoId;
	private String tipoDocumentoNome;
	private Character tipoDocumentoSituacao;

	private Integer clienteFornecedorId;
	private String clienteFornecedorNome;
	private Character clienteFornecedorTipo;
	private String clienteFornecedorCpfCnpj;
	private Character clienteFornecedorSituacao;

	private String observacao;

	private List<ItensMovimentoDTO> itensMovimento;

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

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getTipoMovimentacaoId() {
		return tipoMovimentacaoId;
	}

	public void setTipoMovimentacaoId(Integer tipoMovimentacaoId) {
		this.tipoMovimentacaoId = tipoMovimentacaoId;
	}

	public String getTipoMovimentacaoNome() {
		return tipoMovimentacaoNome;
	}

	public void setTipoMovimentacaoNome(String tipoMovimentacaoNome) {
		this.tipoMovimentacaoNome = tipoMovimentacaoNome;
	}

	public Character getTipoMovimentacaoSituacao() {
		return tipoMovimentacaoSituacao;
	}

	public void setTipoMovimentacaoSituacao(Character tipoMovimentacaoSituacao) {
		this.tipoMovimentacaoSituacao = tipoMovimentacaoSituacao;
	}

	public Integer getClienteFornecedorId() {
		return clienteFornecedorId;
	}

	public void setClienteFornecedorId(Integer clienteFornecedorId) {
		this.clienteFornecedorId = clienteFornecedorId;
	}

	public String getClienteFornecedorNome() {
		return clienteFornecedorNome;
	}

	public void setClienteFornecedorNome(String clienteFornecedorNome) {
		this.clienteFornecedorNome = clienteFornecedorNome;
	}

	public Character getClienteFornecedorTipo() {
		return clienteFornecedorTipo;
	}

	public void setClienteFornecedorTipo(Character clienteFornecedorTipo) {
		this.clienteFornecedorTipo = clienteFornecedorTipo;
	}

	public String getClienteFornecedorCpfCnpj() {
		return clienteFornecedorCpfCnpj;
	}

	public void setClienteFornecedorCpfCnpj(String clienteFornecedorCpfCnpj) {
		this.clienteFornecedorCpfCnpj = clienteFornecedorCpfCnpj;
	}

	public Character getClienteFornecedorSituacao() {
		return clienteFornecedorSituacao;
	}

	public void setClienteFornecedorSituacao(Character clienteFornecedorSituacao) {
		this.clienteFornecedorSituacao = clienteFornecedorSituacao;
	}

	public List<ItensMovimentoDTO> getItensMovimento() {
		return itensMovimento;
	}

	public void setItensMovimento(List<ItensMovimentoDTO> itensMovimento) {
		this.itensMovimento = itensMovimento;
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
		MovimentoDTO other = (MovimentoDTO) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MovimentoDTO [id=" + id + ", dtMovimento=" + dtMovimento + ", tipoMovimentacaoId=" + tipoMovimentacaoId
				+ ", tipoMovimentacaoNome=" + tipoMovimentacaoNome + ", tipoMovimentacaoSituacao="
				+ tipoMovimentacaoSituacao + ", documento=" + documento + ", tipoDocumentoId=" + tipoDocumentoId
				+ ", tipoDocumentoNome=" + tipoDocumentoNome + ", tipoDocumentoSituacao=" + tipoDocumentoSituacao
				+ ", clienteFornecedorId=" + clienteFornecedorId + ", clienteFornecedorNome=" + clienteFornecedorNome
				+ ", clienteFornecedorTipo=" + clienteFornecedorTipo + ", clienteFornecedorCpfCnpj="
				+ clienteFornecedorCpfCnpj + ", clienteFornecedorSituacao=" + clienteFornecedorSituacao
				+ ", observacao=" + observacao + ", itensMovimento=" + itensMovimento + "]";
	}

}
