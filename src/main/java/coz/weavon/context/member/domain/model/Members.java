package coz.weavon.context.member.domain.model;

import java.util.List;
import java.util.Optional;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Members {

    private final List<Member> members;

    public static Members of(List<Member> members) {
        return Members.builder().members(members).build();
    }

    public Optional<Member> getSingleMember() {
        if (members.size() == 1) {
            return members.stream().findFirst();
        }

        return Optional.empty();
    }

    public Optional<Member> getMemberByUsername(String username) {
        return members.stream()
                .filter(member -> member.getUsername().equals(username))
                .findFirst();
    }

    public int size() {
        return members.size();
    }
}
