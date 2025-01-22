package coz.weavon.context.member.domain.model;

import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Members {

    private final List<Member> members;

    public static Members of(List<Member> members) {
        return Members.builder().members(members).build();
    }
}
