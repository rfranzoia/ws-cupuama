package api

import (
	"log"
	"net/http"
	"time"

	"github.com/dgrijalva/jwt-go"

	"github.com/labstack/echo"
	"github.com/rfranzoia/ws-cupuama/model"
	"github.com/rfranzoia/ws-cupuama/util"
)

// Login efetua o login do usuario e gera um Cookie
// TODO: gerar regisro na tabela de LOGIN
func Login(c echo.Context) error {

	var usuario *model.Usuario

	username := c.QueryParam("username")
	password := c.QueryParam("password")

	u := usuario.GetByLogin(username)

	if u.ID == 0 {
		c.String(http.StatusUnauthorized, "Usuário e/ou Senha inválido(s)")

	} else if u.Senha == util.GetMD5Hash(password) {

		cookie := &http.Cookie{}

		cookie.Name = "sessionID"
		cookie.Value = "hash"
		cookie.Expires = time.Now().Add(1 * time.Hour)

		c.SetCookie(cookie)

		token, err := createJwtToken()
		if err != nil {
			log.Println("Erro ao criar token")
			return c.String(http.StatusInternalServerError, "Erro ao criar Token JWT")
		}

		return c.JSON(http.StatusOK, map[string]string{
			"message": "login successful!",
			"token":   token,
		})

	}

	return c.String(http.StatusUnauthorized, "Usuário e/ou Senha inválido(s)")
}

func createJwtToken() (string, error) {
	claim := JwtClaim{
		"43727000244",
		jwt.StandardClaims{
			Id:        "userId",
			Issuer:    "43727000244",
			ExpiresAt: time.Now().Add(1 * time.Hour).Unix(),
		},
	}

	rawToken := jwt.NewWithClaims(jwt.SigningMethodHS512, claim)
	token, err := rawToken.SignedString([]byte(util.SECRET_PASS))

	if err != nil {
		return "", err
	}

	return token, nil
}
