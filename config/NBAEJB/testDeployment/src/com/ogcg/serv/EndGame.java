package com.ogcg.serv;

import com.google.gson.Gson;
import com.osc.nba.actual.NbaRemoteInterface;
import entities.GameEnded;
import entities.Player;
import entities.Players;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;

/**
 * Created by oscar on 9/12/2017.
 */
@WebServlet(name = "EndGame",urlPatterns = "/endGame")

public class EndGame extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson g = new Gson();
        System.out.println(request.getHeader("Content-Type"));
        for(Part p : request.getParts()){
            System.out.println(p.getName());
        }
//        for(String s: request.getParameterMap().keySet()){
//            System.out.println(s);
//        }
        String t1 = request.getParameter("team_1");
        String t2 = request.getParameter("team_2");
        String t3 = request.getParameter("winning_team");
        String p =  request.getParameter("players1");
        String p2 = request.getParameter("players2");
        String game_id = request.getParameter("game_id");
        System.out.println(t1);
        System.out.println(t2);
        System.out.println(p);
        System.out.println(p2);
        System.out.println(game_id);
        Integer team_1 = Integer.valueOf(t1);
        Integer team_2 = Integer.valueOf(t2);
        Integer winner = Integer.valueOf(t3);
        Players pla = g.fromJson(p, Players.class);
        Players pla2 = g.fromJson(p2, Players.class);
        NbaRemoteInterface na = DataSourceOsc.Helper.oinstance.getNba();
        try {
            na.insertGameResults(pla2.toMap(),pla.toMap(),Integer.valueOf(game_id),winner);
            response.setStatus(200);
            response.getWriter().print(g.toJson(pla) + " " +  g.toJson(pla2));
        } catch (NamingException e) {
            e.printStackTrace();
            response.setStatus(500);
            response.getWriter().print(g.toJson(pla) + " " +  g.toJson(pla2));
        }
//        na.insertGameResults();
//        NbaRemoteInterface nba = DataSourceOsc.Helper.oinstance.getNba().insertGameResults();
//        System.out.println("" + ge.getWinning_team());

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
