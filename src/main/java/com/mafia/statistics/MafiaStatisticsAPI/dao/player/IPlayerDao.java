package com.mafia.statistics.MafiaStatisticsAPI.dao.player;

import com.mafia.statistics.MafiaStatisticsAPI.dto.player.Player;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPlayerDao extends JpaRepository<Player, Long> {

    Boolean existsByNickname(String nickname);

    Player findByNickname(String nickname);

    List<Player> findTop15ByOrderByGamesTotalDesc();
}
