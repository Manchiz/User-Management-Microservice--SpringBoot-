package com.Qouro.HRMManagementUser.UserEntity;

import org.springframework.data.repository.CrudRepository;

public interface usersReposirories extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
    UserEntity findByUserId(String userId);
}
