package com.example.demo.Models;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByOrderByAutorAsc();
    List<Book> findAllByOrderByAutorDesc();
    List<Book> findAllByOrderByPublishDateAsc();
    List<Book> findAllByOrderByPublishDateDesc();
    List<Book> findAllByCoverIsNotNull();

}
