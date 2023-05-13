package pharmacy;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;

/**
 * Scopul clasei UserInterface este de a ajuta utilizatorul sa vada produsele din magazin
 * Interfata prezinta o lista de medicamente detinuta de farmacie
 * impreuna cu 3 butoane folosite pentru a cauta in lista sau a merge
 * inapoi la pagina principala
 * @author Koczi Eduard
 *
 */

public class UserInterface extends JFrame implements ActionListener,DocumentListener {
	private static final long serialVersionUID = 1L;
	/**
	 * Panouri folosite pentru structura interfetei
	 * panelNorth,panelCenter,panelButtons etc.
	 */
	JPanel panelNorth;
	JPanel panelWest;
	JPanel panelEast;
	JPanel panelSouth;
	JPanel panelCenter;
	JPanel panelButtons;
	JPanel panelMain;
	/**
	 * Textfield folosit pentru a cauta
	 * searchText
	 */
	JTextField searchText;
	/**
	 * Butoane folosite pentru a naviga aplicatia
	 * backButton,resetButton etc.
	 */
	JButton backButton;
	JButton resetButton;
	JButton searchMedButton;
	JLabel searchMedsLabel;
	/**
	 * Tabel folosit pentru a afisa lista
	 * de medicamente (preluata dintr-o baza de date sql)
	 */
	JTable sqlTable;
	TableRowSorter sorter;
	List<Pill> pillList;
	String[] firstRow = {"ID","Nume","Cantitate","Pret","Companie"};
	
