package coz.weavon.context.member.infrastructure.repository;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.common.application.model.exception.BusinessException;
import coz.weavon.context.member.infrastructure.model.condition.MemberSearchCondition;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class MemberQueryRepositoryTest {

    @Mock
    private JPAQueryFactory jpaQueryFactory;

    @Test
    public void findAllByCondition_invalidCondition_exceptionTest() {
        // given
        MemberQueryRepository memberQueryRepository = new MemberQueryRepository(jpaQueryFactory);

        // when
        MemberSearchCondition emptyCondition = MemberSearchCondition.builder().build();

        // then
        assertThrows(BusinessException.class, () -> memberQueryRepository.findAllByCondition(emptyCondition));
    }
}
