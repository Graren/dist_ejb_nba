package com.ogcg.serv;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by oscar on 9/12/2017.
 */
@WebServlet(name = "TeamsByWins", urlPatterns = "/teamWins")
public class TeamsByWins extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson g = new Gson();
        JsonArray jArr = new JsonArray();
        try {
            String[][] arr = DataSourceOsc.Helper.oinstance.getNba().getTeamsOrderedByWins();
            for (String[] row : arr) {
                JsonObject js = new JsonObject();
                js.addProperty("count",Integer.valueOf(row[0]));
                js.addProperty("team", row[1]);
                jArr.add(js);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
        response.getWriter().print(g.toJson(jArr));
    }
}
