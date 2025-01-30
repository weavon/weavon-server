package coz.weavon.context.member.infrastructure.model;

import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "MEMBER", schema = "WEAVON")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID", unique = true, nullable = false, updatable = false)
    private Long memberId;

    @Column(name = "USERNAME", unique = true, nullable = false, updatable = false)
    private String username;

    @Column(name = "NICKNAME", nullable = false)
    private String nickname;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "ROLE", nullable = false)
    private RoleColumn role;

    public static MemberEntity fromDomain(Member member) {
        return MemberEntity.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .role(RoleColumn.USER)
                .build();
    }

    public static List<MemberEntity> fromDomains(Members members) {
        return members.getValue().stream().map(MemberEntity::fromDomain).toList();
    }

    public Member toDomain() {
        return Member.builder()
                .memberId(memberId)
                .username(username)
                .nickname(nickname)
                .email(email)
                .role(role.toDomain())
                .build();
    }

    public void update(MemberEntity member) {
        this.nickname = Objects.requireNonNullElse(member.getNickname(), this.nickname);
        this.email = Objects.requireNonNullElse(member.getEmail(), this.email);
        this.role = Objects.requireNonNullElse(member.getRole(), this.role);
    }
}
