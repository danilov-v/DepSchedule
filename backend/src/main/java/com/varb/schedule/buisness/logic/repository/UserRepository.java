package com.varb.schedule.buisness.logic.repository;

import com.varb.schedule.buisness.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>
{}
