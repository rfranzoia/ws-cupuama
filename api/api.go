package api

import (
	"log"

	"github.com/labstack/echo"

	jwt "github.com/dgrijalva/jwt-go"
)

// JwtClaim estrutura para claim no jwt
type JwtClaim struct {
	Name string `json:"name"`
	jwt.StandardClaims
}

// AuthLog log de autorizacao por token
func AuthLog(c echo.Context) {
	user := c.Get("user")
	token := user.(*jwt.Token)
	claims := token.Claims.(jwt.MapClaims)

	log.Println("User:", claims["name"], "Id:", claims["jti"])
}
