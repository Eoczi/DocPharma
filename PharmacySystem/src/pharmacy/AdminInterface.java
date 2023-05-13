package pharmacy;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Scopul clasei AdminInterface este de a permite unui utilizator administrator
 * sa modifice date asupra listei de medicamente
 * Interfata prezinta 6 butoane care te lasa sa navighezi intre interfete
 * sau sa adaugi/stergi medicamente, 5 campuri ajutatoare pentru operatii asupra tabelului
 * si tabelul in sine
 * @author Koczi Eduard
 *
 */

public class AdminInterface extends JFrame implements ActionListener,MouseListener {
	private static final long serialVersionUID = 1L;
	
	JPanel panelNorth;
	JPanel panelWest;
	JPanel panelEast;
	JPanel panelSouth;
	JPanel panelCenter;
	JPanel panelButtons;
	JPanel panelMain;
	JPanel panelTextFields;
	JPanel panelSQLTable; // boxLayout?
	JLabel medIDLabel;
	JLabel medNameLabel;
	JLabel medQtyLabel;
	JLabel medPriceLabel;
	JLabel medCompLabel;
	/**
	 * Textfielduri ajutatoare
	 * medIDText,medNameText etc.
	 */
	JTextField medIDText;
	JTextField medNameText;
	JTextField medQtyText;
	JTextField medPriceText;
	JTextField medCompText;
	/**
	 * Butoane folosite pentru a naviga aplicatia
	 * backButton,addButton etc.
	 */
	JButton backButton;
	JButton addButton;
	JButton removeButton;
	JButton resetButton;
	JButton searchButton;
	JButton nextButton;
	TableRowSorter sorter;
	DefaultTableModel model;
	/**
	 * Tabel folosit pentru a afisa lista
	 * de medicamente(preluata dintr-o baza de date sql)
	 */
	JTable sqlTable;
	List<Pill> pillList;
	List<JTextField> searchTexts;
	String[] firstRow = {"ID","Nume","Cantitate","Pret","Companie"};
	String[] collumns = {"MED_ID","MED_NAME","MED_QTY","MED_PRICE","MED_COMP"};
	/**
	 * In constructorul clasei se afla operatii asupra JFrame-ului
	 * pentru a oferi un design aplicatiei
	 */
	AdminInterface (){
		
		panelNorth = new JPanel();
		panelWest = new JPanel();
		panelEast = new JPanel();
		panelSouth = new JPanel();
		panelCenter = new JPanel();
		panelButtons = new JPanel();
		panelMain = new JPanel();
		panelTextFields= new JPanel();
		panelSQLTable= new JPanel();
		medIDLabel = new JLabel("ID: ");
		medNameLabel = new JLabel("Name: ") ;
		medQtyLabel = new JLabel("Quantity: ") ;
		medPriceLabel = new JLabel("Price: ") ;
		medCompLabel=new JLabel("Company: ");
		medIDText =new JTextField ();
		medNameText =new JTextField ();
		medQtyText =new JTextField ();
		medPriceText=new JTextField();
		medCompText=new JTextField();
		backButton = new JButton("< Back");
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		resetButton = new JButton("Clear");
		searchButton= new JButton("Search");
		nextButton = new JButton("Next");
		sqlTable = new JTable();
		pillList= PillRead();
		searchTexts = TextsRead();
		
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
		
		panelMain.setLayout(new GridLayout(1,2));
		panelMain.setPreferredSize(new Dimension(100,550));
		panelMain.setBackground(Color.white);
		panelButtons.setBackground(Color.white);
		
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(panelMain,BorderLayout.NORTH);
		panelCenter.add(panelButtons,BorderLayout.CENTER);
		
		panelMain.add(panelTextFields);
		panelMain.add(panelSQLTable);
		panelTextFields.setBackground(Color.white);
		panelSQLTable.setBackground(Color.white);
		
		panelTextFields.setLayout(new GridLayout(5,2));
		for (int i = 0; i <= 9; i++) {
		      JPanel panel = new JPanel();
		      panel.setBackground(Color.white);
		      panel.setLayout(new GridBagLayout());
		      if (i==0)
		    	  panel.add(medIDLabel);
		      else if (i==1)
		    	  panel.add(medIDText);
		      else if (i==2)
		    	  panel.add(medNameLabel);
		      else if (i==3)
		    	  panel.add(medNameText);
		      else if (i==4)
		    	  panel.add(medQtyLabel);
		      else if (i==5)
		    	  panel.add(medQtyText);
		      else if (i==6)
		    	  panel.add(medPriceLabel);
		      else if (i==7)
		    	  panel.add(medPriceText);
		      else if (i==8)
		    	  panel.add(medCompLabel);
		      else
		    	  panel.add(medCompText);
		      //panel.setBackground(Color.BLUE);
		      panelTextFields.add(panel);
		}
		
		medIDText.setPreferredSize(new Dimension(200,20));
		medNameText.setPreferredSize(new Dimension(200,20));
		medQtyText.setPreferredSize(new Dimension(200,20));
		medPriceText.setPreferredSize(new Dimension(200,20));
		medCompText.setPreferredSize(new Dimension(200,20));
		medIDLabel.setFont(new Font(medIDLabel.getFont().getName(), medIDLabel.getFont().getStyle(), 19));
		medNameLabel.setFont(new Font(medNameLabel.getFont().getName(), medNameLabel.getFont().getStyle(), 19));
		medQtyLabel.setFont(new Font(medQtyLabel.getFont().getName(), medQtyLabel.getFont().getStyle(), 19));
		medPriceLabel.setFont(new Font(medPriceLabel.getFont().getName(), medPriceLabel.getFont().getStyle(), 19));
		medCompLabel.setFont(new Font(medCompLabel.getFont().getName(), medCompLabel.getFont().getStyle(), 19));
		
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 50));
		
		backButton.addActionListener(this);
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		resetButton.addActionListener(this);
		searchButton.addActionListener(this);
		nextButton.addActionListener(this);
		
		panelButtons.add(backButton);
		panelButtons.add(addButton);
		panelButtons.add(removeButton);
		panelButtons.add(searchButton);
		panelButtons.add(resetButton);
		panelButtons.add(nextButton);
		
		model = new DefaultTableModel(firstRow,0){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		sqlTable = new JTable(model);
		for (Pill o : pillList) {
		    Object[] row = {o.getMedID(), o.getMedName(), o.getMedQTY(),o.getMedPrice(),o.getMedComp()};
		    model.addRow(row);
		}
		sqlTable.addMouseListener(this);
		JScrollPane scrollPane = new JScrollPane(sqlTable);
		panelSQLTable.add(scrollPane);
		
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
		
		if (e.getSource() == searchButton) {
			int index=0;
			for (int i=0 ; i< searchTexts.size() ; i++) {
				if (searchTexts.get(i).getText().equals("") || searchTexts.get(i).getText().equals(null)){
					index++;
				}
				else {
					break;
				}
			}
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement searchStmt = connection.prepareStatement("SELECT * FROM pill_table WHERE " + collumns[index] + "='" + searchTexts.get(index).getText()+ "'");
				DefaultTableModel auxModel = new DefaultTableModel(firstRow,0);
				ResultSet resultSet = searchStmt.executeQuery();
				while (resultSet.next()) {
					Pill pill = new Pill(resultSet.getInt("MED_ID"),
							resultSet.getString("MED_NAME"),
							resultSet.getInt("MED_QTY"),
							resultSet.getDouble("MED_PRICE"),
							resultSet.getString("MED_COMP"));
					Object[] row = {pill.getMedID(), pill.getMedName(), pill.getMedQTY(),pill.getMedPrice(),pill.getMedComp()};
					auxModel.addRow(row);
				}
				sqlTable.setModel(auxModel);
				
				searchStmt.close();
				connection.close();
			}catch (Exception arg) {
				System.out.println(arg);
			}
		}
		
		if (e.getSource() == addButton) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement addStmt = connection.prepareStatement("INSERT INTO pill_table "
						+ "(MED_ID, "
						+ "MED_NAME, "
						+ "MED_QTY, "
						+ "MED_PRICE, "
						+ "MED_COMP) "
						+ "VALUES(?,?,?,?,?)");
				addStmt.setInt(1,Integer.parseInt(medIDText.getText()));
				addStmt.setString(2, medNameText.getText());
				addStmt.setInt(3,Integer.parseInt(medQtyText.getText()));
				addStmt.setDouble(4,Double.parseDouble(medPriceText.getText()));
				addStmt.setString(5,medCompText.getText());
				addStmt.executeUpdate();
				JOptionPane.showMessageDialog(null, "ai adaugat un medicament");
				
				Pill pill = new Pill(Integer.parseInt(medIDText.getText()),
						medNameText.getText(), 
						Integer.parseInt(medQtyText.getText()), 
						Double.parseDouble(medPriceText.getText()),
						medCompText.getText());
				Object[] row = {pill.getMedID(), pill.getMedName(), pill.getMedQTY(),pill.getMedPrice(),pill.getMedComp()};
				model.addRow(row);
				
				addStmt.close();
				connection.close();
			}catch (Exception arg) {
				System.out.println(arg);
			}
		}
		
		if (e.getSource() == removeButton) {
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement removeStmt = connection.prepareStatement("DELETE FROM pill_table"
						+ " WHERE MED_ID = ?"
						+ " AND MED_NAME = ?"
						+ " AND MED_QTY = ?"
						//+ " AND MED_PRICE = ?"
						+ " AND MED_COMP = ?");
				removeStmt.setString(1, medIDText.getText());
				removeStmt.setString(2, medNameText.getText());
				removeStmt.setString(3, medQtyText.getText());
				//removeStmt.setDouble(4,Double.parseDouble(medPriceText.getText()));
				removeStmt.setString(4, medCompText.getText());
				removeStmt.executeUpdate();
				
				int row = sqlTable.getSelectedRow();
				model.removeRow(row);
				
				JOptionPane.showMessageDialog(null, "ai sters un medicament");
				removeStmt.close();
				connection.close();
			}catch (Exception arg) {
				System.out.println(arg);
			}
		}
		
		if (e.getSource() == resetButton) {
			medIDText.setText("");
			medNameText.setText("");
			medQtyText.setText("");
			medPriceText.setText("");
			medCompText.setText("");
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement resetStmt = connection.prepareStatement("SELECT * FROM pill_table");
				//searchStmt.setString(1, searchText.getText());
				ResultSet resultSet = resetStmt.executeQuery();
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
				sqlTable.setModel(auxModel);
				resetStmt.close();
				connection.close();
			}catch (Exception arg) {
				System.out.println(arg);
			}
		}
		if (e.getSource() == nextButton) {
			this.dispose();
			ManagerInterface frameNext= new ManagerInterface ();
		}
	}
	/**
	 * Metoda mouseClicked este supraincarcata din interfata MouseListener
	 * pentru a interactiona cu tabelul prin click-uri
	 * 
	 */
	public void mouseClicked(MouseEvent e) {
		int row = sqlTable.getSelectedRow();
		if  (row >= 0) {
			Integer id =(Integer) sqlTable.getModel().getValueAt(row, 0);
			medIDText.setText(id.toString());
			String name =(String) sqlTable.getModel().getValueAt(row, 1);
			medNameText.setText(name);
			Integer qty =(Integer) sqlTable.getModel().getValueAt(row, 2);
			medQtyText.setText(qty.toString());
			Double price =(Double) sqlTable.getModel().getValueAt(row, 3);
			medPriceText.setText(price.toString());
			String comp =(String) sqlTable.getModel().getValueAt(row, 4);
			medCompText.setText(comp);
		}
	}
	
	public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
    
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
			PreparedStatement searchStmt = connection.prepareStatement("SELECT * FROM pill_table ");
			//searchStmt.setString(1, searchText.getText());
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
	/**
	 * Initializarea unei liste ce contine toate campurile de text
	 * @return aux - lista cu campurile de text
	 */
	public List<JTextField> TextsRead(){
		List<JTextField> aux = new ArrayList<JTextField>();
		aux.add(medIDText);
		aux.add(medNameText);
		aux.add(medQtyText);
		aux.add(medPriceText);
		aux.add(medCompText);
		return aux;
	}
}
