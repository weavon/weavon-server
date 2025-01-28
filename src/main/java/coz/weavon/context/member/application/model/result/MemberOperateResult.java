package coz.weavon.context.member.application.model.result;

import coz.weavon.context.member.domain.model.Members;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberOperateResult {

    private Members createdMembers;

    private Members updatedMembers;
}
