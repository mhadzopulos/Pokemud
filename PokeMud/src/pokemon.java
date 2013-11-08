import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Random;



public class pokemon 
{
	int hp,att,def,spatt,spdef,speed,type1,type2,level,evolve, exp, nextLevel, status, expworth;
	int tempHp, StatusCounter, StatusDrop, tempAtt, tempDef, tempSpatt, tempSpdef, tempSp;
	int DEXNUM, USERNUM, IDNUM, AttCounter, AttDrop, roomNum, faintedCounter;

	boolean wild, isFlash;
	String name, description;
	Random rand = new Random();
	LinkedList<action> MoveList = new LinkedList<action>();
	int[] baseStats= new int[6]; int[] EV= new int[6]; int[] IV = new int[6]; int[] EVY = new int[6];
	
	//for players
	public pokemon(int level9, int DEXNUM15, 
			int Usernum) throws IOException
	{
		
		FileReader getPoke = new FileReader("Poke.db");
		BufferedReader gp = new BufferedReader(getPoke);
		
		Integer a = DEXNUM15;
		DEXNUM = a;
		String b = gp.readLine();
		
		while(b.equals("#" + a.toString()) != true)
		{
			b = gp.readLine();
		}
		
		name = gp.readLine();
		level = level9;
		
		for(int i = 0; i < 6; i++)
			baseStats[i] = Integer.valueOf(gp.readLine());
		for(int i = 0; i < 6; i++)
			EVY[i] = Integer.valueOf(gp.readLine());
			
		type1 = Integer.valueOf(gp.readLine());
		type2 = Integer.valueOf(gp.readLine());
		evolve = Integer.valueOf(gp.readLine());
		expworth = Integer.valueOf(gp.readLine());
		IDNUM = rand.nextInt(100000) + 1000;
		exp = (int) Math.round(level*level*level);
		nextLevel = (int) Math.round((level+1)*(level+1)*(level+1));
		wild = false; 
		status = 0;
		USERNUM = Usernum;
		description = gp.readLine();
		getPoke.close();
		
		for(int i = 0; i < 6; i++)
		{
			IV[i] = rand.nextInt(32);
		}
		for(int i = 0; i < 6; i++)
		{
			EV[i] = 0;
		}
		hp = ((IV[0] + (2*baseStats[0]) + (EV[0]/4) + 100) * level)/100 + 10;
		att = ((IV[1] + (2*baseStats[1]) + (EV[1]/4) + 100) * level)/100 + 5;
		def = ((IV[2] + (2*baseStats[2]) + (EV[2]/4) + 100) * level)/100 + 5;
		spatt = ((IV[3] + (2*baseStats[3]) + (EV[3]/4) + 100) * level)/100 + 5;
		spdef = ((IV[4] + (2*baseStats[4]) + (EV[4]/4) + 100) * level)/100 + 5;
		speed = ((IV[5] + (2*baseStats[5]) + (EV[5]/4) + 100) * level)/100 + 5;
		tempHp = hp;
		tempAtt = att;
		tempDef = def;
		tempSpatt = spatt;
		tempSpdef = spdef;
		tempSp = speed;
		
		FileReader getMoveList = new FileReader("ML.txt");
		BufferedReader gml = new BufferedReader(getMoveList);
		
		String k = gml.readLine();
		while(k.equals("#" + a.toString()) != true )
		{
			k = gml.readLine();
		}
		while(k.equals("&") != true)
		{
			k = gml.readLine();
			if(k.equals("~") == true)
			{
				if(level >= Integer.valueOf(gml.readLine()))
				{
				action temp = new action(Integer.valueOf(gml.readLine()));
				MoveList.add(temp);
				
				}
			}
		}
		getMoveList.close();
	}
	//for evolutions
	public pokemon(pokemon p)throws IOException
	{
		FileReader getPoke = new FileReader("Poke.db");
		BufferedReader gp = new BufferedReader(getPoke);
		
		Integer a = p.DEX()+1;
		DEXNUM = a;
		String b = gp.readLine();
		
		while(b.equals("#" + a.toString()) != true)
		{
			b = gp.readLine();
		}
		
		name = gp.readLine();
		level = p.level();
		
		for(int i = 0; i < 6; i++)
			baseStats[i] = Integer.valueOf(gp.readLine());
		for(int i = 0; i < 6; i++)
			EVY[i] = Integer.valueOf(gp.readLine());
		
		type1 = Integer.valueOf(gp.readLine());
		type2 = Integer.valueOf(gp.readLine());
		evolve = Integer.valueOf(gp.readLine());
		expworth = Integer.valueOf(gp.readLine());
		
		for(int i = 0; i < 6; i++)
			IV[i] = p.IV(i);
		for(int i = 0; i < 6; i++)
			EV[i] = p.EV[i];
		IDNUM = p.ID();
		USERNUM = p.UserNum();
		nextLevel = ((level+1)*(level+1)*(level+1));
		exp = (level*level*level);
		description = gp.readLine();
		
		hp = ((IV[0] + (2*baseStats[0]) + (EV[0]/4) + 100) * level)/100 + 10;
		att = ((IV[1] + (2*baseStats[1]) + (EV[1]/4) + 100) * level)/100 + 5;
		def = ((IV[2] + (2*baseStats[2]) + (EV[2]/4) + 100) * level)/100 + 5;
		spatt = ((IV[3] + (2*baseStats[3]) + (EV[3]/4) + 100) * level)/100 + 5;
		spdef = ((IV[4] + (2*baseStats[4]) + (EV[4]/4) + 100) * level)/100 + 5;
		speed = ((IV[5] + (2*baseStats[5]) + (EV[5]/4) + 100) * level)/100 + 5;
		tempHp = hp;
		tempAtt = att;
		tempDef = def;
		tempSpatt = spatt;
		tempSpdef = spdef;
		tempSp = speed;
		getPoke.close();
		
		FileReader getMoveList = new FileReader("ML.txt");
		BufferedReader gml = new BufferedReader(getMoveList);
		
		String k = gml.readLine();
		while(k.equals("#" + a.toString()) != true )
		{
			k = gml.readLine();
		}
		while(k.equals("&") != true)
		{
			k = gml.readLine();
			if(k.equals("~") == true)
			{
				if(level >= Integer.valueOf(gml.readLine()))
				{
				action temp = new action(Integer.valueOf(gml.readLine()));
				MoveList.add(temp);
				
				}
			}
		}
		getMoveList.close();
	}
	//for personal/trainer pokes//
	public pokemon(int DEXNUM15) throws IOException
	{
		
		FileReader getPoke = new FileReader("Poke.db");
		BufferedReader gp = new BufferedReader(getPoke);
		
		Integer a = DEXNUM15;
		DEXNUM = a;
		String b = gp.readLine();
		while(b.equals("#" + a.toString()) != true)
		{
			b = gp.readLine();
		}
		
		name = gp.readLine();
		level = 1;
		for(int i = 0; i < 6; i++)
			baseStats[i] = Integer.valueOf(gp.readLine());
		for(int i = 0; i < 6; i++)
			EVY[i]= Integer.valueOf(gp.readLine());
		for(int i = 0; i < 6; i++)
			IV[i] = 0;
		type1 = Integer.valueOf(gp.readLine());
		type2 = Integer.valueOf(gp.readLine());
		evolve = Integer.valueOf(gp.readLine());
		IDNUM = 0;
		expworth = Integer.valueOf(gp.readLine());
		exp = (int) Math.round(level*level*level);
		nextLevel = (int) Math.round((level+1)*(level+1)*(level+1));
		wild = true; 
		status = 0;
		hp = ((IV[0] + (2*baseStats[0]) + (EV[0]/4) + 100) * level)/100 + 10;
		att = ((IV[1] + (2*baseStats[1]) + (EV[1]/4) + 100) * level)/100 + 5;
		def = ((IV[2] + (2*baseStats[2]) + (EV[2]/4) + 100) * level)/100 + 5;
		spatt = ((IV[3] + (2*baseStats[3]) + (EV[3]/4) + 100) * level)/100 + 5;
		spdef = ((IV[4] + (2*baseStats[4]) + (EV[4]/4) + 100) * level)/100 + 5;
		speed = ((IV[5] + (2*baseStats[5]) + (EV[5]/4) + 100) * level)/100 + 5;
		tempAtt = att;
		tempDef = def;
		tempSpatt = spatt;
		tempSpdef = spdef;
		tempSp = speed;
		USERNUM = 0;
		tempHp = hp;
		description = gp.readLine();
		getPoke.close();

	}
	//for world poke
	public pokemon(int DEXNUM15,int room) throws IOException
	{
		
		FileReader getPoke = new FileReader("Poke.db");
		BufferedReader gp = new BufferedReader(getPoke);
		roomNum = room;
		Integer a = DEXNUM15;
		DEXNUM = a;
		String b = gp.readLine();
		while(b.equals("#" + a.toString()) != true)
		{
			b = gp.readLine();
		}
		
		name = gp.readLine();
		level = 1;
		for(int i = 0; i < 6; i++)
			baseStats[i] = Integer.valueOf(gp.readLine());
		for(int i = 0; i < 6; i++)
			EVY[i]= Integer.valueOf(gp.readLine());
		for(int i = 0; i < 6; i++)
			IV[i] = 0;
		type1 = Integer.valueOf(gp.readLine());
		type2 = Integer.valueOf(gp.readLine());
		evolve = Integer.valueOf(gp.readLine());
		IDNUM = 0;
		expworth = Integer.valueOf(gp.readLine());
		exp = (int) Math.round(level*level*level);
		nextLevel = (int) Math.round((level+1)*(level+1)*(level+1));
		wild = true; 
		status = 0;
		hp = ((IV[0] + (2*baseStats[0]) + (EV[0]/4) + 100) * level)/100 + 10;
		att = ((IV[1] + (2*baseStats[1]) + (EV[1]/4) + 100) * level)/100 + 5;
		def = ((IV[2] + (2*baseStats[2]) + (EV[2]/4) + 100) * level)/100 + 5;
		spatt = ((IV[3] + (2*baseStats[3]) + (EV[3]/4) + 100) * level)/100 + 5;
		spdef = ((IV[4] + (2*baseStats[4]) + (EV[4]/4) + 100) * level)/100 + 5;
		speed = ((IV[5] + (2*baseStats[5]) + (EV[5]/4) + 100) * level)/100 + 5;
		tempAtt = att;
		tempDef = def;
		tempSpatt = spatt;
		tempSpdef = spdef;
		tempSp = speed;
		USERNUM = 0;
		tempHp = hp;
		description = gp.readLine();
		getPoke.close();

	}
	public pokemon()
	{
	
	}
	public int UserNum()
	{
		return USERNUM;
	}
	public void setName(String name1)
	{
		name = name1;
	}
	public void moves(Socket client) throws IOException
	{
		PrintWriter out = new PrintWriter(client.getOutputStream(), true);
		out.println(name + "'s moves:");
		for(int i = 0; i < MoveList.size(); i++)
		{
			out.println(MoveList.get(i).name());
		}
	}
	public void moves() throws IOException
	{
		
		System.out.println(name + "'s moves:");
		for(int i = 0; i < MoveList.size(); i++)
		{
			System.out.println(MoveList.get(i).name());
		}
	}
	public boolean checkMoves(String check)
	{
		boolean foundAction = false;
		for(int i = 0; i < MoveList.size(); i++)
		{
			if(MoveList.get(i).name().startsWith(check) == true)
			{
				foundAction = true;
			}
		}
		return foundAction;
	}
	public action getMove(String input)
	{
		action Action = new action();
		
		for(int i = 0; i < MoveList.size(); i++)
		{
			if(MoveList.get(i).name().equals(input) == true)
			{
				Action = MoveList.get(i);
			}
		}
		return Action;
	}
	public action getMove(int i)
	{
		action Action = MoveList.get(i);
		return Action;
	}
	 public int hp()
     {
        return hp; 
     }
	 public String description()
	 {
		 return description;
	 }
	 public String getMoveName(int i)
	 {
		 return (String)MoveList.get(i).name();
	 }
	 public int totalMoves()
	 {
		 return MoveList.size();
	 }
      public int newhp(int newhp)
     {
        hp = newhp;
        return hp;
     }
      public int tempHp()
     {
        return tempHp;
     }
      public void setTempHp(int set)
     {
        tempHp = set;
     }
   
