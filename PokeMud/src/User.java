import java.io.*;
import java.net.Socket;
import java.util.*;
public class User {

	String UserName, Password;
	int ID, gold, roomNum = 0;
	Random rand = new Random();
	Socket client;
	LinkedList<pokemon> party = new LinkedList<pokemon>();
	boolean[] badges = new boolean[11];
	LinkedList<Item> Inventory = new LinkedList<Item>();
	LinkedList<pokemon> PC = new LinkedList<pokemon>();
	
	public User()
	{
		ID = rand.nextInt(100000);
		UserName = "";
		Password = "";
	}
	public User(String name,String pass,int id)
	{
		UserName = name;
		Password = pass;
		ID = id;
		
		for(int i = 0; i < badges.length; i++)
		{
			badges[i] = false;
		}
	}

	public void setInfo(String name, String pass) 
	{
		UserName = name;
		Password = pass;

	}
	public int ID()
	{
		return ID;
	}
	public String name()
	{
		return UserName;
	}
	public void addPoke(pokemon Pokemon)
	{
		party.add(Pokemon);
	}
	public void addFirstP(pokemon Pokemon)
	{
		party.addFirst(Pokemon);
	}
	public void addBadge()
	{
		int i = 0;
		while(badges[i] == true)
		{
			i++;
		}
		badges[i] = true;
	}
	public int countBadges()
	{
		int count = 0;
		for(int i = 0; i < badges.length; i++)
		{
			if(badges[i] == true)
				count++;
		}
		return count;
		
	}
	public int badgeSize()
	{
		return badges.length;
	}
	public void addBadge(int b)
	{
		badges[b-1] = true;
	}
	public boolean badge(int i)
	{
		return badges[i];
	}
	public void savePoke()throws IOException
	{
		FileWriter PokeInfo = new FileWriter(ID + ".txt");
		for(int i = 0; i < party.size(); i++)
		{
			pokemon temp = new pokemon();
			temp = (pokemon)party.get(i);
			PokeInfo.write(temp.ID() + "\r");
		}
		for(int i = 0; i < PC.size(); i++)
		{
			pokemon temp = new pokemon();
			temp = (pokemon)PC.get(i);
			PokeInfo.write(temp.ID() + "\r");
		}
		PokeInfo.close();
	}
	public void saveInfo() throws IOException
	{
		FileWriter UserInfo = new FileWriter(UserName + ".txt");
		UserInfo.write(ID + "\r");
		UserInfo.write(UserName + "\r");
		UserInfo.write(Password + "\r");
		UserInfo.write(gold + "\r");
		UserInfo.write(roomNum + "\r");
		for(int i = 0; i < badges.length; i++)
		{
			UserInfo.write(badges[i] + "\r");
		}
		for(int i = 0; i < Inventory.size(); i++)
		{
			UserInfo.write(Inventory.get(i).ID() + "\r");
		}
		UserInfo.close();
		
	}

	public int gold()
	{
		return gold;
	}
	public void setGold(int n)
	{
		gold = n;
	}
	public int invSize()
	{
		return Inventory.size();
	}
	public Item getItem(int i)
	{
		return Inventory.get(i);
	}
	public void addItem(Item item)
	{
		Inventory.add(item);
	}
	public void removeItem(int i)
	{
		Inventory.remove(i);
	}
	public void removeItem(Item item)
	{
		Inventory.remove(item);
	}
	public void inventory(Socket client) throws IOException
	{
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		out.println("Your bag:");
		for(int i = 0; i < Inventory.size(); i++)
		{
			out.println(Inventory.get(i).name());
		}
	}
	public void inventory()
	{
		System.out.println("Your bag:");
		for(int i = 0; i < Inventory.size(); i++)
		{
			System.out.println(Inventory.get(i).name());
		}
	}
	public pokemon getPoke(int i)
	{
		return party.get(i);
	}
	public void removePoke(int i)
	{
		party.remove(i);
	}
	public void removePoke(pokemon i)
	{
		party.remove(i);
	}
	public int partySize()
	{
		return party.size();
	}
	public void addLastP(pokemon p)
	{
		party.addLast(p);
	}
	public void setP(int a, pokemon p)
	{
		party.set(a, p);
	}
	public pokemon getPC(int i)
	{
		return PC.get(i);
	}
	public void removePC(int i)
	{
		PC.remove(i);
	}
	public int PCSize()
	{
		return PC.size();
	}
	public void addPC(pokemon p)
	{
		PC.add(p);
	}
	public void addLastPC(pokemon p)
	{
		PC.addLast(p);
	}
	public void setPC(int a, pokemon p)
	{
		PC.set(a, p);
	}
	public void loadInfo() throws IOException
	{
		FileReader UserInfo = new FileReader(UserName + ".txt");
		BufferedReader ui = new BufferedReader(UserInfo);
		
		ID = Integer.valueOf(ui.readLine());
		UserName = ui.readLine();
		Password = ui.readLine();
		gold = Integer.valueOf(ui.readLine());
		roomNum = Integer.valueOf(ui.readLine());
		//badges
		for(int i = 0; i < badges.length; i++)
		{
			badges[i] = Boolean.valueOf(ui.readLine());
		}
		
		int i = 0;
		//Inventory
		while(i != -1)
		{
			ui.mark(3);
			i = ui.read();
			if(i == -1)
				return;
			else
			{
			ui.reset();
			Item temp = new Item(Integer.valueOf(ui.readLine()));
			Inventory.add(temp);		
			}
		}
		
		UserInfo.close();
	}
	public int roomNum()
	{
		return roomNum;
	}
	public void setRoomNum(int i)
	{
		roomNum = i;
	}
	public Item getItem(Item i)
	{
		return Inventory.get(Inventory.indexOf(i));
	}
	public void setSocket(Socket c)
	{
		client = c;
	}
}
