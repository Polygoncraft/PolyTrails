package com.github.cc007.polytrails.Trails;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class HeartAura extends Trail {

    public HeartAura(String s) {
        super(s);
    }

    @Override
    public void playEffect(Player p) {
        Location l = p.getLocation();
        p.getWorld().spawnParticle(Particle.HEART,
                l.add(0,1,0), 1, 2, 1, 2, 0.01);
    }

    @Override
    public boolean hasPermission(Player p) {
        return p.hasPermission("polytrails.love");
    }

    @Override
    public boolean hasValidTrailString() {
        return "love".equalsIgnoreCase(trailString);
    }
}
