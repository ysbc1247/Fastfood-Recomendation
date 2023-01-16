package com.projects.hamburger.fastfood.repository;

import com.projects.hamburger.fastfood.entity.Fastfood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FastfoodRepository extends JpaRepository<Fastfood, Long> {
}
