package com.tht.abstract_crud.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.tht.abstract_crud.model.user.User;
import com.tht.abstract_crud.repository.base.BaseRepository;

@Repository
public interface UserRepository extends BaseRepository<User>, JpaSpecificationExecutor<User> {
  Optional<User> findByUsername(String username);
}
