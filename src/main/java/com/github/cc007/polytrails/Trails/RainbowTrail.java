package com.github.cc007.polytrails.Trails;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;


public class RainbowTrail extends Trail {
    private enum Mode {
        Cycle,
        Random
    }
    private  static Pattern rp = Pattern.compile("^rainbow(?:-([0-9a-f]+))?(\\?)?(\\*)?(?:#(\\d+))?$");

    private int state = 0;
    private int prev = 0;
    private Mode mode = Mode.Cycle;
    private double speed = 0;
    private int freq = 5;   //"e1453ba"
    private int[] pattern = {14,1,4,5,3,11,10};

    public RainbowTrail(String s) {
        super(s);
        init();
    }

    private void init() {
        if (trailString.equalsIgnoreCase("rainbow")) {
            return;
        }
        Matcher m = rp.matcher(trailString);
        if (m.find()) {
            String pat = m.group(1);
            if (pat != null) {
                IntStream l = pat.chars().map((c) -> Character.digit(c, 16));
                pattern = l.toArray();
            }
            if (m.group(2) != null) {
                mode = Mode.Random;
            }
            if (m.group(3) != null) {
                speed = 1;
            }
            if (m.group(4) != null) {
                freq = Integer.parseInt(m.group(4));
            }
        }
    }

    @Override
    public void playEffect(Player p) {
        Location l = p.getLocation().add(new Vector(0,0.2,0));
        MaterialData m = new MaterialData(Material.WOOL);
        byte data = 0;
        switch (mode) {
            case Cycle:
                data = (byte)pattern[(state/freq) % pattern.length];
                break;
            case Random:
                if (state%freq == 0)
                    prev = new Random().nextInt(pattern.length);
                data = (byte)pattern[prev];
        }
        m.setData(data);
        state++;
        p.getWorld().spawnParticle(Particle.FALLING_DUST,
                l, 5, 0.2, 0.1, 0.2, speed, m);
    }

    @Override
    public boolean hasPermission(Player p) {
        if (trailString.equalsIgnoreCase("rainbow")) {
            return p.hasPermission("polytrails.rainbow");
        }
        return p.hasPermission("polytrails.rainbow.pattern");
    }

    @Override
    public boolean hasValidTrailString() {
        if ("rainbow".equalsIgnoreCase(trailString)) {
            return true;
        }
        Matcher m = rp.matcher(trailString);
        if (m.find()) {
            return true;
        }
        return false;
    }


}
