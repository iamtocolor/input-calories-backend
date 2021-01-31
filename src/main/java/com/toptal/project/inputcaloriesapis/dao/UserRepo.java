package com.toptal.project.inputcaloriesapis.dao;

import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    UserEntity findByEmail(String email);

    UserEntity findByUserId(UUID uuid);

}
