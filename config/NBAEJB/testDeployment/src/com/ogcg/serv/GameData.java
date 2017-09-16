package com.ogcg.serv;

import com.google.gson.Gson;
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
@WebServlet(name = "GameData",urlPatterns = "/gameData/*")
public class GameData extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson g = new Gson();
        String pathInfo = request.getPathInfo(); // /{value}/test
        String[] pathParts = pathInfo.split("/");
        String part1 = pathParts[1]; // {value}

        entities.GameData gd = new entities.GameData();
        Vector<Player> players = new Vector<>();
        try {
            String[][] arr = DataSourceOsc.Helper.oinstance.getNba().getGameData(Integer.valueOf(part1));
            Integer game_id;
            String game_date;
            String arena;
            gd.setGame_id(Integer.valueOf(arr[0][0]));
            gd.setGame_date(arr[0][1]);
            gd.setArena_name(arr[0][2]);
            for (String[] row : arr) {
                Player player = new Player();
                player.setTeam_name(row[3]);
                player.setPerson_id(Integer.valueOf(row[4]));
                player.setPlayer_number(Integer.valueOf(row[5]));
                player.setPlayer_name(row[6]);
                player.setTeam_id(Integer.valueOf(row[7]));
                players.add(player);
            }
            gd.setPs(players);
        } catch (NamingException e) {
            e.printStackTrace();
        }


        response.setStatus(200);
        response.getWriter().print(g.toJson(gd));
    }
}
