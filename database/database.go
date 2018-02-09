package database

import (
	_ "github.com/go-sql-driver/mysql" // driver do mysql
	_ "github.com/jinzhu/gorm/dialects/mysql"

	"log"

	"github.com/jinzhu/gorm"
)

var database *gorm.DB
var err error

func init() {
	database, err = gorm.Open("mysql", "cupuama:Cupu4m4.@tcp(localhost:3306)/cupujava")
	database.SingularTable(true)

	if err != nil {
		log.Fatal(err)
	}

}

// GetConnection retorna uma conexao com banco ativa
func GetConnection() *gorm.DB {
	return database
}
