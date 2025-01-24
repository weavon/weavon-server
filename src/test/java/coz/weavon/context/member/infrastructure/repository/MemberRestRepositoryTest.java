package coz.weavon.context.member.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class MemberRestRepositoryTest {

    @Nested
    class MemberRestRepositoryMockTest {

        @InjectMocks
        private MemberQueryRepository memberQueryRepository;

        @InjectMocks
        private MemberRestRepository memberRepository;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            memberRepository = new MemberRestRepository(memberQueryRepository);
        }

        @Test
        public void findMembersByCommand_invalidConditionExceptionTest() {
            // given
            MemberSearchCommand emptyCommand = MemberSearchCommand.builder().build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembersByCommand(emptyCommand));
        }

        @Test
        public void findMembersByCommand_emptyMemberIds_invalidConditionExceptionTest() {
            // given
            MemberSearchCommand emptyMemberIdsCommand = MemberSearchCommand.builder()
                    .memberIds(Collections.emptyList())
                    .build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembersByCommand(emptyMemberIdsCommand));
        }

        @Test
        public void findMembersByCommand_emptyUsernames_invalidConditionExceptionTest() {
            // given
            MemberSearchCommand emptyUsernamesCommand = MemberSearchCommand.builder()
                    .usernames(Collections.emptyList())
                    .build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembersByCommand(emptyUsernamesCommand));
        }

        @Test
        public void findMembersByCommand_blankNickname_invalidConditionExceptionTest() {
            // given
            MemberSearchCommand blankUsernameCommand =
                    MemberSearchCommand.builder().nickname("").build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembersByCommand(blankUsernameCommand));
        }
    }
}
