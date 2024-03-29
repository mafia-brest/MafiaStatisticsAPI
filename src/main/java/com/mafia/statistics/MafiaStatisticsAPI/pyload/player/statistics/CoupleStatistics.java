package com.mafia.statistics.MafiaStatisticsAPI.pyload.player.statistics;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mafia.statistics.MafiaStatisticsAPI.pyload.player.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class CoupleStatistics {

    @JsonFormat(pattern = "yyyy-MM", timezone = "Europe/Minsk")
    private Date fromDate;
    @JsonFormat(pattern = "yyyy-MM", timezone = "Europe/Minsk")
    private Date toDate;

    private Player couplePlayer;

    private Long games;

    private Long wins;

    private Long percentOfWins;
}
