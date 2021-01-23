package com.toptal.project.inputcaloriesapis.dao;

import com.toptal.project.inputcaloriesapis.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends PagingAndSortingRepository<UserEntity, Long> {

    public UserEntity findByEmail(String email);
}
