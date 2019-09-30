package main

import (
	"log"
	"net/http"
	"time"

	"heejin"
)

func main() {
	app := heejin.NewHeejin()
	log.Println("Heejin will listen at port 31337")

	srv := &http.Server{
		Handler:      app.Router,
		Addr:         ":31337",
		WriteTimeout: 15 * time.Second,
		ReadTimeout:  15 * time.Second,
	}
	log.Fatal(srv.ListenAndServe())
}
