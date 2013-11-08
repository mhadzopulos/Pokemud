import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class action 
{	int ID, power, pp, accuracy, effect,special, type, effectChance, specialID, weatherReq, weather, ef2 = 0, efchance2 = 0;
	String name;
	boolean target, contact;
	
	public action(int IDACT1, String name1 , int power1, int pp1, int accuracy1, boolean contact1,
			int effect1, int special1, boolean target1, int type1, int effectChance1)
	{
		name = name1;
		ID = IDACT1;
		power = power1;
		pp = pp1;
		accuracy = accuracy1;
		contact = contact1;
		effect = effect1;
		special = special1;
		target = target1;
		type = type1;
		effectChance = effectChance1;
	}
	public action()
	{
		
	}
	public action(int ID1) throws IOException
	{
		FileReader getMove = new FileReader("actn.txt");
		BufferedReader gm = new BufferedReader(getMove);
		
		Integer a = ID1;
		ID = a;
		String k = gm.readLine();
		
		while(k.equals("#" + a.toString()) != true)
		{
			k = gm.readLine();
		}
		name = gm.readLine();
		power = Integer.valueOf(gm.readLine());
		pp = Integer.valueOf(gm.readLine());
		accuracy = Integer.valueOf(gm.readLine());
		contact = Boolean.valueOf(gm.readLine());
		effect = Integer.valueOf(gm.readLine());
		if(effect > 0)
			effectChance = Integer.valueOf(gm.readLine());
		special = Integer.valueOf(gm.readLine()); 			//HM = 1, // move req = 2// weather req = 3									
		if(special == 4)									//weather change = 4//other = 5 // extra effect = 6
			weather = Integer.valueOf(gm.readLine());
		if(special == 3)
			weatherReq = Integer.valueOf(gm.readLine());
		if(special == 2)
			specialID = Integer.valueOf(gm.readLine());
		if(special == 6)
		{
			ef2 = Integer.valueOf(gm.readLine());
			efchance2 = Integer.valueOf(gm.readLine());
		}	
		target = Boolean.valueOf(gm.readLine());
		type = Integer.valueOf(gm.readLine());
		
		getMove.close();
	}
	public String name()
	{
		return name;
	}
	public int power()
	{
		return power;
	}
	public int pp()
	{
		return pp;
	}
	public int accuracy()
	{
		return accuracy;
	}
	public boolean contact()
	{
		return contact;
	}
	public int effect()
	{
		return effect;
	}
	public int special()
	{
		return special;
	}
	public boolean target()
	{
		return target;
	}
	public int type()
	{
		return type;
	}
	public int chance()
	{
		return effectChance;
	}
	public int effect2()
	{
		return ef2;
	}
	public int chance2()
	{
		return efchance2;
	}
	public int weatherReq()
	{
		return weatherReq;
	}
	public int weather()
	{
		return weather;
	}
	public int specialID()
	{
		return specialID;
	}
}
