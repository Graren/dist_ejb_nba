package com.osc.nba.actual;

import ogcg.org.jdbc.JDBCOConnection;
import ogcg.org.jdbc.Query;
import ogcg.org.jdbc.QueryExecutor;
import ogcg.org.jdbc.Result;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by oscar on 9/3/2017.
 */

@Stateless
@Remote(NbaRemoteInterface.class)
public class NBADataSource implements NbaRemoteInterface{

    @EJB
    DBConn d;

    public String[][] getPlayers() throws NamingException {
        Context context = new InitialContext();
//        DBConn Dcon = (DBConn)
//                context.lookup("java:module/" + DBConn.class.getSimpleName());
//            calculator = (CalculatorBean) context.lookup(CalculatorBean.class.getSimpleName() + "/no-interface");
        JDBCOConnection con = d.con;

        Connection c = d.getSQLConn();
        try {

            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT * from person ");
            String[][] result = getResult(rs);
            return result;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public String[][] getPlayersFromTeam(Integer team_id) throws NamingException{
        Context context = new InitialContext();
        System.out.println(""+team_id);
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
//        JDBCOConnection con = Dcon.con;
//        QueryExecutor qs = con.Query();
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT contract.contract_id as pId, contract.player_number as number, contract.team_id as tId, person.name as player_name FROM contract INNER JOIN person ON contract.person_id = person.person_id WHERE contract.team_id = " + team_id);
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.beforeFirst();
            List<String[]> rsArr = new Vector<>();
            List<String> columnNames = new Vector<>();
            for(Integer i = 0 ; i < rsmd.getColumnCount(); i++){
                columnNames.add(rsmd.getColumnName(i+1));
            }
            String[] cNames = columnNames.toArray(new String[rsmd.getColumnCount()]);
            rs.beforeFirst();
            for(Integer i = 0 ; rs.next() ; i++){
                String [] arr = new String[rsmd.getColumnCount()];
                for(Integer j = 0; j < rsmd.getColumnCount() ; j++){
                    arr[j] = rs.getObject(j+1).toString();
                }
                rsArr.add(arr);
            }
            if(rsArr.size() == 0){
                String[] x = {"", ""};
                rsArr.add(x);
            }
            String[][] r = rsArr.toArray(new String[rsArr.size()][rsmd.getColumnCount()]);
//            while(rs.next()){
//
//            }
//            Query q = qs.createQuery().select("contract.*,person.name").from("contract")
//                    .join("INNER","person")
//                    .on("contract.person_id","=","person.person_id")
//                    .where("contract.team_id","=",team_id.intValue());
//            qs.prepare(q);
//            Result res = qs.executeQuery().getResult();
//            String[][] result = res.getResultSet();
//            HashMap<String, String> columns = res.getColumns();
//            String[] columnNames = res.getColumnNames();
            return r;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    public Boolean createGame(int team_1, List<Integer> players_1, int team_2, List<Integer> players_2, int arena_id) throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        JDBCOConnection con = Dcon.con;
        Connection c = Dcon.getSQLConn();
        Date d = new Date();
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        String e = df.format(d);
        Integer game_id;
        try {
            /*qs.prepare(qs.createQuery().insertInto("game")
                    .column("arena_id",arena_id).column("game_date",d));
            qs.execute();
            qs.clear();*/
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String q = String.format("Insert into game(arena_id,game_date) values( '%d','%s' )", arena_id, e);
            System.out.println(q);
            int affected = stmt.executeUpdate(q,Statement.RETURN_GENERATED_KEYS);
            if(affected == 0){
                return false;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {

                if (generatedKeys.next()) {
                    game_id = generatedKeys.getInt(1);
                    q = String.format("Insert into game_teams(game_id,team_id) values( '%d', '%d'), ( '%d', '%d') ",game_id, team_1,game_id,team_2);
                    System.out.println(q);
                    stmt.executeUpdate(q,Statement.RETURN_GENERATED_KEYS);
                    for(Integer p1 : players_1){
                        int t = stmt.executeUpdate(String.format("Insert into game_rooster(game_id,contract_id) values( '%d','%d')", game_id, p1),Statement.RETURN_GENERATED_KEYS);
                        if( t == 0){
                            return false;
                        }
                    }
                    for(Integer p1 : players_2){
                        int t = stmt.executeUpdate(String.format("Insert into game_rooster(game_id,contract_id) values( '%d','%d')", game_id, p1),Statement.RETURN_GENERATED_KEYS);
                        if( t == 0){
                            return false;
                        }
                    }
                }
                else {
                    return false;
                }
            }
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
    }

    @Override
    public String[][] getTeams() throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        JDBCOConnection con = Dcon.con;
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String q = String.format("Select * from team");
            ResultSet res = stmt.executeQuery(q);
            String[][] result = getResult(res);
            return result;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }
    @Override
    public String[][] getArenas() throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        JDBCOConnection con = Dcon.con;
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            String q = String.format("select arena_id, name from arena");
            ResultSet res = stmt.executeQuery(q);
            String[][] result = getResult(res);
            return result;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }


    @Override
    public String[][] getTeamsOrderedByWins() throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        JDBCOConnection con = Dcon.con;
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
//            select  count(g.game_id) as co, t.name from game as g INNER JOIN game_end as e ON e.game_id = g.game_id INNER JOIN team as t ON t.team_id =  e.winning_team GROUP BY t.name,e.winning_team
            ResultSet rs = stmt.executeQuery("select  count(g.game_id) as count, t.name from game as g Left Join game_end as e ON e.game_id = g.game_id Right Join team as t ON t.team_id =  e.winning_team GROUP BY t.name,e.winning_team ORDER BY count DESC");
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.beforeFirst();
            List<String[]> rsArr = new Vector<>();
            List<String> columnNames = new Vector<>();
            for (Integer i = 0; i < rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
            String[] cNames = columnNames.toArray(new String[rsmd.getColumnCount()]);
            rs.beforeFirst();
            for (Integer i = 0; rs.next(); i++) {
                String[] arr = new String[rsmd.getColumnCount()];
                for (Integer j = 0; j < rsmd.getColumnCount(); j++) {
                    arr[j] = rs.getObject(j + 1).toString();
                }
                rsArr.add(arr);
            }
            if (rsArr.size() == 0) {
                String[] x = {"", ""};
                rsArr.add(x);
            }
            String[][] r = rsArr.toArray(new String[rsArr.size()][rsmd.getColumnCount()]);
//            while(rs.next()){
//
//            }
//            Query q = qs.createQuery().select("contract.*,person.name").from("contract")
//                    .join("INNER","person")
//                    .on("contract.person_id","=","person.person_id")
//                    .where("contract.team_id","=",team_id.intValue());
//            qs.prepare(q);
//            Result res = qs.executeQuery().getResult();
//            String[][] result = res.getResultSet();
//            HashMap<String, String> columns = res.getColumns();
//            String[] columnNames = res.getColumnNames();
            return r;
        } catch (Exception e1) {
            e1.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        JDBCOConnection con = Dcon.con;
        Connection c = Dcon.getSQLConn();
        try {
            c.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String[][] getTeamWithMostFouls() throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("        select team.name, SUM(r.fouls) from team Left JOIN game_teams as g on g.team_id = team.team_id Left JOIN game_rooster as r ON g.game_id = r.game_id GROUP BY team.name ORDER BY sum DESC LIMIT 1");
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.beforeFirst();
            List<String[]> rsArr = new Vector<>();
            List<String> columnNames = new Vector<>();
            for (Integer i = 0; i < rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
            String[] cNames = columnNames.toArray(new String[rsmd.getColumnCount()]);
            rs.beforeFirst();
            for (Integer i = 0; rs.next(); i++) {
                String[] arr = new String[rsmd.getColumnCount()];
                for (Integer j = 0; j < rsmd.getColumnCount(); j++) {
                    arr[j] = rs.getObject(j + 1).toString();
                }
                rsArr.add(arr);
            }
            if (rsArr.size() == 0) {
                String[] x = {"", ""};
                rsArr.add(x);
            }
            String[][] r = rsArr.toArray(new String[rsArr.size()][rsmd.getColumnCount()]);
//            while(rs.next()){
//
//            }
//            Query q = qs.createQuery().select("contract.*,person.name").from("contract")
//                    .join("INNER","person")
//                    .on("contract.person_id","=","person.person_id")
//                    .where("contract.team_id","=",team_id.intValue());
//            qs.prepare(q);
//            Result res = qs.executeQuery().getResult();
//            String[][] result = res.getResultSet();
//            HashMap<String, String> columns = res.getColumns();
//            String[] columnNames = res.getColumnNames();
            return r;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String[][] getGameData(Integer game_id) throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("Select g.game_id,g.game_date,arena.name,t.name,c.contract_id as pId, c.player_number as num,p.name as player_name, c.team_id as tId from game as g INNER JOIN arena ON arena.arena_id = g.arena_id INNER JOIN game_rooster as gr ON g.game_id = gr.game_id INNER JOIN contract as c ON c.contract_id = gr.contract_id INNER JOIN team as t ON t.team_id = c.team_id INNER JOIN person as p on p.person_id = c.person_id where g.game_id=" + game_id);
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.beforeFirst();
            List<String[]> rsArr = new Vector<>();
            List<String> columnNames = new Vector<>();
            for (Integer i = 0; i < rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
            String[] cNames = columnNames.toArray(new String[rsmd.getColumnCount()]);
            rs.beforeFirst();
            for (Integer i = 0; rs.next(); i++) {
                String[] arr = new String[rsmd.getColumnCount()];
                for (Integer j = 0; j < rsmd.getColumnCount(); j++) {
                    arr[j] = rs.getObject(j + 1).toString();
                }
                rsArr.add(arr);
            }
            if (rsArr.size() == 0) {
                String[] x = {"", ""};
                rsArr.add(x);
            }
            String[][] r = rsArr.toArray(new String[rsArr.size()][rsmd.getColumnCount()]);
//            while(rs.next()){
//
//            }
//            Query q = qs.createQuery().select("contract.*,person.name").from("contract")
//                    .join("INNER","person")
//                    .on("contract.person_id","=","person.person_id")
//                    .where("contract.team_id","=",team_id.intValue());
//            qs.prepare(q);
//            Result res = qs.executeQuery().getResult();
//            String[][] result = res.getResultSet();
//            HashMap<String, String> columns = res.getColumns();
//            String[] columnNames = res.getColumnNames();
            return r;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String[][] getGameWithMostFouls() throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("select g.game_id,arena.name, g.game_date, SUM(r.fouls) from game as g INNER JOIN arena ON arena.arena_id = g.arena_id INNER JOIN game_rooster as r ON g.game_id = r.game_id GROUP BY g.game_id, arena.name ORDER BY sum DESC LIMIT 1 ");
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.beforeFirst();
            List<String[]> rsArr = new Vector<>();
            List<String> columnNames = new Vector<>();
            for (Integer i = 0; i < rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
            String[] cNames = columnNames.toArray(new String[rsmd.getColumnCount()]);
            rs.beforeFirst();
            for (Integer i = 0; rs.next(); i++) {
                String[] arr = new String[rsmd.getColumnCount()];
                for (Integer j = 0; j < rsmd.getColumnCount(); j++) {
                    arr[j] = rs.getObject(j + 1).toString();
                }
                rsArr.add(arr);
            }
            if (rsArr.size() == 0) {
                String[] x = {"", ""};
                rsArr.add(x);
            }
            String[][] r = rsArr.toArray(new String[rsArr.size()][rsmd.getColumnCount()]);
//            while(rs.next()){
//
//            }
//            Query q = qs.createQuery().select("contract.*,person.name").from("contract")
//                    .join("INNER","person")
//                    .on("contract.person_id","=","person.person_id")
//                    .where("contract.team_id","=",team_id.intValue());
//            qs.prepare(q);
//            Result res = qs.executeQuery().getResult();
//            String[][] result = res.getResultSet();
//            HashMap<String, String> columns = res.getColumns();
//            String[] columnNames = res.getColumnNames();
            return r;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public Boolean insertGameResults(List<HashMap<String,Integer>> players,Integer game_id, Integer game_winner) throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        JDBCOConnection con = Dcon.con;
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.execute(String.format("INSERT INTO game_end(game_id,winning_team) VALUES ( '%d' , '%d')",game_id, game_winner));
            for(HashMap<String,Integer> p : players){
                String q = String.format("Update game_rooster SET two_points_shot= '%d' , three_points_shot = '%d' ," +
                                "rebound = '%d' , fouls = '%d' , " +
                                "block = '%d' , assist = '%d' " +
                                "WHERE game_id = '%d' AND contract_id = '%d' "
                                ,p.get("two"), p.get("three"),
                                p.get("reb"), p.get("f"),p.get("bl"),
                                p.get("as"),game_id,p.get("id")
                        );
                int affected = stmt.executeUpdate(q,Statement.RETURN_GENERATED_KEYS);
                if(affected == 0){
                    return false;
                }
            }
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean insertGameResults(List<HashMap<String, Integer>> players1, List<HashMap<String, Integer>> players2, Integer game_id, Integer game_winner) throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        JDBCOConnection con = Dcon.con;
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            stmt.execute(String.format("INSERT INTO game_end(game_id,winning_team) VALUES ( '%d' , '%d')",game_id, game_winner));
            for(HashMap<String,Integer> p : players1){
                String q = String.format("Update game_rooster SET two_points_shot= '%d' , three_points_shot = '%d' ," +
                                "rebound = '%d' , fouls = '%d' , " +
                                "block = '%d' , assist = '%d' " +
                                "WHERE game_id = '%d' AND contract_id = '%d' "
                        ,p.get("two"), p.get("three"),
                        p.get("reb"), p.get("f"),p.get("bl"),
                        p.get("as"),game_id,p.get("id")
                );
                int affected = stmt.executeUpdate(q,Statement.RETURN_GENERATED_KEYS);
                if(affected == 0){
                    return false;
                }
            }
            for(HashMap<String,Integer> p : players2){
                String q = String.format("Update game_rooster SET two_points_shot= '%d' , three_points_shot = '%d' ," +
                                "rebound = '%d' , fouls = '%d' , " +
                                "block = '%d' , assist = '%d' " +
                                "WHERE game_id = '%d' AND contract_id = '%d' "
                        ,p.get("two"), p.get("three"),
                        p.get("reb"), p.get("f"),p.get("bl"),
                        p.get("as"),game_id,p.get("id")
                );
                int affected = stmt.executeUpdate(q,Statement.RETURN_GENERATED_KEYS);
                if(affected == 0){
                    return false;
                }
            }
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
    }

    public String[][] getHighestPlayers() throws NamingException {
        Context context = new InitialContext();
        DBConn Dcon = (DBConn)
                context.lookup("java:module/" + DBConn.class.getSimpleName());
        Connection c = Dcon.getSQLConn();
        try {
            Statement stmt = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = stmt.executeQuery("SELECT contract.contract_id, person.name as player_name, SUM(g.two_points_shot) as twoP FROM contract INNER JOIN person ON contract.person_id = person.person_id INNER JOIN game_rooster g ON g.contract_id = contract.contract_id \n" +
                    "GROUP BY contract.contract_id, person.name ORDER BY twoP DESC LIMIT 3 ");
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.beforeFirst();
            List<String[]> rsArr = new Vector<>();
            List<String> columnNames = new Vector<>();
            for (Integer i = 0; i < rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
            String[] cNames = columnNames.toArray(new String[rsmd.getColumnCount()]);
            rs.beforeFirst();
            for (Integer i = 0; rs.next(); i++) {
                String[] arr = new String[rsmd.getColumnCount()];
                for (Integer j = 0; j < rsmd.getColumnCount(); j++) {
                    arr[j] = rs.getObject(j + 1).toString();
                }
                rsArr.add(arr);
            }
            if (rsArr.size() == 0) {
                String[] x = {"", ""};
                rsArr.add(x);
            }
            String[][] r = rsArr.toArray(new String[rsArr.size()][rsmd.getColumnCount()]);
//            while(rs.next()){
//
//            }
//            Query q = qs.createQuery().select("contract.*,person.name").from("contract")
//                    .join("INNER","person")
//                    .on("contract.person_id","=","person.person_id")
//                    .where("contract.team_id","=",team_id.intValue());
//            qs.prepare(q);
//            Result res = qs.executeQuery().getResult();
//            String[][] result = res.getResultSet();
//            HashMap<String, String> columns = res.getColumns();
//            String[] columnNames = res.getColumnNames();
            return r;
        } catch (SQLException e) {
            e.printStackTrace();
        }
            return null;
        }

        private String[][] getResult(ResultSet rs) throws SQLException {
            ResultSetMetaData rsmd = rs.getMetaData();
            rs.beforeFirst();
            List<String[]> rsArr = new Vector<>();
            List<String> columnNames = new Vector<>();
            for (Integer i = 0; i < rsmd.getColumnCount(); i++) {
                columnNames.add(rsmd.getColumnName(i + 1));
            }
            String[] cNames = columnNames.toArray(new String[rsmd.getColumnCount()]);
            rs.beforeFirst();
            for (Integer i = 0; rs.next(); i++) {
                String[] arr = new String[rsmd.getColumnCount()];
                for (Integer j = 0; j < rsmd.getColumnCount(); j++) {
                    arr[j] = rs.getObject(j + 1).toString();
                }
                rsArr.add(arr);
            }
            if (rsArr.size() == 0) {
                String[] x = {"", ""};
                rsArr.add(x);
            }
            String[][] r = rsArr.toArray(new String[rsArr.size()][rsmd.getColumnCount()]);
            return r;
        }
}
