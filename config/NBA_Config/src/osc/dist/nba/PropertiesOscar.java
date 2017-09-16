package osc.dist.nba;

import java.util.Properties;

public class PropertiesOscar {

    public Properties p;
    PropertiesOscar(){
        p = new Properties();
        p.put("java.naming.factory.initial","org.jboss.naming.remote.client.InitialContextFactory");
        p.put("jboss.naming.client.ejb.context",true);
        p.put("java.naming.factory.url.pkgs","org.jboss.ejb.client.naming");
        p.put("java.naming.provider.url","http-remoting://localhost:9080");
    }

    public Properties getProperties(){
        return p;
    }

}