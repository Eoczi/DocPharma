package pharmacy;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 * Scopul clasei ManagerInterface este de a permite unui utilizator administrator
 * sa modifice date asupra listei de angajati
 * Interfata prezinta 6 butoane care te lasa sa navighezi intre interfete
 * sau sa adaugi/stergi angajati, 4 campuri ajutatoare pentru operatii asupra tabelului
 * si tabelul in sine
 * @author Koczi Eduard
 *
 */

public class ManagerInterface extends JFrame implements ActionListener,MouseListener { //,DocumentListener
	private static final long serialVersionUID = 1L;
	
	JPanel panelNorth;
	JPanel panelWest;
	JPanel panelEast;
	JPanel panelSouth;
	JPanel panelCenter;
	JPanel panelButtons;
	JPanel panelMain;
	JPanel panelTextFields;
	JPanel panelSQLTable;
	JLabel empIDLabel;
	JLabel empNameLabel;
	JLabel empSalLabel;
	JLabel empStatusLabel;
	/**
	 * Textfielduri ajutatoare
	 * empIDText,empNameText etc.
	 */
	JTextField empIDText;
	JTextField empNameText;
	JTextField empSalText;
	JTextField empStatusText;
	/**
	 * Butoane folosite pentru a naviga aplicatia
	 * backButton,addButton etc.
	 */
	JButton backButton;
	JButton addButton;
	JButton removeButton;
	JButton clearButton;
	JButton searchButton;
	JButton nextButton;
	/**
	 * Tabel folosit pentru a afisa lista
	 * de angajati(preluata dintr-o baza de date sql)
	 */
	DefaultTableModel model;
	JTable sqlTable;
	List<Employee> empList;
	List<JTextField> searchTexts;
	String[] firstRow = {"ID","Name","Salary","Position"};
	String[] collumns = {"EMP_ID","EMP_NAME","EMP_SAL","EMP_STATUS"};
	
