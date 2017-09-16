package osc.dist.nba;

import java.util.LinkedList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.MutableComboBoxModel;
import javax.swing.event.ListDataListener;

public class PlayerComboBoxModel extends DefaultComboBoxModel<Player> {

	PlayerComboBoxModel(Player[] players){
		super(players);
	}
	
	@Override
    public Player getSelectedItem() {
        Player selectedPlayer = (Player) super.getSelectedItem();
 
        // do something with this Player before returning...
 
        return selectedPlayer;
    }
	
}
