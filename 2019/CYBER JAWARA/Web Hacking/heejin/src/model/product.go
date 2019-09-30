package model

type Product struct {
	ID          uint64 `json:"id" gorm:"primary_key;type:bigint"`
	Name        string `json:"name" gorm:"type:varchar(20);not null"`
	Description string `json:"description" gorm:"type:varchar(255)";not null`
	Price       uint64 `json:"price" gorm:"type:bigint;not null"`
	Image       string `json:"image" gorm:"type:text"`
	Flag        string `json:"-" gorm:"type:varchar(255)"`
}
