package api

import (
	"encoding/json"
	"net/http"

	"golang.org/x/crypto/bcrypt"

	"heejin/database"
	"heejin/model"
	"heejin/response"
	"heejin/session"
)

type loginData struct {
	Token string `json:"token"`
}

func (h Handler) Login(w http.ResponseWriter, r *http.Request) {
	defer r.Body.Close()

	var userLogin model.User
	err := json.NewDecoder(r.Body).Decode(&userLogin)
	if err != nil {
		response.Write(w, response.BuildError(err), http.StatusBadRequest)
		return
	}

	var user model.User
	if database.MySQL.Where("username = ?", userLogin.Username).First(&user).RecordNotFound() {
		response.Write(w, response.BuildError(response.ErrInvalidUsernameOrPassword), http.StatusUnauthorized)
		return
	}
	if err := bcrypt.CompareHashAndPassword([]byte(user.Password), []byte(userLogin.Password)); err != nil {
		response.Write(w, response.BuildError(response.ErrInvalidUsernameOrPassword), http.StatusUnauthorized)
		return
	}

	token, err := session.Sign(user.ID)
	if err != nil {
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}
	response.Write(w, response.BuildSuccess(loginData{token}), http.StatusOK)
}
