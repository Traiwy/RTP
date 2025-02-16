package ru.traiwy.rtp;

import Command.CommandRTP;
import Command.CommandRTPHistory;
import Command.CommandRTPLong;
import Command.CommandRtpMenu;
import Event.OnInventoryClick;
import Event.PlayerMove;
import Util.*;

import org.bukkit.plugin.java.JavaPlugin;


public final class RTP extends JavaPlugin {
    private LocationGenerator locationGenerator;
    private RtpInProcessing rtpInProcessing;
    private RtpLongInProcessing rtpLongInProcessing;
    private ConfigManager configManager;
    private MySqlStorage mySqlStorage;

    @Override
    public void onEnable() {
        mySqlStorage = new MySqlStorage();
        configManager = new ConfigManager(this);
        rtpInProcessing =  new RtpInProcessing(this);
        getCommand("rtp").setExecutor(new CommandRTP(this));
        getCommand("rtpmenu").setExecutor(new CommandRtpMenu());
        getCommand("rtphist").setExecutor(new CommandRTPHistory());
        getCommand("rtplong").setExecutor(new CommandRTPLong(this));
        getServer().getPluginManager().registerEvents(new OnInventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(this), this);

        locationGenerator = new LocationGenerator(this);
        rtpInProcessing = new RtpInProcessing(this);
        rtpLongInProcessing = new RtpLongInProcessing(this);


        configManager = new ConfigManager(this);

    }

    @Override
    public void onDisable() {
        if (mySqlStorage != null) {
            mySqlStorage.close();
        }
    }
    public LocationGenerator getLocationGenerator() {
        return locationGenerator;
    }
    public RtpInProcessing getRtpInProcessing(){
        return rtpInProcessing;
    }
    public RtpLongInProcessing getRtpLongInProcessing() {
        return rtpLongInProcessing;
    }

}