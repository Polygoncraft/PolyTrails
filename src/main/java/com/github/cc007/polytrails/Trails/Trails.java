package com.github.cc007.polytrails.Trails;

public class Trails {
    public static Trail fromString(String trailString) {
        Trail trail = null;

        // rainbow trail
        trail = new RainbowTrail(trailString);
        if(trail.hasValidTrailString()){
            return trail;
        }

        // heart aura trail trail
        trail = new HeartAura(trailString);
        if(trail.hasValidTrailString()){
            return trail;
        }

        // love particle trail
        trail = new DebugTrail(trailString);
        if(trail.hasValidTrailString()){
            return trail;
        }

        // love particle trail
        trail = new SimpleParticleTrail(trailString);
        if(trail.hasValidTrailString()){
            return trail;
        }

        return null;
    }
}
