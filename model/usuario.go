package model

import "github.com/rfranzoia/tutorial_echo/util"

// Usuario modelo para tabela de usuario
type Usuario struct {
	ID       uint   `json:"id" gorm:"primary_key"`
	Nome     string `json:"nome"`
	Login    string `json:"login"`
	Senha    string `json:"senha"`
	Situacao string `json:"situacao"`
}

// List lista todas as usuarios
func (f *Usuario) List() []Usuario {

	var usuarios []Usuario

	db.Find(&usuarios)

	return usuarios
}

// Get retorna uma usuario pelo ID
func (f *Usuario) Get(ID uint) Usuario {
	var usuario Usuario

	db.Where("id = ?", ID).Find(&usuario)

	return usuario
}

// GetByLogin retorna uma usuario pelo login
func (f *Usuario) GetByLogin(login string) Usuario {
	var usuario Usuario

	db.Where("login = ? AND situacao = ?", login, util.ATIVO).Find(&usuario)

	return usuario
}
