package com.github.cc007.polytrails.Trails;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.Map;

public class SimpleParticleTrail extends Trail {
    public static final Map<String, Particle> particleMap;
    private Vector prev;
    private Particle particle;

    static {
        particleMap = new HashMap<>();
        for (Particle p : Particle.values()) {
            particleMap.put(p.name().toLowerCase(), p);
        }
        particleMap.put("smoke", Particle.SMOKE_NORMAL);
        particleMap.put("fire", Particle.FLAME);
    }

    public SimpleParticleTrail(String s) {
        super(s);
        this.particle = getParticle(s);
    }

    @Override
    public void playEffect(Player p) {
        if (prev != null) {
            Location l = p.getLocation();
            p.getWorld().spawnParticle(this.particle,
                    l, 5, 0.2, 0.2, 0.2, 0.01);
        }
        prev = p.getLocation().toVector();
    }

    @Override
    public boolean hasPermission(Player p) {
        return p.hasPermission("polytrails.simple." + this.particle.name().toLowerCase());
    }

    @Override
    public boolean hasValidTrailString() {
        return particle != null;
    }

    private Particle getParticle(String from) {
        return particleMap.get(from);
    }

}
