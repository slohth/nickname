package dev.slohth.nickname.papi;

import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.user.User;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PAPIExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister() { return true; }

    @Override
    public String getIdentifier() { return "nickname"; }

    @Override
    public String getAuthor() { return "Slohth"; }

    @Override
    public String getVersion() { return "DEV-1.0"; }

    @Override
    public String onPlaceholderRequest(Player player, String id) {
        Nickname core = JavaPlugin.getPlugin(Nickname.class);
        User user = core.getUserManager().get(player.getUniqueId());
        switch (id) {
            case "name":
                if (user.isNicked()) return user.getNick().getName();
                return user.getTrueName();
            case "true_name":
                return user.getTrueName();
            case "rank_name":
                if (user.isNicked()) return user.getNick().getRank();
                return core.getLp().getUserManager().getUser(user.getUuid()).getPrimaryGroup();
            case "rank_prefix":
                if (user.isNicked()) return core.getLp().getGroupManager().getGroup(user.getNick().getRank()).getDisplayName();
                return core.getLp().getGroupManager().getGroup( core.getLp().getUserManager().getUser(user.getUuid()).getPrimaryGroup()).getDisplayName();
            case "true_rank_name":
                return core.getLp().getUserManager().getUser(user.getUuid()).getPrimaryGroup();
            case "true_rank_prefix":
                return core.getLp().getGroupManager().getGroup( core.getLp().getUserManager().getUser(user.getUuid()).getPrimaryGroup()).getDisplayName();
        }
        return null;
    }

}
