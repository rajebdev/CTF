package database

import (
	"github.com/jinzhu/gorm"
)

var (
	MySQL *gorm.DB
)

func Init() {
	MySQL = NewMySQL()
}
