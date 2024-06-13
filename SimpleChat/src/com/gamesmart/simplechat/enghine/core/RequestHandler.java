package com.gamesmart.simplechat.enghine.core;

import com.gamesmart.simplechat.enghine.io.PlayerState;
import com.gamesmart.simplechat.enghine.io.Reply;
import com.gamesmart.simplechat.enghine.io.Request;

public abstract class RequestHandler
{
    protected LobbyManager lobby;

    public RequestHandler(LobbyManager lobby)
    {
        this.lobby = lobby;
    }

    public abstract Reply doRequest(Request request, PlayerState playerState);
}