	/**
	 * In constructorul clasei se afla operatii asupra JFrame-ului
	 * pentru a oferi un design aplicatiei
	 */
	ManagerInterface (){
		
		panelNorth = new JPanel();
		panelWest = new JPanel();
		panelEast = new JPanel();
		panelSouth = new JPanel();
		panelCenter = new JPanel();
		panelButtons = new JPanel();
		panelMain = new JPanel();
		panelTextFields= new JPanel();
		panelSQLTable= new JPanel();
		empIDLabel = new JLabel("ID: ");
		empNameLabel = new JLabel("Name: ");
		empSalLabel = new JLabel("Salary: ");
		empStatusLabel = new JLabel("Position: ");
		empIDText =new JTextField ();
		empNameText =new JTextField ();
		empSalText =new JTextField ();
		empStatusText=new JTextField();
		backButton = new JButton("< Back");
		addButton = new JButton("Add");
		removeButton = new JButton("Remove");
		clearButton = new JButton("Clear");
		searchButton= new JButton("Search");
		nextButton = new JButton("Next");
		empList = EmpRead();
		searchTexts = TextsRead();
		
		ImageIcon icon = new ImageIcon("image-large-Employee-bottle-icon.jpg");
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
		
		panelTextFields.setLayout(new GridLayout(4,2));
		for (int i = 0; i <= 7; i++) {
		      JPanel panel = new JPanel();
		      panel.setBackground(Color.white);
		      panel.setLayout(new GridBagLayout());
		      if (i==0)
		    	  panel.add(empIDLabel);
		      else if (i==1)
		    	  panel.add(empIDText);
		      else if (i==2)
		    	  panel.add(empNameLabel);
		      else if (i==3)
		    	  panel.add(empNameText);
		      else if (i==4)
		    	  panel.add(empSalLabel);
		      else if (i==5)
		    	  panel.add(empSalText);
		      else if (i==6)
		    	  panel.add(empStatusLabel);
		      else if (i==7)
		    	  panel.add(empStatusText);
		      panelTextFields.add(panel);
		}
		
		empIDText.setPreferredSize(new Dimension(200,20));
		empNameText.setPreferredSize(new Dimension(200,20));
		empSalText.setPreferredSize(new Dimension(200,20));
		empStatusText.setPreferredSize(new Dimension(200,20));
		empIDLabel.setFont(new Font(empIDLabel.getFont().getName(), empIDLabel.getFont().getStyle(), 19));
		empNameLabel.setFont(new Font(empNameLabel.getFont().getName(), empNameLabel.getFont().getStyle(), 19));
		empSalLabel.setFont(new Font(empSalLabel.getFont().getName(), empSalLabel.getFont().getStyle(), 19));
		empStatusLabel.setFont(new Font(empStatusLabel.getFont().getName(), empStatusLabel.getFont().getStyle(), 19));
		
		panelButtons.setLayout(new FlowLayout(FlowLayout.CENTER, 75, 50));
		
		backButton.addActionListener(this);
		addButton.addActionListener(this);
		removeButton.addActionListener(this);
		clearButton.addActionListener(this);
		searchButton.addActionListener(this);
		nextButton.addActionListener(this);
		
		panelButtons.add(backButton);
		panelButtons.add(addButton);
		panelButtons.add(removeButton);
		panelButtons.add(searchButton);
		panelButtons.add(clearButton);
		panelButtons.add(nextButton);
		
		model = new DefaultTableModel(firstRow,0){
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		sqlTable = new JTable(model);
		for (Employee o : empList) {
		    Object[] row = {o.getEmpID(), o.getEmpName(), o.getEmpSal(),o.getEmpStatus()};
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
				if (searchTexts.get(i).getText().equals("") || searchTexts.get(i).getText().equals(null))
					index++;
				else
					break;
			}
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement searchStmt = connection.prepareStatement("SELECT * FROM employee_table WHERE " + collumns[index] + "='" + searchTexts.get(index).getText()+ "'");
				DefaultTableModel auxModel = new DefaultTableModel(firstRow,0);
				ResultSet resultSet = searchStmt.executeQuery();
				while (resultSet.next()) {
					System.out.println(index);
					Employee employee = new Employee(resultSet.getInt("EMP_ID"),
							resultSet.getString("EMP_NAME"),
							resultSet.getInt("EMP_SAL"),
							resultSet.getString("EMP_STATUS"));
					Object[] row = {employee.getEmpID(), employee.getEmpName(), employee.getEmpSal(),employee.getEmpStatus()};
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
				PreparedStatement addStmt = connection.prepareStatement("INSERT INTO employee_table "
						+ "(EMP_ID, "
						+ "EMP_NAME, "
						+ "EMP_SAL, "
						+ "EMP_STATUS) "
						+ "VALUES(?,?,?,?)");
				addStmt.setInt(1,Integer.parseInt(empIDText.getText()));
				addStmt.setString(2,empNameText.getText());
				addStmt.setInt(3,Integer.parseInt(empSalText.getText()));
				addStmt.setString(4,empStatusText.getText());
				addStmt.executeUpdate();
				
				Employee Employee = new Employee(Integer.parseInt(empIDText.getText()),
						empNameText.getText(),
						Integer.parseInt(empSalText.getText()),
						empStatusText.getText());
				Object[] row= {Integer.parseInt(empIDText.getText()),
						empNameText.getText(),
						Integer.parseInt(empSalText.getText()),
						empStatusText.getText()};
				model.addRow(row);
				
				JOptionPane.showMessageDialog(null, "ai adaugat un angajat");
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
				PreparedStatement removeStmt = connection.prepareStatement("DELETE FROM employee_table"
						+ " WHERE EMP_ID = ?"
						+ " AND EMP_NAME = ?"
						+ " AND EMP_SAL = ?"
						+ " AND EMP_STATUS = ?");
				removeStmt.setString(1,empIDText.getText());
				removeStmt.setString(2,empNameText.getText());
				removeStmt.setString(3,empSalText.getText());
				removeStmt.setString(4,empStatusText.getText());
				removeStmt.executeUpdate();
				
				int row = sqlTable.getSelectedRow();
				model.removeRow(row);
				
				JOptionPane.showMessageDialog(null, "ai adaugat un angajat");
				removeStmt.close();
				connection.close();
			}catch (Exception arg) {
				System.out.println(arg);
			}
			
		}
		if (e.getSource() == clearButton) {
			empIDText.setText("");
			empNameText.setText("");
			empSalText.setText("");
			empStatusText.setText("");
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement clearStmt = connection.prepareStatement("SELECT * FROM employee_table");
				ResultSet resultSet = clearStmt.executeQuery();
				DefaultTableModel auxModel = new DefaultTableModel(firstRow,0);
				while(resultSet.next()) {
					Employee employee = new Employee(resultSet.getInt("EMP_ID"),
							resultSet.getString("EMP_NAME"),
							resultSet.getInt("EMP_SAL"),
							resultSet.getString("EMP_STATUS"));
					Object[] row = {employee.getEmpID(), employee.getEmpName(), employee.getEmpSal(),employee.getEmpStatus()};
					auxModel.addRow(row);
				}
				sqlTable.setModel(auxModel);
				
				clearStmt.close();
				connection.close();
			}catch (Exception arg) {
				System.out.println(arg);
			}
		}
		if (e.getSource() == nextButton) {
			this.dispose();
			AdminInterface frameNext= new AdminInterface ();
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
			empIDText.setText(id.toString());
			String name =(String) sqlTable.getModel().getValueAt(row, 1);
			empNameText.setText(name);
			Integer qty =(Integer) sqlTable.getModel().getValueAt(row, 2);
			empSalText.setText(qty.toString());
			String comp =(String) sqlTable.getModel().getValueAt(row, 3);
			empStatusText.setText(comp);
		}
	}
	
	public void mousePressed(MouseEvent e) {}

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}
    
    /**
     * Asa cum intuieste numele EmpRead este o functie
     * care returneaza o lista de angajati din
     * tabelul sql employee_table
     * @return employeeList - returneaza o lista de angajati
     */
    
	public List<Employee> EmpRead(){
		List<Employee> employeeList = new ArrayList<Employee>();
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
			PreparedStatement searchStmt = connection.prepareStatement("SELECT * FROM employee_table ");
			ResultSet resultSet = searchStmt.executeQuery();
			while(resultSet.next()) {
				Employee Employee = new Employee(resultSet.getInt("EMP_ID"),
						resultSet.getString("EMP_NAME"),
						resultSet.getInt("EMP_SAL"),
						resultSet.getString("EMP_STATUS"));
				employeeList.add(Employee);
			}
			searchStmt.close();
			connection.close();
			
		}catch (Exception arg) {
			System.out.println(arg);
		}
		return employeeList;
	}
	/**
	 * Initializarea unei liste ce contine toate campurile de text
	 * @return aux - lista cu campurile de text
	 */
	public List<JTextField> TextsRead(){
		List<JTextField> aux = new ArrayList<JTextField>();
		aux.add(empIDText);
		aux.add(empNameText);
		aux.add(empSalText);
		aux.add(empStatusText);
		return aux;
	}
}
