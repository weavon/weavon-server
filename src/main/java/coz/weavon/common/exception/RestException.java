package coz.weavon.common.exception;

import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Getter
public class RestException extends RuntimeException {

    private final String messageCode;

    private final List<String> labelCodes;

    public RestException(String message, String messageCode) {
        super(message);
        this.messageCode = messageCode;
        this.labelCodes = Collections.emptyList();
    }

    public RestException(String message, String messageCode, String... labelCodes) {
        super(message);
        this.messageCode = messageCode;
        this.labelCodes = List.of(labelCodes);
    }

    public boolean hasMessageCode() {
        return StringUtils.hasText(messageCode);
    }

    public boolean hasLabelCodes() {
        return !CollectionUtils.isEmpty(labelCodes);
    }
}
