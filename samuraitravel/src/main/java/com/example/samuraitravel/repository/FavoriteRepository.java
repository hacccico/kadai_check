package com.example.samuraitravel.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.samuraitravel.entity.Favorite;
import com.example.samuraitravel.entity.House;
import com.example.samuraitravel.entity.User;

import jakarta.transaction.Transactional;

public interface FavoriteRepository extends JpaRepository<Favorite, Integer>{
	public List<Favorite> findByHouse(House house);
	public Page<Favorite> findByUser(User user, Pageable pageable);
	
	@Transactional
	public Favorite findByHouseAndUser(House house, User user);
	public Favorite deleteByHouseAndUser(House house, User user);

}
