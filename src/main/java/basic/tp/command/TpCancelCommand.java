package basic.tp.command;

import basic.tp.BasicTp;
import basic.tp.utility.LangUtil;
import basic.tp.utility.MsgUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class TpCancelCommand implements CommandExecutor {

    private final BasicTp plugin;

    public TpCancelCommand(BasicTp plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player player)) {
            MsgUtil.error(sender, LangUtil.get("only-players"));
            return true;
        }

        var manager = plugin.getRequestManager();
        var removed = manager.removeRequestFrom(player.getUniqueId(), player.getUniqueId());

        if (removed == null) {
            MsgUtil.error(sender, LangUtil.get("tpcancel-none"));
            return true;
        }

        MsgUtil.success(sender, LangUtil.get("tpcancel-done"));

        return true;
    }
}
