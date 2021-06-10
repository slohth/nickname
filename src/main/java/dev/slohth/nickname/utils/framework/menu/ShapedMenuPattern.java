package dev.slohth.nickname.utils.framework.menu;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ShapedMenuPattern {

    private final LinkedList<Character> icons = new LinkedList<>();
    private final Map<Integer, ItemStack> items = new HashMap<>();
    private final Map<Integer, Button> buttons = new HashMap<>();

    public ShapedMenuPattern(char[]... rows) {
        this.setPattern(rows);
    }

    public void set(char c, ItemStack item) {
        for (char icon : icons) if (icon == c) items.put(icons.indexOf(c), item);
    }

    public void set(char c, Button button) {
        for (char icon : icons) if (icon == c) buttons.put(icons.indexOf(c), button);
    }

    public void setPattern(char[]... rows) { for (char[] row : rows) for (char c : row) icons.add(c); }

    public void clear() { icons.clear(); items.clear(); buttons.clear(); }

    public Map<Integer, ItemStack> getItems() { return this.items; }

    public Map<Integer, Button> getButtons() { return this.buttons; }
}
