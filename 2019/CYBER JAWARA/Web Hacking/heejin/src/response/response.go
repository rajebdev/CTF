package response

import (
	"encoding/json"
	"net/http"
)

type SuccessResponse struct {
	Data   interface{} `json:"data,omitempty"`
	Errors []string    `json:"errors,omitempty"`
}

type ErrorResponse struct {
	Errors []string `json:"errors"`
}

func BuildSuccess(data interface{}) SuccessResponse {
	return SuccessResponse{
		Data: data,
	}
}

func BuildError(err error) ErrorResponse {
	return BuildErrors([]error{err})
}

func BuildErrors(errors []error) ErrorResponse {
	var errorInfos []string

	for _, err := range errors {
		errorInfos = append(errorInfos, err.Error())
	}

	return ErrorResponse{
		Errors: errorInfos,
	}
}

func Write(w http.ResponseWriter, result interface{}, status int) {
	w.Header().Set("Content-Type", "application/json")
	w.WriteHeader(status)
	json.NewEncoder(w).Encode(result)
}
