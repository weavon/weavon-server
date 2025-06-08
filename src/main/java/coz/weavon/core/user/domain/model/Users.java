package coz.weavon.core.user.domain.model;

import java.util.List;
import java.util.Optional;
import org.springframework.util.CollectionUtils;

public record Users(List<User> value) {
    public static Users of(List<User> users) {
        return new Users(users);
    }

    public static Users of(User user) {
        return new Users(List.of(user));
    }

    public Optional<User> getSingleUser() {
        if (value.size() == 1) {
            return value.stream().findFirst();
        }
        return Optional.empty();
    }

    public Optional<User> getUserByUsername(String username) {
        return value.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public Optional<User> getUserByEmail(String email) {
        return value.stream().filter(user -> user.getEmail().equals(email)).findFirst();
    }

    public List<Long> getUserIds() {
        return value.stream().map(User::getUserId).toList();
    }

    public int size() {
        return value.size();
    }

    public boolean isEmpty() {
        return CollectionUtils.isEmpty(value);
    }
}
