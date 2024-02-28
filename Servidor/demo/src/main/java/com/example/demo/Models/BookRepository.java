package com.example.demo.Models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findTopByAutorBetweenOrderByAutorAutorAsc();
    List<Book> findTopByOrderByAuthorDesc();
    List<Book> findTopByOrderByPublishDateAsc();
    List<Book> findTopByOrderByPublishDateDesc();

}
