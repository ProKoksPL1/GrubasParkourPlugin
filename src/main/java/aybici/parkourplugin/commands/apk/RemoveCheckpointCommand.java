package aybici.parkourplugin.commands.apk;

import aybici.parkourplugin.ParkourPlugin;
import aybici.parkourplugin.sessions.ParkourSession;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoveCheckpointCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;
        if (!player.hasPermission(ParkourPlugin.permissionSet.apkPermission)) {
            player.sendMessage(ChatColor.RED + "Nie masz dostępu do komend admin-parkour!");
            return true;
        }
        if (args.length != 1) return false;
        ParkourSession session = ParkourPlugin.parkourSessionSet.getSession(player);
        session.staticCheckpoint.remove(Integer.parseInt(args[0]));
        return true;
    }
}
