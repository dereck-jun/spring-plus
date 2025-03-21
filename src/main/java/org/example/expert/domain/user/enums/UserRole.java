package org.example.expert.domain.user.enums;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.common.exception.InvalidRequestException;

@Getter
@RequiredArgsConstructor
public enum UserRole {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER");

    private final String role;

    public static UserRole of(String role) {
        return Arrays.stream(UserRole.values())
                .filter(r -> r.name().equalsIgnoreCase(role))
                .findFirst()
                .orElseThrow(() -> new InvalidRequestException("유효하지 않은 UserRole"));
    }

    public static UserRole fromRole(String role) {
        return Arrays.stream(UserRole.values())
            .filter(r -> r.role.equalsIgnoreCase(role))
            .findFirst()
            .orElseThrow(() -> new InvalidRequestException("유효하지 않은 UserRole"));
    }
}
