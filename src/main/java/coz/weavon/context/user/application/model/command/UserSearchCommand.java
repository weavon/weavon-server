package coz.weavon.context.user.application.model.command;

import coz.weavon.common.application.model.command.RestCommand;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchCommand extends RestCommand {

    private List<Long> userIds;

    private List<String> usernames;

    private String nickname;

    private List<String> emails;

    public static UserSearchCommand ofUsername(String username) {
        return UserSearchCommand.builder().usernames(List.of(username)).build();
    }

    public static UserSearchCommand ofEmail(String email) {
        return UserSearchCommand.builder().emails(List.of(email)).build();
    }

    @Override
    public void validate() {}
}
