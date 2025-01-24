package coz.weavon.context.member.application.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import coz.weavon.context.member.application.repository.MemberRepository;
import coz.weavon.context.member.domain.model.Members;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
            when(memberRepository.findMembersByCommand(emptyCommand)).thenReturn(members);

            // then
            assertThrows(BusinessException.class, () -> memberService.searchMember(emptyCommand));
        }
    }
}
