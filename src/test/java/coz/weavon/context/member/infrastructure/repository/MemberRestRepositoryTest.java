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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
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
        public void findMembers_invalidCondition_exceptionTest() {
            // given
            MemberSearchCondition emptyCondition =
                    MemberSearchCondition.builder().build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembers(emptyCondition));
        }

        @Test
        public void findMembers_emptyMemberIds_invalidCondition_exceptionTest() {
            // given
            MemberSearchCondition emptyMemberIdsCondition = MemberSearchCondition.builder()
                    .memberIds(Collections.emptyList())
                    .build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembers(emptyMemberIdsCondition));
        }

        @Test
        public void findMembers_emptyUsernames_invalidCondition_exceptionTest() {
            // given
            MemberSearchCondition emptyUsernamesCondition = MemberSearchCondition.builder()
                    .usernames(Collections.emptyList())
                    .build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembers(emptyUsernamesCondition));
        }

        @Test
        public void findMembers_blankNickname_invalidCondition_exceptionTest() {
            // given
            MemberSearchCondition blankUsernameCondition =
                    MemberSearchCondition.builder().nickname("").build();

            // then
            assertThrows(BusinessException.class, () -> memberRepository.findMembers(blankUsernameCondition));
        }
    }

    @Nested
    @SpringBootTest
    @ActiveProfiles("test")
    class MemberRestRepositorySpringBootTest {

        private static final Member member = Member.builder()
                .username("test")
                .nickname("test")
                .email("test@email.com")
                .build();

        @Autowired
        private MemberRepository memberRepository;

        @Test
        @Transactional
        public void findMembers_inUsernames_successTest() {
            // given
            Members members = Members.of(List.of(member));
            memberRepository.saveMembers(members);

            // when
            MemberSearchCondition condition = MemberSearchCondition.builder()
                    .usernames(List.of(member.getUsername()))
                    .build();
            Members foundMembers = memberRepository.findMembers(condition);

            // then
            Optional<Member> foundMember = foundMembers.getMemberByUsername(member.getUsername());
            assertTrue(foundMember.isPresent());
            assertEquals(member.getUsername(), foundMember.get().getUsername());
        }

        @Test
        @Transactional
        public void findMembers_likeNicknames_successTest() {
            // given
            Members members = Members.of(List.of(member));
            memberRepository.saveMembers(members);
            MemberSearchCondition memberSearchCondition =
                    MemberSearchCondition.builder().nickname("te").build();

            // when
            Members foundMembers = memberRepository.findMembers(memberSearchCondition);
            Optional<Member> foundMember = foundMembers.getMemberByUsername(member.getUsername());

            // then
            assertTrue(foundMember.isPresent());
            assertEquals(member.getUsername(), foundMember.get().getUsername());
        }

        @Test
        @Transactional
        public void saveMembers_successTest() {
            // when
            Members members = memberRepository.saveMembers(Members.of(List.of(member)));
            Optional<Member> foundMember = members.getMemberByUsername(member.getUsername());

            // then
            assertTrue(foundMember.isPresent());
            assertEquals(1, members.size());
            assertEquals(member.getUsername(), foundMember.get().getUsername());
            assertNotNull(foundMember.get().getMemberId());
        }

        @Test
        @Transactional
        public void updateMembers_successTest() {
            // given
            Members members = Members.of(List.of(member));
            Members savedMembers = memberRepository.saveMembers(members);
            Optional<Member> foundSavedMember = savedMembers.getMemberByUsername(member.getUsername());
            assertTrue(foundSavedMember.isPresent());
            Member savedMember = foundSavedMember.get();

            // when
            String newNickname = "test";
            String newEmail = "test@email.com";
            savedMember.setNickname(newNickname);
            savedMember.setEmail(newEmail);
            memberRepository.updateMembers(savedMembers);

            MemberSearchCondition memberSearchCondition = MemberSearchCondition.builder()
                    .memberIds(List.of(savedMember.getMemberId()))
                    .build();
            Members updatedMembers = memberRepository.findMembers(memberSearchCondition);
            Optional<Member> foundUpdatedMember = updatedMembers.getMemberByUsername(savedMember.getUsername());

            // then
            assertTrue(foundUpdatedMember.isPresent());
            assertEquals(newNickname, foundUpdatedMember.get().getNickname());
            assertEquals(newEmail, foundUpdatedMember.get().getEmail());
        }

        @Test
        public void deleteMembers_successTest() {
            // given
            Members savedMembers = memberRepository.saveMembers(Members.of(List.of(member)));

            // when
            memberRepository.deleteMembers(savedMembers.getMemberIds());
            Members foundMembers = memberRepository.findMembers(MemberSearchCondition.builder()
                    .memberIds(savedMembers.getMemberIds())
                    .build());

            // then
            assertEquals(0, foundMembers.size());
        }
    }
}
