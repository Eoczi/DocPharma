package pharmacy;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Scopul clasei JFrameInterface este de a permite utilizatorului sa intre pe un cont
 * fie ca administrator, fie ca si client
 * Interfata prezinta 2 campuri in care se trec numele utilizatorului si parola,
 * 2 butoane in care intri in aplicatie sau creezi un cont nou,
 * si o bifa care iti indica sa te loghezi ca administrator daca este cazul
 * @author Koczi Eduard
 *
 */

public class JFrameInterface extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	
	/**
	 * Butoane folosite pentru a naviga aplicatia
	 * buttonLogin,buttonRegister etc.
	 */
	JButton buttonLogin;
	JButton buttonRegister;
	/**
	 * Textfield si radiobutton folosit pentru a intra in cont
	 * username,password etc.
	 */
	JRadioButton employeeCheck;
	JTextField username;
	JTextField password;
	JTextField ID;
	/**
	 * Panouri folosite pentru structura interfetei
	 * panelNorth,panelCenter,panelMainLogin etc.
	 */
	JPanel panelTitle;
	JPanel panelMain;
	JPanel panelNorth;
	JPanel panelWest;
	JPanel panelEast;
	JPanel panelSouth;
	JPanel panelCenter;
	JPanel panelMainLogin;
	JLabel title;
	JLabel IDLabel;
	JLabel UsernameLabel;
	JLabel PasswordLabel;
	
	/**
	 * In constructorul clasei se afla operatii asupra JFrame-ului
	 * pentru a oferi un design aplicatiei
	 */
	JFrameInterface (){
		
		buttonLogin = new JButton();
		buttonRegister = new JButton();
		employeeCheck = new JRadioButton("Administrator");
		username = new JTextField();
		password = new JTextField();
		ID = new JTextField();
		panelTitle = new JPanel();
		panelMain = new JPanel();
		panelNorth = new JPanel();
		panelWest = new JPanel();
		panelEast = new JPanel();
		panelSouth = new JPanel();
		panelCenter = new JPanel();
		panelMainLogin = new JPanel();
		title = new JLabel("Best Farma");
		IDLabel = new JLabel ("ID");
		UsernameLabel = new JLabel ("Username");
		PasswordLabel = new JLabel ("Password");
		
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
		
		panelTitle.setPreferredSize(new Dimension(panelCenter.getWidth(),300)); //panelCenter.getWidth() nu face vreo diferenta
		panelMain.setBackground(Color.white);
		panelTitle.setBackground(Color.white);
		
		panelCenter.setLayout(new BorderLayout());
		panelCenter.add(panelTitle,BorderLayout.NORTH);
		panelCenter.add(panelMain,BorderLayout.CENTER);
		
		panelMain.add(panelMainLogin);
		panelMainLogin.setPreferredSize(new Dimension(400,300));
		panelMainLogin.setBackground(Color.white);
		
		panelMain.setLayout(new GridBagLayout());
		panelMainLogin.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));
		
		panelMainLogin.add(IDLabel);
		panelMainLogin.add(ID);
		panelMainLogin.add(UsernameLabel);
		panelMainLogin.add(username);
		panelMainLogin.add(PasswordLabel);
		panelMainLogin.add(password);
		panelMainLogin.add(buttonLogin);
		panelMainLogin.add(buttonRegister);
		panelMainLogin.add(employeeCheck);
		
		username.setPreferredSize(new Dimension (360,25)); //panelMainLogin.getWidth()-40
		password.setPreferredSize(new Dimension (360,25));
		ID.setPreferredSize(new Dimension (360,25));
		ID.setVisible(false);
		IDLabel.setVisible(false);
		
		employeeCheck.setBackground(Color.white);
		employeeCheck.setFocusable(false);
		employeeCheck.addActionListener(this);
		buttonLogin.setText("Login");
		buttonLogin.setPreferredSize(new Dimension(100,30));
		buttonLogin.addActionListener(this);
		buttonLogin.setFocusable(false);
		buttonRegister.setText("Register");
		buttonRegister.setPreferredSize(new Dimension(100,30));
		buttonRegister.addActionListener(this);
		buttonRegister.setFocusable(false);
		
		panelTitle.add(title);
		title.setFont(new Font("Lucida Handwriting", Font.PLAIN, 72));
		title.setIcon(icon);
		icon.setImage(icon.getImage().getScaledInstance(400, 400, Image.SCALE_DEFAULT));
		Image image = icon.getImage();
		Image resizedImage = image.getScaledInstance(85, 85, Image.SCALE_DEFAULT);
		icon.setImage(resizedImage);
		
		this.setLocationRelativeTo(null);
	}
	/**
	 * Metoda ActionPerformed este supraincarcata din interfata ActionListener
	 * pentru a adauga functionalitati butoanelor din JFrame
	 * Aici se intampla deseori sa fac o conexiune la
	 * baza de date sql pentru a prelua date din tabel
	 */
	public void actionPerformed (ActionEvent e) {
		if (e.getSource() == buttonLogin) {
			if (employeeCheck.isSelected()) {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
					//Connection con=ConnectionDB.getConnection();
					String sqlPassw = new String("SELECT LOGIN_PASSW FROM login_table WHERE LOGIN_USER = ?");
					String sqlID = new String ("SELECT LOGIN_ID FROM login_table JOIN employee_table ON login_table.LOGIN_ID=employee_table.EMP_ID WHERE LOGIN_USER = ?");
					PreparedStatement statementPassw = connection.prepareStatement(sqlPassw);
					PreparedStatement statementID = connection.prepareStatement(sqlID);
					statementPassw.setString(1, username.getText());
					statementID.setString(1, username.getText());
					ResultSet resultSetPassw = statementPassw.executeQuery();
					ResultSet resultSetID = statementID.executeQuery();
					if (resultSetPassw.next() && resultSetID.next()) {
						String retrievedPassword = resultSetPassw.getString("LOGIN_PASSW");
						int retrievedID = resultSetID.getInt("LOGIN_ID");
						int auxID = Integer.parseInt(ID.getText());
						if (password.getText().equals(retrievedPassword) && retrievedID == auxID) {
							this.dispose();
							AdminInterface frameAdmin = new AdminInterface();
				        } 
						else {
				            System.out.println("nu te-ai logat bine");
				        }
					}
					statementPassw.close();
					statementID.close();
					connection.close();
				}catch (Exception arg) {
					
					System.out.println(arg);
				}
			}
			else {
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
		            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
					//Connection con=ConnectionDB.getConnection();
					PreparedStatement statement = connection.prepareStatement("SELECT LOGIN_PASSW FROM login_table WHERE LOGIN_USER = ?");
					statement.setString(1, username.getText());
					ResultSet resultSet = statement.executeQuery();
					if (resultSet.next()) {
						String retrievedPassword = resultSet.getString("LOGIN_PASSW");
						if (password.getText().equals(retrievedPassword)) {
							this.dispose();
							UserInterface frameUser = new UserInterface();
				        } 
						else {
				            System.out.println("nu te-ai logat bine");
				        }
					}
					statement.close();
					connection.close();
				}catch (Exception arg) {
					
					System.out.println(arg);
				}
			}
		}
			
		if (e.getSource() == buttonRegister) {
			System.out.println("Register");
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/doc_farma", "root", "edik");
				PreparedStatement add = connection.prepareStatement("INSERT INTO login_table (LOGIN_USER, LOGIN_PASSW) VALUES(?,?)");
				add.setString(1,username.getText());
				add.setString(2,password.getText());
				add.executeUpdate();
				JOptionPane.showMessageDialog(null, "cont nou creat");
				add.close();
				connection.close();
			}catch (Exception arg) {
				
				System.out.println(arg);
			}
		}

		if (e.getSource() == employeeCheck) {
			if (employeeCheck.isSelected()) {
				ID.setVisible(true);
				IDLabel.setVisible(true);
				if (!ID.getText().isEmpty())
					ID.setText("");
			}
			else {
				ID.setVisible(false);
				IDLabel.setVisible(false);
			}
		}
	}

}
