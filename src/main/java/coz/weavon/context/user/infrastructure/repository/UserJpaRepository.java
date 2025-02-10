package coz.weavon.context.user.infrastructure.repository;

import coz.weavon.context.user.infrastructure.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {}
