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
@WebServlet(name = "GameFouls", urlPatterns = "/gameFouls")
public class GameFouls extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson g = new Gson();
        JsonArray jArr = new JsonArray();
        JsonObject js = new JsonObject();
        try {
            String[][] arr = DataSourceOsc.Helper.oinstance.getNba().getGameWithMostFouls();
            js.addProperty("game_id",Integer.valueOf(arr[0][0]));
            js.addProperty("arena_name", arr[0][1]);
            js.addProperty("date", arr[0][2]);
            js.addProperty("fouls", Integer.valueOf(arr[0][3]));
        } catch (NamingException e) {
            e.printStackTrace();
        }
        response.setStatus(200);
        response.getWriter().print(g.toJson(js));
    }
}
