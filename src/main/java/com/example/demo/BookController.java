package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {
    private BookDAO bookDAO = new BookDAO();
    private UserDAO userDAO = new UserDAO();

    @PostMapping(value = "/book")
    public ResponseEntity<Book> addBook(@RequestHeader String username, @RequestHeader String password, @RequestBody Book book) {
        User user = userDAO.findUser(username, password);
        if (user != null && user.getStatus() == Status.ADMIN) {
            return ResponseEntity.ok(bookDAO.insertBook(book));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping(value = "/book")
    public ResponseEntity<List<Book>> getBooks() {
        return ResponseEntity.ok(bookDAO.selectAll());
    }

    @GetMapping(value = "/book/{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        return ResponseEntity.ok(bookDAO.selectBookByID(id));
    }

}
