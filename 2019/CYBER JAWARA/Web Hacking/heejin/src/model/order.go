package model

type Order struct {
	ID        uint64 `gorm:"primary_key;type:bigint"`
	UserID    uint64
	User      User
	ProductID uint64
	Product   Product
}
