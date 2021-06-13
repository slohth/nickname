package dev.slohth.nickname.user;

import com.mojang.authlib.GameProfile;
import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.utils.CC;
import dev.slohth.nickname.utils.framework.menu.Menu;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class User {

    private final UUID uuid;
    private final Nickname core;
    private final String trueName;
    private final net.luckperms.api.model.user.User user;

    private final GameProfile profile;

    private Nick nick;
    private String rank;

    public User(UUID uuid, Nickname core) {
        this.uuid = uuid; this.core = core; this.profile = core.getNms().getProfile(Bukkit.getPlayer(uuid));
        this.trueName = Bukkit.getPlayer(uuid).getName();
        this.user = core.getLp().getUserManager().getUser(this.uuid);
    }

    public void load() {

    }

    public void msg(String... in) {
        for (String s : in) if (s != null) this.getPlayer().sendMessage(CC.trns(s));
    }

    public void openMenu(Menu menu) {
        menu.open(this.getPlayer());
    }

    public UUID getUuid() { return this.uuid; }

    public Player getPlayer() { return Bukkit.getPlayer(this.uuid); }

    public String getTrueName() {
        return trueName;
    }

    public void setProfile(GameProfile profile) {
        core.getNms().setProfile(profile, this.getPlayer());
    }

    public GameProfile getProfile() {
        return profile;
    }

    public Nick getNick() {
        return nick;
    }

    public void setNick(Nick nick) {
        this.nick = nick;
    }
}
