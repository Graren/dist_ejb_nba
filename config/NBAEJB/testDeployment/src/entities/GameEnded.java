package entities;

import java.util.Vector;

/**
 * Created by oscar on 9/12/2017.
 */
public class GameEnded {
    Integer winning_team;

    Integer team_1;
    Integer team_2;

    Players players;

    public Integer getWinning_team() {
        return winning_team;
    }

    public void setWinning_team(Integer winning_team) {
        this.winning_team = winning_team;
    }

    public Integer getTeam_1() {
        return team_1;
    }

    public void setTeam_1(Integer team_1) {
        this.team_1 = team_1;
    }

    public Integer getTeam_2() {
        return team_2;
    }

    public void setTeam_2(Integer team_2) {
        this.team_2 = team_2;
    }

    public Players getPlayers() {
        return players;
    }

    public void setPlayers(Players players) {
        this.players = players;
    }
}