      public int exp()
     {
        return exp; 
     }
  
      public void newExp(int newExp)
     {
        exp = newExp;
     }
     public int expWorth()
     {
    	 return expworth;
     }

      public int att()
     {
        return att;
     }
  	
      public void newAtt(int newAt)
     {
        att = newAt;
     }
  	
      public int def()
     {
        return def; 
     }
  	
      public void newDef(int newDef)
     {
        def = newDef;
     }
  	
      public int spatt()
     {
        return spatt; 
     }
  	
      public void newSpatt(int magStr)
     {
        spatt = magStr;
     }
  	
      public int spdef()
     {
        return spdef; 
     }
  	
      public void newSpDef(int magDef)
     {
        spdef = magDef;
     }
  	
      public int speed()
     {
        return speed;
     }
  	
      public void newSpeed(int newSpeed)
     {
        speed = newSpeed;
     }
      public String name()
     {
        return name;
     }
      public int type1()
      {
    	  return type1;
      }
      public int type2()
      {
    	  return type2;
      }
      public int ID()
      {
    	  return IDNUM;
      }
      public void setID(int set)
      {
    	  IDNUM = set;
      }
      public int DEX()
      {
    	  return DEXNUM;
      }
      public void levelUp(Socket client) throws IOException
     {
    	PrintWriter out = new PrintWriter(client.getOutputStream(), true);
        out.println(name + " has leveled up! - ");
       
        level = level + 1;
        hp = ((IV[0] + (2*baseStats[0]) + (EV[0]/4) + 100) * level)/100 + 10;
		att = ((IV[1] + (2*baseStats[1]) + (EV[1]/4) + 100) * level)/100 + 5;
		def = ((IV[2] + (2*baseStats[2]) + (EV[2]/4) + 100) * level)/100 + 5;
		spatt = ((IV[3] + (2*baseStats[3]) + (EV[3]/4) + 100) * level)/100 + 5;
		spdef = ((IV[4] + (2*baseStats[4]) + (EV[4]/4) + 100) * level)/100 + 5;
		speed = ((IV[5] + (2*baseStats[5]) + (EV[5]/4) + 100) * level)/100 + 5;
        tempHp = hp;
		tempAtt = att;
		tempDef = def;
		tempSpatt = spatt;
		tempSpdef = spdef;
		tempSp = speed;
        nextLevel = (level+1)*(level+1)*(level+1);
        out.println(" level-" + level + ", hp-" + hp +
           ", att-" + att + ", def-" + def + ", special att-" +
           spatt + ", special def-" + spdef + ", speed-" + speed + ".");
     }
      public void levelUp() throws IOException
      {
     	
         System.out.println(name + " has leveled up! - ");
        
         level = level + 1;
         hp = ((IV[0] + (2*baseStats[0]) + (EV[0]/4) + 100) * level)/100 + 10;
 		att = ((IV[1] + (2*baseStats[1]) + (EV[1]/4) + 100) * level)/100 + 5;
 		def = ((IV[2] + (2*baseStats[2]) + (EV[2]/4) + 100) * level)/100 + 5;
 		spatt = ((IV[3] + (2*baseStats[3]) + (EV[3]/4) + 100) * level)/100 + 5;
 		spdef = ((IV[4] + (2*baseStats[4]) + (EV[4]/4) + 100) * level)/100 + 5;
 		speed = ((IV[5] + (2*baseStats[5]) + (EV[5]/4) + 100) * level)/100 + 5;
         tempHp = hp;
 		tempAtt = att;
 		tempDef = def;
 		tempSpatt = spatt;
 		tempSpdef = spdef;
 		tempSp = speed;
         nextLevel = (level+1)*(level+1)*(level+1);
         System.out.println(" level-" + level + ", hp-" + hp +
            ", att-" + att + ", def-" + def + ", special att-" +
            spatt + ", special def-" + spdef + ", speed-" + speed + ".");
      }
      public int nextLevel()
     {
        return nextLevel;
     }
               
