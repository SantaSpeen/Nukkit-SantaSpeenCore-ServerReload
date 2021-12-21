package ru.santaspeen.reloadServer;

import cn.nukkit.Server;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.event.Listener;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;

public class Reload extends PluginBase implements Listener {

    private String SERVER_RESTART;
    private String SERVER_STOP;

    public void onEnable() {

        File path = this.getDataFolder();
        if(!path.exists()) path.mkdirs();
        saveResource("config.yml");

        Config cfg = new Config(path + "/config.yml");
        this.SERVER_RESTART = cfg.get("reloadMessage", "Server restart.");
        this.SERVER_STOP = cfg.get("stopMessage", "Server closed.");

    }
    public void onDisable() {}

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (!sender.isPlayer()) {
            switch (label) {
                case "reload" -> {
                    Server server = this.getServer();
                    server.getOnlinePlayers().forEach((k, v) -> v.kick(SERVER_RESTART, false));
                    server.reload();
                }
                case "stop" -> {
                    Server server = this.getServer();
                    server.getOnlinePlayers().forEach((k, v) -> v.kick(SERVER_STOP, false));
                    server.shutdown();
                }
            }
        }
        return true;
    }
}
