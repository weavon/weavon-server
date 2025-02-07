package coz.weavon.context.member.application.model.command;

import coz.weavon.common.application.model.command.RestCommand;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberSearchCommand extends RestCommand {

    private List<Long> memberIds;

    private List<String> usernames;

    private String nickname;

    private List<String> emails;

    public static MemberSearchCommand ofUsername(String username) {
        return MemberSearchCommand.builder().usernames(List.of(username)).build();
    }

    public static MemberSearchCommand ofEmail(String email) {
        return MemberSearchCommand.builder().emails(List.of(email)).build();
    }

    @Override
    public void validate() {}
}
