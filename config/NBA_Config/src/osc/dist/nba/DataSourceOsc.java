package osc.dist.nba;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.osc.nba.actual.NBADataSource;
import com.osc.nba.actual.NbaRemoteInterface;

public class DataSourceOsc {

    NbaRemoteInterface nba;

    public static class Helper {
        public static DataSourceOsc oinstance = new DataSourceOsc();
    }

    DataSourceOsc(){

    final String appName = "";
    // This is the module name of the deployed EJBs on the server. This is typically the jar name of the
    // EJB deployment, without the .jar suffix, but can be overridden via the ejb-jar.xml
    // In this example, we have deployed the EJBs in a jboss-as-ejb-remote-app.jar, so the module name is
    // jboss-as-ejb-remote-app
    final String moduleName = "NBAEJB2";
    // AS7 allows each deployment to have an (optional) distinct name. We haven't specified a distinct name for
    // our EJB deployment, so this is an empty string
    final String distinctName = "";
    // The EJB name which by default is the simple class name of the bean implementation class
//        final String beanName = SomeImpl.class.getSimpleName();
    // the remote view fully qualified class name
//        final String viewClassName = SomeThing.class.getName();
    // let's do the lookup
    // lookup the account manager bean

        try {
        Properties p = new Properties();
        PropertiesOscar po = new PropertiesOscar();
        Context context = new InitialContext(po.getProperties());

        System.out.println(context.getEnvironment().toString());
//            Context envContext = (Context) context.lookup("java:comp/env");
        nba = (NbaRemoteInterface) context.lookup( appName + "/" + moduleName + "/" + distinctName + "/" + NBADataSource.class.getSimpleName() + "!" + NbaRemoteInterface.class.getName());
        } catch (NamingException ne) {
        	throw new RuntimeException("Could not lookup AccountManagerBean: ", ne);
        }
    }

    public NbaRemoteInterface getNba() {
        return nba;
    }

    public void setNba(NbaRemoteInterface nba) {
        this.nba = nba;
    }
}

