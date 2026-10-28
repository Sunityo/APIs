package com.codesunny.app.crudapi.Repo;

import com.codesunny.app.crudapi.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
