package osc.dist.nba;

public class Arena {
	Integer arena_id;
	String arena_name;
	/**
	 * @return the arena_id
	 */
	public Integer getArena_id() {
		return arena_id;
	}
	/**
	 * @param arena_id the arena_id to set
	 */
	public void setArena_id(Integer arena_id) {
		this.arena_id = arena_id;
	}
	/**
	 * @return the arena_name
	 */
	public String getArena_name() {
		return arena_name;
	}
	/**
	 * @param arena_name the arena_name to set
	 */
	public void setArena_name(String arena_name) {
		this.arena_name = arena_name;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return arena_name ;
	}
}
