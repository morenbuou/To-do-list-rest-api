package com.thoughtworks.restful.restful.repository;

import com.thoughtworks.restful.restful.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User getByUsername(String username);

}
