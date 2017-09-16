package osc.dist.nba;

import javax.swing.DefaultComboBoxModel;

public class ArenaComboBoxModel extends DefaultComboBoxModel<Arena> {
	
	ArenaComboBoxModel(Arena[] items) {
        super(items);
    }
 
    @Override
    public Arena getSelectedItem() {
        Arena selectedArena = (Arena) super.getSelectedItem();
 
        // do something with this Arena before returning...
 
        return selectedArena;
    }
}
