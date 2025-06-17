package com.example.OptimalYou.dao;

import com.example.OptimalYou.model.NoteBucket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteBucketRepo extends JpaRepository<NoteBucket, Integer>{

    List<NoteBucket> findByUser_Username(String username);

    @Query("FROM NoteBucket n WHERE n.user.username = :username AND n.title LIKE %:searchTerm% ")
    List<NoteBucket> searchNoteBucketByTitle(String username, String searchTerm);
}
