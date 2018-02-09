package model

import (
	"github.com/jinzhu/gorm"
	"github.com/rfranzoia/ws-cupuama/database"
)

var db = database.GetConnection()

// GetDB retorna a variavel db
func GetDB() *gorm.DB {
	return db
}
