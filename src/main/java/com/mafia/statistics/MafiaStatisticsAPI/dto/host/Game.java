package com.mafia.statistics.MafiaStatisticsAPI.dto.host;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.mafia.statistics.MafiaStatisticsAPI.dto.host.adapter.BestPlayersJsonAdapter;
import com.mafia.statistics.MafiaStatisticsAPI.dto.host.adapter.PlayerJsonAdapter;
import com.mafia.statistics.MafiaStatisticsAPI.dto.host.adapter.PlayersJsonAdapter;
import com.mafia.statistics.MafiaStatisticsAPI.pyload.player.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {

    private Long id;

    @SerializedName(value = "host", alternate = "hostId")
    @JsonAdapter(PlayerJsonAdapter.class)
    private Player host;

    @SerializedName(value = "creator", alternate = "creatorId")
    @JsonAdapter(PlayerJsonAdapter.class)
    private Player creator;

    private Integer number;

    private Integer[] bestMove;

    private Date startDatetime;
    private Date endDatetime;

    private GameStatus status;

    private TeamWon won;

    private String note;

    @SerializedName(value = "blackPlayerOne", alternate = "blackPlayerOneId")
    @JsonAdapter(PlayerJsonAdapter.class)
    private Player blackPlayerOne;
    @SerializedName(value = "blackPlayerTwo", alternate = "blackPlayerTwoId")
    @JsonAdapter(PlayerJsonAdapter.class)
    private Player blackPlayerTwo;
    @SerializedName(value = "donPlayer", alternate = "donPlayerId")
    @JsonAdapter(PlayerJsonAdapter.class)
    private Player donPlayer;
    @SerializedName(value = "sheriffPlayer", alternate = "sheriffPlayerId")
    @JsonAdapter(PlayerJsonAdapter.class)
    private Player sheriffPlayer;
    @SerializedName(value = "firstShootPlayer", alternate = "firstShootPlayerId")
    @JsonAdapter(PlayerJsonAdapter.class)
    private Player firstShootPlayer;

    @JsonAdapter(PlayersJsonAdapter.class)
    private List<GamePlayer> players;

    @JsonAdapter(BestPlayersJsonAdapter.class)
    private List<BestPlayer> bestPlayers;

    private List<Day> days;

    private Boolean isAggregated;

    private Date insertedAt;
    private Date updatedAt;
}
