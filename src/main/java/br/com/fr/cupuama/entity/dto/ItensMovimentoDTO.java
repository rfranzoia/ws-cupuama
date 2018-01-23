package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;
import java.util.Date;

public class ItensMovimentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	private Integer movimentodId;
	private Date movimentodDtMovimento;

	private Integer movimentodTipoMovimentacaoId;
	private String movimentodTipoMovimentacaoNome;
	private Character movimentodTipoMovimentacaoSituacao;

	private String documento;

	private Integer movimentoClienteFornecedorId;
	private String movimentodClienteFornecedorNome;
	private Character movimentodClienteFornecedorTipo;
	private String movimentodclienteFornecedorCpfCnpj;
	private Character movimentodClienteFornecedorSituacao;

	private String movimentodObservacao;

	private Character tipoEntradaSaida;
	
	private Integer produtoIid;
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

	
	public Integer getMovimentodId() {
		return movimentodId;
	}

	public void setMovimentodId(Integer movimentodId) {
		this.movimentodId = movimentodId;
	}

	public Date getMovimentodDtMovimento() {
		return movimentodDtMovimento;
	}

	public void setMovimentodDtMovimento(Date movimentodDtMovimento) {
		this.movimentodDtMovimento = movimentodDtMovimento;
	}

	public Integer getMovimentodTipoMovimentacaoId() {
		return movimentodTipoMovimentacaoId;
	}

	public void setMovimentodTipoMovimentacaoId(Integer movimentodTipoMovimentacaoId) {
		this.movimentodTipoMovimentacaoId = movimentodTipoMovimentacaoId;
	}

	public String getMovimentodTipoMovimentacaoNome() {
		return movimentodTipoMovimentacaoNome;
	}

	public void setMovimentodTipoMovimentacaoNome(String movimentodTipoMovimentacaoNome) {
		this.movimentodTipoMovimentacaoNome = movimentodTipoMovimentacaoNome;
	}

	public Character getMovimentodTipoMovimentacaoSituacao() {
		return movimentodTipoMovimentacaoSituacao;
	}

	public void setMovimentodTipoMovimentacaoSituacao(Character movimentodTipoMovimentacaoSituacao) {
		this.movimentodTipoMovimentacaoSituacao = movimentodTipoMovimentacaoSituacao;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Integer getMovimentoClienteFornecedorId() {
		return movimentoClienteFornecedorId;
	}

	public void setMovimentoClienteFornecedorId(Integer movimentoClienteFornecedorId) {
		this.movimentoClienteFornecedorId = movimentoClienteFornecedorId;
	}

	public String getMovimentodClienteFornecedorNome() {
		return movimentodClienteFornecedorNome;
	}

	public void setMovimentodClienteFornecedorNome(String movimentodClienteFornecedorNome) {
		this.movimentodClienteFornecedorNome = movimentodClienteFornecedorNome;
	}

	public Character getMovimentodClienteFornecedorTipo() {
		return movimentodClienteFornecedorTipo;
	}

	public void setMovimentodClienteFornecedorTipo(Character movimentodClienteFornecedorTipo) {
		this.movimentodClienteFornecedorTipo = movimentodClienteFornecedorTipo;
	}

	public String getMovimentodclienteFornecedorCpfCnpj() {
		return movimentodclienteFornecedorCpfCnpj;
	}

	public void setMovimentodclienteFornecedorCpfCnpj(String movimentodclienteFornecedorCpfCnpj) {
		this.movimentodclienteFornecedorCpfCnpj = movimentodclienteFornecedorCpfCnpj;
	}

	public Character getMovimentodClienteFornecedorSituacao() {
		return movimentodClienteFornecedorSituacao;
	}

	public void setMovimentodClienteFornecedorSituacao(Character movimentodClienteFornecedorSituacao) {
		this.movimentodClienteFornecedorSituacao = movimentodClienteFornecedorSituacao;
	}

	public String getMovimentodObservacao() {
		return movimentodObservacao;
	}

	public void setMovimentodObservacao(String movimentodObservacao) {
		this.movimentodObservacao = movimentodObservacao;
	}

	public Integer getProdutoIid() {
		return produtoIid;
	}

	public void setProdutoIid(Integer produtoIid) {
		this.produtoIid = produtoIid;
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
		return "ItensMovimentoDTO [id=" + id + ", movimentodId=" + movimentodId + ", movimentodDtMovimento="
				+ movimentodDtMovimento + ", movimentodTipoMovimentacaoId=" + movimentodTipoMovimentacaoId
				+ ", movimentodTipoMovimentacaoNome=" + movimentodTipoMovimentacaoNome
				+ ", movimentodTipoMovimentacaoSituacao=" + movimentodTipoMovimentacaoSituacao + ", documento="
				+ documento + ", movimentoClienteFornecedorId=" + movimentoClienteFornecedorId
				+ ", movimentodClienteFornecedorNome=" + movimentodClienteFornecedorNome
				+ ", movimentodClienteFornecedorTipo=" + movimentodClienteFornecedorTipo
				+ ", movimentodclienteFornecedorCpfCnpj=" + movimentodclienteFornecedorCpfCnpj
				+ ", movimentodClienteFornecedorSituacao=" + movimentodClienteFornecedorSituacao
				+ ", movimentodObservacao=" + movimentodObservacao + ", tipoEntradaSaida=" + tipoEntradaSaida
				+ ", produtoIid=" + produtoIid + ", produtoNome=" + produtoNome + ", produtoUnidade=" + produtoUnidade
				+ ", produtoSituacao=" + produtoSituacao + ", frutaId=" + frutaId + ", frutaNome=" + frutaNome
				+ ", frutaSigla=" + frutaSigla + ", frutaSafra=" + frutaSafra + ", frutaSituacao=" + frutaSituacao
				+ ", localEstoqueId=" + localEstoqueId + ", localEstoqueNome=" + localEstoqueNome
				+ ", localEstoqueGuardaEstoque=" + localEstoqueGuardaEstoque + ", localEstoqueSituacao="
				+ localEstoqueSituacao + ", qtMovimento=" + qtMovimento + ", vlMovimento=" + vlMovimento
				+ ", vlDesconto=" + vlDesconto + "]";
	}


}
