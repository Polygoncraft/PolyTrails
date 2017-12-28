package com.github.cc007.polytrails.utils;

import com.github.cc007.polytrails.Trails.Trail;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.UUID;

public class TrailMapSerializer implements JsonSerializer<Map<UUID, Trail>> {
    @Override
    public JsonElement serialize(Map<UUID, Trail> uuidTrailMap, Type type, JsonSerializationContext jsonSerializationContext) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Trail.class, new TrailSerializer())
                .create();
        JsonArray arr = new JsonArray();
        for (Map.Entry<UUID, Trail> entry: uuidTrailMap.entrySet()){
            JsonObject obj = new JsonObject();
            obj.add("key", new JsonPrimitive(entry.getKey().toString()));
            obj.addProperty("value", gson.toJson(entry.getValue()));
            arr.add(obj);
        }
        return arr;
    }
}
