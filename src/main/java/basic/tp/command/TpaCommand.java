package basic.tp.command;

import basic.tp.BasicTp;
import basic.tp.misc.TeleportEffects;
import basic.tp.utility.LangUtil;
import basic.tp.utility.LuckPermsUtil;
import basic.tp.utility.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpaCommand implements CommandExecutor {

    private final BasicTp plugin;

    public TpaCommand(BasicTp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            MsgUtil.error(sender, LangUtil.get("only-players"));
            return true;
        }

        if (args.length < 1) {
            MsgUtil.error(sender, LangUtil.get("tpa-usage"));
            return true;
        }

        var target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MsgUtil.error(sender, LangUtil.get("player-not-found"));
            return true;
        }

        if (target.equals(player)) {
            MsgUtil.error(sender, LangUtil.get("tpa-self"));
            return true;
        }

        if (!plugin.getConfig().getBoolean("allow-cross-world", true)) {
            if (!target.getWorld().equals(player.getWorld())) {
                MsgUtil.error(sender, LangUtil.get("tpa-cross-world"));
                return true;
            }
        }

        if (plugin.getRequestManager().isIgnored(target.getUniqueId(), player.getUniqueId())) {
            MsgUtil.error(sender, LangUtil.get("tpa-blocked"));
            return true;
        }

        var cd = plugin.getCooldownUtil();
        if (cd.hasCooldown(player.getUniqueId()) && !LuckPermsUtil.hasPermission(player, "basictp.cooldown.bypass")) {
            MsgUtil.error(sender, LangUtil.get("cooldown", cd.remaining(player.getUniqueId()) / 1000 + 1));
            return true;
        }

        plugin.getRequestManager().createRequest(player.getUniqueId(), target.getUniqueId(), false);
        cd.apply(player.getUniqueId());

        MsgUtil.success(sender, LangUtil.get("tpa-sent", target.getName()));
        MsgUtil.info(target, LangUtil.get("tpa-incoming", player.getName()));
        MsgUtil.sendRequestButtons(target, player.getName());
        TeleportEffects.playRequestSound(target);

        return true;
    }
}
