package osc.dist.nba;

import javax.swing.DefaultComboBoxModel;

public class TeamComboBoxModel extends DefaultComboBoxModel<Team> {
	TeamComboBoxModel(Team[] items) {
        super(items);
    }
 
    @Override
    public Team getSelectedItem() {
        Team selectedTeam = (Team) super.getSelectedItem();
 
        // do something with this Team before returning...
 
        return selectedTeam;
    }
}
