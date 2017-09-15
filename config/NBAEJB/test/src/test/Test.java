package test;

import com.osc.nba.actual.NBADataSource;
import com.osc.nba.actual.NbaRemoteInterface;
import com.osc.nba.actual.Player;
import com.osc.nba.test.SomeImpl;
import com.osc.nba.test.SomeThing;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Created by oscar on 9/1/2017.
 */
public class Test {
    public static SomeThing accountManager;
    public static NbaRemoteInterface nba;

    @BeforeAll
    public static void doBefore() {
        final String appName = "";
        // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
        // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
        // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
        // jboss-as-ejb-remote-app
        final String moduleName = "NBAEJB3";
        // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
        // our EJB deployment, so this is an empty string
        final String distinctName = "";
        // The EJB name which by default is the simple class name of the bean implementation class
        final String beanName = SomeImpl.class.getSimpleName();
        // the remote view fully qualified class name
        final String viewClassName = SomeThing.class.getName();
        // let's do the lookup
        // lookup the account manager bean

        System.out.println(InitialContextFactory.class.getName());
        try {
            Context context = new InitialContext();
            accountManager = (SomeThing) context.lookup(appName + "/" + moduleName + "/" + distinctName + "/" + beanName + "!" + viewClassName);
            nba = (NbaRemoteInterface) context.lookup(appName + "/" + moduleName + "/" + distinctName + "/" + NBADataSource.class.getSimpleName() + "!" + NbaRemoteInterface.class.getName());
        } catch (NamingException ne) {
            throw new RuntimeException("Could not lookup AccountManagerBean: ", ne);
        }
    }

    @org.junit.jupiter.api.Test
    public void Sum() {
        // credit 50 dollars (Note that the current balance is hard coded in the bean to 100)
        // so after crediting, the current balance is going to be 150
        int currentBalance = accountManager.sum(2, 50);

        assertEquals(52, currentBalance);
        // now let's debit 10 dollars (Note that the current balance is again hard coded in the bean to 100).
        // So after debiting, the current balance is going to be 90
    }

    @org.junit.jupiter.api.Test
    public void Substract() {
        int currentBalance;
        currentBalance = accountManager.debit(20, 10);

        assertEquals(10, currentBalance);
    }

    @org.junit.jupiter.api.Test
    public void SomethingDB() {
        Boolean b = accountManager.something();

        assertEquals(true, b);
    }

    @org.junit.jupiter.api.Test
    public void getPlayers() {
        String[][] arr = new String[0][];
        try {
            arr = nba.getPlayers();
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, arr.length > 1);
    }


    @org.junit.jupiter.api.Test
    public void getPlayersFromTeam() {
        String[][] arr = new String[0][];
        try {
            arr = nba.getPlayersFromTeam(1);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, arr.length > 1);
    }

    @org.junit.jupiter.api.Test
    public void insertTeam() {
        Boolean b = false;
        try {
            List<Integer> l = new Vector<>();
            l.add(1);
            l.add(6);
            l.add(3);
            l.add(4);
            l.add(5);

            List<Integer> l2 = new Vector<>();
            l2.add(11);
            l2.add(7);
            l2.add(8);
            l2.add(9);
            l2.add(10);
            b = nba.createGame(3, l, 1, l2, 1);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, b);
    }

    @org.junit.jupiter.api.Test
    public void getTeams() {
        String[][] arr = new String[0][];
        try {
            arr = nba.getTeams();
            for (String[] row : arr) {
                for (String col : row) {
                    System.out.println(col);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, arr.length > 1);
    }

    @org.junit.jupiter.api.Test
    public void endGameTest() {
        Boolean b = false;
        Integer game_id = 73;
        try {
            List<Player> players = generatePlayers(15);
            List<HashMap<String, Integer>> map = new Vector<>();
            for (Player p : players) {
                map.add(p.toMap());
            }
            b = nba.insertGameResults(map, game_id, 3);
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, b);
    }

    @org.junit.jupiter.api.Test
    public void getHighestPlayers() {
        String[][] arr = new String[0][];
        try {
            arr = nba.getHighestPlayers();
            for (String[] row : arr) {
                for (String col : row) {
                    System.out.println(col);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, arr.length == 3);
    }

    @org.junit.jupiter.api.Test
    public void getTeamsByWins() {
        String[][] arr = new String[0][];
        try {
            arr = nba.getTeamsOrderedByWins();
            for (String[] row : arr) {
                for (String col : row) {
                    System.out.println(col);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, arr.length > 1);
    }

    @org.junit.jupiter.api.Test
    public void getTeamWithMostFouls() {
        String[][] arr = new String[0][];
        try {
            arr = nba.getTeamWithMostFouls();
            for (String[] row : arr) {
                for (String col : row) {
                    System.out.println(col);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, arr.length == 1);
    }

    @org.junit.jupiter.api.Test
    public void getGameWithMostFouls() {
        String[][] arr = new String[0][];
        try {
            arr = nba.getGameWithMostFouls();
            for (String[] row : arr) {
                for (String col : row) {
                    System.out.println(col);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, arr.length == 1);
    }

    @org.junit.jupiter.api.Test
    public void getGameData() {
        String[][] arr = new String[0][];
        try {
            arr = nba.getGameData(74);
            for (String[] row : arr) {
                for (String col : row) {
                    System.out.println(col);
                }
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
        assertEquals(true, arr.length == 1);
    }


    public List<Player> generatePlayers(Integer game_id) {
        Random rnd = new Random(System.currentTimeMillis());
        List<Player> players = new Vector<>();
        Integer[] ids = {1, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        Vector<Integer> idList = new Vector<>(Arrays.asList(ids));
        for (Integer i : idList) {
            Player s = new Player(i, game_id, rnd.nextInt(100), rnd.nextInt(100), rnd.nextInt(100), rnd.nextInt(100), rnd.nextInt(100), rnd.nextInt(100));
            players.add(s);
        }
        return players;
    }

    @AfterAll
    public static void close() {
        try {
            System.out.println("CERRANDO");
            nba.close();
        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}

