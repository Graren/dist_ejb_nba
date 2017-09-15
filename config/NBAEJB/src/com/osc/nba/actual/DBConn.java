package com.osc.nba.actual;

import ogcg.org.jdbc.JDBCOConnection;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Stateful;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by oscar on 9/3/2017.
 */
@Singleton
public class DBConn {
    public JDBCOConnection con;

    public JDBCOConnection getCon() {
        return con;
    }

    public void setCon(JDBCOConnection con) {
        this.con = con;
    }

    @PostConstruct
    public void init(){
        con = new JDBCOConnection("postgresql", 5432, "nba", "postgres", "masterkey", "localhost");
        con.startConnection();
        System.out.println("INSTANCE INITIALIZED");
    }

    public Connection getSQLConn(){
        return con.getConn();
    }

    @PreDestroy
    public void close() {
        try {
            getSQLConn().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
