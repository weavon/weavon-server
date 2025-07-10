package coz.weavon.constant;

public class Message {

    public static class Common {

        public static final String BAD_USER_REQUEST = "message.common.BAD_USER_REQUEST";
    }

    public static class Validation {

        public static final String IS_REQUIRED = "message.validation.IS_REQUIRED";

        public static final String MUST_BE_GREATER = "message.validation.MUST_BE_GREATER";

        public static final String RANGE_MISS_START_END_DATE = "message.validation.RANGE_MISS_START_END_DATE";

        public static final String INVALID_RANGE = "message.validation.INVALID_RANGE";

        public static final String INVALID_SEARCH_CONDITION = "message.validation.INVALID_SEARCH_CONDITION";

        public static final String IS_NOT_SINGLE_RESULT = "message.validation.IS_NOT_SINGLE_RESULT";
    }

    public static class Authentication {

        public static final String SIGN_UP_FAILED = "message.authentication.SIGN_UP_FAILED";

        public static final String UNSUPPORTED_REGISTRATION_PROVIDER =
                "message.authentication.UNSUPPORTED_REGISTRATION_PROVIDER";

        public static final String USER_NOT_FOUND = "message.authentication.USER_NOT_FOUND";

        public static final String USER_LOGGED_IN = "message.authentication.USER_LOGGED_IN";

        public static final String AUTHENTICATION_FAILED = "message.authentication.AUTHENTICATION_FAILED";

        public static final String NOT_AUTHENTICATED = "message.authentication.NOT_AUTHENTICATED";

        public static final String USERNAME_DUPLICATE = "message.authentication.USERNAME_DUPLICATE";

        public static final String JOIN_SUCCESS = "message.authentication.JOIN_SUCCESS";
    }

    public static class User {

        public static final String NO_OPERATION_USER_TARGET = "message.user.NO_OPERATION_USER_TARGET";
    }
}
