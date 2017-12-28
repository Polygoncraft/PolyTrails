package com.github.cc007.polytrails.Trails;

import org.bukkit.entity.Player;

public class DebugTrail extends Trail {

    public DebugTrail(String s) {
        super(s);
    }

    @Override
    public void playEffect(Player p) {
        p.sendMessage("playing particle effect");
    }

    @Override
    public boolean hasPermission(Player p) {
        return p.isOp();
    }

    @Override
    public boolean hasValidTrailString() {
        return "debug".equalsIgnoreCase(trailString);
    }
}
