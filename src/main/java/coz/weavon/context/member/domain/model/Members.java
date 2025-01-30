package coz.weavon.context.member.domain.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

@Data
@Builder
public class Members {

    private List<Member> value;

    public static Members of(List<Member> members) {
        return Members.builder().value(members).build();
    }

    public static Members of(Member member) {
        return Members.builder().value(List.of(member)).build();
    }

    public Optional<Member> getSingleMember() {
        if (value.size() == 1) {
            return value.stream().findFirst();
        }

        return Optional.empty();
    }

    public Optional<Member> getMemberByUsername(String username) {
        return value.stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst();
    }

    public Optional<Member> getMemberByEmail(String email) {
        return value.stream().filter(member -> member.getEmail().equals(email)).findFirst();
    }

    public List<Long> getMemberIds() {
        return value.stream().map(Member::getMemberId).toList();
    }

    public int size() {
        return value.size();
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(value);
    }
}
