package com.github.cc007.polytrails.Trails;

import org.bukkit.entity.Player;

public abstract class Trail {
    protected String trailString;

    public Trail(String trailString) {
        this.trailString = trailString;
    }

    public abstract void playEffect(Player p);

    public abstract boolean hasPermission(Player p);

    public abstract boolean hasValidTrailString();

    public String getTrailString() {
        return trailString;
    }
}
