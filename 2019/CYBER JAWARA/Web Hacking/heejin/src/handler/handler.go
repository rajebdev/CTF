package handler

import (
	"heejin/handler/api"
	"heejin/handler/spa"
)

var (
	APIHandler api.Handler
	SPAHandler spa.Handler
)

func Init() {
	APIHandler = api.Handler{}
	SPAHandler = spa.Handler{
		StaticPath: "build",
		IndexPath:  "index.html",
	}
}
