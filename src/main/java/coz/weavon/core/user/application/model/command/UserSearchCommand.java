package coz.weavon.core.user.application.model.command;

import coz.weavon.common.shared.Command;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserSearchCommand extends Command {

    private List<Long> userIds;

    private List<String> usernames;

    private String nickname;

    private List<String> emails;

    public static UserSearchCommand ofUserIds(List<Long> userIds) {
        return UserSearchCommand.builder().userIds(userIds).build();
    }

    public static UserSearchCommand ofUsername(String username) {
        return UserSearchCommand.builder().usernames(List.of(username)).build();
    }

    public static UserSearchCommand ofEmail(String email) {
        return UserSearchCommand.builder().emails(List.of(email)).build();
    }

    @Override
    public void validate() {}
}
