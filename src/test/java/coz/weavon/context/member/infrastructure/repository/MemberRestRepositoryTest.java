package coz.weavon.context.member.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

class MemberRestRepositoryTest {

    @Nested
    class MemberRestRepositoryMockTest {

        @InjectMocks
        private MemberRestRepository memberRepository;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void findMembers_invalidConditionExceptionTest() {
            // given
            MemberSearchCondition emptyCondition =
                    MemberSearchCondition.builder().build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembers(emptyCondition));
        }

        @Test
        public void findMembers_emptyMemberIds_invalidConditionExceptionTest() {
            // given
            MemberSearchCondition emptyMemberIdsCondition = MemberSearchCondition.builder()
                    .memberIds(Collections.emptyList())
                    .build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembers(emptyMemberIdsCondition));
        }

        @Test
        public void findMembers_emptyUsernames_invalidConditionExceptionTest() {
            // given
            MemberSearchCondition emptyUsernamesCondition = MemberSearchCondition.builder()
                    .usernames(Collections.emptyList())
                    .build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembers(emptyUsernamesCondition));
        }

        @Test
        public void findMembers_blankNickname_invalidConditionExceptionTest() {
            // given
            MemberSearchCondition blankUsernameCondition =
                    MemberSearchCondition.builder().nickname("").build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembers(blankUsernameCondition));
        }
    }

    @Nested
    @SpringBootTest
    class MemberRestRepositorySpringBootTest {

        @Autowired
        private MemberRepository memberRepository;

        @Test
        @Transactional
        public void findMembers_inUsernames_successTest() {
            // given
            String[] usernames = {"coza", "cozb", "cozc", "cozd", "coze"};

            List<Member> memberList = Stream.iterate(0, i -> i + 1)
                    .limit(5)
                    .map(i -> Member.builder()
                            .username(usernames[i])
                            .nickname(usernames[i])
                            .email(usernames[i] + "@email.com")
                            .build())
                    .toList();

            Members members = Members.of(memberList);
            memberRepository.saveMembers(members);

            // when
            MemberSearchCondition condition = MemberSearchCondition.builder()
                    .usernames(List.of("coza", "cozb"))
                    .build();
            Members foundMembers = memberRepository.findMembers(condition);

            // then
            assertEquals(2, foundMembers.size());

            Optional<Member> foundCoza = foundMembers.getMemberByUsername("coza");
            assertTrue(foundCoza.isPresent());
            assertEquals("coza", foundCoza.get().getUsername());

            Optional<Member> foundCozb = foundMembers.getMemberByUsername("cozb");
            assertTrue(foundCozb.isPresent());
            assertEquals("cozb", foundCozb.get().getUsername());
        }

        @Test
        @Transactional
        public void saveMembers_successTest() {
            // given
            Member member = Member.builder()
                    .username("codesver")
                    .nickname("JaeWon")
                    .email("codesver@email.com")
                    .build();

            // when
            Members members = memberRepository.saveMembers(Members.of(List.of(member)));
            Optional<Member> foundMember = members.getMemberByUsername(member.getUsername());

            // then
            assertTrue(foundMember.isPresent());
            assertEquals(1, members.size());
            assertEquals(member.getUsername(), foundMember.get().getUsername());
            assertNotNull(foundMember.get().getMemberId());
        }
    }
}
