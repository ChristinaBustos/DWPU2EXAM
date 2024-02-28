package com.example.demo.Dto;

import com.example.demo.Models.Book;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BookDto {
    private Long id;
    private String name;
    private String autor;
    LocalDate publishDate;

    public Book getBook(){
        return new Book(
                getId(),
                getName(),
                getAutor(),
                getPublishDate()
        );
    }
}
