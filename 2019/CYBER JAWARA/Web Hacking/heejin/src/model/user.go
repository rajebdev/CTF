package model

type User struct {
	ID       uint64  `json:"id" gorm:"primary_key;type:bigint"`
	Username string  `json:"username" gorm:"type:varchar(20);unique;not null"`
	Password string  `json:"password,omitempty" gorm:"type:varchar(255)";not null`
	Money    uint64  `json:"money" gorm:"type:bigint;not null;default:100000"`
	Orders   []Order `json:"-"`
}
