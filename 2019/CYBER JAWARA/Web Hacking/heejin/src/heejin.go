package heejin

import (
	"net/http"

	"github.com/gorilla/mux"

	"heejin/database"
	"heejin/handler"
	"heejin/middleware"
	"heejin/session"
)

type Heejin struct {
	Router http.Handler
}

func NewHeejin() *Heejin {
	database.Init()
	handler.Init()
	session.Init()

	api := handler.APIHandler
	spa := handler.SPAHandler

	router := mux.NewRouter().StrictSlash(false)
	apiRouter := router.PathPrefix("/api").Subrouter()

	authMiddleware := middleware.AuthMiddleware

	apiRouter.HandleFunc("/register", api.Register).Methods("POST")
	apiRouter.HandleFunc("/login", api.Login).Methods("POST")
	apiRouter.HandleFunc("/me", authMiddleware(api.Me)).Methods("GET")

	productsRouter := apiRouter.PathPrefix("/products").Subrouter()
	productsRouter.HandleFunc("", api.ProductAll).Methods("GET")
	productsRouter.HandleFunc("/{id:[0-9]+}/buy", authMiddleware(api.ProductBuy)).Methods("POST")
	productsRouter.HandleFunc("/{id:[0-9]+}/flag", authMiddleware(api.ProductFlag)).Methods("GET")

	router.PathPrefix("/").Handler(spa)

	return &Heejin{router}
}
