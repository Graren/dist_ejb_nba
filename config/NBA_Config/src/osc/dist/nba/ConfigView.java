package osc.dist.nba;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


import net.miginfocom.swing.MigLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.naming.NamingException;
import javax.swing.JButton;
import javax.swing.JTable;

public class ConfigView extends JFrame {

	private JPanel contentPane;
	private JTable table;
	private JTable table_1;
	private String[] columns = {"nombre", "numero"};
	private List<Player> players;
	private JComboBox<Player> comboBox_3;
	private JComboBox<Player> comboBox_2;
	private JComboBox<Team> comboBox_1;
	private JComboBox<Team> comboBox;
	private List<Player> selectedPlayers1 = new LinkedList();
	private List<Player> selectedPlayers2 = new LinkedList();
	private JComboBox<Arena> comboBox_4;
	
	List<Integer> status = new ArrayList<>();
	Integer status1;
	Integer status2;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConfigView frame = new ConfigView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConfigView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 824, 597);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[center][grow,center][grow,center][grow][grow][grow][grow,center][grow,center][grow,center][grow,center][center]", "[grow][38.00][36.00,center][45.00,center][grow][grow][grow][grow][grow][grow][grow][grow]"));
		
		JLabel lblTeam = new JLabel("Team 1");
		lblTeam.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblTeam, "cell 1 1,alignx center");
		
		comboBox = new JComboBox();
		contentPane.add(comboBox, "cell 2 1 4 1,growx");
		
		JLabel lblTeam_1 = new JLabel("Team 2");
		lblTeam_1.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(lblTeam_1, "cell 6 1,alignx center");
		
		comboBox_1 = new JComboBox();
		contentPane.add(comboBox_1, "cell 7 1 3 1,growx");
		
		JLabel lblPlayer = new JLabel("Player T 1");
		contentPane.add(lblPlayer, "cell 1 2,alignx center");
		
		comboBox_2 = new JComboBox<>();
		contentPane.add(comboBox_2, "cell 2 2 4 1,growx");
		
		JLabel lblPlayerForTeam = new JLabel("Player T 2");
		contentPane.add(lblPlayerForTeam, "cell 6 2");
		
		comboBox_3 = new JComboBox<>();
		contentPane.add(comboBox_3, "cell 7 2 3 1,growx");
		
		
		DefaultTableModel t2 = new DefaultTableModel();
		t2.setColumnIdentifiers(columns);
		
		table_1 = new JTable(t2);
		contentPane.add(table_1, "cell 6 4 4 5,grow");
		
		DefaultTableModel t1 = new DefaultTableModel();
		t1.setColumnIdentifiers(columns);
		
		table = new JTable(t1);
		contentPane.add(table, "cell 1 4 4 5,grow");
		
		JButton btnAddPlayer = new JButton("Add Player");
		contentPane.add(btnAddPlayer, "cell 1 3,alignx center");
		btnAddPlayer.addActionListener((e) -> {
			if(t1.getRowCount() < 5){
				Player pe = (Player)comboBox_2.getSelectedItem();
				String name = pe.getPlayer_name();
				Integer number = pe.getPlayer_number();
				Object[] obj = new Object[] { name, number };
				t1.addRow(obj);
				comboBox_2.setSelectedIndex(comboBox_2.getSelectedIndex() + 1);
				selectedPlayers1.add(pe);
			}
		});
		
		JButton btnAddPlayer_1 = new JButton("Add Player");
		contentPane.add(btnAddPlayer_1, "cell 6 3");
		btnAddPlayer_1.addActionListener((e) -> {
			if(t2.getRowCount() < 5){
				Player pe = (Player)comboBox_3.getSelectedItem();
				String name = pe.getPlayer_name();
				Integer number = pe.getPlayer_number();
				Object[] obj = new Object[] { name, number };
				t2.addRow(obj);
				comboBox_3.setSelectedIndex(comboBox_3.getSelectedIndex() + 1);
				selectedPlayers2.add(pe);
			}
		});
		
		JLabel lblNewLabel = new JLabel("Arena");
		contentPane.add(lblNewLabel, "cell 1 10");
		
		comboBox_4 = new JComboBox<Arena>();
		contentPane.add(comboBox_4, "cell 2 10 4 1,growx");
		
		JButton btnNewButton = new JButton("Create");
		contentPane.add(btnNewButton, "cell 7 10 3 1,grow");
		btnNewButton.addActionListener((e) -> {
//			selectedPlayers1.forEach((p) -> {
//				System.out.println(p.player_name);
//			});
//			selectedPlayers2.stream().map((Player pla) -> {
//				pla.setPlayer_name("HI " + pla.getPlayer_number());
//				return pla;
//			}).collect(Collectors.toList()).forEach((sex) -> {
//				System.out.println(sex.player_name);
//			});
			Arena a = (Arena) comboBox_4.getSelectedItem();
			System.out.println(a.arena_name);
			List<Integer> p1 = new ArrayList<>();
			for(Player p : selectedPlayers1){
				p1.add(p.getContract_id());
			}
			List<Integer> p2 = new ArrayList<>();
			for(Player p : selectedPlayers2){
				p2.add(p.getContract_id());
			}
			
			try {
				Boolean b = DataSourceOsc.Helper.oinstance.nba.createGame(((Team)comboBox.getSelectedItem()).team_id, p1, ((Team)comboBox_1.getSelectedItem()).team_id, p2,a.arena_id );
				System.out.println(b.toString());
			} catch (NamingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		onFinish();
	}
	
	public void onFinish(){
		 String[][] arr = null;
		 String[][] arenaArr = null;
		 try {
			arr = DataSourceOsc.Helper.oinstance.nba.getTeams();
			arenaArr = DataSourceOsc.Helper.oinstance.nba.getArenas();
			for(int i = 0 ; i < arr.length; i++){
				status.add(i);
			}
			System.out.println(status.toString());
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 ArenaComboBoxModel am = new ArenaComboBoxModel(buildArena(arenaArr).toArray(new Arena[0]));
		 comboBox_4.setModel(am);
		 List<Team> teams = buildTeams(arr);
		 comboBox.addActionListener((e) -> {
			// TODO Auto-generated method stub
			 	
				JComboBox cb = (JComboBox)e.getSource();
				Team te = (Team) cb.getSelectedItem();
				
				status1 = cb.getSelectedIndex();
				System.out.println(status1);
				if(status1 == status2){
					if(status1 == status.get(0)){
						cb.setSelectedIndex(status1 + 1 );
					}
					else{
						cb.setSelectedIndex(status1 - 1 );
					}
					return;
				}
				try {
					String[][] ar = DataSourceOsc.Helper.oinstance.nba.getPlayersFromTeam(te.team_id);
					PlayerComboBoxModel pm = new PlayerComboBoxModel(buildPlayers(ar).toArray(new Player[0]));
					comboBox_2.setModel(pm);
					comboBox_2.setSelectedIndex(0);
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 });
		 comboBox_1.addActionListener((e) -> {
				// TODO Auto-generated method stub
				JComboBox cb = (JComboBox)e.getSource();
				Team te = (Team) cb.getSelectedItem();
				status2 = cb.getSelectedIndex();
				System.out.println(status2);
				if(status1 == status2){
					if(status2 == status.get(0)){
						cb.setSelectedIndex(status2 + 1 );
					}
					else{
						cb.setSelectedIndex(status2 - 1 );
					}
				}
				try {
					String[][] ar = DataSourceOsc.Helper.oinstance.nba.getPlayersFromTeam(te.team_id);
					PlayerComboBoxModel pm = new PlayerComboBoxModel(buildPlayers(ar).toArray(new Player[0])); 
					comboBox_3.setModel(pm);
					comboBox_3.setSelectedIndex(0);
				} catch (NamingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		 });
		 comboBox_2.addActionListener((e) -> {
			 JComboBox cb = (JComboBox)e.getSource();
			 Player pe = (Player) cb.getSelectedItem();
			 System.out.println(pe.player_name);
		 });
		 comboBox_3.addActionListener((e) -> {
			 JComboBox cb = (JComboBox)e.getSource();
			 Player pe = (Player) cb.getSelectedItem();
			 System.out.println(pe.player_name);
		 });
		 TeamComboBoxModel t = new TeamComboBoxModel(teams.toArray(new Team[0]));
		 TeamComboBoxModel t2 = new TeamComboBoxModel(teams.toArray(new Team[0]));
		 comboBox.setModel(t);
		 comboBox_1.setModel(t2);
		 comboBox.setSelectedIndex(0);
		 comboBox_1.setSelectedIndex(1);
	}
	
	public List<Team> buildTeams(String[][] arr){
		List<Team> teams = new LinkedList<>();
		for(String[] a : arr){
			 Team t = new Team();
			 t.setTeam_id(Integer.valueOf(a[0]));
			 t.setTeam_name(a[2]);
			 teams.add(t);
		 }
		return teams;
	}
	
	public List<Arena> buildArena(String[][] arr){
		List<Arena> teams = new LinkedList<>();
		for(String[] a : arr){
			 Arena t = new Arena();
			 t.setArena_id(Integer.valueOf(a[0]));
			 t.setArena_name(a[1]);
			 teams.add(t);
		 }
		return teams;
	}
	
	public List<Player> buildPlayers(String[][] arr){
		List<Player> teams = new LinkedList<>();
		for(String[] a : arr){
			 Player t = new Player();
			 t.setContract_id(Integer.valueOf(a[0]));
			 t.setPlayer_number(Integer.valueOf(a[1]));
			 t.setTeam_id(Integer.valueOf(a[2]));
			 t.setPlayer_name(a[3]);
			 teams.add(t);
		 }
		return teams;
	}

}
