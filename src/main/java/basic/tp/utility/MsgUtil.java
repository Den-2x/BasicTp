package basic.tp.utility;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class MsgUtil {

    private static final Component PREFIX = Component.text("[BasicTp] ", NamedTextColor.GRAY);

    private MsgUtil() {
    }

    public static void send(CommandSender sender, Component message) {
        sender.sendMessage(PREFIX.append(message));
    }

    public static void success(CommandSender sender, String message) {
        sender.sendMessage(PREFIX.append(Component.text(message, NamedTextColor.GREEN)));
    }

    public static void error(CommandSender sender, String message) {
        sender.sendMessage(PREFIX.append(Component.text(message, NamedTextColor.RED)));
    }

    public static void warn(CommandSender sender, String message) {
        sender.sendMessage(PREFIX.append(Component.text(message, NamedTextColor.YELLOW)));
    }

    public static void info(CommandSender sender, String message) {
        sender.sendMessage(PREFIX.append(Component.text(message, NamedTextColor.AQUA)));
    }

    public static void sendRequestButtons(Player target, String requesterName) {
        var accept = Component.text(LangUtil.get("button-accept") + " ", NamedTextColor.GREEN, TextDecoration.BOLD)
            .clickEvent(ClickEvent.runCommand("/tpaccept"))
            .hoverEvent(HoverEvent.showText(Component.text(LangUtil.get("button-accept-hover"), NamedTextColor.GREEN)));

        var deny = Component.text(LangUtil.get("button-deny"), NamedTextColor.RED, TextDecoration.BOLD)
            .clickEvent(ClickEvent.runCommand("/tpdeny"))
            .hoverEvent(HoverEvent.showText(Component.text(LangUtil.get("button-deny-hover"), NamedTextColor.RED)));

        target.sendMessage(PREFIX.append(accept).append(deny));
    }
}
