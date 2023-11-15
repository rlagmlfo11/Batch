package com.sample.project.dto;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sample.project.entity.Player;

public interface PlayerRepository extends JpaRepository<Player, Long> {

}
