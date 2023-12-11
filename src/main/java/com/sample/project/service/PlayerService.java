package com.sample.project.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.project.dto.PlayerRepository;
import com.sample.project.entity.Player;

@Service
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;

	public List<Player> getAllPlayers() {
		return playerRepository.findAll();
	}

	public void registerPlayer(Player player) {
		playerRepository.save(player);
	}

	public void updatePlayer(Player player) {
		playerRepository.save(player);
	}

	public Player getPlayerById(Long id) {
		Player getPlayerById = playerRepository.findById(id).get();
		return getPlayerById;
	}

}
