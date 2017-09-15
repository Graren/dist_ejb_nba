package com.ogcg.serv;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.osc.nba.actual.NBADataSource;
import entities.Player;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Vector;

/**
 * Created by oscar on 9/11/2017.
 */
@WebServlet(name = "getPlayersFromTeamServlet",urlPatterns = "/players/*")
public class serv extends HttpServlet {



    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson g = new Gson();
        String pathInfo = request.getPathInfo(); // /{value}/test
        System.out.println(pathInfo);
        String[] pathParts = pathInfo.split("/");
        String part1 = pathParts[1]; // {value}
        Vector<Player> players = new Vector<>();
        if(pathParts.length > 2){
            String part2 = pathParts[2]; // {value}
            if( null != part1 || ("").equals(part2) ){
                try {
                    String[][] arr = DataSourceOsc.Helper.oinstance.getNba().getPlayersFromTeam(Integer.valueOf(part2));
                    for (String[] row : arr) {
                        for (String col : row) {
                            System.out.println(col);
                        }
                        Player p = new Player();
                        p.setPerson_id(Integer.valueOf(row[0]));
                        p.setPlayer_number(Integer.valueOf(row[1]));
                        p.setTeam_id(Integer.valueOf(row[2]));
                        p.setPlayer_name(row[3]);
                        players.add(p);
                    }
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }
        }

        if( null != part1 || ("").equals(part1) ){
            try {
                String[][] arr = DataSourceOsc.Helper.oinstance.getNba().getPlayersFromTeam(Integer.valueOf(part1));
                for (String[] row : arr) {
                    for (String col : row) {
                        System.out.println(col);
                    }
                    Player p = new Player();
                    p.setPerson_id(Integer.valueOf(row[0]));
                    p.setPlayer_number(Integer.valueOf(row[1]));
                    p.setTeam_id(Integer.valueOf(row[2]));
                    p.setPlayer_name(row[3]);
                    players.add(p);

                }
            } catch (NamingException e) {
                e.printStackTrace();
            }
        }
        response.setStatus(200);
        response.getWriter().print(g.toJson(players));
    }
}
