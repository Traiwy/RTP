package ru.traiwy.rtp;

import Command.CommandRTP;
import Command.CommandRtpMenu;
import Event.OnInventoryClick;
import Event.PlayerMove;
import Util.LocationGenerator;
import Util.RtpInProcessing;

import org.bukkit.plugin.java.JavaPlugin;


public final class RTP extends JavaPlugin {
    private LocationGenerator locationGenerator;
    private RtpInProcessing rtpInProcessing;

    @Override
    public void onEnable() {
        locationGenerator = new LocationGenerator(this);
        rtpInProcessing =  new RtpInProcessing(this);
        getCommand("rtp").setExecutor(new CommandRTP(this));
        getCommand("rtpmenu").setExecutor(new CommandRtpMenu());
        getServer().getPluginManager().registerEvents(new OnInventoryClick(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(this), this);
    }

    @Override
    public void onDisable() {
    }
    public LocationGenerator getLocationGenerator() {
        return locationGenerator;
    }
    public RtpInProcessing getRtpInProcessing(){
        return rtpInProcessing;
    }
}