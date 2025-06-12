package com.example.OptimalYou.dao;
import com.example.OptimalYou.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepo extends JpaRepository<User, String> {

	User findByUsername(String username);
}
