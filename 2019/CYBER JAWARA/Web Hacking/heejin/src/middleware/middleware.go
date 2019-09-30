package middleware

import (
	"context"
	"net/http"
	"strings"

	"heejin/response"
	"heejin/session"
)

type Middleware func(w http.ResponseWriter, r *http.Request)

func AuthMiddleware(handle Middleware) Middleware {
	return func(w http.ResponseWriter, r *http.Request) {
		token, err := getToken(r)
		if err != nil {
			response.Write(w, response.BuildError(response.ErrUnauthorized), http.StatusUnauthorized)
			return
		}

		userID, err := session.Parse(token)
		if err != nil {
			response.Write(w, response.BuildError(response.ErrUnauthorized), http.StatusUnauthorized)
			return
		}

		ctx := r.Context()
		ctx = context.WithValue(ctx, "UserID", userID)
		handle(w, r.WithContext(ctx))
	}
}

func getToken(r *http.Request) (string, error) {
	reqToken := r.Header.Get("Authorization")
	splitToken := strings.Split(reqToken, "Bearer")
	if len(splitToken) != 2 {
		return "", response.ErrUnauthorized
	}

	reqToken = strings.TrimSpace(splitToken[1])
	return reqToken, nil
}
