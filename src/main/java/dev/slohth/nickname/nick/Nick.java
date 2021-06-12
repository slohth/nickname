package dev.slohth.nickname.nick;

import dev.slohth.nickname.Nickname;
import dev.slohth.nickname.nick.random.RandomNick;
import dev.slohth.nickname.user.User;
import dev.slohth.nickname.utils.CC;
import dev.slohth.nickname.utils.ColoredString;
import dev.slohth.nickname.utils.ItemBuilder;
import dev.slohth.nickname.utils.MojangUtil;
import dev.slohth.nickname.utils.framework.menu.Button;
import dev.slohth.nickname.utils.framework.menu.Menu;
import dev.slohth.nickname.utils.framework.menu.ShapedMenuPattern;
import net.luckperms.api.model.group.Group;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
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

    public Nick(Nickname core, User user) {
        this.core = core; this.user = user;
    }

    public void apply() { core.getNms().applyNickname(this.user, this); }

    public void openRankSelectionMenu() {
        Menu menu = new Menu(core, "Select a rank!", 4) {};
        ShapedMenuPattern pattern = new ShapedMenuPattern(
                new char[] { '&', ' ', '&', ' ', '&', ' ', '&', ' ', '&' },
                new char[] { '&', ' ', '&', ' ', '&', ' ', '&', ' ', '&' },
                new char[] { '&', ' ', '&', ' ', '&', ' ', '&', ' ', '&' },
                new char[] { '&', ' ', '&', ' ', '&', ' ', '&', ' ', '&' }
        );
        pattern.set('&', new ItemBuilder(Material.STAINED_GLASS_PANE, 7).name(" ").build());
        menu.applyMenuPattern(pattern);

        Set<Group> groups = core.getLp().getGroupManager().getLoadedGroups();
        for (Group group : groups) {
            ChatColor color = ColoredString.getColorOf(group.getDisplayName());

            ItemStack stack = new ItemStack(Material.LEATHER_CHESTPLATE);
            LeatherArmorMeta meta = (LeatherArmorMeta) stack.getItemMeta();
            meta.setColor(ColoredString.getColor(color));
            meta.setDisplayName(CC.trns(group.getDisplayName()));
            meta.setLore(Collections.singletonList(CC.trns("&7Click to select this rank!")));
            stack.setItemMeta(meta);

            Button button = new Button() {
                @Override
                public void clicked(Player player) {
                    rank = group.getName(); menu.close();
                    Bukkit.getScheduler().runTaskLater(core, () -> openNameSelectionMenu(RandomNick.NAME.get()[0]), 1);
                }
            }.setIcon(stack);
            menu.setButton(menu.firstEmpty(), button);
        }

        this.user.openMenu(menu.build());
    }

    public void openNameSelectionMenu(String s) {
        Menu menu = new Menu(core, "Select a name!", 5) {};
        this.name = s;

        ShapedMenuPattern pattern = new ShapedMenuPattern(
                new char[] { '&', '&', ' ', ' ', '*', ' ', ' ', '&', '&' },
                new char[] { '&', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '&' },
                new char[] { '&', ' ', 'M', ' ', ' ', ' ', 'R', ' ', '&' },
                new char[] { '&', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '&' },
                new char[] { '&', '&', ' ', 'N', ' ', 'Y', ' ', '&', '&' }
        );

        pattern.set('&', new ItemBuilder(Material.STAINED_GLASS_PANE, 7).name(" ").build());
        pattern.set('*', new ItemBuilder(Material.BOOK).name("&d&l(!) &dInformation").lore(
                "&7Here you can select a name to nick as",
                "&7You will appear as this username in-game",
                " ",
                "&7Your name must fit this criteria:",
                "&8* &7Username must already exist on a &dvalid account",
                "&8* &7Username must be &d16 characters or under",
                "&8* &7Account the name belongs to must not have &dplayed before",
                "",
                "&7You can click the nether star to pick",
                "&7a random name that fit these requirements",
                " ",
                "&7To confirm, press the &agreen &7dye"
        ).glow().build());

        pattern.set('M', new Button() {
            @Override
            public void clicked(Player player) {
                AnvilGUI.Builder builder = new AnvilGUI.Builder().plugin(core).text("Enter a name").title("Select a name!")
                        .itemLeft(new ItemBuilder(Material.NAME_TAG).name("&d&l(!) &dEnter a name!").build())
                        .onClose(p -> user.openMenu(menu)).onComplete((p, text) -> {
                            if (MojangUtil.isValidProfile(text) && !Bukkit.getOfflinePlayer(text).hasPlayedBefore()) {
                                name = text; Bukkit.getScheduler().runTaskLater(core, Nick.this::openSkinSelectionMenu, 1);
                                return AnvilGUI.Response.close();
                            } else {
                                return AnvilGUI.Response.text("Invalid name!");
                            }
                        });
                builder.open(player);
            }
        }.setIcon(new ItemBuilder(Material.NAME_TAG).name("&d&l(!) &dSelect a custom name").lore("&7Click to enter a name!").build()));

        pattern.set('R', new Button() {
            @Override
            public void clicked(Player player) {
                name = RandomNick.NAME.get()[0]; menu.close();
                openNameSelectionMenu(name);
            }
        }.setIcon(new ItemBuilder(Material.NETHER_STAR).name("&d&l(!) &dSelect a random name").lore("&7Click to generate a name!").build()));

        pattern.set('N', new Button() {
            @Override
            public void clicked(Player player) {
                menu.close();
            }
        }.setIcon(new ItemBuilder(Material.INK_SACK, 1).name("&cCancel").build()));

        pattern.set('Y', new Button() {
            @Override
            public void clicked(Player player) {
                menu.close();
                // open skin menu
            }
        }.setIcon(new ItemBuilder(Material.INK_SACK, 10).name("&aConfirm").build()));

        menu.applyMenuPattern(pattern);

        menu.setItem(22, new ItemBuilder(Material.SKULL_ITEM, 3).name("&f" + this.name).lore("&7This is your current name!").build());

        this.user.openMenu(menu.build());
    }

    public void openSkinSelectionMenu() {

    }

    public String getName() {
        return name;
    }

    public String getRank() {
        return rank;
    }

    public String getSkin() {
        return skin;
    }
}
