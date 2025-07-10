package coz.weavon.core.user.application.service;

import coz.weavon.common.exception.BusinessException;
import coz.weavon.constant.Label;
import coz.weavon.constant.Message;
import coz.weavon.core.user.application.model.command.UserOperateCommand;
import coz.weavon.core.user.application.model.command.UserSearchCommand;
import coz.weavon.core.user.application.model.result.UserOperateResult;
import coz.weavon.core.user.application.repository.UserRepository;
import coz.weavon.core.user.domain.model.User;
import coz.weavon.core.user.domain.model.Users;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class UserRestService implements UserService {

    private final UserRepository repository;

    @Override
    @Transactional(readOnly = true)
    public Users searchUsers(UserSearchCommand command) {
        command.validate();

        return repository.findUsers(command);
    }

    @Override
    @Transactional(readOnly = true)
    public User searchUser(UserSearchCommand command) {
        command.validate();

        Users users = repository.findUsers(command);
        Optional<User> optionalUser = users.getSingleUser();
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }

        throw new BusinessException(Message.Validation.IS_NOT_SINGLE_RESULT, Label.User.USER);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> searchOptionalUser(UserSearchCommand command) {
        command.validate();

        Users foundUsers = repository.findUsers(command);

        return foundUsers.getSingleUser();
    }

    @Override
    @Transactional
    public UserOperateResult operateUsers(UserOperateCommand command) {
        command.validate();

        UserOperateResult result = new UserOperateResult();

        if (command.hasUsersToDelete()) {
            repository.deleteUsers(command.getDeleteTargetUserIds());
        }

        if (command.hasUsersToUpdate()) {
            repository.updateUsers(command.getUpdateTargetUsers());
            result.setUpdatedUsers(command.getUpdateTargetUsers());
        }

        if (command.hasUsersToCreate()) {
            Users savedUsers = repository.saveUsers(command.getCreateTargetUsers());
            result.setCreatedUsers(savedUsers);
        }

        return result;
    }
}
