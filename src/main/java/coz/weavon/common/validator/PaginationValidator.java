package coz.weavon.common.validator;

import coz.weavon.common.exception.ClientException;
import coz.weavon.common.io.PageRequest;
import coz.weavon.constant.Label;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PaginationValidator implements ConstraintValidator<ValidPagination, PageRequest> {

    private static final String MSG_VLD_REQ_REQ = "message.validation.required";

    private static final String MSG_VLD_REQ_MIN = "message.validation.min";

    private boolean required;

    @Override
    public void initialize(ValidPagination constraintAnnotation) {
        this.required = constraintAnnotation.required();
    }

    @Override
    public boolean isValid(PageRequest request, ConstraintValidatorContext constraintValidatorContext) {
        Integer pageNo = request.getPageNo();
        Integer pageSize = request.getPageSize();

        if (required && Objects.isNull(pageNo)) {
            throw new ClientException(MSG_VLD_REQ_REQ, Label.Common.PAGE_NO);
        }

        if (required && Objects.isNull(pageSize)) {
            throw new ClientException(MSG_VLD_REQ_REQ, Label.Common.PAGE_SIZE);
        }

        if (Objects.nonNull(pageNo) && pageNo < 1) {
            throw new ClientException(MSG_VLD_REQ_MIN, Label.Common.PAGE_NO, "1");
        }

        if (Objects.nonNull(pageSize) && pageSize < 1) {
            throw new ClientException(MSG_VLD_REQ_MIN, Label.Common.PAGE_SIZE, "1");
        }

        if (Objects.isNull(pageNo)) {
            request.setPageNo(1);
        }

        if (Objects.isNull(pageSize)) {
            request.setPageSize(10);
        }

        return true;
    }
}
