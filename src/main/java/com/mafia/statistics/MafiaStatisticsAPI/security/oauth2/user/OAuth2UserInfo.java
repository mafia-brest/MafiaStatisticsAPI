package com.mafia.statistics.MafiaStatisticsAPI.security.oauth2.user;

import lombok.AllArgsConstructor;

import java.util.Map;

@AllArgsConstructor
public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public abstract Integer getVkId();

    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String getImageUrl();
}
