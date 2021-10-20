package com.karankumar.booksapi.repository;

import com.karankumar.booksapi.model.award.Award;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AwardRepository extends CrudRepository<Award, Long> {

    @Query("SELECT DISTINCT a FROM Award a LEFT JOIN FETCH a.books")
    List<Award> findAllAwards();

}