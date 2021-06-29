package dev.slohth.nickname.utils.vsc;

import com.mojang.authlib.GameProfile;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.user.User;
import org.bukkit.entity.Player;

public interface NMSHandler {

    void applyNickname(User user, Nick nick);

    GameProfile getProfile(Player player);

    void setProfile(GameProfile profile, Player player);

    void applyPackets(User user);

}
