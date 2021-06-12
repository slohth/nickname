package dev.slohth.nickname.utils;

import org.bukkit.ChatColor;
import org.bukkit.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ColoredString {

    public static ChatColor getColorOf(String text) {
        text = text.replace('§', '&');
        Map<ChatColor, Integer> colors = new HashMap<>();
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                ChatColor c = ChatColor.getByChar(text.charAt(i + 1));
                colors.put(c, colors.containsKey(c) ? colors.get(c) + 1 : 1);
            }
        }
        if (colors.isEmpty()) return ChatColor.WHITE;
        ChatColor c = colors.keySet().stream().findFirst().get();
        for (ChatColor color : colors.keySet()) if (colors.get(color) >= colors.get(c)) c = color;
        return c;
    }

    public static Color getColor(ChatColor chatColor) {
        switch (chatColor) {
            case AQUA:
                return Color.AQUA;
            case BLACK:
                return Color.BLACK;
            case BLUE:
                return Color.BLUE;
            case DARK_AQUA:
                return Color.TEAL;
            case DARK_BLUE:
                return Color.NAVY;
            case DARK_GRAY:
                return Color.SILVER;
            case GRAY:
                return Color.GRAY;
            case DARK_GREEN:
                return Color.GREEN;
            case GREEN:
                return Color.LIME;
            case DARK_PURPLE:
                return Color.PURPLE;
            case LIGHT_PURPLE:
                return Color.FUCHSIA;
            case DARK_RED:
            case RED:
                return Color.RED;
            case GOLD:
                return Color.ORANGE;
            case YELLOW:
                return Color.YELLOW;
            default:
                return Color.WHITE;
        }

    }
}
