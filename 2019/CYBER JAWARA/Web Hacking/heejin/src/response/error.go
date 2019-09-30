package response

import (
	"fmt"
)

var (
	ErrNotFound                  = fmt.Errorf("Not found")
	ErrBadRequest                = fmt.Errorf("Bad request")
	ErrUnauthorized              = fmt.Errorf("Unauthorized")
	ErrInvalidUsernameOrPassword = fmt.Errorf("Invalid username or password")
	ErrInternalServerError       = fmt.Errorf("Internal server error")
)
