import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.Socket;




public class Logger 
{
	public Socket user;
	public int state = 1, name = 1, pass = 2, newuser = 3, newpass = 4, newpoke = 5, loggedIn = 6, ALI = 7;
	
	String UserName, UserPass;
	User you;
	
	public Logger(Socket client)
	{
		user = client;
	}
	
	public String process(String input) throws Exception
	{
		String output = "";
		if(state == name)
		{
			FileReader getUser = new FileReader("UserInfo.txt");
			BufferedReader gu = new BufferedReader(getUser);
			boolean found = false;
			String k = gu.readLine();
			UserName = input;
			
			while(k != null)
			{
				if(k.equals(input))
				{
					found = true;
				}
				k = gu.readLine();
			}
			if(found == true && state != ALI)
			{
				state = pass;
				output = "password: ";
			}
			else if(found != true && state != ALI)
			{
				state = newuser;
				output = "Are you a new user? y/n?";
			}
			else
			{
				state = name;
				output = "name: ";
			}
			
		}
		else if(state == pass) //fix this maybe
		{
			UserPass = input;
			FileReader getPass = new FileReader(UserName + ".txt");
			BufferedReader gp = new BufferedReader(getPass);
			gp.readLine();
			gp.readLine();
			gp.mark(2);
			if(gp.readLine().equals(UserPass))
			{
				FileReader getUserID = new FileReader(UserName + ".txt");
				BufferedReader gui = new BufferedReader(getUserID);
				
				state = loggedIn;
				you = new User(UserName, UserPass, Integer.valueOf(gui.readLine()));
				output = ("Welcome Back!");
				
			}
			else
			{
				state = name;
				output = "name: ";
			}
		}
		else if(state == newuser)
		{
			if(input.startsWith("y"))
			{
				state = newpass;
				output = "What do you want your password to be?";
			}
			else if(input.startsWith("n"))
			{
				state = name;
				output = "whats your name then: ";
			}
			else
				output = "this isn't hard";
		}
		else if(state == newpass)
		{
			UserPass = input;
			state = newpoke;
			output = "Welcome to the amazing world of pokemon! \r From here on out " +
			"you will be living the life of pokemon trainer. \r" +
			"\r\rYou will be given one of the following pokemon. Make a choice.\n" +
			"1. Bulbasaur, 2.Charmander, 3.Squirtle";
		}
		else if(state == newpoke)
		{
			String pokechoice = input;
			
			if(pokechoice.startsWith("b") == true || pokechoice.startsWith("1") || pokechoice.startsWith("B"))
			{
				FileReader filer = new FileReader("UserInfo.txt");
				BufferedReader fr = new BufferedReader(filer);
				fr.mark(2);
				int a = fr.read();
				String b = "";
				while(a != -1)
				{
					fr.reset();
					b = b + fr.readLine() + "\r";
					fr.mark(2);
					a = fr.read();
					
				}
				FileWriter userinfo = new FileWriter("UserInfo.txt");
				userinfo.write(b + UserName + "\r");
				userinfo.close();
				
				you = new User();
				you.setInfo(UserName, UserPass);
				you.setGold(2000);
				pokemon Bulbasaur = new pokemon(5 , 1, you.ID());
				you.addPoke(Bulbasaur);
				output = "You chose Bulbasaur!";
				for(int i = 0; i < you.partySize(); i++)
				{
					pokemon temp = new pokemon();
					temp = (pokemon)you.getPoke(i);
					temp.statsSave();
				}
				you.saveInfo();
				you.savePoke();
				you.removePoke(0);
				state = loggedIn;
			}
			if(pokechoice.startsWith("c") == true || pokechoice.startsWith("2")|| pokechoice.startsWith("C"))
			{
				FileReader filer = new FileReader("UserInfo.txt");
				BufferedReader fr = new BufferedReader(filer);
				fr.mark(2);
				int a = fr.read();
				String b = "";
				while(a != -1)
				{
					fr.reset();
					b = b + fr.readLine() + "\r";
					fr.mark(2);
					a = fr.read();
					
				}
				FileWriter userinfo = new FileWriter("UserInfo.txt");
				userinfo.write(b + UserName + "\r");
				userinfo.close();
				
				you = new User();
				you.setInfo(UserName, UserPass);
				you.setGold(2000);
				pokemon Charmander = new pokemon(5, 4, you.ID());
				you.addPoke(Charmander);
				System.out.println("You chose Charmander!");
				
				for(int i = 0; i < you.partySize(); i++)
				{
					pokemon temp = new pokemon();
					temp = (pokemon)you.getPoke(i);
					temp.statsSave();
				}
				you.saveInfo();
				you.savePoke();
				state = loggedIn;

			}
			if(pokechoice.startsWith("s") == true || pokechoice.startsWith("3")|| pokechoice.startsWith("S"))
			{
				FileReader filer = new FileReader("UserInfo.txt");
				BufferedReader fr = new BufferedReader(filer);
				fr.mark(2);
				int a = fr.read();
				String b = "";
				while(a != -1)
				{
					fr.reset();
					b = b + fr.readLine() + "\r";
					fr.mark(2);
					a = fr.read();
					
				}
				FileWriter userinfo = new FileWriter("UserInfo.txt");
				userinfo.write(b + UserName + "\r");
				userinfo.close();
				
				you = new User();
				you.setInfo(UserName, UserPass);
				you.setGold(2000);
				pokemon Squirtle = new pokemon(5, 7, you.ID());
				you.addPoke(Squirtle);
				System.out.println("You chose Squirtle!");
				
				for(int i = 0; i < you.partySize(); i++)
				{
					pokemon temp = new pokemon();
					temp = (pokemon)you.getPoke(i);
					temp.statsSave();
				}
				you.saveInfo();
				you.savePoke();
				state = loggedIn;
				
			}
			else
				output = "this isn't hard now";
		}
		return output;
		
		
	}
	
	public int state()
	{
		return state;
	}
	public User user()
	{
		return you;
	}
	public int loggedIn()
	{
		return loggedIn;
	}
	public void setState(int i)
	{
		state = i;
	}
}
