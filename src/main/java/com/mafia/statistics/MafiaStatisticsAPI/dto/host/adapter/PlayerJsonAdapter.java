package com.mafia.statistics.MafiaStatisticsAPI.dto.host.adapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mafia.statistics.MafiaStatisticsAPI.pyload.player.Player;

import java.lang.reflect.Type;

import static com.mafia.statistics.MafiaStatisticsAPI.dto.host.adapter.AdapterUtil.getPlayerById;

public class PlayerJsonAdapter implements JsonDeserializer<Player> {

    @Override
    public Player deserialize(
            JsonElement json,
            Type typeOfT,
            JsonDeserializationContext context
    ) throws JsonParseException {
        if (json instanceof JsonObject) {
            return getPlayerById(json.getAsJsonObject().get("id").getAsLong());
        } else {
            return getPlayerById(json.getAsLong());
        }
    }
}
