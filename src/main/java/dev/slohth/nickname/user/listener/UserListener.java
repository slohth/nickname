package dev.slohth.nickname.user.listener;

import dev.slohth.nickname.Nickname;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

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

}
