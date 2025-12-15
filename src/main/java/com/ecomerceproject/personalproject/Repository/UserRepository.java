package com.ecomerceproject.personalproject.Repository;

import com.ecomerceproject.personalproject.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
