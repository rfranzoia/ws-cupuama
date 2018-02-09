package main

import (
	"fmt"
	"log"
	"net/http"
	"strings"

	"github.com/labstack/echo"
	"github.com/labstack/echo/middleware"

	"github.com/rfranzoia/ws-cupuama/api"
	"github.com/rfranzoia/ws-cupuama/model"
	"github.com/rfranzoia/ws-cupuama/util"
)

func validateSession(cookie *http.Cookie) bool {
	if cookie.Value == "hash" {
		return true
	}
	return false
}

func chekCookie(next echo.HandlerFunc) echo.HandlerFunc {
	return func(c echo.Context) error {

		cookie, err := c.Cookie("sessionID")

		if err != nil {
			log.Println(err)

			if strings.Contains(err.Error(), "named cookie not present") {
				c.String(http.StatusUnauthorized, "Sessão Invalida!")
			}

			return err
		}

		if validateSession(cookie) {
			return next(c)
		}

		return c.String(http.StatusUnauthorized, "Sessão Invalida!")
	}
}

func main() {

	defer model.GetDB().Close()

	fmt.Println("Welcome to Server")

	e := echo.New()
	g := e.Group("/ws-cupuama/api")

	g.Use(middleware.LoggerWithConfig(middleware.LoggerConfig{
		Format: "${time_rfc3339} ${status} ${host}${path} ${latency_human}\n",
	}))

	g.Use(middleware.CORSWithConfig(middleware.CORSConfig{
		AllowOrigins: []string{"*"},
		AllowMethods: []string{echo.GET, echo.PUT, echo.POST, echo.DELETE},
		AllowHeaders: []string{echo.HeaderOrigin, echo.HeaderContentType, echo.HeaderAccept},
	}))

	g.GET("/login", api.Login)

	gfruta := g.Group("/v1/fruta")

	gfruta.Use(middleware.JWTWithConfig(middleware.JWTConfig{
		SigningMethod: "HS512",
		SigningKey:    []byte(util.SECRET_PASS),
	}))

	gfruta.GET("", api.ListFrutas)
	gfruta.GET("/:frutaId", api.GetFruta)

	e.Start(":8080")
}
