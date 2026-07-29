package basic.tp.command;

import basic.tp.BasicTp;
import basic.tp.utility.LangUtil;
import basic.tp.utility.LuckPermsUtil;
import basic.tp.utility.MsgUtil;
import basic.tp.utility.TeleportUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpHereCommand implements CommandExecutor {

    private final BasicTp plugin;

    public TpHereCommand(BasicTp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            MsgUtil.error(sender, LangUtil.get("only-players"));
            return true;
        }

        if (!LuckPermsUtil.hasPermission(player, "basictp.admin")) {
            MsgUtil.error(sender, LangUtil.get("no-permission"));
            return true;
        }

        if (args.length < 1) {
            MsgUtil.error(sender, LangUtil.get("tphere-usage"));
            return true;
        }

        var target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MsgUtil.error(sender, LangUtil.get("player-not-found"));
            return true;
        }

        TeleportUtil.teleport(target, player.getLocation(), plugin.hasParticleEffects());
        MsgUtil.success(sender, LangUtil.get("tphere-done", target.getName()));
        MsgUtil.info(target, LangUtil.get("tphere-notify", player.getName()));

        return true;
    }
}
