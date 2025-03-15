package coz.weavon.core.shared.presentation.model.reqeuest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PageRequest {

    private Integer pageNo;

    private Integer pageSize;
}
