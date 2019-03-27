package ru.virarnd.coderslist.models.users;

import java.util.Objects;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public final class User {
    private String name;
    private String avatar;
    private String site;
    @PrimaryKey private long userId;

    public User(String name, String avatar, String site, long userId) {
        this.name = name;
        this.avatar = avatar;
        this.site = site;
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getSite() {
        return site;
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
                Objects.equals(getAvatar(), user.getAvatar()) &&
                Objects.equals(getSite(), user.getSite());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAvatar(), getSite(), getUserId());
    }

}
