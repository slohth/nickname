package dev.slohth.nickname.user;

import com.mojang.authlib.GameProfile;
import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.utils.CC;
import dev.slohth.nickname.utils.framework.menu.Menu;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.query.QueryOptions;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private final UUID uuid;
    private final Nickname core;
    private final String trueName;
    private final net.luckperms.api.model.user.User user;

    private final GameProfile profile;

    private Nick nick;

    private String[] ranks;

    public User(UUID uuid, Nickname core) {
        this.uuid = uuid; this.core = core; this.profile = core.getNms().getProfile(Bukkit.getPlayer(uuid));
        this.trueName = Bukkit.getPlayer(uuid).getName();
        this.user = core.getLp().getUserManager().getUser(this.uuid);

        try { this.load(); } catch (SQLException e) { e.printStackTrace(); }
    }

    public void load() throws SQLException {

        List<String> groups = new ArrayList<>();
        for (Group r : user.getInheritedGroups(QueryOptions.defaultContextualOptions())) { groups.add(r.getName()); }
        groups.add(user.getPrimaryGroup());

        this.ranks = groups.toArray(new String[0]);

        ResultSet rs = core.getSqlManager().execQuery("SELECT * FROM `active-nicknames` WHERE `uuid` = '" + this.uuid.toString() + "';");
        if (rs.next()) {
            String name = rs.getString("name");
            String[] skin = rs.getString("skin").split(":");
            String nickedRank = rs.getString("rank");
            this.nick = new Nick(this.core, this, name, skin, nickedRank);
        }
    }

    public void save() {

    }

    public void msg(String... in) {
        for (String s : in) if (s != null) this.getPlayer().sendMessage(CC.trns(s));
    }

    public void openMenu(Menu menu) {
        menu.open(this.getPlayer());
    }

    public UUID getUuid() { return this.uuid; }

    public net.luckperms.api.model.user.User getUser() {
        return user;
    }

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

    public String[] getRanks() {
        return ranks;
    }
}
