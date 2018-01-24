package br.com.fr.cupuama.entity.dto;

import java.io.Serializable;

public class LocalEstoqueDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	private Character guardaEstoque;
    private Character situacao;
	
	public Character getSituacao() {
		return situacao;
	}

	public void setSituacao(Character situacao) {
		this.situacao = situacao;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public Character getGuardaEstoque() {
		return guardaEstoque;
	}

	public void setGuardaEstoque(Character guardaEstoque) {
		this.guardaEstoque = guardaEstoque;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
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
		LocalEstoqueDTO other = (LocalEstoqueDTO) obj;
		if (id == null) {
			if (other.getId() != null)
				return false;
		} else if (!id.equals(other.getId()))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LocalEstoqueDTO {\"id\":\"" + id + "\", \"nome\":\"" + nome + "\", \"guardaEstoque\":\"" + guardaEstoque + "\", \"situacao\":\"" + situacao + "\"}";
	}
	
	

}
