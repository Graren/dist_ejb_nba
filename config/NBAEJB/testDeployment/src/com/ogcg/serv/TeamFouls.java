package com.ogcg.serv;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
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
 * Created by oscar on 9/12/2017.
 */
@WebServlet(name = "TeamFouls",urlPatterns = "/teamFouls")
public class TeamFouls extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson g = new Gson();
        JsonArray jArr = new JsonArray();
        JsonObject js = new JsonObject();
        try {
            String[][] arr = DataSourceOsc.Helper.oinstance.getNba().getTeamWithMostFouls();
            js.addProperty("team", arr[0][0]);
            js.addProperty("fouls",Integer.valueOf(arr[0][1]));

        } catch (NamingException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
        response.getWriter().print(g.toJson(js));
    }
}
