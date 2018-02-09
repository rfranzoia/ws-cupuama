package util

import (
	"crypto/md5"
	"encoding/hex"
)

// ATIVO situacao ativo
const ATIVO = "A"

// INATIVO situacao inativo
const INATIVO = "I"

const SECRET_PASS = "s3cr3tT0l3nP455"

// GetMD5Hash retorna o hash MD5 para uma string
func GetMD5Hash(text string) string {
	hash := md5.Sum([]byte(text))
	return hex.EncodeToString(hash[:])
}
