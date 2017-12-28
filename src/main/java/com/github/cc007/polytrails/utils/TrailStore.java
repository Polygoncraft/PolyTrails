package com.github.cc007.polytrails.utils;

import com.github.cc007.polytrails.PolyTrails;
import com.github.cc007.polytrails.Trails.Trail;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

public class TrailStore {
    private static Path filePath = PolyTrails.getPlugin().getDataFolder().toPath().resolve("trails.json");
    public static void store(Map<UUID, Trail>  map) {
        if(Files.notExists(filePath)){
            try {
                Files.createFile(filePath);
                PolyTrails.getPlugin().getLogger().info("Trails json file created");
            } catch (IOException ex) {
                PolyTrails.getPlugin().getLogger().log(Level.WARNING, "Cannot create new file!", ex);
            }
        }
        try {
            FileWriter fw = new FileWriter(filePath.toFile());
            new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Map<UUID, Trail>>(){}.getType(), new TrailMapSerializer())
                .create()
                .toJson(map, new TypeToken<Map<UUID, Trail>>(){}.getType(), fw);
            fw.close();
        } catch (IOException ex) {
            PolyTrails.getPlugin().getLogger().log(Level.WARNING, "Cannot edit file!", ex);
        }
    }

    public static Map<UUID, Trail> retrieve(){
        try {
            FileReader fr = new FileReader(filePath.toFile());
            Map<UUID, Trail> map = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Map<UUID, Trail>>(){}.getType(), new TrailMapDeserializer())
                .create()
                .fromJson(fr, new TypeToken<Map<UUID, Trail>>(){}.getType());
            fr.close();
            if(map == null) {
                return new HashMap<>();
            }
            return map;
        } catch (IOException e) {
            return new HashMap<>();
        }
    }
}
