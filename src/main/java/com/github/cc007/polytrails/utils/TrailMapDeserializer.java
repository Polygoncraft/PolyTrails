package com.github.cc007.polytrails.utils;

import com.github.cc007.polytrails.Trails.Trail;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TrailMapDeserializer implements JsonDeserializer<Map<UUID, Trail>> {
    @Override
    public Map<UUID, Trail> deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        Map<UUID, Trail> map = new HashMap<>();
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Trail.class, new TrailDeserializer())
                .create();
        if (!(jsonElement instanceof JsonArray)) {
            return map;
        }
        JsonArray jsonArray = (JsonArray) jsonElement;
        for (JsonElement entry : jsonArray) {
            if(entry instanceof JsonObject) {
                JsonObject jsonObject = (JsonObject) entry;
                map.put(
                    UUID.fromString(jsonObject.getAsJsonPrimitive("key").getAsString()),
                    gson.fromJson(jsonObject.getAsJsonPrimitive("value").getAsString(), Trail.class)
                );
            }
        }
        return map;
    }
}
