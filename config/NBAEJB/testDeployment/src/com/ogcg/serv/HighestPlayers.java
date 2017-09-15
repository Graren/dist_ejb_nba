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
@WebServlet(name = "HighestPlayers", urlPatterns = "/stats/players")
public class HighestPlayers extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson g = new Gson();


        Vector<Player> players = new Vector<>();
        try {
            String[][] arr = DataSourceOsc.Helper.oinstance.getNba().getHighestPlayers();
            for (String[] row : arr) {
                for (String col : row) {
                    System.out.println(col);
                }
                Player p = new Player();
                p.setContract_id(Integer.valueOf(row[0]));
                p.setPlayer_name(row[1]);
                p.setTwo_points(Integer.valueOf(row[2]));
                players.add(p);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }


        response.setStatus(200);
        response.getWriter().print(g.toJson(players));
    }
}
