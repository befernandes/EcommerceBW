package com.ecombw.EcommerceBW.repository;

import com.ecombw.EcommerceBW.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
