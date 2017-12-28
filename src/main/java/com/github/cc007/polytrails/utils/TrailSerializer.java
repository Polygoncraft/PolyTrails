package com.github.cc007.polytrails.utils;

import com.github.cc007.polytrails.Trails.Trail;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TrailSerializer implements JsonSerializer<Trail> {

    @Override
    public JsonElement serialize(Trail trail, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject obj = new JsonObject();
        obj.add("trailString", new JsonPrimitive(trail.getTrailString()));
        return obj;
    }
}
