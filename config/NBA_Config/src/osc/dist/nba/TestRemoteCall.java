package osc.dist.nba;

import static org.junit.Assert.*;

import javax.naming.NamingException;

import org.junit.Test;

import com.osc.nba.actual.NbaRemoteInterface;

public class TestRemoteCall {

	@Test
	public void test() {
		NbaRemoteInterface nba = DataSourceOsc.Helper.oinstance.getNba();
		String[][] arr = null;
		try {
			arr = nba.getPlayers();
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String[] row : arr){
			for(String st : row){
				System.out.println(st);
			}
		}
	}
	
	@Test
	public void test2() {
		NbaRemoteInterface nba = DataSourceOsc.Helper.oinstance.getNba();
		String[][] arr = null;
		try {
			arr = nba.getTeams();
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(String[] row : arr){
			for(String st : row){
				System.out.println(st);
			}
		}
	}

}
