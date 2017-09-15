package entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by oscar on 9/12/2017.
 */
public class Players {
    ArrayList<Player> players;

    Players(){
        players = new ArrayList<>();
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public List<HashMap<String,Integer>> toMap(){
        List<HashMap<String,Integer>> list = new ArrayList<>();
        for(Player p : players){
            HashMap<String,Integer> map = new HashMap<>();
            map.put("two",p.getTwo_points());
            map.put("three",0);
            map.put("reb",0);
            map.put("f",p.getFouls());
            map.put("bl",0);
            map.put("as",0);
            map.put("id",p.getContract_id());
            list.add(map);
        }
        return list;
    }

}
