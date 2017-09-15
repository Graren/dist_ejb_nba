package com.ogcg.serv;


import com.osc.nba.actual.NBADataSource;
import com.osc.nba.actual.NbaRemoteInterface;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by oscar on 9/10/2017.
 */
@WebServlet(name = "fola", urlPatterns = "/fola")
public class Servlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        NbaRemoteInterface nba = DataSourceOsc.Helper.oinstance.getNba();
        String[][] arr = new String[0][];
        try {
            arr = nba.getPlayers();
        } catch (NamingException e) {
            e.printStackTrace();
        }

        for (String[] row : arr) {
            for (String col : row) {
                System.out.println(col);
            }
        }
        response.setStatus(200);
        response.getWriter().print("{" + "\"response\":\"Stuff\" }");
    }

}
