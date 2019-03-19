package ru.virarnd.coderslist.models.github;

import com.google.gson.annotations.SerializedName;

import ru.virarnd.coderslist.models.users.User;

public class GitUser {
    @SerializedName("login")
    private String name;
    @SerializedName("avatar_url")
    private String avatar;
    @SerializedName("id")
    private long userId;

    public User mapToUser() {
        return new User(name, avatar, userId);
    }
}
