package model

// Fruta modelo de frutas
type Fruta struct {
	ID       uint   `json:"id" gorm:"primary_key"`
	Nome     string `json:"nome"`
	Sigla    string `json:"sigla"`
	Safra    string `json:"safra"`
	Situacao string `json:"situacao"`
}

// List lista todas as frutas
func (f *Fruta) List() []Fruta {

	var frutas []Fruta

	db.Find(&frutas)

	return frutas
}

// Get retorna uma fruta pelo ID
func (f *Fruta) Get(ID uint) Fruta {
	var fruta Fruta

	db.Where("id = ?", ID).Find(&fruta)

	return fruta
}
