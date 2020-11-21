package org.pk.rolebasedauth.models;

public enum UserRole {
    ROLE_USER, ROLE_ADMIN, ROLE_MODERATOR;

    public static UserRole getInstance(String role) {
        if (role == null) {
            return null;
        }
        for (UserRole r : UserRole.values()) {
            if (role.equals(r.name())) {
                return r;
            }
        }
        return null;
    }

    public static String[] ADMIN_ACCESS = {ROLE_ADMIN.name(), ROLE_MODERATOR.name()};
    public static String[] MODERATOR_ACCESS = {ROLE_USER.name(), ROLE_MODERATOR.name()};
}
