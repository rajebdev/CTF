package api

import (
	"net/http"

	"heejin/database"
	"heejin/model"
	"heejin/response"
)

func (h Handler) Me(w http.ResponseWriter, r *http.Request) {
	userID := r.Context().Value("UserID").(uint64)
	var user model.User
	if result := database.MySQL.First(&user, userID); result.Error != nil {
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	user.Password = ""
	response.Write(w, response.BuildSuccess(user), http.StatusOK)
}
