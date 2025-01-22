package coz.weavon.context.member.domain.model;

import coz.weavon.common.presentation.model.exception.BusinessException;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Members {

    private static final String MSG_VLD_INVLD_SINGLE_RESULT = "message.validation.invalid.singleResult";

    private static final String LBL_MEMBER = "label.member";

    private final List<Member> members;

    public static Members of(List<Member> members) {
        return Members.builder().members(members).build();
    }

    public Member getSingleMember() {
        if (members.size() != 1) {
            throw new BusinessException(MSG_VLD_INVLD_SINGLE_RESULT, LBL_MEMBER);
        }

        return members.getFirst();
    }
}
