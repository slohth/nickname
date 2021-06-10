package dev.slohth.nickname.nick;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.CC;
import dev.slohth.nickname.utils.ColoredString;
import dev.slohth.nickname.utils.ItemBuilder;
import dev.slohth.nickname.utils.MojangUtil;
import dev.slohth.nickname.utils.framework.menu.Button;
import dev.slohth.nickname.utils.framework.menu.Menu;
import dev.slohth.nickname.utils.framework.menu.ShapedMenuPattern;
import net.luckperms.api.model.group.Group;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PacketPlayOutRespawn;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import net.minecraft.server.v1_8_R3.WorldSettings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Collections;
import java.util.Set;

public class Nick {

    private final Nickname core;

    private User user;
    private String rank;
    private String name;
    private String skin;
    private GameProfile profile;

    public Nick(Nickname core, User user) {
        this.core = core; this.user = user;
    }

    public void apply() {
        String[] data = MojangUtil.getData(this.skin);
        CraftPlayer player = ((CraftPlayer) user.getPlayer());
        PlayerConnection connection = player.getHandle().playerConnection;

        this.profile = new GameProfile(user.getUuid(), this.name);
        profile.getProperties().put("textures", new Property("textures", data[0], data[1]));
        this.user.setProfile(this.profile);

        // luckperms


        this.sendUpdatePackets();
    }

    public void openRankSelectionMenu() {
        Menu menu = new Menu(core, "Select a rank!", 4) {};
        ShapedMenuPattern pattern = new ShapedMenuPattern(
                new char[] { '&', ' ', '&', ' ', '&', ' ', '&', ' ', '&' },
                new char[] { '&', ' ', '&', ' ', '&', ' ', '&', ' ', '&' },
                new char[] { '&', ' ', '&', ' ', '&', ' ', '&', ' ', '&' },
                new char[] { '&', ' ', '&', ' ', '&', ' ', '&', ' ', '&' }
        );
        pattern.set('&', new ItemBuilder(Material.STAINED_GLASS_PANE, 7).name(" ").build());

        Set<Group> groups = core.getLp().getGroupManager().getLoadedGroups();
        for (Group group : groups) {
            ChatColor color = ColoredString.getColors(group.getDisplayName()).get(0);

            ItemStack stack = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            meta.setColor(ColoredString.getColor(color));
            meta.setDisplayName(group.getDisplayName());
            meta.setLore(Collections.singletonList(CC.trns("&7Click to select this rank!")));
            stack.setItemMeta(meta);

            Button button = new Button() {
                @Override
                public void clicked(Player player) { rank = group.getName(); menu.close(); }
            };

            menu.setButton(menu.firstEmpty(), button);
        }


        menu.applyMenuPattern(pattern);
    }

    private void sendUpdatePackets() {
        CraftPlayer cp = ((CraftPlayer) this.user.getPlayer());
        PlayerConnection connection = cp.getHandle().playerConnection;
        boolean fly = user.getPlayer().getAllowFlight();

        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, cp.getHandle()));
        connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, cp.getHandle()));

        Location location = user.getPlayer().getLocation().clone();
        connection.sendPacket(new PacketPlayOutRespawn(
                cp.getHandle().getWorld().worldProvider.getDimension(),
                cp.getHandle().getWorld().getDifficulty(), cp.getHandle().getWorld().worldData.getType(),
                WorldSettings.EnumGamemode.getById(cp.getGameMode().getValue())
        ));
        user.getPlayer().teleport(location);
        user.getPlayer().updateInventory();

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.hidePlayer(user.getPlayer()); p.showPlayer(user.getPlayer());
        }

        user.getPlayer().setAllowFlight(fly);
    }

}
