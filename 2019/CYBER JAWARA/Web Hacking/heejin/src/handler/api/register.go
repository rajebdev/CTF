package api

import (
	"encoding/json"
	"fmt"
	"net/http"
	"regexp"

	"github.com/go-sql-driver/mysql"
	"golang.org/x/crypto/bcrypt"

	"heejin/database"
	"heejin/model"
	"heejin/response"
)

var (
	usernameRegex     = regexp.MustCompile("^[A-Za-z0-9]{6,20}$")
	minPasswordLength = 6
)

func (h Handler) Register(w http.ResponseWriter, r *http.Request) {
	defer r.Body.Close()

	var user model.User
	err := json.NewDecoder(r.Body).Decode(&user)
	if err != nil {
		response.Write(w, response.BuildError(err), http.StatusBadRequest)
		return
	}

	err = validateRegister(user)
	if err != nil {
		response.Write(w, response.BuildError(err), http.StatusUnprocessableEntity)
		return
	}

	hashed, err := bcrypt.GenerateFromPassword([]byte(user.Password), bcrypt.DefaultCost)
	if err != nil {
		response.Write(w, response.BuildError(err), http.StatusInternalServerError)
		return
	}
	user.Password = string(hashed)

	if result := database.MySQL.Create(&user); result.Error != nil {
		sqlErr, ok := result.Error.(*mysql.MySQLError)
		if ok {
			if sqlErr.Number == 1062 {
				err = fmt.Errorf("User already exists")
				response.Write(w, response.BuildError(err), http.StatusUnprocessableEntity)
				return
			}
		}
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	response.Write(w, response.BuildSuccess(nil), http.StatusCreated)
}

func validateRegister(user model.User) error {
	if !usernameRegex.MatchString(user.Username) {
		return fmt.Errorf("Username must be alphanumeric with 6-20 characters")
	}

	if len(user.Password) < minPasswordLength {
		return fmt.Errorf("Password must be at least %d characters", minPasswordLength)
	}
	return nil
}
