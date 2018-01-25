package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ItensMovimentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer movimentoId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy HH:mm:ss")
	private Date movimentoDtMovimento;

	private Integer movimentoTipoMovimentacaoId;
	private String movimentoTipoMovimentacaoNome;
	private Character movimentoTipoMovimentacaoSituacao;

	private String movimentoDocumento;

	private Integer movimentoClienteFornecedorId;
	private String movimentoClienteFornecedorNome;
	private String movimentoClienteFornecedorCpfCnpj;
	private Character movimentoClienteFornecedorSituacao;

	private String movimentoObservacao;

	private Character tipoEntradaSaida;

	private Integer produtoId;
	private String produtoNome;
	private String produtoUnidade;
	private Character produtoSituacao;

	private Integer frutaId;
	private String frutaNome;
	private String frutaSigla;
	private String frutaSafra;
	private Character frutaSituacao;

	private Integer localEstoqueId;
	private String localEstoqueNome;
	private Character localEstoqueGuardaEstoque;
	private Character localEstoqueSituacao;

	private Float qtMovimento;
	private Double vlMovimento;
	private Double vlDesconto;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Character getTipoEntradaSaida() {
		return tipoEntradaSaida;
	}

	public void setTipoEntradaSaida(Character tipoEntradaSaida) {
		this.tipoEntradaSaida = tipoEntradaSaida;
	}

	public Float getQtMovimento() {
		return qtMovimento;
	}

	public void setQtMovimento(Float qtMovimento) {
		this.qtMovimento = qtMovimento;
	}

	public Double getVlMovimento() {
		return vlMovimento;
	}

	public void setVlMovimento(Double vlMovimento) {
		this.vlMovimento = vlMovimento;
	}

	public Double getVlDesconto() {
		return vlDesconto;
	}

	public void setVlDesconto(Double vlDesconto) {
		this.vlDesconto = vlDesconto;
	}

	public String getMovimentoDocumento() {
		return movimentoDocumento;
	}

	public void setMovimentoDocumento(String movimentoDocumento) {
		this.movimentoDocumento = movimentoDocumento;
	}

	public Integer getMovimentoClienteFornecedorId() {
		return movimentoClienteFornecedorId;
	}

	public void setMovimentoClienteFornecedorId(Integer movimentoClienteFornecedorId) {
		this.movimentoClienteFornecedorId = movimentoClienteFornecedorId;
	}

	public Integer getProdutoId() {
		return produtoId;
	}

	public void setProdutoId(Integer produtoId) {
		this.produtoId = produtoId;
	}

	public String getProdutoNome() {
		return produtoNome;
	}

	public void setProdutoNome(String produtoNome) {
		this.produtoNome = produtoNome;
	}

	public String getProdutoUnidade() {
		return produtoUnidade;
	}

	public void setProdutoUnidade(String produtoUnidade) {
		this.produtoUnidade = produtoUnidade;
	}

	public Character getProdutoSituacao() {
		return produtoSituacao;
	}

	public void setProdutoSituacao(Character produtoSituacao) {
		this.produtoSituacao = produtoSituacao;
	}

	public Integer getFrutaId() {
		return frutaId;
	}

	public void setFrutaId(Integer frutaId) {
		this.frutaId = frutaId;
	}

	public String getFrutaNome() {
		return frutaNome;
	}

	public void setFrutaNome(String frutaNome) {
		this.frutaNome = frutaNome;
	}

	public String getFrutaSigla() {
		return frutaSigla;
	}

	public void setFrutaSigla(String frutaSigla) {
		this.frutaSigla = frutaSigla;
	}

	public String getFrutaSafra() {
		return frutaSafra;
	}

	public void setFrutaSafra(String frutaSafra) {
		this.frutaSafra = frutaSafra;
	}

	public Character getFrutaSituacao() {
		return frutaSituacao;
	}

	public void setFrutaSituacao(Character frutaSituacao) {
		this.frutaSituacao = frutaSituacao;
	}

	public Integer getLocalEstoqueId() {
		return localEstoqueId;
	}

	public void setLocalEstoqueId(Integer localEstoqueId) {
		this.localEstoqueId = localEstoqueId;
	}

	public String getLocalEstoqueNome() {
		return localEstoqueNome;
	}

	public void setLocalEstoqueNome(String localEstoqueNome) {
		this.localEstoqueNome = localEstoqueNome;
	}

	public Character getLocalEstoqueGuardaEstoque() {
		return localEstoqueGuardaEstoque;
	}

	public void setLocalEstoqueGuardaEstoque(Character localEstoqueGuardaEstoque) {
		this.localEstoqueGuardaEstoque = localEstoqueGuardaEstoque;
	}

	public Character getLocalEstoqueSituacao() {
		return localEstoqueSituacao;
	}

	public void setLocalEstoqueSituacao(Character localEstoqueSituacao) {
		this.localEstoqueSituacao = localEstoqueSituacao;
	}

	public Integer getMovimentoId() {
		return movimentoId;
	}

	public void setMovimentoId(Integer movimentoId) {
		this.movimentoId = movimentoId;
	}

	public Date getMovimentoDtMovimento() {
		return movimentoDtMovimento;
	}

	public void setMovimentoDtMovimento(Date movimentoDtMovimento) {
		this.movimentoDtMovimento = movimentoDtMovimento;
	}

	public Integer getMovimentoTipoMovimentacaoId() {
		return movimentoTipoMovimentacaoId;
	}

	public void setMovimentoTipoMovimentacaoId(Integer movimentoTipoMovimentacaoId) {
		this.movimentoTipoMovimentacaoId = movimentoTipoMovimentacaoId;
	}

	public String getMovimentoTipoMovimentacaoNome() {
		return movimentoTipoMovimentacaoNome;
	}

	public void setMovimentoTipoMovimentacaoNome(String movimentoTipoMovimentacaoNome) {
		this.movimentoTipoMovimentacaoNome = movimentoTipoMovimentacaoNome;
	}

	public Character getMovimentoTipoMovimentacaoSituacao() {
		return movimentoTipoMovimentacaoSituacao;
	}

	public void setMovimentoTipoMovimentacaoSituacao(Character movimentoTipoMovimentacaoSituacao) {
		this.movimentoTipoMovimentacaoSituacao = movimentoTipoMovimentacaoSituacao;
	}

	public String getMovimentoClienteFornecedorNome() {
		return movimentoClienteFornecedorNome;
	}

	public void setMovimentoClienteFornecedorNome(String movimentoClienteFornecedorNome) {
		this.movimentoClienteFornecedorNome = movimentoClienteFornecedorNome;
	}

	public String getMovimentoClienteFornecedorCpfCnpj() {
		return movimentoClienteFornecedorCpfCnpj;
	}

	public void setMovimentoClienteFornecedorCpfCnpj(String movimentoClienteFornecedorCpfCnpj) {
		this.movimentoClienteFornecedorCpfCnpj = movimentoClienteFornecedorCpfCnpj;
	}

	public Character getMovimentoClienteFornecedorSituacao() {
		return movimentoClienteFornecedorSituacao;
	}

	public void setMovimentoClienteFornecedorSituacao(Character movimentoClienteFornecedorSituacao) {
		this.movimentoClienteFornecedorSituacao = movimentoClienteFornecedorSituacao;
	}

	public String getMovimentoObservacao() {
		return movimentoObservacao;
	}

	public void setMovimentoObservacao(String movimentoObservacao) {
		this.movimentoObservacao = movimentoObservacao;
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
		ItensMovimentoDTO other = (ItensMovimentoDTO) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ItensMovimentoDTO [id=" + id + ", movimentoId=" + movimentoId + ", movimentoDtMovimento="
				+ movimentoDtMovimento + ", movimentoTipoMovimentacaoId=" + movimentoTipoMovimentacaoId
				+ ", movimentoTipoMovimentacaoNome=" + movimentoTipoMovimentacaoNome
				+ ", movimentoTipoMovimentacaoSituacao=" + movimentoTipoMovimentacaoSituacao + ", movimentoDocumento="
				+ movimentoDocumento + ", movimentoClienteFornecedorId=" + movimentoClienteFornecedorId
				+ ", movimentoClienteFornecedorNome=" + movimentoClienteFornecedorNome
				+ ", movimentoClienteFornecedorCpfCnpj=" + movimentoClienteFornecedorCpfCnpj
				+ ", movimentoClienteFornecedorSituacao=" + movimentoClienteFornecedorSituacao
				+ ", movimentoObservacao=" + movimentoObservacao + ", tipoEntradaSaida=" + tipoEntradaSaida
				+ ", produtoId=" + produtoId + ", produtoNome=" + produtoNome + ", produtoUnidade=" + produtoUnidade
				+ ", produtoSituacao=" + produtoSituacao + ", frutaId=" + frutaId + ", frutaNome=" + frutaNome
				+ ", frutaSigla=" + frutaSigla + ", frutaSafra=" + frutaSafra + ", frutaSituacao=" + frutaSituacao
				+ ", localEstoqueId=" + localEstoqueId + ", localEstoqueNome=" + localEstoqueNome
				+ ", localEstoqueGuardaEstoque=" + localEstoqueGuardaEstoque + ", localEstoqueSituacao="
				+ localEstoqueSituacao + ", qtMovimento=" + qtMovimento + ", vlMovimento=" + vlMovimento
				+ ", vlDesconto=" + vlDesconto + "]";
	}

}
