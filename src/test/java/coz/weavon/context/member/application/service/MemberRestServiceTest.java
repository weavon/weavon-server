package coz.weavon.context.member.application.service;

import static org.assertj.core.api.BDDAssumptions.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.command.MemberOperateCommand;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.model.condition.MemberSearchCondition;
import coz.weavon.context.member.application.model.result.MemberOperateResult;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Member;
import coz.weavon.context.member.domain.model.Members;
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
import org.springframework.transaction.annotation.Transactional;

class MemberRestServiceTest {

    @Nested
    class MemberRestServiceMockTest {

        @Mock
        private MemberRepository memberRepository;

        @InjectMocks
        private MemberRestService memberService;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void searchMember_memberNotFound_nonSingleResultExceptionTest() {
            // given
            MemberSearchCommand emptyCommand =
                    MemberSearchCommand.builder().memberIds(List.of(-1L)).build();
            Members members = Members.of(Collections.emptyList());

            // when
            when(memberRepository.findMembers(any(MemberSearchCondition.class))).thenReturn(members);

            // then
            assertThrows(BusinessException.class, () -> memberService.searchMember(emptyCommand));
        }

        @Test
        public void operateMembers_noOperateTargetsExist_exceptionTest() {
            // given
            MemberOperateCommand noTargetOperateCommand =
                    MemberOperateCommand.builder().build();

            // then
            assertThrows(BusinessException.class, () -> memberService.operateMembers(noTargetOperateCommand));
        }
    }

    @Nested
    @SpringBootTest
    class MemberRestServiceSpringBootTest {

        @Autowired
        private MemberService memberService;

        @Test
        @Transactional
        public void operateMembers_createMembers_successTest() {
            // given
            Members members = Stream.iterate(1, i -> i + 1)
                    .limit(3)
                    .map(i -> Member.builder()
                            .username("user" + i)
                            .nickname("nickname" + i)
                            .email("user" + i + "@email.com")
                            .build())
                    .collect(Collectors.collectingAndThen(Collectors.toList(), Members::of));
            MemberOperateCommand operateCommand = MemberOperateCommand.ofCreateTargets(members);

            // when
            MemberOperateResult operateResult = memberService.operateMembers(operateCommand);

            // then
            Members createdMembers = operateResult.getCreatedMembers();
            assertEquals(3, createdMembers.size());
            createdMembers.getValue().forEach(member -> assertNotNull(member.getMemberId()));
        }

        @Test
        @Transactional
        public void operateMembers_updateMembers_successTest() {
            // given
            Members members = Members.of(List.of(Member.of("user1", "nickname1", "user1@email.com")));
            MemberOperateCommand createOperateCommand = MemberOperateCommand.ofCreateTargets(members);
            MemberOperateResult createOperateResult = memberService.operateMembers(createOperateCommand);
            Optional<Member> foundCreatedUser1 = createOperateResult.getCreatedMembers().getMemberByUsername("user1");
            assertTrue(foundCreatedUser1.isPresent());
            Member user1 = foundCreatedUser1.get();

            // when
            user1.setNickname("nickname1_updated");
            MemberOperateCommand updateOperateCommand = MemberOperateCommand.ofUpdateTargets(Members.of(List.of(user1)));
            MemberOperateResult updateOperateResult = memberService.operateMembers(updateOperateCommand);
            Members updatedMembers = updateOperateResult.getUpdatedMembers();
            Optional<Member> foundUpdatedUser1 = updatedMembers.getMemberByUsername("user1");
            assertTrue(foundUpdatedUser1.isPresent());

            // then
            assertEquals(1, updatedMembers.size());
            assertEquals("nickname1_updated", foundUpdatedUser1.get().getNickname());
        }

        @Test
        public void operateMembers_deleteMembers_successTest() {
            // given
            Members members = Members.of(List.of(Member.of("user1", "nickname1", "user1@email.com")));
            MemberOperateCommand createOperateCommand = MemberOperateCommand.ofCreateTargets(members);
            MemberOperateResult createOperateResult = memberService.operateMembers(createOperateCommand);

            // when
            MemberOperateCommand deleteOperateCommand = MemberOperateCommand.ofDeleteTargets(createOperateResult.getCreatedMembers().getMemberIds());
            memberService.operateMembers(deleteOperateCommand);

            MemberSearchCommand memberSearchCommand = MemberSearchCommand.builder().usernames(List.of("user1")).build();

            // then
            assertThrows(BusinessException.class, () -> memberService.searchMember(memberSearchCommand));
        }
    }
}
