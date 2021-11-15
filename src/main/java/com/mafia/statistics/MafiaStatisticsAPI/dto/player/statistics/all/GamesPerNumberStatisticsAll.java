package com.mafia.statistics.MafiaStatisticsAPI.dto.player.statistics.all;

import com.mafia.statistics.MafiaStatisticsAPI.dto.player.statistics.base.Statistics;

import org.hibernate.Hibernate;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class GamesPerNumberStatisticsAll extends Statistics {

    @Id
    @GeneratedValue
    private Long id;

    private Integer number;

    private Date fromDate;
    private Date toDate;

    private Integer gamesTotal;

    private Integer firstShot;

    private Integer percentFirstShot;

    private Integer percentSelectedRed;
    private Integer percentSelectedBlack;
    private Integer percentSelectedDon;
    private Integer percentSelectedSheriff;

    private Integer percentWinningRed;
    private Integer percentWinningBlack;
    private Integer percentWinningDon;
    private Integer percentWinningSheriff;

    private Integer percentWinningAllRed; // with sheriff
    private Integer percentWinningAllBlack; // with don

    private Boolean isActive;
    private Date uploadingDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o))
            return false;
        GamesPerNumberStatisticsAll that = (GamesPerNumberStatisticsAll) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
