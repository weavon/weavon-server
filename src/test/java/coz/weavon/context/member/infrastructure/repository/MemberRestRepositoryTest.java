package coz.weavon.context.member.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.application.model.command.MemberSearchCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

class MemberRestRepositoryTest {

    @Mock
    private JPAQueryFactory jpaQueryFactory;

    @InjectMocks
    private MemberQueryRepository memberQueryRepository;

    @BeforeEach
    public void setUp() {
        memberQueryRepository = new MemberQueryRepository(jpaQueryFactory);
    }

    @Test
    public void findMembersByCommand_invalidConditionExceptionTest() {
        // given
        MemberRestRepository memberRestRepository = new MemberRestRepository(memberQueryRepository);

        // when
        MemberSearchCommand memberSearchCommand = MemberSearchCommand.builder().build();

        // then
        assertThrows(BusinessException.class, () -> memberRestRepository.findMembersByCommand(memberSearchCommand));
    }
}
