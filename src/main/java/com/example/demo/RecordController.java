package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RecordController {

    private RecordDAO recordDAO = new RecordDAO();
    private UserDAO userDAO = new UserDAO();
    private BookDAO bookDAO = new BookDAO();

    private Boolean isAvailable(int book_id){
        List<BookRecord> list = recordDAO.selectAll();
        for (BookRecord record : list) {
            if (record.getBook_id() == book_id)
                return false;
        }
        return true;
    }

    @GetMapping(value = "/record")
    public ResponseEntity<List<BookRecord>> getRecords(){
        return ResponseEntity.ok(recordDAO.selectAll());
    }

    @GetMapping(value = "/record/{id}")
    public ResponseEntity<BookRecord> getRecord(@PathVariable int id){
        return ResponseEntity.ok(recordDAO.selectRecordByID(id));
    }




}
