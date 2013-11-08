import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;


public class trainer 
{
	int ID, roomNum, gold, badgNum, itemReq, badgReq, timeCounter;
	boolean isShop, isCenter, isLeader,isTrainer;
	String battle, buy, heal, speak, des, name, lose, beat, noBattle;
	int[] p = new int[6];
	int[] l = new int[6];
	
	LinkedList<pokemon> party = new LinkedList<pokemon>();

	public trainer()
	{
	
	}
	public trainer(int id) throws IOException
	{
		FileReader getTrainer = new FileReader("Trn.txt");
		BufferedReader gt = new BufferedReader(getTrainer);
		
		Integer a = id;
		String k = gt.readLine();
		ID = a;
		
		while(k.equals("#" + a.toString()) != true)
		{
			k = gt.readLine();
		}
		
		name = gt.readLine();
		roomNum = Integer.valueOf(gt.readLine());
		isTrainer = Boolean.valueOf(gt.readLine());
		if(isTrainer == true)
		{
		gold = Integer.valueOf(gt.readLine());
		for(int i = 0; i < p.length; i++)
			p[i]= Integer.valueOf(gt.readLine());
		
		for(int i = 0; i < l.length; i++)
			l[i] = Integer.valueOf(gt.readLine());
		}
		
		isShop = Boolean.valueOf(gt.readLine());
		isCenter = Boolean.valueOf(gt.readLine());
		isLeader = Boolean.valueOf(gt.readLine());
		
		battle = gt.readLine();
		
		if(isShop == true)
			buy = gt.readLine();
		
		if(isCenter == true)
			heal = gt.readLine();
		
		speak = gt.readLine();
		if(isTrainer == true)
		lose = gt.readLine();
		beat = gt.readLine();
		timeCounter = 0;
		if(isLeader == true)
		{
			badgNum = Integer.valueOf(gt.readLine());
			badgReq = Integer.valueOf(gt.readLine());
			noBattle = gt.readLine();
		}
		
		getTrainer.close();
		///////////////////////////
		//Set up their pokeparty //
		///////////////////////////
		for(int i = 0; i < p.length; i ++)
		{
			
			if(p[i] != 0)
			{
				pokemon poke = new pokemon(p[i]);
				poke.setStats(l[i]);
				poke.setWild(false);
				party.addLast(poke);
			}
		}
		if(party.size() != 0)
			des = name + " is standing around with their " + party.get(0).name();
		else
			des = name + " is standing here.";
		
	}
	
	public String name()
	{
		return name;
	}
	public int ID()
	{
		return ID;
	}
	public boolean isShop()
	{
		return isShop;
	}
	public boolean isCenter()
	{
		return isCenter;
	}
	public boolean isLeader()
	{
		return isLeader;
	}
	public String battle()
	{
		return battle;
	}
	public String des()
	{
		return des;
	}
	public String buy()
	{
		return buy;
	}
	public String heal()
	{
		return heal;
	}
	public String lose()
	{
		return lose;
	}
	public String beat()
	{
		return beat;
	}
	public String speak()
	{
		return speak;
	}
	public pokemon getPoke(int i)
	{
		return (pokemon)party.get(i);
	}
	public int pokeNum()
	{
		return party.size();
	}
	public int roomNum()
	{
		return roomNum;
	}
	public int gold()
	{
		return gold;
	}
	public int partySize()
	{
		return party.size();
	}
	public void setP(int a, pokemon p)
	{
		party.set(a, p);
	}
	public int counter()
	{
		return timeCounter;
	}
	public void setCounter(int i)
	{
		timeCounter = i;
	}
	public int badgeNum()
	{
		return badgNum;
	}
	public int badgeReq()
	{
		return badgReq;
	}
	public String noBattle()
	{
		return noBattle;
	}
}
