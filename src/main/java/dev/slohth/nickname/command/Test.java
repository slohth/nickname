package dev.slohth.nickname.command;

import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.framework.command.Args;
import dev.slohth.nickname.utils.framework.command.Command;
import org.bukkit.entity.Player;

public class Test {

    private final Nickname core;

    public Test(Nickname core) { this.core = core; core.getFramework().registerCommands(this); }

    @Command(name = "nick")
    public void nickCommand(Args cmd) {
        Player player = cmd.getPlayer();
        User user = core.getUserManager().get(player.getUniqueId());
        Nick nick = new Nick(core, user);
        nick.openRankSelectionMenu();
    }

}
