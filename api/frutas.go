package api

import (
	"net/http"
	"strconv"

	"github.com/labstack/echo"
	"github.com/rfranzoia/ws-cupuama/model"
)

// ListFrutas lista todas das frutas
func ListFrutas(c echo.Context) error {
	var fruta *model.Fruta

	frutas := fruta.List()

	defer c.Request().Body.Close()

	return c.JSON(http.StatusOK, frutas)
}

// GetFruta busca por uma fruta usando ID
func GetFruta(c echo.Context) error {
	var fruta *model.Fruta

	frutaID, _ := strconv.ParseUint(c.Param("frutaId"), 10, 32)

	return c.JSON(http.StatusOK, fruta.Get(uint(frutaID)))
}
