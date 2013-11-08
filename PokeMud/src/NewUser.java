import java.awt.*;

import javax.swing.*;

import java.awt.event.*;
import java.io.*;
import java.nio.CharBuffer;


public class NewUser extends JFrame 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private JTextField newusername;
	private JPasswordField newpassword, passwordcheck;
	private JButton enter;
	private JLabel label1, label2, label3;
	private ButtonGroup radioGroup;
	private JRadioButton c, b, s;
	private pokemon PokeChoice;
	pokemon Bulbasaur = new pokemon();
	pokemon Charmander = new pokemon();
	pokemon Squirtle = new pokemon();
	
	public NewUser() throws Exception
	{
		super(" New User Information");
		Container container = getContentPane();
		container.setLayout(new FlowLayout());
		label1 = new JLabel("Username");
		container.add(label1);
		newusername = new JTextField( 10 );
		container.add(newusername);
		label2 = new JLabel("Password");
		container.add(label2);
		newpassword = new JPasswordField("string", 10);
		container.add(newpassword);
		label3 = new JLabel("Re-type Password");
		container.add(label3);
		passwordcheck = new JPasswordField("string", 10);
		container.add(passwordcheck);
		enter = new JButton("Enter");
		container.add(enter);
		c = new JRadioButton("Charmander", false);
		b = new JRadioButton("Bulbasaur", false);
		s = new JRadioButton("Squirtle", false);
		container.add(c);
		container.add(b);
		container.add(s);
		radioGroup = new ButtonGroup();
		radioGroup.add(c);
		radioGroup.add(b);
		radioGroup.add(s);
		
		c.addItemListener( new RadioButtonHandler( Bulbasaur ) );
		b.addItemListener( new RadioButtonHandler( Charmander ) );
		s.addItemListener( new RadioButtonHandler( Squirtle) );
		
		ButtonHandler handlerB= new ButtonHandler();
		
		enter.addActionListener(handlerB);
		setSize(640, 100);
		setVisible(true);
		
	}
	public static void main(String args[]) throws Exception
	{
		NewUser application =  new NewUser();
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	public class ButtonHandler implements ActionListener 
	{ 
		public void actionPerformed(ActionEvent event)
		{
			if(event.getSource() == enter)
			{ 
				
				FileWriter UserInfo = null;
				try {
					UserInfo = new FileWriter("Userinfo.txt");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				FileReader getUserInfo = null;
				try {
					getUserInfo = new FileReader("Userinfo.txt");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				BufferedReader br = new BufferedReader(getUserInfo);
				CharBuffer CB1 = CharBuffer.wrap(passwordcheck.getPassword());
				CharBuffer CB2 = CharBuffer.wrap(newpassword.getPassword());
				String d = CB2.toString();
				String k = CB1.toString();
				String r = newusername.getText();
				
		
				if(r.equals("") != true && d.equals("")!= true && k.equals("")!= true)
				{
					
					if(d.equals(k)== true)
					{
						try {
							String g = "";
							g = br.readLine();
							if(r.equals(g) != true )
							{
								UserInfo.append(r + "~\r");
								User tempU = new User();
								tempU.setInfo(r, k);
								tempU.setGold(2000);
								if(PokeChoice == Bulbasaur)
									PokeChoice = new pokemon(5, 1, tempU.ID());
								if(PokeChoice == Charmander)
									PokeChoice = new pokemon(5, 4, tempU.ID());
								if(PokeChoice == Squirtle)
									PokeChoice = new pokemon(5, 7, tempU.ID());
								
								tempU.addPoke(PokeChoice);
								
								for(int i = 0; i < tempU.partySize(); i++)
								{
									pokemon temp = new pokemon();
									temp = (pokemon)tempU.getPoke(i);
									temp.statsSave();
								}
								tempU.savePoke();
								tempU.saveInfo();
								
								JOptionPane.showMessageDialog(NewUser.this,
										"You have been added to the system");
								getUserInfo.close();
								UserInfo.close();
								
							}
							else
							{
								JOptionPane.showMessageDialog( NewUser.this,
								           "That name already exists." );
							}
						} catch (HeadlessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							JOptionPane.showMessageDialog( NewUser.this,
					           "def" );
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							JOptionPane.showMessageDialog( NewUser.this,
					           "asdf" );
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
							
						try {
							getUserInfo.close();
							UserInfo.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						
					}
					else
					{
						JOptionPane.showMessageDialog( NewUser.this,
					            "Your passwords do not match");
					}
				}
				else
				{
					JOptionPane.showMessageDialog( NewUser.this,
		            "Please fill in all the fields");
				}
					
			}
		}
	}
	public class RadioButtonHandler implements ItemListener
	{
		public pokemon Pokemon;
		 public RadioButtonHandler( pokemon p )
	      {
	         Pokemon = p;
	      }
		 public void itemStateChanged(ItemEvent event)
		 {
			PokeChoice = Pokemon;
		 }
	}
}

	
