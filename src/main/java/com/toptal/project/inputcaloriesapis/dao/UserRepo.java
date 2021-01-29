package com.toptal.project.inputcaloriesapis.dao;

import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long>, JpaSpecificationExecutor<UserEntity> {

    public UserEntity findByEmail(String email);

    public UserEntity findByUserId(UUID uuid);

}
