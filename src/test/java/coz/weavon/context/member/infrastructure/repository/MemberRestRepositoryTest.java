package coz.weavon.context.member.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class MemberRestRepositoryTest {

    @Nested
    class MemberRestRepositoryMockTest {

        @Mock
        private JPAQueryFactory jpaQueryFactory;

        @Mock
        private MemberQueryRepository memberQueryRepository;

        @InjectMocks
        private MemberRestRepository memberRestRepository;

        @BeforeEach
        public void setUp() {
            memberQueryRepository = new MemberQueryRepository(jpaQueryFactory);
            memberRestRepository = new MemberRestRepository(memberQueryRepository);
        }

        @Test
        public void findMembersByCommand_invalidConditionExceptionTest() {
            // given
            MemberSearchCommand emptyCommand = MemberSearchCommand.builder().build();

            // then
            assertThrows(BusinessException.class, () -> memberRestRepository.findMembersByCommand(emptyCommand));
        }

        @Test
        public void findMembersByCommand_emptyMemberIds_invalidConditionExceptionTest() {
            // given
            MemberSearchCommand emptyMemberIdsCommand = MemberSearchCommand.builder()
                    .memberIds(Collections.emptyList())
                    .build();

            // then
            assertThrows(
                    BusinessException.class, () -> memberRestRepository.findMembersByCommand(emptyMemberIdsCommand));
        }

        @Test
        public void findMembersByCommand_emptyUsernames_invalidConditionExceptionTest() {
            // given
            MemberSearchCommand emptyUsernamesCommand = MemberSearchCommand.builder()
                    .usernames(Collections.emptyList())
                    .build();

            // then
            assertThrows(
                    BusinessException.class, () -> memberRestRepository.findMembersByCommand(emptyUsernamesCommand));
        }

        @Test
        public void findMembersByCommand_blankNickname_invalidConditionExceptionTest() {
            // given
            MemberSearchCommand blankUsernameCommand =
                    MemberSearchCommand.builder().nickname("").build();

            // then
            assertThrows(
                    BusinessException.class, () -> memberRestRepository.findMembersByCommand(blankUsernameCommand));
        }
    }
}
