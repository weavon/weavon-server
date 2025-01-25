package coz.weavon.context.member.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MemberRestRepositoryTest {

    @Nested
    class MemberRestRepositoryMockTest {

        @Mock
        private MemberQueryRepository memberQueryRepository;

        @InjectMocks
        private MemberRestRepository memberRepository;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
            memberRepository = new MemberRestRepository(memberQueryRepository);
        }

        @Test
        public void findMembersByCondition_invalidConditionExceptionTest() {
            // given
            MemberSearchCondition emptyCondition =
                    MemberSearchCondition.builder().build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembersByCondition(emptyCondition));
        }

        @Test
        public void findMembersByCondition_emptyMemberIds_invalidConditionExceptionTest() {
            // given
            MemberSearchCondition emptyMemberIdsCondition = MemberSearchCondition.builder()
                    .memberIds(Collections.emptyList())
                    .build();

            // then
            assertThrows(
                    BusinessException.class, () -> memberRepository.findMembersByCondition(emptyMemberIdsCondition));
        }

        @Test
        public void findMembersByCondition_emptyUsernames_invalidConditionExceptionTest() {
            // given
            MemberSearchCondition emptyUsernamesCondition = MemberSearchCondition.builder()
                    .usernames(Collections.emptyList())
                    .build();

            // then
            assertThrows(
                    BusinessException.class, () -> memberRepository.findMembersByCondition(emptyUsernamesCondition));
        }

        @Test
        public void findMembersByCondition_blankNickname_invalidConditionExceptionTest() {
            // given
            MemberSearchCondition blankUsernameCondition =
                    MemberSearchCondition.builder().nickname("").build();

            // then
            assertThrows(
                    BusinessException.class, () -> memberRepository.findMembersByCondition(blankUsernameCondition));
        }
    }
}
