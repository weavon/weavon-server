package coz.weavon.core.project.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import coz.weavon.common.repository.QueryRepository;
import coz.weavon.core.project.infrastructure.model.ProjectEntity;
import coz.weavon.core.project.infrastructure.model.ProjectMemberRoleColumn;
import coz.weavon.core.project.infrastructure.model.QProjectEntity;
import coz.weavon.core.project.infrastructure.model.QProjectMemberEntity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProjectQueryRepository implements QueryRepository {

    private static final QProjectEntity project = QProjectEntity.projectEntity;

    private static final QProjectMemberEntity projectMember = QProjectMemberEntity.projectMemberEntity;

    private final JPAQueryFactory queryFactory;

    public List<ProjectEntity> findProjects(
            List<Long> projectIds,
            String projectName,
            List<Long> memberIds,
            List<ProjectMemberRoleColumn> memberRoles) {
        JPAQuery<ProjectEntity> query = queryFactory
                .selectFrom(project)
                .leftJoin(project.projectMembers, projectMember)
                .where(
                        in(project.projectId, projectIds),
                        like(project.name, projectName),
                        in(projectMember.id.memberId, memberIds),
                        in(projectMember.role, memberRoles));

        return query.fetch();
    }
}
