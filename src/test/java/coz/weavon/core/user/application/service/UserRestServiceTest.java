package coz.weavon.core.user.application.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.core.user.application.model.command.UserOperateCommand;
import coz.weavon.core.user.application.model.command.UserSearchCommand;
import coz.weavon.core.user.application.model.condition.UserSearchCondition;
import coz.weavon.core.user.application.model.result.UserOperateResult;
import coz.weavon.core.user.application.repository.UserRepository;
import coz.weavon.core.user.domain.model.User;
import coz.weavon.core.user.domain.model.Users;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

class UserRestServiceTest {

    @Nested
    class UserRestServiceMockTest {

        @Mock
        private UserRepository userRepository;

        @InjectMocks
        private UserRestService userService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void searchUser_userNotFound_nonSingleResultExceptionTest() {
            // given
            UserSearchCommand emptyCommand =
                    UserSearchCommand.builder().userIds(List.of(-1L)).build();
            Users users = Users.of(Collections.emptyList());

            // when
            when(userRepository.findUsers(any(UserSearchCondition.class))).thenReturn(users);

            // then
            assertThrows(BusinessException.class, () -> userService.searchUser(emptyCommand));
        }

        @Test
        public void operateUsers_noOperateTargetsExist_exceptionTest() {
            // given
            UserOperateCommand noTargetOperateCommand =
                    UserOperateCommand.builder().build();

            // then
            assertThrows(BusinessException.class, () -> userService.operateUsers(noTargetOperateCommand));
        }
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("test")
    class UserRestServiceSpringBootTest {

        @Autowired
        private UserService userService;

        @Test
        @Transactional
        public void operateUsers_createUsers_successTest() {
            // given
            Users users = Stream.iterate(1, i -> i + 1)
                    .limit(3)
                    .map(i -> User.builder()
                            .username("user" + i)
                            .nickname("nickname" + i)
                            .email("user" + i + "@email.com")
                            .build())
                    .collect(Collectors.collectingAndThen(Collectors.toList(), Users::of));
            UserOperateCommand operateCommand = UserOperateCommand.ofCreateTargets(users);

            // when
            UserOperateResult operateResult = userService.operateUsers(operateCommand);

            // then
            Users createdUsers = operateResult.getCreatedUsers();
            assertEquals(3, createdUsers.size());
            createdUsers.getValue().forEach(user -> assertNotNull(user.getUserId()));
        }

        @Test
        @Transactional
        public void operateUsers_updateUsers_successTest() {
            // given
            Users users = Users.of(List.of(User.ofUser("user1", "nickname1", "user1@email.com")));
            UserOperateCommand createOperateCommand = UserOperateCommand.ofCreateTargets(users);
            UserOperateResult createOperateResult = userService.operateUsers(createOperateCommand);
            Optional<User> foundCreatedUser1 =
                    createOperateResult.getCreatedUsers().getUserByUsername("user1");
            assertTrue(foundCreatedUser1.isPresent());
            User user1 = foundCreatedUser1.get();

            // when
            user1.setNickname("nickname1_updated");
            UserOperateCommand updateOperateCommand = UserOperateCommand.ofUpdateTargets(Users.of(List.of(user1)));
            UserOperateResult updateOperateResult = userService.operateUsers(updateOperateCommand);
            Users updatedUsers = updateOperateResult.getUpdatedUsers();
            Optional<User> foundUpdatedUser1 = updatedUsers.getUserByUsername("user1");
            assertTrue(foundUpdatedUser1.isPresent());

            // then
            assertEquals(1, updatedUsers.size());
            assertEquals("nickname1_updated", foundUpdatedUser1.get().getNickname());
        }

        @Test
        public void operateUsers_deleteUsers_successTest() {
            // given
            Users users = Users.of(List.of(User.ofUser("user1", "nickname1", "user1@email.com")));
            UserOperateCommand createOperateCommand = UserOperateCommand.ofCreateTargets(users);
            UserOperateResult createOperateResult = userService.operateUsers(createOperateCommand);

            // when
            UserOperateCommand deleteOperateCommand = UserOperateCommand.ofDeleteTargets(
                    createOperateResult.getCreatedUsers().getUserIds());
            userService.operateUsers(deleteOperateCommand);

            UserSearchCommand userSearchCommand =
                    UserSearchCommand.builder().usernames(List.of("user1")).build();

            // then
            assertThrows(BusinessException.class, () -> userService.searchUser(userSearchCommand));
        }
    }
}
