package osc.dist.nba;

public class Team {
	String team_name;
	Integer team_id;
	/**
	 * @return the team_name
	 */
	public String getTeam_name() {
		return team_name;
	}
	/**
	 * @param team_name the team_name to set
	 */
	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}
	/**
	 * @return the team_id
	 */
	public Integer getTeam_id() {
		return team_id;
	}
	/**
	 * @param team_id the team_id to set
	 */
	public void setTeam_id(Integer team_id) {
		this.team_id = team_id;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return team_name ;
	}
	
	
}
