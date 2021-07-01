package dev.slohth.nickname.command;

import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.CC;
import dev.slohth.nickname.utils.framework.command.Args;
import dev.slohth.nickname.utils.framework.command.Command;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Test {

    private final Nickname core;

    public Test(Nickname core) { this.core = core; core.getFramework().registerCommands(this); }

    @Command(name = "nick", permission = "nick.use")
    public void nickCommand(Args cmd) {
        Player player = cmd.getPlayer();
        User user = core.getUserManager().get(player.getUniqueId());

        if (user.isNicked()) {
            user.msg("&cYou are already nicked!");
            return;
        }

        Nick nick = new Nick(core, user);
        nick.openRankSelectionMenu();
    }

//    @Command(name = "test")
//    public void testCommand(Args cmd) {
//        Player player = core.getUserManager().get(cmd.getPlayer().getUniqueId()).getPlayer();
//        if (player.hasPermission("tab.sort.default")) player.sendMessage(ChatColor.AQUA + "default");
//        if (player.hasPermission("tab.sort.helper")) player.sendMessage(ChatColor.AQUA + "helper");
//        if (player.hasPermission("tab.sort.mod")) player.sendMessage(ChatColor.AQUA + "mod");
//        if (player.hasPermission("tab.sort.admin")) player.sendMessage(ChatColor.AQUA + "admin");
//        if (player.hasPermission("rocketplaceholder.default")) player.sendMessage(ChatColor.DARK_AQUA + "default");
//        if (player.hasPermission("rocketplaceholder.helper")) player.sendMessage(ChatColor.DARK_AQUA + "helper");
//        if (player.hasPermission("rocketplaceholder.mod")) player.sendMessage(ChatColor.DARK_AQUA + "mod");
//        if (player.hasPermission("rocketplaceholder.admin")) player.sendMessage(ChatColor.DARK_AQUA + "admin");
//    }

    @Command(name = "unnick", permission = "nick.remove")
    public void unnickCommand(Args cmd) {
        Player player = cmd.getPlayer();
        User user = core.getUserManager().get(player.getUniqueId());
        if (user.isNicked()) {
            user.getNick().remove();
            user.msg("&aYou are no longer nicked!");
        } else {
            user.msg("&cYou are not nicked!");
        }
    }

}
