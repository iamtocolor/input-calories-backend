package com.toptal.project.inputcaloriesapis.dao;

import com.toptal.project.inputcaloriesapis.entity.FoodEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface FoodRepo extends PagingAndSortingRepository<FoodEntity, Long>, JpaSpecificationExecutor<FoodEntity> {

    Page<FoodEntity> findAllByUserId(UUID userId, Pageable pageable);
}
