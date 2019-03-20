package ru.virarnd.coderslist.models.users;

import java.util.Objects;

public final class User {
    private String name;
    private String avatar;
    private long userId;

    public User() {
    }

    public User(String name, String avatar, long userId) {
        this.name = name;
        this.avatar = avatar;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public long getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getUserId() == user.getUserId() &&
                Objects.equals(getName(), user.getName()) &&
                Objects.equals(getAvatar(), user.getAvatar());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAvatar(), getUserId());
    }

}
