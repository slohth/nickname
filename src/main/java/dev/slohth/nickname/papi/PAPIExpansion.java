package dev.slohth.nickname.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

public class PAPIExpansion extends PlaceholderExpansion {

    @Override
    public boolean canRegister() { return true; }

    @Override
    public String getIdentifier() { return "Nickname"; }

    @Override
    public String getAuthor() { return "Slohth"; }

    @Override
    public String getVersion() { return "DEV-1.0"; }

    @Override
    public String onPlaceholderRequest(Player player, String id) {
        switch (id) {
            case "placeholder":
                return "item";
        }
        return null;
    }

}
