package ru.virarnd.coderslist.models.overflow;

import com.google.gson.annotations.SerializedName;

import ru.virarnd.coderslist.models.users.User;

public class OverflowUser {
    @SerializedName("display_name")
    private String name;
    @SerializedName("profile_image")
    private String avatar;
    @SerializedName("account_id")
    private long userId;

    public User mapToUser() {
        return new User(name, avatar, userId);
    }

}
