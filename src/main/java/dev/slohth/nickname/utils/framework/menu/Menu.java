package dev.slohth.nickname.utils.framework.menu;

import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.utils.CC;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public abstract class Menu implements Listener {

    private final Nickname core;

    private final Inventory inventory;
    private final int rows;
    private final String title;

    private Map<Integer, ItemStack> items = new HashMap<>();
    private Map<Integer, Button> buttons = new HashMap<>();

    public Menu(Nickname core, String title, int rows) {
        this.core = core; this.title = CC.trns(title); this.rows = rows;
        this.inventory = Bukkit.createInventory(null, rows * 9, this.title);
        Bukkit.getPluginManager().registerEvents(this, core);
    }

    public void applyMenuPattern(ShapedMenuPattern pattern) {
        this.items.putAll(pattern.getItems());
        this.buttons.putAll(pattern.getButtons());
        pattern.clear();
    }

    public void setItem(int slot, ItemStack item) { items.put(slot, item); }

    public void setButton(int slot, Button button) { buttons.put(slot, button); }

    public Menu build() {
        for (int i : items.keySet()) inventory.setItem(i, items.get(i));
        for (int i : buttons.keySet()) inventory.setItem(i, buttons.get(i).getIcon());
        return this;
    }

    public void open(Player player) { player.openInventory(this.inventory); }

    public void update(Player player) { player.closeInventory(); this.open(player); }

    public void updateAll() { for (HumanEntity e : this.inventory.getViewers()) { e.closeInventory(); this.open((Player) e); } }

    public void close() {
        InventoryCloseEvent.getHandlerList().unregister(this);
        this.items.clear(); this.buttons.clear();
        Bukkit.getScheduler().runTaskLater(core, () -> { for (HumanEntity e : this.inventory.getViewers()) e.closeInventory(); }, 1);
    }

    @EventHandler
    public void onInteract(InventoryClickEvent e) {
        if (!this.inventory.getViewers().contains(e.getWhoClicked()) || !(e.getWhoClicked() instanceof Player)) return;
        e.setCancelled(true);
        if (buttons.containsKey(e.getSlot())) buttons.get(e.getSlot()).clicked((Player) e.getWhoClicked());
    }

}
