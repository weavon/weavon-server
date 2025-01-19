package coz.weavon.io.validator;

import coz.weavon.io.model.reqeuest.PageRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PaginationValidator implements ConstraintValidator<ValidPagination, PageRequest> {

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
            return false;
        }

        if (required && Objects.isNull(pageSize)) {
            return false;
        }

        if (Objects.nonNull(pageNo) && pageNo < 1) {
            return false;
        }

        if (Objects.nonNull(pageSize) && pageSize < 1) {
            return false;
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
