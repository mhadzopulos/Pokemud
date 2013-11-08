import java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.*;

public class client extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField input;
	public JTextArea output;
	public JButton login;
	public int width = 55;
	public String playerInput, serverOutput, history = "";
	static PrintWriter out = null;
	BufferedReader in = null;
	static Socket socket = null;
	public client()
	{
		super("PokeMud v1");
		{
			 Box box = Box.createHorizontalBox();
			 JPanel container = new JPanel();
		     container.setLayout( new BoxLayout(container, BoxLayout.Y_AXIS) );
		     
		     output = new JTextArea(serverOutput, 30, width);
		     output.setEditable(false);
		     JScrollPane scroll = new JScrollPane(output);
		     box.add(scroll);
		     input = new JTextField(width);
		     container.add( box, "south");
		     container.add( input);
		     

		     textFieldHandler inputHandler = new textFieldHandler();
		     input.addActionListener(inputHandler);

		     
		     setSize( 400, 200 );
		     setVisible( true );
		     this.add(container);
		} 
		
	}
  public void listenSocket() throws IOException{
		//Create socket connection
	  String line;
		     try
		     {
		       InetAddress IP = null;
		       IP = InetAddress.getLocalHost();
		       socket = new Socket(IP, 4001);
		       out = new PrintWriter(socket.getOutputStream(), true);
		       in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		       
		     } 
		     catch (UnknownHostException e) 
		     {
		       System.out.println("Unknown host: host");
		       System.exit(1);
		     } 
		     catch  (IOException e) 
		     {
		       System.out.println("No I/O");
		       System.exit(1);
		     }
		     line = in.readLine();
		     while(line != null)
		     {
		    	 history = history + "\n" + line;
		    	 output.setText(history);
		    	 output.setCaretPosition(output.getText().length()-1);
		    	 if(line != null)
		    		 line = in.readLine();
		     }
		  }
  

	public static void main( String args[] ) throws IOException
	{
	      client Client = new client();
	      Client.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	      Client.setTitle("Client Program");
	      Client.setSize(400, 300);
	        WindowListener l = new WindowAdapter() 
	        {
	             public void windowClosing(WindowEvent e) 
	             {
	            	 try {
						socket.close();
					} catch (IOException e1) {

						System.out.println("FUCK");
					}
	                  System.exit(0);
	             }
	        };

	        Client.addWindowListener(l);
	        Client.pack();
	        Client.setVisible(true);
	        Client.listenSocket();

	}
	private class textFieldHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			if ( event.getSource() == input )
			{
				playerInput = event.getActionCommand();
				out.println(playerInput);
				input.setText("");
				history = history + "\n" + playerInput;
				output.setCaretPosition(output.getText().length()-1);
				if(playerInput.equals("quit"))
				{
					try {
						socket.close();
					} catch (IOException e) {
						System.out.println("The program is exiting");
					}
					System.exit(0);
				}
				
			}
	            
		}		
	}


}
