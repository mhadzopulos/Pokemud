import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.nio.CharBuffer;

public class Login extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField Username;
	private JPasswordField password;
	private JButton login, newuser;
	
	FileReader getUserInfo = new FileReader("UserInfo.txt");
	
	
	public Login() throws Exception
	{
		super(" Login ");
		
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
			
		Username = new JTextField(10);
		container.add(Username);
		password = new JPasswordField(10);
		container.add(password);
		login = new JButton("Login");
		container.add(login);
		newuser = new JButton("New User");
		container.add(newuser);
		
		ButtonHandler handlerB = new ButtonHandler();
		
		login.addActionListener(handlerB);
		newuser.addActionListener(handlerB);
		
		
		setSize(480, 100);
		setVisible(true);
			
	}
	public static void main(String args[]) throws Exception 
	{
		Login application = new Login();
		application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	}
	
	private class ButtonHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			
			if(event.getSource() == login)
			{
				String usern = Username.getText();
				CharBuffer CB1 = CharBuffer.wrap(password.getPassword());
				String passw = CB1.toString();
				BufferedReader br = new BufferedReader(getUserInfo);
				String useri = null;
				try {
					useri = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String userp = null;
				try {
					userp = br.readLine();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(usern.equals(useri) == true && passw.equals(userp) == true)
				{
					JOptionPane.showMessageDialog(Login.this,
					"You have successfully logged in!");
					return;
				}
				else
				{
					JOptionPane.showMessageDialog(Login.this,
					"Incorrect username or password");
				}
				
				
			}
			if(event.getSource() == newuser)
			{
				try {
					NewUser newUser = new NewUser();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
}
