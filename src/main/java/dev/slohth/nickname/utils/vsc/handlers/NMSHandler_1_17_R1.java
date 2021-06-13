package dev.slohth.nickname.utils.vsc.handlers;

import com.mojang.authlib.GameProfile;
import dev.slohth.nickname.nick.Nick;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.vsc.NMSHandler;
import org.bukkit.entity.Player;

public class NMSHandler_1_17_R1 implements NMSHandler {

    @Override
    public void applyNickname(User user, Nick nick) {
//
//        String[] data = (nick.getSkinData() == null) ? MojangUtil.getData(nick.getSkin() == null ? "MHF_Steve" : nick.getSkin()) : nick.getSkinData();
//        CraftPlayer player = ((CraftPlayer) user.getPlayer());
//
//        GameProfile profile = new GameProfile(user.getUuid(), nick.getName());
//        profile.getProperties().put("textures", new Property("textures", data[0], data[1]));
//        user.setProfile(profile);
//
//        // luckperms
//
//        EntityPlayer ep = player.getHandle();
//        WorldServer worldServer = (WorldServer) ep.getWorld();
//
//        PacketPlayOutEntityDestroy destroyEntity = new PacketPlayOutEntityDestroy(ep.getId());
//        PacketPlayOutPlayerInfo removePlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.e, ep);
//        PacketPlayOutPlayerInfo addPlayer = new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.a, ep);
//        PacketPlayOutNamedEntitySpawn spawnEntity = new PacketPlayOutNamedEntitySpawn(ep);
//        PacketPlayOutHeldItemSlot helditem = new PacketPlayOutHeldItemSlot(ep.getInventory().k);
//        PacketPlayOutEntityMetadata metadata = new PacketPlayOutEntityMetadata(ep.getId(), ep.getDataWatcher(), true);
//        PacketPlayOutEntityStatus status = new PacketPlayOutEntityStatus(ep, (byte) 28);
//        PacketPlayOutRespawn respawn = new PacketPlayOutRespawn(worldServer.getDimensionManager(), worldServer.getDimensionKey(), BiomeManager.a(worldServer.getSeed()), ep.playerInteractManager.getGameMode(), ep.playerInteractManager.c(), worldServer.isDebugWorld(), worldServer.isFlatWorld(), true);
//        PacketPlayOutPosition position = new PacketPlayOutPosition(ep.locX(), ep.locY(), ep.locZ(), ep.yaw, ep.pitch, new HashSet(), 0);
//        PacketPlayOutEntityHeadRotation headrotation = new PacketPlayOutEntityHeadRotation(ep, (byte)MathHelper.d(ep.getHeadRotation() * 256.0f / 360.0f));
//
//        DedicatedPlayerList playerList = ((CraftServer) Bukkit.getServer()).getHandle();
//        Bukkit.getScheduler().runTask(JavaPlugin.getPlugin(Nickname.class), () -> {
//            int i = 0;
//            while (i < playerList.players.size()) {
//                EntityPlayer ep1 = playerList.players.get(i);
//                if (ep1.getBukkitEntity().canSee(ep.getBukkitEntity())) {
//                    PlayerConnection con = ep1.playerConnection;
//                    con.sendPacket(removePlayer);
//                    con.sendPacket(addPlayer);
//                    if (ep1.getId() != ep.getId()) {
//                        con.sendPacket(destroyEntity);
//                        con.sendPacket(spawnEntity);
//                        con.sendPacket(headrotation);
//                    }
//                    int j = 0;
//                    while (j < EnumItemSlot.values().length) {
//                        EnumItemSlot slot = EnumItemSlot.values()[j];
//                        ItemStack itemstack = ep.getEquipment(slot);
//                        if (!itemstack.isEmpty()) {
//                            ArrayList<Pair<EnumItemSlot, ItemStack>> list = Lists.newArrayListWithCapacity(1);
//                            list.add(Pair.of(slot, itemstack));
//                            con.sendPacket(new PacketPlayOutEntityEquipment(ep.getId(), list));
//                        }
//                        ++j;
//                    }
//                }
//                ++i;
//            }
//
//            PlayerConnection con = ep.b;
//            con.sendPacket(respawn);
//            con.sendPacket(position);
//            con.sendPacket(helditem);
//            con.sendPacket(metadata);
//            con.sendPacket(status);
//            ep.updateAbilities();
//            ep.triggerHealthUpdate();
//            ep.updateInventory(ep.defaultContainer);
//            ep.getBukkitEntity().recalculatePermissions();
//        });
    }

    @Override
    public GameProfile getProfile(Player player) {
        return null;
    }

    @Override
    public void setProfile(GameProfile profile, Player player) {
        //
    }

}
