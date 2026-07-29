package basic.tp.command;

import basic.tp.BasicTp;
import basic.tp.utility.LangUtil;
import basic.tp.utility.MsgUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TpIgnoreCommand implements CommandExecutor {

    private final BasicTp plugin;

    public TpIgnoreCommand(BasicTp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            MsgUtil.error(sender, LangUtil.get("only-players"));
            return true;
        }

        if (args.length < 1) {
            MsgUtil.error(sender, LangUtil.get("tpignore-usage"));
            return true;
        }

        var target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            MsgUtil.error(sender, LangUtil.get("player-not-found"));
            return true;
        }

        if (target.equals(player)) {
            MsgUtil.error(sender, LangUtil.get("tpignore-self"));
            return true;
        }

        var manager = plugin.getRequestManager();
        var isNowIgnored = manager.toggleIgnore(player.getUniqueId(), target.getUniqueId());

        if (isNowIgnored) {
            MsgUtil.success(sender, LangUtil.get("tpignore-added", target.getName()));
        } else {
            MsgUtil.success(sender, LangUtil.get("tpignore-removed", target.getName()));
        }

        return true;
    }
}
