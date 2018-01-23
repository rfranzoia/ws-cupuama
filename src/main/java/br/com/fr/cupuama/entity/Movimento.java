package br.com.fr.cupuama.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Movimento implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "dt_movimento")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtMovimento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tipo_movimentacao_id")
	private TipoMovimentacao tipoMovimentacao;

	private String documento;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cliente_fornecedor_id")
	private ClienteFornecedor clienteFornecedor;

	private String observacao;

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

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public ClienteFornecedor getClienteFornecedor() {
		return clienteFornecedor;
	}

	public void setClienteFornecedor(ClienteFornecedor clienteFornecedor) {
		this.clienteFornecedor = clienteFornecedor;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
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
		Movimento other = (Movimento) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movimento [id=" + id + ", dtMovimento=" + dtMovimento + ", tipoMovimentacao=" + tipoMovimentacao
				+ ", documento=" + documento + ", clienteFornecedor=" + clienteFornecedor + ", observacao=" + observacao
				+ "]";
	}
	
}
