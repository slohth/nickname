package dev.slohth.nickname.utils.vsc.handlers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.MojangUtil;
import dev.slohth.nickname.utils.vsc.NMSHandler;
import net.minecraft.server.v1_14_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class NMSHandler_1_14_R1 implements NMSHandler {

    @Override
    public void applyNickname(User user, Nick nick) {

        String[] data = MojangUtil.getData(nick.getSkin());
        CraftPlayer player = ((CraftPlayer) user.getPlayer());
        PlayerConnection connection = player.getHandle().playerConnection;

        GameProfile profile = new GameProfile(user.getUuid(), nick.getName());
        profile.getProperties().put("textures", new Property("textures", data[0], data[1]));
        user.setProfile(profile);

        // luckperms

        boolean fly = user.getPlayer().getAllowFlight();

        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, player.getHandle()));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, player.getHandle()));

        Location location = user.getPlayer().getLocation().clone();
        user.getPlayer().spigot().respawn();
        user.getPlayer().teleport(location);
        user.getPlayer().updateInventory();

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(user.getPlayer()); p.showPlayer(user.getPlayer());
        }

        user.getPlayer().setAllowFlight(fly);
    }

}
