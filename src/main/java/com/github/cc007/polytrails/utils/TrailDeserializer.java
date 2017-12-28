package com.github.cc007.polytrails.utils;

import com.github.cc007.polytrails.Trails.Trail;
import com.github.cc007.polytrails.Trails.Trails;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TrailDeserializer implements JsonDeserializer<Trail> {
    @Override
    public Trail deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if(jsonElement instanceof JsonObject) {
            JsonObject jsonObject = (JsonObject) jsonElement;
            return Trails.fromString(jsonObject.get("trailString").getAsString());
        }
        return null;
    }
}