      public int status()
     {
        return status;
     }
      public void setStatus(int set)
     {
        status = set;
     }
     public boolean isWild()
     {
    	 return wild;
     }
     public void setWild(boolean set)
     {
    	 wild = set;
     }
     public int level()
     {
    	 return level;
     }
     public void statsSave() throws Exception
     {
   	LinkedList<Integer> stats = new LinkedList<Integer>();
	
   	stats.add(DEXNUM);
   	stats.add(IDNUM);
   	for(int i = 0; i < 6; i++)
   		stats.add(EVY[i]);
   	stats.add(tempHp);
   	stats.add(type1);
   	stats.add(type2);
   	stats.add(expworth);
   	stats.add(level);
   	stats.add(evolve);
   	stats.add(exp);
   	stats.add(status);
   	stats.add(USERNUM);
   	for(int i = 0; i < 6; i++)
   		stats.add(IV[i]);
   	for(int i = 0; i < 6; i++)
   		stats.add(EV[i]);
   	
   	
   	FileWriter statsSaver= new FileWriter(IDNUM + ".txt");
   	
   	statsSaver.write(name + "\r");
   	
   	for(int i = 0; i < stats.size(); i++)
   	{
   		Integer a = stats.get(i);
   		String k = a.toString();
   		statsSaver.write(k);
   		if(i != stats.size() - 1)
   		statsSaver.write("\r");
   		
   	}
   	statsSaver.close();

     }
     public void statsLoad() throws Exception
     {
   	  	FileReader statsLoader = new FileReader(IDNUM + ".txt");
   	   	BufferedReader br = new BufferedReader(statsLoader);
   	   		
   	   		name = br.readLine();
   	   		DEXNUM = Integer.valueOf(br.readLine());
   	   		IDNUM = Integer.valueOf(br.readLine());
         	for(int i = 0; i < 6; i++)
         		EVY[i] = Integer.valueOf(br.readLine());
         	tempHp = Integer.valueOf(br.readLine());
         	type1 = Integer.valueOf(br.readLine());
         	type2 = Integer.valueOf(br.readLine());
         	expworth = Integer.valueOf(br.readLine());
         	level = Integer.valueOf(br.readLine());
         	nextLevel = (level+1)*(level+1)*(level+1);
         	evolve = Integer.valueOf(br.readLine());
         	exp = Integer.valueOf(br.readLine());
         	status = Integer.valueOf(br.readLine());
         	wild = false;
         	USERNUM = Integer.valueOf(br.readLine());
         	for(int i = 0; i < 6; i++)
         		IV[i] = Integer.valueOf(br.readLine());
         	for(int i = 0; i < 6; i++)
         		EV[i] = Integer.valueOf(br.readLine());
         	hp = ((IV[0] + (2*baseStats[0]) + (EV[0]/4) + 100) * level)/100 + 10;
    		att = ((IV[1] + (2*baseStats[1]) + (EV[1]/4) + 100) * level)/100 + 5;
    		def = ((IV[2] + (2*baseStats[2]) + (EV[2]/4) + 100) * level)/100 + 5;
    		spatt = ((IV[3] + (2*baseStats[3]) + (EV[3]/4) + 100) * level)/100 + 5;
    		spdef = ((IV[4] + (2*baseStats[4]) + (EV[4]/4) + 100) * level)/100 + 5;
    		speed = ((IV[5] + (2*baseStats[5]) + (EV[5]/4) + 100) * level)/100 + 5;
    		tempAtt = att;
    		tempDef = def;
    		tempSpatt = spatt;
    		tempSpdef = spdef;
    		tempSp = speed;
         	
         	Integer a = DEXNUM;
         	
    		FileReader getMoveList = new FileReader("ML.txt");
    		BufferedReader gml = new BufferedReader(getMoveList);
    		
    		String k = gml.readLine();
    		while(k.equals("#" + a.toString()) != true )
    		{
    			k = gml.readLine();
    		}
    		while(k.equals("&") != true)
    		{
    			k = gml.readLine();
    			if(k.equals("~") == true)
    			{
    				if(level >= Integer.valueOf(gml.readLine()))
    				{
    				action temp = new action(Integer.valueOf(gml.readLine()));
    				MoveList.add(temp);
    				}
    			}
    		}
    		getMoveList.close();
     	
     	
   	statsLoader.close();
     
     }
     public void setStats(int level1) throws IOException
     {
    	level = level1;
    	Integer a = DEXNUM;
    	hp = ((IV[0] + (2*baseStats[0]) + (EV[0]/4) + 100) * level)/100 + 10;
		att = ((IV[1] + (2*baseStats[1]) + (EV[1]/4) + 100) * level)/100 + 5;
		def = ((IV[2] + (2*baseStats[2]) + (EV[2]/4) + 100) * level)/100 + 5;
		spatt = ((IV[3] + (2*baseStats[3]) + (EV[3]/4) + 100) * level)/100 + 5;
		spdef = ((IV[4] + (2*baseStats[4]) + (EV[4]/4) + 100) * level)/100 + 5;
		speed = ((IV[5] + (2*baseStats[5]) + (EV[5]/4) + 100) * level)/100 + 5;
 		tempHp = hp;
		tempAtt = att;
		tempDef = def;
		tempSpatt = spatt;
		tempSpdef = spdef;
		tempSp = speed;
 		exp = (int) Math.round(level*level*level);
		nextLevel = (int) Math.round((level+1)*(level+1)*(level+1));
		wild = true;
 		
		FileReader getMoveList = new FileReader("ML.txt");
		BufferedReader gml = new BufferedReader(getMoveList);
		
		String k = gml.readLine();
		while(k.equals("#" + a.toString()) != true )
		{
			k = gml.readLine();
		}
		while(k.equals("&") != true)
		{
			
			if(k.equals("~") == true)
			{
				if(level >= Integer.valueOf(gml.readLine()))
				{
				action temp = new action(Integer.valueOf(gml.readLine()));
				MoveList.add(temp);
				}
			}
			k = gml.readLine();
		}
		getMoveList.close();
     }
     public String PrintStatus()
     {
    	 String stat = "";
    	 
    	 if(status == 1)
    		stat = "psn";
    	 if(status == 2)
    		 stat = "brn";
    	 if(status == 3)
    		 stat = "slp";
    	 if(status == 4)
    		 stat = "frzn";
    	 if(status == 5)
    		 stat = "prlz";
    	 
    	 return stat;
     }
     public void StartStatusCounter()
     {
    	 StatusCounter = 1;
     }
     public void setStatusCounter(int i)
     {
    	 StatusCounter = 0;
     }
     public int getStatusCounter()
     {
    	 return StatusCounter;
     }
     public void setStatusDrop(int i)
     {
    	 StatusDrop = i;
     }
     public int statusDrop()
     {
    	 return StatusDrop;
     }
     public int evolve()
     {
    	 return evolve;
     }
     public int IV(int i)
     {
    	 return IV[i];
     }
     public int EV(int i)
     {
    	 return EV[i];
     }
     public int EVY(int i)
     {
    	 return EVY[i];
     }
     public void addEV(int y, int t)
     {
    	EV[t] = EV[t] + y;
     }
     public int OT()
     {
    	 return USERNUM;
     }
     public int tempAtt()
     {
    	 return tempAtt;
     }
     public int tempDef()
     {
    	 return tempDef;
     }
     public int tempSpA()
     {
    	 return tempSpatt;
     }
     public int tempSpD()
     {
    	 return tempSpdef;
     }
     public int tempSp()
     {
    	 return tempSp;
     }
     public void settempAtt(int i)
     {
    	 tempAtt = i;
     }
     public void settempDef(int i)
     {
    	 tempDef = i;
     }
     public void settempSpA(int i)
     {
    	 tempSpatt = i;
     }
     public void settempSpD(int i)
     {
    	 tempSpdef = i;
     }
     public void settempSp(int i)
     {
    	 tempSp = i;
     }
     public void addMove(int id) throws IOException
     {
    	 action hm = new action(id);
    	 MoveList.add(hm);
     }
     public int faintedCounter()
     {
    	 return faintedCounter;
     }
     public void setFaintedCounter(int i)
     {
    	 faintedCounter = i;
     }
}
