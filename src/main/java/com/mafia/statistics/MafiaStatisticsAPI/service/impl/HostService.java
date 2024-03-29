package com.mafia.statistics.MafiaStatisticsAPI.service.impl;

import com.google.gson.GsonBuilder;
import com.mafia.statistics.MafiaStatisticsAPI.dto.host.Game;
import com.mafia.statistics.MafiaStatisticsAPI.dto.host.Games;
import com.mafia.statistics.MafiaStatisticsAPI.dto.host.adapter.DateJsonAdapter;
import com.mafia.statistics.MafiaStatisticsAPI.dto.host.adapter.GameJsonAdapter;
import com.mafia.statistics.MafiaStatisticsAPI.exception.BadRequestException;
import com.mafia.statistics.MafiaStatisticsAPI.exception.InternalServerException;
import com.mafia.statistics.MafiaStatisticsAPI.exception.ResourceNotFoundException;
import com.mafia.statistics.MafiaStatisticsAPI.pyload.player.Player;
import com.mafia.statistics.MafiaStatisticsAPI.security.UserPrincipal;
import com.mafia.statistics.MafiaStatisticsAPI.service.inter.IHostService;
import com.mafia.statistics.MafiaStatisticsAPI.service.inter.IHostServiceApi;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.Date;

@Service
public class HostService implements IHostService {

    private final IHostServiceApi hostServiceApi;

    public HostService(
            @Value("${app.hostService.baseUrl}") String hostServiceBaseUrl
    ) {
        this.hostServiceApi = new Retrofit.Builder()
                .baseUrl(hostServiceBaseUrl)
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder()
                                .registerTypeAdapter(Game.class, new GameJsonAdapter())
                                .registerTypeAdapter(Date.class, new DateJsonAdapter())
                                .create()
                ))
                .build()
                .create(IHostServiceApi.class);
    }

    private static void correctGame(Game game, UserPrincipal userPrincipal) {
        correctCreator(game, userPrincipal);
        correctHost(game, userPrincipal);
        correctNumber(game);
    }

    private static void correctCreator(Game game, UserPrincipal userPrincipal) {
        if (game.getCreator() == null) {
            game.setCreator(new Player(userPrincipal.getId()));
        }
    }

    private static void correctHost(Game game, UserPrincipal userPrincipal) {
        if (game.getHost() == null) {
            game.setHost(new Player(userPrincipal.getId()));
        }
    }

    private static void correctNumber(Game game) {
        if (game.getNumber() == null) {
            game.setNumber(1); // TODO: mock
        }
    }

    @SneakyThrows
    @Override
    public Game getGameById(Long id) {
        Call<Game> retrofitCall = hostServiceApi.getGameById(id);
        Response<Game> response = retrofitCall.execute();

        if (response.code() == 400) {
            throw new BadRequestException(response.errorBody().string());
        } else if (response.code() == 404) {
            throw new ResourceNotFoundException("Game", "id", id);
        } else if (response.code() == 500) {
            throw new InternalServerException(response.errorBody().string());
        }

        return response.body();
    }

    @SneakyThrows
    @Override
    public Games getAllGames(Integer limit, Integer page) {
        Call<Games> retrofitCall = hostServiceApi.getAllGames(limit, page);
        Response<Games> response = retrofitCall.execute();

        if (response.code() == 400) {
            throw new BadRequestException(response.errorBody().string());
        } else if (response.code() == 500) {
            throw new InternalServerException(response.errorBody().string());
        }

        return response.body();
    }

    @SneakyThrows
    @Override
    public Game createGame(Game game, UserPrincipal userPrincipal) {
        correctGame(game, userPrincipal);

        Call<Game> retrofitCall = hostServiceApi.createGame(game);
        Response<Game> response = retrofitCall.execute();

        if (response.code() == 400) {
            throw new BadRequestException(response.errorBody().string());
        } else if (response.code() == 500) {
            throw new InternalServerException(response.errorBody().string());
        }

        return response.body();
    }

    @SneakyThrows
    @Override
    public Game updateGame(Long id, Game game) {
        Call<Game> retrofitCall = hostServiceApi.updateGame(id, game);
        Response<Game> response = retrofitCall.execute();

        if (response.code() == 400) {
            throw new BadRequestException(response.errorBody().string());
        } else if (response.code() == 404) {
            throw new ResourceNotFoundException("Game", "id", id);
        } else if (response.code() == 500) {
            throw new InternalServerException(response.errorBody().string());
        }

        return response.body();
    }

    @SneakyThrows
    @Override
    public void deleteGame(Long id) {
        Call<Void> retrofitCall = hostServiceApi.deleteGame(id);
        Response<Void> response = retrofitCall.execute();

        if (response.code() == 400) {
            throw new BadRequestException(response.errorBody().string());
        } else if (response.code() == 404) {
            throw new ResourceNotFoundException("Game", "id", id);
        } else if (response.code() == 500) {
            throw new InternalServerException(response.errorBody().string());
        }
    }
}
