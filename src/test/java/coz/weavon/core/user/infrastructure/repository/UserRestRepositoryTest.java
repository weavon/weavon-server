package coz.weavon.core.user.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.core.user.application.model.condition.UserSearchCondition;
import coz.weavon.core.user.application.repository.UserRepository;
import coz.weavon.core.user.domain.model.User;
import coz.weavon.core.user.domain.model.Users;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

class UserRestRepositoryTest {

    @Nested
    class UserRestRepositoryMockTest {

        @InjectMocks
        private UserRestRepository userRepository;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void findUsers_invalidCondition_exceptionTest() {
            // given
            UserSearchCondition emptyCondition = UserSearchCondition.builder().build();

            // then
            assertThrows(BusinessException.class, () -> userRepository.findUsers(emptyCondition));
        }

        @Test
        public void findUsers_emptyUserIds_invalidCondition_exceptionTest() {
            // given
            UserSearchCondition emptyUserIdsCondition = UserSearchCondition.builder()
                    .userIds(Collections.emptyList())
                    .build();

            // then
            assertThrows(BusinessException.class, () -> userRepository.findUsers(emptyUserIdsCondition));
        }

        @Test
        public void findUsers_emptyUsernames_invalidCondition_exceptionTest() {
            // given
            UserSearchCondition emptyUsernamesCondition = UserSearchCondition.builder()
                    .usernames(Collections.emptyList())
                    .build();

            // then
            assertThrows(BusinessException.class, () -> userRepository.findUsers(emptyUsernamesCondition));
        }

        @Test
        public void findUsers_blankNickname_invalidCondition_exceptionTest() {
            // given
            UserSearchCondition blankUsernameCondition =
                    UserSearchCondition.builder().nickname("").build();

            // then
            assertThrows(BusinessException.class, () -> userRepository.findUsers(blankUsernameCondition));
        }
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("test")
    class UserRestRepositorySpringBootTest {

        private static final User user = User.builder()
                .username("test")
                .nickname("test")
                .email("test@email.com")
                .build();

        @Autowired
        private UserRepository userRepository;

        @Test
        @Transactional
        public void findUsers_inUsernames_successTest() {
            // given
            Users users = Users.of(List.of(user));
            userRepository.saveUsers(users);

            // when
            UserSearchCondition condition = UserSearchCondition.builder()
                    .usernames(List.of(user.getUsername()))
                    .build();
            Users foundUsers = userRepository.findUsers(condition);

            // then
            Optional<User> foundUser = foundUsers.getUserByUsername(user.getUsername());
            assertTrue(foundUser.isPresent());
            assertEquals(user.getUsername(), foundUser.get().getUsername());
        }

        @Test
        @Transactional
        public void findUsers_likeNicknames_successTest() {
            // given
            Users users = Users.of(List.of(user));
            userRepository.saveUsers(users);
            UserSearchCondition userSearchCondition =
                    UserSearchCondition.builder().nickname("te").build();

            // when
            Users foundUsers = userRepository.findUsers(userSearchCondition);
            Optional<User> foundUser = foundUsers.getUserByUsername(user.getUsername());

            // then
            assertTrue(foundUser.isPresent());
            assertEquals(user.getUsername(), foundUser.get().getUsername());
        }

        @Test
        @Transactional
        public void saveUsers_successTest() {
            // when
            Users user = userRepository.saveUsers(Users.of(List.of(UserRestRepositorySpringBootTest.user)));
            Optional<User> foundUser = user.getUserByUsername(UserRestRepositorySpringBootTest.user.getUsername());

            // then
            assertTrue(foundUser.isPresent());
            assertEquals(1, user.size());
            assertEquals(
                    UserRestRepositorySpringBootTest.user.getUsername(),
                    foundUser.get().getUsername());
            assertNotNull(foundUser.get().getUserId());
        }

        @Test
        @Transactional
        public void updateUsers_successTest() {
            // given
            Users users = Users.of(List.of(user));
            Users savedUsers = userRepository.saveUsers(users);
            Optional<User> optionalSavedUser = savedUsers.getUserByUsername(user.getUsername());
            assertTrue(optionalSavedUser.isPresent());
            User savedUser = optionalSavedUser.get();

            // when
            String newNickname = "test";
            String newEmail = "test@email.com";
            savedUser.setNickname(newNickname);
            savedUser.setEmail(newEmail);
            userRepository.updateUsers(savedUsers);

            UserSearchCondition userSearchCondition = UserSearchCondition.builder()
                    .userIds(List.of(savedUser.getUserId()))
                    .build();
            Users updatedUsers = userRepository.findUsers(userSearchCondition);
            Optional<User> optionalUpdatedUsers = updatedUsers.getUserByUsername(savedUser.getUsername());

            // then
            assertTrue(optionalUpdatedUsers.isPresent());
            assertEquals(newNickname, optionalUpdatedUsers.get().getNickname());
            assertEquals(newEmail, optionalUpdatedUsers.get().getEmail());
        }

        @Test
        public void deleteUsers_successTest() {
            // given
            Users savedUsers = userRepository.saveUsers(Users.of(List.of(user)));

            // when
            userRepository.deleteUsers(savedUsers.getUserIds());
            Users foundUsers = userRepository.findUsers(UserSearchCondition.builder()
                    .userIds(savedUsers.getUserIds())
                    .build());

            // then
            assertEquals(0, foundUsers.size());
        }
    }
}
