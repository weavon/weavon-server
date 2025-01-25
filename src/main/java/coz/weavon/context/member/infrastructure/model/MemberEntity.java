package coz.weavon.context.member.infrastructure.model;

import coz.weavon.context.member.domain.model.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "MEMBER", schema = "WEAVON")
public class MemberEntity {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(name = "USERNAME", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "NICKNAME", unique = true, nullable = false)
    private String nickname;

    @Column(name = "EMAIL", unique = true, nullable = false)
    private String email;

    public Member toDomain() {
        return Member.builder()
                .memberId(memberId)
                .username(username)
                .nickname(nickname)
                .email(email)
                .build();
    }
}