	/**
	 * In constructorul clasei se afla operatii asupra JFrame-ului
	 * pentru a oferi un design aplicatiei
	 */
	UserInterface (){
		
		panelNorth = new JPanel();
		panelWest = new JPanel();
		panelEast = new JPanel();
		panelSouth = new JPanel();
		panelCenter = new JPanel();
		panelButtons = new JPanel();
		panelMain = new JPanel();
		searchText = new JTextField();		
		backButton = new JButton("< Back");
		resetButton = new JButton("Reset");
		searchMedButton = new JButton("Search");
		searchMedsLabel = new JLabel("Search for meds:");
		pillList= PillRead();
		
		ImageIcon icon = new ImageIcon("image-large-pill-bottle-icon.jpg");
		this.setIconImage(icon.getImage());
		
		panelNorth.setBackground(Color.red);
		panelWest.setBackground(Color.red);
		panelEast.setBackground(Color.red);
		panelSouth.setBackground(Color.red);
		panelCenter.setBackground(Color.white);
		panelNorth.setPreferredSize(new Dimension(100,50));
		panelWest.setPreferredSize(new Dimension(50,100));
		panelEast.setPreferredSize(new Dimension(50,100));
		panelSouth.setPreferredSize(new Dimension(100,50));
		
		this.setTitle("BestFarma");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setSize(1050,850);
		this.setResizable(true);
		this.setVisible(true);
		
		this.add(panelNorth,BorderLayout.NORTH);
		this.add(panelWest,BorderLayout.WEST);
		this.add(panelEast,BorderLayout.EAST);
		this.add(panelSouth,BorderLayout.SOUTH);
		this.add(panelCenter,BorderLayout.CENTER);
		
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(panelMain,BorderLayout.NORTH);
		panelCenter.add(panelButtons,BorderLayout.CENTER);
		
		panelMain.setPreferredSize(new Dimension(100,600));
		panelMain.setBackground(Color.white);
		panelButtons.setBackground(Color.white);
		
		panelMain.setLayout(new GridBagLayout());
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 125, 50));
		
		searchText.setPreferredSize(new Dimension(180,25));
		searchText.getDocument().addDocumentListener(this);
		backButton.addActionListener(this);
		searchMedButton.addActionListener(this);
		resetButton.addActionListener(this);
		
		panelButtons.add(backButton);
		panelButtons.add(resetButton);
		//panelButtons.add(searchMedButton);
		panelButtons.add(searchMedsLabel);
		panelButtons.add(searchText);
		
		DefaultTableModel model = new DefaultTableModel(firstRow,0){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		for (Pill o : pillList) {
		    Object[] row = {o.getMedID(), o.getMedName(), o.getMedQTY(),o.getMedPrice(),o.getMedComp()};
		    model.addRow(row);
		}
		sqlTable = new JTable(model);
		sorter = new TableRowSorter<>(model);
		sqlTable.setRowSorter(sorter);
		JScrollPane scrollPane = new JScrollPane(sqlTable);
		panelMain.add(scrollPane);
		
		this.setLocationRelativeTo(null);
	}
	/**
	 * Metoda ActionPerformed este supraincarcata din interfata ActionListener
	 * pentru a adauga functionalitati butoanelor din JFrame
	 * Aici se face conexiunea la
	 * baza de date sql pentru a prelua date din tabel
	 */
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == backButton) {
			this.dispose();
			JFrameInterface frame = new JFrameInterface();
		}
		/*if (e.getSource() == searchMedButton) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement searchStmt = connection.prepareStatement("SELECT * FROM pill_table WHERE MED_NAME LIKE '%" +searchText.getText()+ "%'");
				//searchStmt.setString(1, searchText.getText());
				ResultSet resultSet = searchStmt.executeQuery();
				DefaultTableModel auxModel = new DefaultTableModel(firstRow,0);
				while (resultSet.next()) {
					Pill pill = new Pill(resultSet.getInt("MED_ID"),
							resultSet.getString("MED_NAME"),
							resultSet.getInt("MED_QTY"),
							resultSet.getDouble("MED_QTY"),
							resultSet.getString("MED_COMP"));
					
					Object[] row = {pill.getMedID(), pill.getMedName(), pill.getMedQTY(),pill.getMedPrice(),pill.getMedComp()};
					
					auxModel.addRow(row);
					sqlTable.setModel(auxModel);
				}
				
			}catch (Exception arg) {
				System.out.println(arg);
			}
		}*/
		if (e.getSource() == resetButton) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement searchStmt = connection.prepareStatement("SELECT * FROM pill_table");
				//searchStmt.setString(1, searchText.getText());
				ResultSet resultSet = searchStmt.executeQuery();
				DefaultTableModel auxModel = new DefaultTableModel(firstRow,0);
				while(resultSet.next()) {
					Pill pill = new Pill(resultSet.getInt("MED_ID"),
							resultSet.getString("MED_NAME"),
							resultSet.getInt("MED_QTY"),
							resultSet.getDouble("MED_PRICE"),
							resultSet.getString("MED_COMP"));
					Object[] row = {pill.getMedID(), pill.getMedName(), pill.getMedQTY(),pill.getMedPrice(),pill.getMedComp()};
					auxModel.addRow(row);
				}
				sorter = new TableRowSorter<>(auxModel);
				sqlTable.setRowSorter(sorter);
				sqlTable.setModel(auxModel);
				searchText.setText("");
			}catch (Exception arg) {
				System.out.println(arg);
			}
		}
	}
	/**
	 * Folosindu-ma de interfata DocumentListener am creat
	 * o metoda de a cauta in JTable
	 * @param text - cuvantul aflat in TextField
	 */
	public void insertUpdate(DocumentEvent e) {
        search(searchText.getText());
    }

    public void removeUpdate(DocumentEvent e) {
    	search(searchText.getText());
    }

    public void changedUpdate(DocumentEvent e) {}
	
    public void search(String text) {
        if (text.length() == 0) {
           sorter.setRowFilter(null);
        } else {
           sorter.setRowFilter(RowFilter.regexFilter(text));
        }
     }
    
    /**
     * Asa cum intuieste numele PillRead este o functie
     * care returneaza o lista de medicamente din
     * tabelul sql pill_table
     * @return pillList - returneaza o lista de medicamente
     */
    
	public List<Pill> PillRead(){
		List<Pill> pillList = new ArrayList<Pill>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
			PreparedStatement searchStmt = connection.prepareStatement("SELECT * FROM pill_table "); //WHERE MED_NAME = ?
			ResultSet resultSet = searchStmt.executeQuery();
			while(resultSet.next()) {
				Pill pill = new Pill(resultSet.getInt("MED_ID"),
						resultSet.getString("MED_NAME"),
						resultSet.getInt("MED_QTY"),
						resultSet.getDouble("MED_PRICE"),
						resultSet.getString("MED_COMP"));
				pillList.add(pill);
			}
			searchStmt.close();
			connection.close();
			
		}catch (Exception arg) {
			System.out.println(arg);
		}
		return pillList;
	}
	
}
