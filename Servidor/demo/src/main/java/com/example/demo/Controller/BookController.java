package com.example.demo.Controller;

import com.example.demo.Dto.BookDto;
import com.example.demo.Models.Book;
import com.example.demo.Service.BookService;
import com.example.demo.Service.CustomResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-book")
@CrossOrigin(origins = "*")
public class BookController {
    @Autowired
    BookService service;
    @GetMapping("/")
    public ResponseEntity<List<Book>> getAll(){
        return new ResponseEntity(
                this.service.getAll(),
                HttpStatus.OK
        );
    }


    @GetMapping("/orderAutorByAsc/")
    public ResponseEntity<CustomResponse<List<Book>>> finbyAutorAsc(BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        CustomResponse<List<Book>> bookFilter = this.service.OrderByAutorAsc();
        return new ResponseEntity<>(bookFilter, HttpStatus.CREATED);
    }

    @GetMapping("/orderAutorByDesc/")
    public ResponseEntity<CustomResponse<List<Book>>> finbyAutorDesc(BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        CustomResponse<List<Book>> bookFilter = this.service.OrderByAutorDesc();
        return new ResponseEntity<>(bookFilter, HttpStatus.CREATED);
    }

    @GetMapping("/orderDatesByAsc/")
    public ResponseEntity<CustomResponse<List<Book>>> finbyDateAsc(BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        CustomResponse<List<Book>> bookFilter = this.service.OrderByPublishDateAsc();
        return new ResponseEntity<>(bookFilter, HttpStatus.CREATED);
    }

    @GetMapping("/orderDatesByDesc/")
    public ResponseEntity<CustomResponse<List<Book>>> finbyDateDesc(BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        CustomResponse<List<Book>> bookFilter = this.service.OrderByPublishDateDesc();
        return new ResponseEntity<>(bookFilter, HttpStatus.CREATED);
    }


    @PostMapping("/")
    public ResponseEntity<CustomResponse<Book>> insert(@Valid @RequestBody BookDto dto){
        return new ResponseEntity<>(
                this.service.insert(dto.getBook()),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/")
    public ResponseEntity<CustomResponse<Book>> update(
            @RequestBody BookDto dto, @Valid BindingResult result) {
        if (result.hasErrors()) {
            return new ResponseEntity<>(
                    null,
                    HttpStatus.BAD_REQUEST
            );
        }
        return new ResponseEntity<>(
                this.service.update(dto.getBook()),// traer el get
                HttpStatus.CREATED
        );
    }

    @DeleteMapping("/libro/{id}")
    public ResponseEntity<CustomResponse<Book>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(
                this.service.delete(id),
                HttpStatus.CREATED
        );
    }

}
