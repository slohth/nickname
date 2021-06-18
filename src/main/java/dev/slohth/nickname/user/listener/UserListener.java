package dev.slohth.nickname.user.listener;

import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.MojangUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserListener implements Listener {

    private final Nickname core;

    public UserListener(Nickname core) {
        Bukkit.getPluginManager().registerEvents(this, core);
        this.core = core;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(core, () -> core.getUserManager().register(e.getPlayer().getUniqueId()), 1);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        User user = core.getUserManager().get(e.getPlayer().getUniqueId());
        Bukkit.getScheduler().runTaskAsynchronously(core, () -> {
            try {
                ResultSet rs = core.getSqlManager().execQuery("SELECT * FROM `nicknames` WHERE `uuid` = '" + e.getPlayer().getUniqueId().toString() + "';");
                String name = user.getNick().getName();
                String[] data = user.getNick().getSkinData() == null ? MojangUtil.getData(user.getNick().getSkin()) : user.getNick().getSkinData();
                String skin = data[0] + ":" + data[1];
                String rank = user.getNick().getRank();
                if (rs.next()) {
                    if (user.getNick() == null) {
                        core.getSqlManager().execStatement("DELETE FROM `active-nicknames` WHERE uuid = " + user.getUuid().toString() + " ;");
                    } else {
                        core.getSqlManager().execStatement("UPDATE `active-nicknames` SET name = " + name + ", skin = " + skin + ", rank = " + rank + " WHERE " +
                                "uuid = " + user.getUuid().toString() + " ;");
                    }
                } else {
                    if (user.getNick() != null) {
                        core.getSqlManager().execStatement("INSERT INTO `active-nicknames` (uuid, name, skin, rank) VALUES (" +
                                user.getUuid().toString() + ", " + name + ", " + skin + ", " + rank + ");");
                    }
                }
            } catch (SQLException x) { x.printStackTrace(); }
        });
    }

}
