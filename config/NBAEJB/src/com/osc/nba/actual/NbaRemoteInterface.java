package com.osc.nba.actual;

import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.naming.NamingException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by oscar on 9/3/2017.
 */
public interface NbaRemoteInterface {

    public String[][] getPlayers() throws NamingException;
    public String[][] getPlayersFromTeam(Integer team_id) throws NamingException;
    public Boolean createGame(int team_1, List<Integer> players_1, int team_2, List<Integer> players_2, int arena_id) throws NamingException;
    public String[][] getTeams() throws NamingException;

    String[][] getGameWithMostFouls() throws NamingException;

    public Boolean insertGameResults(List<HashMap<String,Integer>> players, Integer game_id, Integer winning_team) throws NamingException;

    Boolean insertGameResults(List<HashMap<String, Integer>> players1, List<HashMap<String, Integer>> players2, Integer game_id, Integer game_winner) throws NamingException;

    public String[][] getHighestPlayers() throws NamingException;
    public String[][] getTeamsOrderedByWins() throws NamingException;
    public void close() throws NamingException;
    public String[][] getTeamWithMostFouls() throws NamingException;
    public String[][] getGameData(Integer game_id) throws NamingException;

    String[][] getArenas() throws NamingException;

    public String[][] getOpenGames() throws NamingException;
}
