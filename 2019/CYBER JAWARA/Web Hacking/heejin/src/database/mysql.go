package database

import (
	"fmt"
	"os"
	"time"

	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"

	"heejin/model"
)

func NewMySQL() *gorm.DB {
	username := os.Getenv("DATABASE_USERNAME")
	password := os.Getenv("DATABASE_PASSWORD")
	host := os.Getenv("DATABASE_HOST")
	port := os.Getenv("DATABASE_PORT")
	name := os.Getenv("DATABASE_NAME")

	dataSourceName := fmt.Sprintf("%s:%s@tcp(%s:%s)/%s?charset=utf8mb4,utf8&parseTime=True&loc=Local", username, password, host, port, name)

	var db *gorm.DB
	var err error
	for try, maxTry := 0, 5; ; try++ {
		db, err = gorm.Open("mysql", dataSourceName)
		if err == nil {
			break
		}
		if try >= maxTry {
			panic(err)
		}
		fmt.Println("DB is not up, sleeping...")
		time.Sleep(10 * time.Second)
	}

	db.DB().SetConnMaxLifetime(5 * time.Second)
	db.DB().SetMaxIdleConns(0)
	db.DB().SetMaxOpenConns(256)
	db.AutoMigrate(&model.User{}, &model.Product{}, &model.Order{})

	return db
}
