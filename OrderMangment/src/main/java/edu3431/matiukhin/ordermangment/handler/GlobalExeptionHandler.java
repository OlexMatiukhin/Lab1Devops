package edu3431.matiukhin.ordermangment.handler;/*
@author sasha
@project springshop
@class GlobalExeptionHandler
@version 1.0.0
@since 25.03.2025 - 21 - 54
*/


import edu3431.matiukhin.ordermangment.exeption.CostExeption;
import edu3431.matiukhin.ordermangment.exeption.ElementNotFoundInBaseExeption;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExeptionHandler {

    @ExceptionHandler(ElementNotFoundInBaseExeption.class)
    public ResponseEntity<String> handleClientNotFound(ElementNotFoundInBaseExeption e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CostExeption.class)
    public ResponseEntity<String> handleCostLessThenNull(CostExeption e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAllExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error occured: " + e.getMessage());
    }
}
