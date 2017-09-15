package com.osc.nba.test;

import ogcg.org.damson.Damson;
import ogcg.org.jdbc.JDBCOConnection;
import ogcg.org.jdbc.QueryExecutor;
import ogcg.org.jdbc.Result;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by oscar on 9/1/2017.
 */
@Stateless
@Remote(SomeThing.class)
public class SomeImpl implements SomeThing {
    @EJB
    private CalculatorBean simpleCalculator;
    @Override
    public Integer sum(Integer a, Integer b) {
        int currentBalance = 100;
        CalculatorBean calculator = null;
        // lookup the no-interface view of the Calculator
        // We could have used the injected Calculator too, but
        // in this method we wanted to demonstrate how to lookup an no-interface view
        try {
            Context context = new InitialContext();
            calculator = (CalculatorBean)
                    context.lookup("java:module/" + CalculatorBean.class.getSimpleName());
//            calculator = (CalculatorBean) context.lookup(CalculatorBean.class.getSimpleName() + "/no-interface");
            return calculator.add(a, b);
        } catch (NamingException ne) {
            throw new RuntimeException("Could not lookup no-interface view of calculator: ", ne);
        }
    }

    public int debit(int a, int b)
    {
        if(a < b){
            return 0;
        }
        // get current account balance of this account number, from DB.
        // But for this example let's just hardcode it
        // let's use the injected calculator
        return this.simpleCalculator.subtract(a, b);
    }

    @Override
    public boolean something() {
        JDBCOConnection con = new JDBCOConnection("postgresql", 5432, "EmpresaDeNerio", "postgres", "masterkey", "localhost");
        con.startConnection();
        QueryExecutor qs = con.Query();
        try {
            qs.prepare(qs.createQuery().select("*").from("cliente").join("Inner", "factura").on("cliente.idcliente", "=", "factura.idcliente")
                    .join("INNER", "vendedor").on("factura.idvendedor", "=", "vendedor.idvendedor")
                    .where("cliente.idcliente", "<", 8001).or("cliente.idcliente", "=", 8002));
            Result res = qs.executeQuery().getResult();
            String[][] result = res.getResultSet();
            HashMap<String, String> columns = res.getColumns();
            String[] columnNames = res.getColumnNames();
//            for (int i = 0; i < columnNames.length; i++) {
//                System.out.println(columnNames[i]);
//            }
//            for (Map.Entry<String, String> k : columns.entrySet()) {
//                System.out.println(k.getKey() + ":" + k.getValue());
//            }
//            for (int i = 0; i < result.length; i++) {
//                for (int j = 0; j < result[0].length; j++) {
//                    System.out.println(result[i][j]);
//                }
//            }
            Damson d = new Damson();
            d.addElem("DAM", columnNames, result);
            System.out.println(d);
            return true;
        } catch (Exception e1) {
            e1.printStackTrace();
            return false;
        }
    }

    }
