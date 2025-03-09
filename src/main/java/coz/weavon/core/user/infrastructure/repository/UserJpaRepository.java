package coz.weavon.core.user.infrastructure.repository;

import coz.weavon.core.user.infrastructure.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {}
