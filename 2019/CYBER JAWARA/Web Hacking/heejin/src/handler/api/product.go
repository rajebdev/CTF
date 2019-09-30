package api

import (
	"fmt"
	"net/http"
	"strconv"

	"github.com/gorilla/mux"

	"heejin/database"
	"heejin/model"
	"heejin/response"
)

var errNotEnoughMoney = fmt.Errorf("Not enough money")

func (h Handler) ProductAll(w http.ResponseWriter, r *http.Request) {
	var products []model.Product
	if result := database.MySQL.Find(&products); result.Error != nil {
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}
	response.Write(w, response.BuildSuccess(products), http.StatusOK)
}

func (h Handler) ProductBuy(w http.ResponseWriter, r *http.Request) {
	id, err := strconv.ParseInt(mux.Vars(r)["id"], 10, 64)
	if err != nil {
		response.Write(w, response.BuildError(err), http.StatusBadRequest)
		return
	}

	var product model.Product
	if result := database.MySQL.First(&product, id); result.Error != nil {
		if result.RecordNotFound() {
			response.Write(w, response.BuildError(response.ErrNotFound), http.StatusNotFound)
			return
		}
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	tx := database.MySQL.Begin()
	if err := tx.Error; err != nil {
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	userId := r.Context().Value("UserID").(uint64)
	var user model.User
	if result := tx.Set("gorm:query_option", "FOR UPDATE").First(&user, userId); result.Error != nil {
		tx.Rollback()
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	if user.Money < product.Price {
		tx.Rollback()
		response.Write(w, response.BuildError(errNotEnoughMoney), http.StatusUnprocessableEntity)
		return
	}

	user.Money -= product.Price
	if result := tx.Model(&user).Update("money", user.Money); result.Error != nil {
		tx.Rollback()
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	order := model.Order{UserID: user.ID, ProductID: product.ID}
	if result := tx.Create(&order); result.Error != nil {
		tx.Rollback()
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	tx.Commit()

	response.Write(w, response.BuildSuccess(nil), http.StatusOK)
}

func (h Handler) ProductFlag(w http.ResponseWriter, r *http.Request) {
	productID, err := strconv.ParseInt(mux.Vars(r)["id"], 10, 64)
	if err != nil {
		response.Write(w, response.BuildError(err), http.StatusBadRequest)
		return
	}

	userID := r.Context().Value("UserID").(uint64)
	var count uint64
	if result := database.MySQL.Model(&model.Order{}).Where("product_id = ? AND user_id = ?", productID, userID).Count(&count); result.Error != nil && !result.RecordNotFound() {
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	if count == 0 {
		err = fmt.Errorf("You have to buy this product")
		response.Write(w, response.BuildError(err), http.StatusForbidden)
		return
	}

	var product model.Product
	if result := database.MySQL.First(&product, productID); result.Error != nil {
		response.Write(w, response.BuildError(response.ErrInternalServerError), http.StatusInternalServerError)
		return
	}

	response.Write(w, response.BuildSuccess(product.Flag), http.StatusOK)
}
