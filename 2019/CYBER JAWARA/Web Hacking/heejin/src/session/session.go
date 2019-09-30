package session

import (
	"os"
	"time"

	jwt "github.com/dgrijalva/jwt-go"
)

type Claims struct {
	UserID uint64 `json:"user_id"`
	jwt.StandardClaims
}

var JWTSecretKey string

func Init() {
	JWTSecretKey = os.Getenv("JWT_SECRET_KEY")
	if JWTSecretKey == "" {
		panic("JWT_SECRET_KEY is not set")
	}
}

func Sign(userID uint64) (string, error) {
	claims := Claims{
		userID,
		jwt.StandardClaims{
			Issuer:    "Heejin",
			ExpiresAt: time.Now().Add(time.Duration(24) * time.Hour).Unix(),
		},
	}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)
	signed, err := token.SignedString([]byte(JWTSecretKey))
	if err != nil {
		return "", err
	}
	return signed, nil
}

func Parse(tokenString string) (uint64, error) {
	token, err := jwt.ParseWithClaims(tokenString, &Claims{}, func(token *jwt.Token) (interface{}, error) {
		return []byte(JWTSecretKey), nil
	})

	if err != nil {
		return 0, err
	}

	if claims, ok := token.Claims.(*Claims); ok && token.Valid {
		return claims.UserID, nil
	}
	return 0, err
}
