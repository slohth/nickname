package dev.slohth.nickname.utils.vsc.handlers;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.MojangUtil;
import dev.slohth.nickname.utils.vsc.NMSHandler;
import dev.slohth.nickname.utils.vsc.VersionControl;
import net.minecraft.server.v1_9_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.CraftServer;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.HashSet;

public class NMSHandler_1_9_R1 implements NMSHandler {

    @Override
    public void applyNickname(User user, Nick nick) {

        String[] data = nick.getSkinData() == null ? MojangUtil.getData(nick.getSkin()) : nick.getSkinData();
        CraftPlayer player = ((CraftPlayer) user.getPlayer());

        GameProfile profile = new GameProfile(user.getUuid(), nick.getName());
        profile.getProperties().put("textures", new Property("textures", data[0], data[1]));
        user.setProfile(profile);

        // luckperms

        EntityPlayer ep = player.getHandle();
        WorldServer worldserver = (WorldServer) ep.getWorld();

        PacketPlayOutEntityDestroy destroyEntity = new PacketPlayOutEntityDestroy(ep.getId());
        PacketPlayOutPlayerInfo removePlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, ep);
        PacketPlayOutPlayerInfo addPlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, ep);
        PacketPlayOutNamedEntitySpawn spawnEntity = new PacketPlayOutNamedEntitySpawn(ep);
        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(ep.getId(), ep.getDataWatcher(), true);
        PacketPlayOutHeldItemSlot helditem = new PacketPlayOutHeldItemSlot(ep.inventory.itemInHandIndex);
        PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(ep, (byte) 28);
        PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(worldserver.worldProvider.getDimensionManager().getDimensionID(), worldserver.getDifficulty(), worldserver.getWorldData().getType(), ep.playerInteractManager.getGameMode());
        PacketPlayOutPosition position = new PacketPlayOutPosition(ep.locX, ep.locY, ep.locZ, ep.yaw, ep.pitch, new HashSet(), 0);
        PacketPlayOutEntityHeadRotation headrotation = new PacketPlayOutEntityHeadRotation(ep, (byte)MathHelper.d(ep.getHeadRotation() * 256.0f / 360.0f));

        DedicatedPlayerList playerList = ((CraftServer) Bukkit.getServer()).getHandle();
        Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Nickname.class), () -> {
            int i = 0;
            while (i < playerList.players.size()) {
                EntityPlayer ep1 = playerList.players.get(i);
                if (ep1.getBukkitEntity().canSee(ep.getBukkitEntity())) {
                    PlayerConnection con = ep1.playerConnection;
                    con.sendPacket(removePlayer);
                    con.sendPacket(addPlayer);
                    if (ep1.getId() != ep.getId()) {
                        con.sendPacket(destroyEntity);
                        con.sendPacket(spawnEntity);
                    }
                    con.sendPacket(headrotation);
                    int j = 0;
                    while (j < EnumItemSlot.values().length) {
                        EnumItemSlot slot = EnumItemSlot.values()[j];
                        ItemStack itemstack = ep.getEquipment(slot);
                        if (itemstack == null || itemstack.count <= 0) {
                            con.sendPacket(new PacketPlayOutEntityEquipment(ep.getId(), slot, itemstack));
                        }
                        ++j;
                    }
                }
                ++i;
            }

            PlayerConnection con = ep.playerConnection;
            con.sendPacket(metadata);
            con.sendPacket(respawn);
            con.sendPacket(position);
            con.sendPacket(helditem);
            con.sendPacket(status);
            ep.updateAbilities();
            ep.triggerHealthUpdate();
            ep.updateInventory(ep.activeContainer);
            ep.updateInventory(ep.defaultContainer);
        });
    }

    @Override
    public GameProfile getProfile(Player player) {
        return ((org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer) player).getProfile();
    }

    @Override
    public void setProfile(GameProfile profile, Player player) {
        try {
            Field field = ((CraftPlayer) player).getHandle().getClass().getSuperclass().getDeclaredField(VersionControl.fieldName);
            field.setAccessible(true);
            field.set(((CraftPlayer) player).getHandle(), profile);
            field.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
