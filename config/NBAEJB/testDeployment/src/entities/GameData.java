package entities;

import java.util.Vector;

/**
 * Created by oscar on 9/11/2017.
 */
public class GameData {
    Integer game_id;

    String game_date;
    String arena_name;
    Player player;

    public Vector<Player> getPs() {
        return ps;
    }

    public void setPs(Vector<Player> ps) {
        this.ps = ps;
    }

    Vector<Player> ps;
    String team_name;

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    public String getGame_date() {
        return game_date;
    }

    public void setGame_date(String game_date) {
        this.game_date = game_date;
    }

    public String getArena_name() {
        return arena_name;
    }

    public void setArena_name(String arena_name) {
        this.arena_name = arena_name;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
