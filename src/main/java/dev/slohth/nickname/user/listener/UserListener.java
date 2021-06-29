package dev.slohth.nickname.user.listener;

import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.MojangUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class UserListener implements Listener {

    private final Nickname core;

    public UserListener(Nickname core) {
        Bukkit.getPluginManager().registerEvents(this, core);
        this.core = core;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        core.getUserManager().register(e.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        User user = core.getUserManager().get(e.getPlayer().getUniqueId());
        Bukkit.getScheduler().runTaskAsynchronously(core, user::save);
        core.getUserManager().remove(user);
    }

}
