package entities;

/**
 * Created by oscar on 9/11/2017.
 */
public class Player{
    Integer team_id;

    Integer two_points;

    Integer fouls;

    Integer player_number;
    Integer person_id;
    String player_name;

    Integer contract_id;

    String team_name;

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public Integer getContract_id() {
        return contract_id;
    }

    public Integer getFouls() {
        return fouls;
    }

    public void setFouls(Integer fouls) {
        this.fouls = fouls;
    }

    public void setContract_id(Integer contract_id) {
        this.contract_id = contract_id;
    }

    public Integer getTwo_points() {
        return two_points;
    }

    public void setTwo_points(Integer two_points) {
        this.two_points = two_points;
    }
    public Integer getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Integer team_id) {
        this.team_id = team_id;
    }

    public Integer getPlayer_number() {
        return player_number;
    }

    public void setPlayer_number(Integer player_number) {
        this.player_number = player_number;
    }

    public Integer getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Integer person_id) {
        this.person_id = person_id;
    }

    public String getPlayer_name() {
        return player_name;
    }

    public void setPlayer_name(String player_name) {
        this.player_name = player_name;
    }
}