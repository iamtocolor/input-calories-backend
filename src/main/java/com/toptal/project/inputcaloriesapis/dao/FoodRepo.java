package com.toptal.project.inputcaloriesapis.dao;

import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface FoodRepo extends PagingAndSortingRepository<FoodEntity, Long> {

    Page<FoodEntity> findAllByUserId(UUID userId, Pageable pageable);
}
