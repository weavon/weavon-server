package coz.weavon.context.member.infrastructure.repository;

import coz.weavon.context.member.infrastructure.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {}
