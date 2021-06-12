package dev.slohth.nickname.user;

import com.mojang.authlib.GameProfile;
import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.utils.framework.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private final Nickname core;
    private final net.luckperms.api.model.user.User user;

    private GameProfile profile;

    private Nick nick;
    private String rank;

    public User(UUID uuid, Nickname core) {
        this.uuid = uuid; this.core = core; this.profile = ((CraftPlayer) this.getPlayer()).getProfile();
        this.user = core.getLp().getUserManager().getUser(this.uuid);
    }

    public void load() {

    }

    public void openMenu(Menu menu) {
        menu.open(this.getPlayer());
    }

    public UUID getUuid() { return this.uuid; }

    public Player getPlayer() { return Bukkit.getPlayer(this.uuid); }

    public void setProfile(GameProfile profile) {
        try {
            Field field = ((CraftPlayer) this.getPlayer()).getHandle().getClass().getSuperclass().getDeclaredField(core.getFieldName());
            field.setAccessible(true);
            field.set(((CraftPlayer) this.getPlayer()).getHandle(), profile);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
