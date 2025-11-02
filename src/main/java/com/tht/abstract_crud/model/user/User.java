package com.tht.abstract_crud.model.user;

import com.tht.abstract_crud.model.base.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Table(name = "USERS")
@Entity
public class User extends BaseEntity {

  @Column(name = "USERNAME", nullable = false, unique = true)
  private String username;
}
