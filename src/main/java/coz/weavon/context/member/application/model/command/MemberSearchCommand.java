package coz.weavon.context.member.application.model.command;

import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSearchCommand {

    private List<Long> memberIds;

    private String username;
}
