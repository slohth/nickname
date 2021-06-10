package dev.slohth.nickname.user.manager;

import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.user.User;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class UserManager {

    private final Nickname core;
    private final Set<User> users = new HashSet<>();

    public UserManager(Nickname core) { this.core = core; }

    public void register(UUID uuid) {
        if (get(uuid) != null) return;
        users.add(new User(uuid, core));
    }

    public User get(UUID uuid) {
        for (User user : users) if (user.getUuid() == uuid) return user;
        return null;
    }

}
