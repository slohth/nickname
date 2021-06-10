package dev.slohth.nickname.utils.framework.menu;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Button {

    private ItemStack icon;

    public abstract void clicked(Player player);

    public ItemStack getIcon() { return this.icon; }

}
