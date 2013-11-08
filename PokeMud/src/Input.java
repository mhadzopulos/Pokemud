import java.util.*;
import java.io.*;

public class Input 
{
//////////////////////////////////////////////////////////////////////////////
	//////////////////////////Set Up////////////////////////////////
//////////////////////////////////////////////////////////////////////////////
	static LinkedList<pokemon> PokePary = new LinkedList<pokemon>();
	static User tempU;
	static LinkedList<location> world = new LinkedList<location>();
	static LinkedList<Item> shop = new LinkedList<Item>();
	static LinkedList<pokemon> faintedPokemon = new LinkedList<pokemon>();
	static Scanner playerInput = new Scanner(System.in);
////////////////////////////////////////	
	///////Poke types////////
	static int normal = 0;
	static int fire = 1;
	static int water = 2;
	static int grass = 3;
	static int electric = 4;
	static int rock = 5;
	static int ground = 6;
	static int flying = 7;
	static int psychic = 8;
	static int ghost = 9;
	static int bug = 10;
	static int poison = 11;
	static int ice = 12;
	static int fighting = 13;
	static int dragon = 14;
	static int noType = 15;
/////////////////////////////////	
	//////status///////
	static int noEffect = 0;
	static int poisonE = 1;
	static int burn = 2;
	static int sleep = 3;
	static int frozen = 4;
	static int stun = 5;
	static int attD = 6;
	static int defD = 7;
	static int speedD = 8;
	static int spattD = 9;
	static int spdefD = 10;
	static int accuracyD = 11;
	static int attU = 12;
	static int defU = 13;
	static int spattU = 14;
	static int spdefU = 15;
	static int speedU = 16;
	static int accuracyU = 17;
	static int heal = 18;
	static int highCrit = 19;
	static int recharge = 20;
	static int allStatus = 21;
	static int evadeU = 22;
	static int hurt = 23;
	static int wait = 24;
	static int killChance = 25;
/////////////////////////////////////////
	/////////Status Tools////////////
	static int StatusCounter = 0;
	static int StatusDrop = 0;
/////////////////////////////////////////////
	///////////Weather/////////////////
	static int weather = 0;
	static int cloudy = 0;
	static int ignite = 1;
	static int raining = 2;
	static int overgrowth = 3;
	static int sandstorm = 6;
	static int hail = 12;
    ///////////////////////////////////////////
	//****************Time*******************//
	//////////////////////////////////////////
	static int TimeCounter = 0;
	public Input()
	{
		Timer time = new Timer();
		time.scheduleAtFixedRate(new hb(), 1000, 1000);
	}
///////////////////////////////////////////////
	static location currentLoc = new location();
	static pokemon batPoke = new pokemon();
	static pokemon hostile = new pokemon();
	static trainer hostileT = new trainer();
	static boolean HostileEnvironment = false;
	static boolean TrainerBattle = false;
	static int BACounter = 0;
	static int HACounter = 0;
	static int SWCounter = 0;
	static LinkedList<trainer> TrainerCounter = new LinkedList<trainer>();
	static LinkedList<pokemon> PCPoke = new LinkedList<pokemon>();
	

//////////////////////////////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	public static void main(String[]args)
	throws Exception,IndexOutOfBoundsException
	{

		FileReader UserInfo = new FileReader("UserInfo.txt");
		BufferedReader ui = new BufferedReader(UserInfo);
		

		System.out.println("PokeMud v1");
		System.out.print("name: ");
		String name = playerInput.next();
		ui.readLine();

		
		if(name.equals(ui.readLine()) != true)
		{
			
			User you = new User();
			System.out.println("What is your password going to be?");
			System.out.print("pass: ");
			String pass = playerInput.next();
			you.setInfo(name, pass);
			you.setGold(2000);
			tempU = you;
			System.out.println("Welcome to the amazing world of pokemon! \r From here on out " +
					"you will be living the life of pokemon trainer. \r" +
					"Don't worry you won't have to beat the shit out of a pidgey!" +
					"\r\rYou will be given one of the following pokemon. Make a choice.");
			System.out.println("1. Bulbasaur, 2.Charmander, 3.Squirtle");
			String pokechoice = playerInput.next();
			boolean choice = false;
			while(choice != true)
			{
			if(pokechoice.startsWith("b") == true || pokechoice.startsWith("1") || pokechoice.startsWith("B"))
			{
				pokemon Bulbasaur = new pokemon(5 , 1, you.ID());
				you.addPoke(Bulbasaur);
				System.out.println("You chose Bulbasaur!");
				choice = true;
				
				
			}
			if(pokechoice.startsWith("c") == true || pokechoice.startsWith("2")|| pokechoice.startsWith("C"))
			{
				pokemon Charmander = new pokemon(5, 4, you.ID());
				you.addPoke(Charmander);
				System.out.println("You chose Charmander!");
				choice = true;
			}
			if(pokechoice.startsWith("s") == true || pokechoice.startsWith("3")|| pokechoice.startsWith("S"))
			{
				pokemon Squirtle = new pokemon(5, 7, you.ID());
				you.addPoke(Squirtle);
				System.out.println("You chose Squirtle!");
				choice = true;
			}
			
			}
			save();
			loadWorld();
		}
		else
		{
			
			System.out.print("password: ");
			String password = playerInput.next();
			boolean passwordFind = false;
			while(passwordFind != true)
			{
			if(password.equals(ui.readLine()) != true)
			{
				System.out.println("Incorrect Password");
				password = playerInput.next();
				if(password.equals("quit_game"))
						System.exit(0);
			}
			else
			{
				System.out.println("WELCOME TO POKEMUD V1");
				FileReader getID = new FileReader("UserInfo.txt");
				BufferedReader gid = new BufferedReader(getID);
				tempU = new User(name, password, Integer.valueOf(gid.readLine()));
				System.out.println("Welcome back " + tempU.name());
				getID.close();
				loadUser();
				loadWorld();
				passwordFind = true;
				
			}
			}
		}
///////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////
		
		System.out.println("Choose your adventure.\r" +
				"1.Play, 2. Quit");
		
		String SInput = playerInput.next();
		
		if(SInput.equals("2") || SInput.equals("quit") == true)
			System.exit(0);
		
		else if(SInput.equals("1") == true)
		{
		
		if(tempU.roomNum == -1)
			currentLoc = world.get(0);
		else
			currentLoc = world.get(tempU.roomNum());
		String Input = "";
	
		Timer time = new Timer();
		new Input();
		
		printWorld();
		
////////////////////////////////////////////////////////////////////////////
//***********************************begin game**************************///
///////////////////////////////////////////////////////////////////////////
		while(Input.equals("quit")!= true) 
		{
			
			batPoke = tempU.getPoke(0);
			boolean foundAction = false;
			String sub = "";
			int partyCheck = 0;
			
			
			Input = playerInput.nextLine();
					
		////////////////////////////////////////////////////////////////	
		//************************Check for actions and substrings*******************//
		///////////////////////////////////////////////////////////////	
			if(Input.length() > 1)
			{
				int a = 0;
				String action = "";
				
				if(Input.contains(" "))
				{
					if(Input.endsWith(" ") != true)
					{
						a = Input.indexOf(' ');
						action = Input.substring(0, a);
						sub = Input.substring(0, a);
						foundAction = batPoke.checkMoves(action);
					}
				}
				
				
			}
		/////////////////////////////////////////////////////////////////////////////////////////	
		//*************************************Check Movement**********************************//
		////////////////////////////////////////////////////////////////////////////////////////
			
			if((Input.equalsIgnoreCase("w") || Input.equalsIgnoreCase("west")) && currentLoc.west() != 0)
			{
				if(HostileEnvironment == false && TrainerBattle == false)
				{
				boolean IDCHECK = false;
				int a = 0;
				while(IDCHECK == false)
				{
					location tempLoc = new location();
					tempLoc = (location)world.get(a);
					if(tempLoc.roomNum() == currentLoc.west())
					{
						currentLoc = null;
						currentLoc = (location)world.get(a);
						IDCHECK = true;
						printWorld();
					}
					if(a < world.size() - 1)
					a++;
				}
				}
				else
				System.out.println("You can't walk away from a battle!");
			}
			if((Input.equalsIgnoreCase("n") || Input.equalsIgnoreCase("north")) && currentLoc.north() != 0)
			{
				if(HostileEnvironment == false && TrainerBattle == false)
				{
				boolean IDCHECK = false;
				int a = 0;
				while(IDCHECK == false)
				{
					location tempLoc = new location();
					tempLoc = (location)world.get(a);
					if(tempLoc.roomNum() == currentLoc.north())
					{
						currentLoc = null;
						currentLoc = (location)world.get(a);
						IDCHECK = true;
						printWorld();
					}
					if(a < world.size() - 1)
					a++;
				}
				}
				else
				System.out.println("You can't walk away from a battle!");
			}
			if((Input.equalsIgnoreCase("s") || Input.equalsIgnoreCase("south")) && currentLoc.south() != 0)
			{
				if(HostileEnvironment == false && TrainerBattle == false)
				{
				boolean IDCHECK = false;
				int a = 0;
				while(IDCHECK == false)
				{
					location tempLoc = new location();
					tempLoc = (location)world.get(a);
					if(tempLoc.roomNum() == currentLoc.south())
					{
						currentLoc = null;
						currentLoc = (location)world.get(a);
						IDCHECK = true;
						printWorld();
					}
					if(a < world.size() - 1)
					a++;
				}
				}
				else
				System.out.println("You can't walk away from a battle!");
			}
			if((Input.equalsIgnoreCase("e") || Input.equalsIgnoreCase("east")) && currentLoc.east() != 0)
			{
				if(HostileEnvironment == false && TrainerBattle == false)
				{
				boolean IDCHECK = false;
				int a = 0;
				while(IDCHECK == false)
				{
					location tempLoc = new location();
					tempLoc = (location)world.get(a);
					if(tempLoc.roomNum() == currentLoc.east())
					{
						currentLoc = null;
						currentLoc = (location)world.get(a);
						IDCHECK = true;
						printWorld();
					}
					if(a < world.size() - 1)
					a++;
				}
				}
				else
				System.out.println("You can't walk away from a battle!");
			}
			if((Input.equalsIgnoreCase("u") || Input.equalsIgnoreCase("up")) && currentLoc.up() != 0)
			{
				if(HostileEnvironment == false && TrainerBattle == false)
				{
				boolean IDCHECK = false;
				int a = 0;
				while(IDCHECK == false)
				{
					location tempLoc = new location();
					tempLoc = (location)world.get(a);
					if(tempLoc.roomNum() == currentLoc.up())
					{
						currentLoc = null;
						currentLoc = (location)world.get(a);
						IDCHECK = true;
						printWorld();
					}
					if(a < world.size() - 1)
					a++;
				}
				}
				else
				System.out.println("You can't walk away from a battle!");
			}
			if((Input.equalsIgnoreCase("d") || Input.equalsIgnoreCase("down")) && currentLoc.down() != 0)
			{
				if(HostileEnvironment == false && TrainerBattle == false)
				{
				boolean IDCHECK = false;
				int a = 0;
				while(IDCHECK == false)
				{
					location tempLoc = new location();
					tempLoc = (location)world.get(a);
					if(tempLoc.roomNum() == currentLoc.down())
					{
						currentLoc = null;
						currentLoc = (location)world.get(a);
						IDCHECK = true;
						printWorld();
					}
					if(a < world.size() - 1)
					a++;
				}
				}
				else
					System.out.println("You can't walk away from a battle!");
					
			}
		
/////////////////////////////////////////////////////////////////////////////////////
//******************************check user/trainer commands************************//
////////////////////////////////////////////////////////////////////////////////////
			if(Input.equals("heal"))
			{
				boolean foundCenter = false;
				trainer temp = new trainer();
				if(currentLoc.mobNum() != 0)
				{
					for(int i = 0; i < currentLoc.mobNum(); i++)
					{
						if(currentLoc.getMob(i).isCenter == true)
						{
							temp = currentLoc.getMob(i);
							foundCenter = true;
						}
					}
					if(foundCenter == true)
					{
						System.out.println( temp.name() + " - " + temp.heal());
						for(int i = 0; i < tempU.partySize(); i++)
						{
							tempU.getPoke(i).setTempHp(tempU.getPoke(i).hp());
							tempU.getPoke(i).setStatus(0);
						}
						System.out.println("Your Pokemon were fully healed!");
					}
					else
					{	
						for(int i = 0; i < currentLoc.mobNum(); i++)
						{
							temp = currentLoc.getMob(i);
							System.out.println(temp.name() + " - " + temp.heal());
						}
					}
					
						
				}
				else
					System.out.println("There's no one around!");
			}
///////////////////////////////////////////////////////////////////////////////////////////
			if(Input.equals("battle") || sub.equals("battle"))
			{
				if(currentLoc.mobNum() > 0)
				{
					if(Input.contains(" ") == true)
					{
						int a = Input.indexOf(' ');
						String who = Input.substring(a+1, Input.length());
						for(int i = 0; i < currentLoc.mobNum(); i++)
						{
							if(currentLoc.getMob(i).name().startsWith(who))
							{
								if(currentLoc.getMob(i).pokeNum() > 0)
								{
									trainer temp = currentLoc.getMob(i);
									if(temp.counter() == 0)
									{
										if(temp.isLeader() == false)
										{
										System.out.println(currentLoc.getMob(i).battle());
										TrainerBattle = true;
										hostileT = currentLoc.getMob(i);
										System.out.println("Go " + currentLoc.getMob(i).getPoke(0).name() + "!");
										}
										else
										{
											if(tempU.countBadges() >= temp.badgeReq())
											{
												System.out.println(currentLoc.getMob(i).battle());
												TrainerBattle = true;
												hostileT = currentLoc.getMob(i);
												System.out.println("Go " + currentLoc.getMob(i).getPoke(0).name() + "!");
											}
											else
												System.out.println(currentLoc.getMob(i).name() + " - " + currentLoc.getMob(i).noBattle());
										}
									}
									else
										System.out.println(temp.name() + " - " + temp.beat());
								}
								else
									System.out.println(currentLoc.getMob(i).name() + " - " + currentLoc.getMob(i).battle());
							}
						}
					}
					else
					{
						for(int i = 0; i < currentLoc.mobNum(); i++)
						{
							if(currentLoc.getMob(i).pokeNum() > 0)
							{
								System.out.println(currentLoc.getMob(i).battle());
								TrainerBattle = true;
							}
							else
								System.out.println(currentLoc.getMob(i).battle());
						}
					}
				}
				else
					System.out.println("There is no one around!");
			}
////////////////////////////////////////////////////////////////////////////////////////////
			if(Input.equals("throw") || sub.equals("throw"))
			{
				if(sub.equals("throw"))
				{
					if(Input.endsWith(" ") != true)
					{
						int a = Input.indexOf(' ') + 1;
						String k = Input.substring(a, Input.length());
						if(k.contains(" "))
						{
							if(k.endsWith(" ") != true)
							{
								a = k.indexOf(' ');
								String what = k.substring(0, a);
								String who = k.substring(a+1, k.length());
								boolean pokefound = false;
								boolean itemfound = false;
								
								for(int i = 0; i < tempU.invSize() && itemfound == false; i++)
								{
									if(tempU.getItem(i).name().startsWith(what))
									{
										
										if(tempU.getItem(i).isBall == true)
										{
											
											itemfound = true;
											for(int b = 0; b < currentLoc.crtNum() && pokefound == false; b++)
											{
												if(currentLoc.getCreature(b).name().startsWith(who))
												{
													pokefound = true;
													catchP(tempU.getItem(i), currentLoc.getCreature(b));
													tempU.removeItem(i);
												}
											}
										}
										else
											System.out.println("Thats not a pokeball!");
									}
								}
							}
						}
					}
				}
				else
					System.out.println("throw what?");
			}
//////////////////////////////////////////////////////////////////////////////////////////			
			if(Input.equals("ask") || sub.equals("ask"))
			{
				if(sub.equals("ask"))
				{
					if(Input.endsWith(" ") != true)
					{
						int a = Input.indexOf(' ') + 1;
						String who = Input.substring(a, Input.length());
						for(int i = 0; i < currentLoc.mobNum(); i++)
						{
							if(currentLoc.getMob(i).name().startsWith(who))
							{
								trainer temp = currentLoc.getMob(i);
								System.out.println(temp.name() + " - " + temp.speak());
							}
						}
					}
				}
				
				if(Input.equals("ask"))
					System.out.println("Who are you asking?");
			}
////////////////////////////////////////////////////////////////////////////////////////
			if(Input.equals("list"))
			{
				
				if(currentLoc.mobNum() > 0)
				{
					for(int i = 0; i < currentLoc.mobNum(); i++)
					{
						if(currentLoc.getMob(i).isShop() == true)
						{
							System.out.println(currentLoc.getMob(i).name() + " - " + currentLoc.getMob(i).buy());
							for(int a = 0; a < shop.size(); a++)
							{	
								System.out.println(a+1 + ". " + shop.get(a).name() + " - " + shop.get(a).price());
							}
						}	
					}
				}
				else
					System.out.println("There is no one around!");
			}
///////////////////////////////////////////////////////////////////////////
			if(Input.equals("buy") || sub.equals("buy"))
			{
				if(currentLoc.mobNum() > 0)
				{
					for(int i = 0; i < currentLoc.mobNum(); i++)
					{
						if(currentLoc.getMob(i).isShop() == true)
						{	
							int a = Input.indexOf(' ') + 1;
							String what = Input.substring(a, Input.length());
							for(int b = 0; b < shop.size(); b++)
							{
								if(shop.get(b).name().startsWith(what))
								{	
									if(shop.get(b).price() < tempU.gold())
									{
										Item temp = shop.get(b);
										tempU.addItem(temp);
										tempU.setGold(tempU.gold() - temp.price());
										System.out.println("You bought a " + temp.name() + "!");
									}
									else
										System.out.println("You don't have the money for that!");
								}
							}
						}
							
					}
				}
				else
					System.out.println("There is no one around!");
			}
//////////////////////////////////////////////////////////////////////////////////
			if(Input.equals("sell") || sub.equals("sell"))
			{
				boolean itemFound = false;
				
				if(currentLoc.mobNum() > 0)
				{
					for(int i = 0; i < currentLoc.mobNum(); i++)
					{
						if(currentLoc.getMob(i).isShop() == true)
						{
							
							int a = Input.indexOf(' ') + 1;
							String what = Input.substring(a, Input.length());
							for(int b = 0; b < tempU.invSize() && itemFound == false; b++)
							{
								if(tempU.getItem(b).name().startsWith(what))
								{
									itemFound = true;
									Item temp = tempU.getItem(b);
									tempU.setGold(temp.price() + tempU.gold());
									tempU.removeItem(b);
								}
							}
						}
							
					}
				}
				else
					System.out.println("There is no one around!");
			}
////////////////////////////////////////////////////////////////////////////
			if(Input.equals("run"))
			{
				if(TrainerBattle != true)
				{
					if(HostileEnvironment == true)
					{
						HostileEnvironment = false;
						System.out.println("You runaway!!!");
					}
					else
						System.out.println("There's no need to run!");
				}
				else
					System.out.println("You can't run from a trainer!");
			}
	/////////////////////////////////////////////////////////////////////////////
			if(Input.equals("use") || sub.equals("use"));
			{
				if(sub.equals("use"))
				{
					if(Input.endsWith(" ") != true)
					{
						int a = Input.indexOf(' ') + 1;
						String k = Input.substring(a, Input.length());
						if(k.contains(" "))
						{
							int b = k.indexOf(' ');
							String what = k.substring(0, b);
							String who = k.substring(b+1, k.length());
							boolean itemF = false;
							boolean pokeF = false;
							System.out.println(what);
							System.out.println(who);
							for(int i = 0; i < tempU.invSize() && itemF == false; i++)
							{	
								if(tempU.getItem(i).name().startsWith(what))
								{
									for(int c = 0; i < tempU.partySize() && pokeF == false; c++)
									{
										if(tempU.getPoke(c).name().startsWith(who))
										{
											pokeF = true;
											itemDealer(tempU.getItem(i), tempU.getPoke(c));
											System.out.println("found");
										}
									}
									itemF = true;
								}
							}
						}
						else
							System.out.println("Use " + k + " on what?");
					}
				}
			}
///////////////////////////////////////////////////////////////////////////////////////////////
			if(Input.equals("take") || sub.equals("take"))
			{
				
			}
			if(Input.equals("give") || sub.equals("give"))
			{
				
			}
//////////////////////////////////////////////////////////////////////////////
			if(Input.equals("inventory") || Input.equals("inv"))
			{
				tempU.inventory();
			}
//////////////////////////////////////////////////////////////////////////////////////
//************************PC Commands***********************************************//
/////////////////////////////////////////////////////////////////////////////////////
			if(Input.equals("pc"))
			{
				if(currentLoc.isPC())
				{
					System.out.println("What would you like to do? \rDeposit \rWithdraw \rRelase");
					System.out.println("These are Pokemon stored in your PC:");
					if(tempU.PCSize() < 1)
						System.out.println("empty...");
					else
						for(int i = 0; i < tempU.PCSize(); i++)
						{
							System.out.println(tempU.getPC(i).name() + tempU.getPC(i).level());
						}
				}
			}
	/////////////////////////////////////////////////////////////////////////////
			if(Input.equals("deposit")|| sub.equals("deposit"))
			{
				boolean pokeFound = false;
				
				if(sub.equals("deposit"))
				{
					if(currentLoc.isPC())
					{
						int a = Input.indexOf(' ') + 1;
						String who = Input.substring(a, Input.length());
					
						if(tempU.partySize() > 1)
						{
							for(int i = 0; i < tempU.partySize() && pokeFound == false; i++)
							{
								if(tempU.getPoke(i).name().startsWith(who))
								{
										pokemon p = tempU.getPoke(i);
									pokeFound = true;
									tempU.addPC(p);
									tempU.removePoke(i);
									System.out.println("You deposited" + p.name() + " in your pc.");
								}
							}
						}
						else
							System.out.println("You need at least one pokemon in your party!");
					}
					else
						System.out.println("You need a PC to do that!");
				}
				else
					System.out.println("Deposit who?");
	/////////////////////////////////////////////////////////////////////////
			}
			if(Input.equals("withdraw")|| sub.equals("deposit"))
			{
				
				if(sub.equals("withdraw"))
				{
					if(currentLoc.isPC())
					{
						int a = Input.indexOf(' ') + 1;
						String who = Input.substring(a, Input.length());
						boolean pokeFound = false;
					
						if(tempU.partySize() < 6)
						{
							for(int i = 0; i < tempU.PCSize() && pokeFound == false; i++)
							{
								if(tempU.getPC(i).name().startsWith(who))
								{
									pokemon p = tempU.getPC(i);
									pokeFound = true;
									tempU.addPoke(tempU.getPC(i));
									tempU.removePC(i);
									System.out.println("You withdrew" + p.name() + ".");
								}
							}
						}
						else
						System.out.println("You need at least one open space in your party!");
					}
					else
						System.out.println("You need a PC to do that!");
				}
				else
					System.out.println("Withdraw who?");
			}
///////////////////////////////////////////////////////////////////////////////////
			if(Input.equals("release") || sub.equals("release"))
			{
				if(sub.equals("release"))
				{
					if(currentLoc.isPC())
					{
						int a = Input.indexOf(' ') + 1;
						String who = Input.substring(a, Input.length());
						boolean pokeFound = false;
						
						for(int i = 0; i < tempU.PCSize() && pokeFound == false; i++)
						{
							if(tempU.getPC(i).name().startsWith(who))
							{
								pokeFound = true;
								System.out.print("Are you sure you want to delete " + tempU.getPC(i).name() + "?");
								System.out.println(" - y/n");
								Input = playerInput.nextLine();
								if(Input.startsWith("y"))
								{
									System.out.println("Alright " + tempU.getPC(i) + " was be released");
									tempU.removePC(i);
								}
								if(Input.startsWith("n"))
									System.out.println("Alright " + tempU.getPC(i).name() + " won't be released");
								else
									System.out.println("try again...");
							}
						}
					}
					
				}
			}

/////////////////////////////////////////////////////////////////////////////////////
//******************************check poke commands********************************//
////////////////////////////////////////////////////////////////////////////////////
			if(sub.equals("go")) 
			{
				int a = Input.indexOf(' ') + 1;
				String who = Input.substring(a, Input.length());
				boolean pokeFound = false;
				
				for(int i = 0; i < tempU.partySize() && pokeFound == false; i++)
				{
					if(tempU.getPoke(i).name().startsWith(who))
					{
						pokeFound = true;
						pokemon c = tempU.getPoke(0);
						pokemon b = tempU.getPoke(i);
						if(b.tempHp() > 0)
						{
							tempU.setP(0, b);
							tempU.setP(i, c);
							System.out.println("You switched out " + c.name() + " for " + b.name() + ".");
						}
						else
							System.out.println("You can't switch with a fainted pokemon!");
						
					}
				}
			}
			
     //**********************Action!*****************************//	
			
			if(foundAction == true)
			{
				if(BACounter == 0)
				{
				if(batPoke.tempHp() > 0)
				{
				int a = 0;
				boolean pokeFound = false;
				pokemon targetP = new pokemon();
				a = Input.indexOf(' ');
				String action = Input.substring(0, a);
				action tempA = batPoke.getMove(action);
				
				if(tempA.target() == false)
				{
					if(tempA.special == 3)
					{
						if(weather == tempA.weatherReq())
							action(batPoke, tempA);
						else
							System.out.println("you can't do that!");
					}
					else if(tempA.special() == 4)
						weather = tempA.weather();
					else
						action(batPoke, tempA);
				}
				if (tempA.target() == true)
				{
					if(tempA.special == 3)
					{
						
					}
					if(Input.contains(" ") == true) //check if there is more than the action
					{ 
						if(Input.endsWith(" ") != true) //check if the last thing is the space//
						{
							a = Input.indexOf(' ') + 1;
							String tar = Input.substring(a, Input.length());
							for(int i = 0; i < currentLoc.crtNum() && pokeFound == false; i++)
							{
								if(currentLoc.getCreatureName(i).startsWith(tar))
								{
									pokeFound = true;
									targetP = currentLoc.getCreature(i);
								}
							}
							if(TrainerBattle == true)
							{
								if(currentLoc.mobNum() > 0)
								{
									boolean mobFound = false;
									for(int i = 0; i < currentLoc.mobNum() && mobFound == false; i++)
									{
										if(currentLoc.getMob(i).pokeNum() > 0)
										{
											trainer temp = currentLoc.getMob(i);
											if(temp.getPoke(0).name().startsWith(tar))
											{
												pokeFound = true;
												mobFound = true;
												targetP = temp.getPoke(0);
											}
										}
									}
								}
							}
							if(pokeFound == true)
							{	
								HostileEnvironment = true;
								hostile = targetP;
								action(batPoke, targetP, tempA);
								BACounter = 1;
								
								if(targetP.tempHp() < 1)
								{
									currentLoc.removeCreature(targetP);
									HostileEnvironment = false;
									HACounter = 0;
									checkLevel(tempU.getPoke(0));
									
								}
								else
									attack(targetP, batPoke);
								
								System.out.println();
							}
						
							}
							else if(currentLoc.crtNum() < 1)
							{
								System.out.println("There's no one around");
							}
						}
					}
				}
				else
					System.out.println("Your Pokemon cannot do that!");
				}
				
			}
///////////////////////////////////////////////////////////////////////
//**********************************ATTACKS**************************//
//////////////////////////////////////////////////////////////////////
			
			if(Input.startsWith("att") || Input.equalsIgnoreCase("a") || Input.equalsIgnoreCase("attack") 
					|| Input.equalsIgnoreCase("hit"))
			{
				if(batPoke.tempHp() > 0)
				{
					
				int a = 0 ;
				boolean pokeFound = false; 
				pokemon targetP = new pokemon();
				
				
				if(Input.contains(" ") == true) //check if there is more than the att command
				{
					if(Input.endsWith(" ") != true) //check if the last thing is the space//
					{
						a = Input.indexOf(' ') + 1;
						if(currentLoc.crtNum() > 0)
						{
							String tar = Input.substring(a, Input.length());
							for(int i = 0; i < currentLoc.crtNum() && pokeFound == false; i++)
							{
								if(currentLoc.getCreatureName(i).startsWith(tar))
								{
									pokeFound = true;
									targetP = currentLoc.getCreature(i);
								}
							}
							if(pokeFound == true)
							{
								attack(batPoke, targetP);
								HostileEnvironment = true;
								hostile = targetP;
								if(targetP.tempHp() < 1)
								{
									currentLoc.removeCreature(targetP);
									HostileEnvironment = false;
									HACounter = 0;
									checkLevel(tempU.getPoke(0));
								}
								else
									attack(targetP, batPoke);
								System.out.println();
							}
						
						}
					}
				}
				else if(Input.contains(" ") != true)
				{
					System.out.println("attack who?");
				}
			}
			else
				System.out.println("Your Pokemon cannot do that!");
			}		
			

/////////////////////////////////////////////////////////////////////////////////////			
//******************************info commands**************************************//
////////////////////////////////////////////////////////////////////////////////////
			if(Input.startsWith("mov") == true)
			{
				batPoke.moves();
			}
			if(Input.equalsIgnoreCase("h") || Input.equalsIgnoreCase("help") == true)
			{
				System.out.println("Here is a list of commands so far: ");
				System.out.println("Pokemon - attack, hit, any move from your movelist, go");
				System.out.println("User/Trainer - use, take, buy, sell, run, battle, heal");
				System.out.println("Info - help, movelist, stats, self, poke");
				System.out.println("PC - pc, withdraw, deposit, release");
			}
			if(Input.equalsIgnoreCase("stat") || Input.equalsIgnoreCase("stats"))
			{
				System.out.println(batPoke.DEX() + ". " + batPoke.name() + "(" + batPoke.level() + ") " + batPoke.tempHp() + "/" + batPoke.hp()
						+ "hp");
				System.out.println("att: " + batPoke.att() + "     speed: " + batPoke.speed() + "     exp: " + batPoke.exp() );
				System.out.println("def: " + batPoke.def() + "     type1: " + "      next: " + batPoke.nextLevel());
				System.out.println("sp.att: " + batPoke.spatt() + "   type2: " + "    O/T: " + batPoke.UserNum());
				System.out.println("sp.def: " + batPoke.spdef() + "   ID: " + batPoke.ID());
			}
			if(Input.equalsIgnoreCase("self"))
			{
				System.out.println(tempU.name());
				System.out.println("Gold - " + tempU.gold());
				System.out.println("badges:");
				for(int i = 0; i < 6; i++)
				{
					if(tempU.badge(i) == false)
						System.out.print(i+1 + ".? ");
					else
					{
						System.out.print(i+1 + ".obtained ");	
					}
						
				}
				System.out.println();
				for(int i = 6; i < tempU.badgeSize(); i++)
				{
					if(tempU.badge(i) == false)
						System.out.print(i+1 + ".? ");
					else
					{
						System.out.print(i+1 + ".obtained ");	
					}
						
				}
				System.out.println();
			}
			if(Input.equals("poke"))
			{
				System.out.println("Your party:");
				for(int i = 0; i < tempU.partySize(); i++)
				{
					pokemon temp = tempU.getPoke(i);
					System.out.println(temp.name() + "(" + temp.level() + ") - " + temp.tempHp() + 
							"/" + temp.hp());
				}
			}

/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
			
			
			save();
			
			
			if(Input.equals("quit") == true)
			{
				time.cancel();
				System.exit(0);
			}
			if(Input.equals("") == true)
			{
				printWorld();
			}
			for(int i = 0; i < tempU.partySize(); i++)
			{
				if(tempU.getPoke(i).tempHp() == 0)
					partyCheck++;
			}
			if(partyCheck == tempU.partySize())
			{
				tempU.setGold(tempU.gold()/2);
				partyCheck = 0;
				System.out.println("You ran out of Pokemon! To Prof Perve!");
				currentLoc = world.get(0);
			}
		}
			
	}
}
	
	
	/////////////////////////////////////////////////////////////////////////////////
	//********************************static commands******************************//
	////////////////////////////////////////////////////////////////////////////////
	public static void save() throws Exception
	{
		
		for(int i = 0; i < tempU.partySize(); i++)
		{
			pokemon temp = new pokemon();
			temp = (pokemon)tempU.getPoke(i);
			temp.statsSave();
		}
		tempU.setRoomNum(world.indexOf(currentLoc));
		tempU.savePoke();
		tempU.saveInfo();
	}
	//////////////////////////////////////////////////////////////////////////////////////
	//************************************load******************************************//
	/////////////////////////////////////////////////////////////////////////////////////
	public static void loadUser() throws Exception
	{
		////////////////////////////////////////
		//**** load user and pokemon *********//
		///////////////////////////////////////
		FileReader getPoke = new FileReader(tempU.ID() + ".txt");
		BufferedReader gp = new BufferedReader(getPoke);
		
		int i = 0;
		System.out.println("loading user info...");
		while(i != -1)
		{
			FileReader PokeLoader = new FileReader(gp.readLine() + ".txt");
			BufferedReader pl = new BufferedReader(PokeLoader);
			gp.mark(2);
			if(gp.read() == -1)
				i = -1;	
			gp.reset();
			
			
			
			pl.readLine();
			pokemon temp = new pokemon(Integer.valueOf(pl.readLine()));
			temp.setID(Integer.valueOf(pl.readLine()));
			temp.statsLoad();
			if(tempU.partySize() < 6)
				tempU.addLastP(temp);
			else
				tempU.addLastPC(temp);
			PokeLoader.close();

		}
		
		getPoke.close();
		tempU.loadInfo();
		
	}
	public static void loadWorld() throws Exception
	{
		////////////////////////////////////////////////////////////
		//****** load worlds**************************************//
		///////////////////////////////////////////////////////////
		FileReader WorldLoader = new FileReader("Wld.txt");
		BufferedReader wl = new BufferedReader(WorldLoader);
		int i = 1;
		Integer a = Integer.valueOf(wl.readLine());
		System.out.println("loading worlds...");
		while(i != a)
		{
			location part = new location(i);
			world.add(part);
			i++;
		}
		WorldLoader.close();
		///////////////////////////////////////////////////////
		//**********load pokes******************************///
		//////////////////////////////////////////////////////
		
		FileReader ZoneLoader = new FileReader("Zne.txt");
		BufferedReader zl = new BufferedReader(ZoneLoader);
		
		i = 0;
		a = Integer.valueOf(zl.readLine());
		System.out.println("loading pokemon...");
		while(i < a)
		{
			zl.readLine();
			Integer dex =  Integer.valueOf(zl.readLine());
			int dexnum = dex.intValue();
			
			String check = zl.readLine();
			
			while(check.equals("&") != true)
			{
				if(check.equals("~") == true)
				{	
					pokemon poke = new pokemon(dexnum);
					poke.setStats(Integer.valueOf(zl.readLine()));
					Integer b = Integer.valueOf(zl.readLine());
					location temp = (location)world.get(b.intValue() - 1);
					temp.addCreature(poke);
					
				}
				check = zl.readLine();
			}
			i++;
		}
		ZoneLoader.close();
		
		//////////////////////////////////////////////////////////////
		//********Load Trainers/Shops/Centers***********************//
		/////////////////////////////////////////////////////////////
		
		//trainers
		FileReader TrainerLoader = new FileReader("Trn.txt");
		BufferedReader tl = new BufferedReader(TrainerLoader);
		
		i = 0;
		a = Integer.valueOf(tl.readLine());
		System.out.println("loading trainers...");
		
		
		while(i < a)
		{ 
			i++;
			trainer trn = new trainer(i);
			int roomNum = trn.roomNum();
			world.get(roomNum - 1).addTrainer(trn);
			
		}
		TrainerLoader.close();
		
		//shops
		
		FileReader shopLoader2 = new FileReader("Obj.txt");
		BufferedReader sl2 = new BufferedReader(shopLoader2);
		
		i = 0;
		a = Integer.valueOf(sl2.readLine());
		System.out.println("loading shops...");
		
		while(i < a)
		{
			i++;
			Item item = new Item(i);
			if(item.badgrq() <= tempU.countBadges())
				shop.add(item);
		}
		shopLoader2.close();
		
	}	
	////////////////////////////////////////////////
	//*********Weather Check**********************//
	///////////////////////////////////////////////
	public static void PrintWeather()
	{
		if(currentLoc.weather() == true)
		{
			if(weather == cloudy)
				System.out.println("There are a few clouds");
			if(weather == ignite)
				System.out.println("The entire area is on fire!");
			if(weather == raining)
				System.out.println("Rain is pouring down");
			if(weather == hail)
				System.out.println("Hail is falling!");
			if(weather == sandstorm)
				System.out.println("A sandstorm is whipping around!");
			if(weather == overgrowth)
				System.out.println("There are vines and shrubs grown over the area!");
		}
	}
	////////////////////////////////////////////////
	//*********Item Dealer************************//
	///////////////////////////////////////////////
	public static void itemDealer(Item item, pokemon p)
	{
		if(item.isUse() == true)
		{
			if(item.revive() == true)
			{
				if(p.tempHp() < 1)
				{
					p.setTempHp(p.hp()/2);
					System.out.println("You revived " + p.name()+ "!");
					tempU.removeItem(item);
				}
				else
					System.out.println("Your pokemon isn't fainted!");
			}
			////////////////////////////
			if(item.heal() > 0)
			{
				if(p.tempHp() > 0)
				{
					if(p.tempHp() != p.hp())
					{
						if(p.tempHp() + item.heal() < p.hp())
						{
							p.setTempHp(p.tempHp() + item.heal());
							System.out.println("You healed " + p.name() + " for " + item.heal() + " hp.");
							tempU.removeItem(item);
						}
						else
						{
							p.setTempHp(p.hp());
							System.out.println("You healed " + p.name() + " completely!");
							tempU.removeItem(item);
						}
					}
					else
						System.out.println(p.name() + " is already at full health!");
				}
				else
					System.out.println("You can't use that on a fainted pokemon!");
			}
			//////////////////////////////////
			if(item.allHealth() == true)
			{
				if(p.tempHp() > 0)
				{
					if(p.tempHp() != p.hp())
					{
						p.setTempHp(p.hp());
						System.out.println("You healed " + p.name() + " completely!");
						tempU.removeItem(item);
					}
					else
						System.out.println("Your pokemon is already at full health");
				}
				else
					System.out.println("You can't use that on a fainted pokemon!");
			}
			//////////////////////////////////
			if(item.allStatus() == true)
			{
				if(p.status() > 0)
				{
					p.setStatus(0);
					System.out.println("Your pokemon has fully recovered!");
					tempU.removeItem(item);
				}
				else
					System.out.println("Your pokemon is already in perfect health!");
			}
			///////////////////////////////
			if(item.status() > 0)
			{
				if(p.status() > 0)
				{
					if(item.status == p.status())
					{
						p.setStatus(0);
						System.out.println("Your pokemon has fully recovered!");
						tempU.removeItem(item);
					}
					else
						System.out.println("Your pokemon is not affected by that illness");
				}
				else
					System.out.println("Your pokemon is already in perfect health!");
			}
			
		}
		else
			System.out.println("You can't use that on " + p.name());
	}
	////////////////////////////////////////////////
	//*********Catch Pokemon**********************//
	///////////////////////////////////////////////
	public static void catchP(Item item, pokemon tar) throws IOException
	{
		double health = tar.tempHp()/tar.hp();
		double levelRate = 1, healthRate, statusRate = 1;
		
		if(health > 0 && health < .26);
			healthRate = 2;
		if(health > .25 && health < .51);
			healthRate = 1.5;
		if(health > .5 && health < .76);
			healthRate = 1;
		if(health > .75 && health < 1.01);
			healthRate = 0;
		////////////////////////
		
		if(tar.level() > 0 && tar.level() < 11)
			levelRate = 1;
		if(tar.level() > 10 && tar.level() < 21)
			levelRate = 2;
		if(tar.level() > 20 && tar.level() < 31)
			levelRate = 4;
		if(tar.level() > 30 && tar.level() < 41)
			levelRate = 8;
		if(tar.level() > 40 && tar.level() < 100)
			levelRate = 16;
		////////////////////////////
		
		if(tar.status == poisonE)
			statusRate = 1.5;
		if(tar.status == stun)
			statusRate = 1.5;
		if(tar.status == frozen)
			statusRate = 2;
		if(tar.status == sleep)
			statusRate = 2;
		///////////////////////////////////
		
		if(item.catchrate()*healthRate*statusRate > levelRate) 
		{
			int level = tar.level();
			int dex = tar.DEX();
			
			if(tempU.partySize() < 6)
			{
				pokemon temp = tar;
				temp = new pokemon(level, dex, tempU.ID());
				tempU.addLastP(temp);
				currentLoc.removeCreature(tar);
				System.out.println("You caught " + temp.name() + "!");
				
			}
			if(tempU.partySize() > 5)
			{
				pokemon temp = tar;
				temp = new pokemon(level, dex, tempU.ID());
				tempU.addLastPC(temp);
				currentLoc.removeCreature(tar);
				System.out.println("You caught " + temp.name() + "!");
				System.out.println("But there wasn't enough room so they were sent to your PC");
				
			}
			
			
		}
		else
			System.out.println("Awwww you didn't catch it!");
	
	}
	public static void checkLevel(pokemon p) throws IOException
	{
		if(p.exp() > p.nextLevel())
		{
			p.levelUp();
			boolean check = false;
			if(p.level() >= p.evolve())
			{
				System.out.println(p.name() + " is evolving. Should " + p.name() + " evolve? y/n");
				String evolveCheck = playerInput.nextLine();
				while(check == false)
				{
					if(evolveCheck.startsWith("y"))
					{
						pokemon temp = new pokemon(p);
						tempU.removePoke(p);
						tempU.addPoke(temp);
						System.out.println("Your " + p.name() + " evolved into a " + temp.name() + "!!");
						check = true;
					}
					else if(evolveCheck.startsWith("n"))
					{
						System.out.println(p.name() + " stopped trying to evolve.");
						check = true;
					}
				}
				
			}
		}
	}
	//////////////////////////////////////////////////
	//******************Regular Attack**************//
	/////////////////////////////////////////////////
	public static void attack(pokemon a, pokemon tar) throws IOException
	{
		int damage, toHit, chance,exp;
		double c, t, l = tar.level(), lp = a.level(), b = tar.expWorth();
		
		Random rand = new Random();
		toHit = a.speed()/tar.speed()*50;
		chance = rand.nextInt(100) + 1;
		
		if(tar.isWild() == true)
			c = 1;
		else
			c = 1.5;
		
		if(a.OT() == tempU.ID())
			t = 1;
		else
			t = 1.5;
		
		exp =(int)(((c*t*l*b)/5) * ((l+2)/(lp+2))) + 1;
		
		if(chance < toHit)
		{
			double p = 2*(((double)a.level())+10)/250;
			double d = (double)a.tempAtt()/((double)tar.tempDef());
				damage = (int)(p*d + 1);
			if(tar.tempHp() - damage > 0)
			{
			tar.setTempHp(tar.tempHp() - damage);
			System.out.println(a.name() + " hit " + tar.name() + " for " + damage + " hp!");
			}
			else
			{
				tar.setTempHp(0);
				System.out.println(a.name() + " hit " + tar.name() + " for " + damage + " hp!");
				System.out.println(tar.name() + " feinted!");
				a.newExp(a.exp + exp);
				for(int i = 0; i < 6; i++)
					a.addEV(tar.EVY(i), i);
				System.out.println(a.name + " got " + exp + " xp!");
			}	
		}
		else
		{
			System.out.println(a.name() + " missed " + tar.name());
		}
	}
	////////////////////////////////////////////////
	//*********Actions****************************//
	///////////////////////////////////////////////
	public static void action(pokemon a, pokemon tar, action Action) throws IOException
	{
		int damage = 1, chance, exp, critical = 1;
		double c, t, l = tar.level(), lp = a.level(), b = tar.expWorth();
		double baseE = 100, baseA = 100, toHit;
		Random rand = new Random();
		double stab = 1, weatherMod = 1;
		if(tar.status() == evadeU)
			baseE = 120;
		if(a.status() == accuracyU)
			baseA = 120;
		if(a.status() == accuracyD)
			baseA = 80;
		toHit = (Action.accuracy()/baseE) * baseA;
		chance = rand.nextInt(100)+1;
		
		
		if(tar.isWild() == true)
			c = 1;
		else
			c = 1.5;
		
		if(a.OT() == tempU.ID())
			t = 1;
		else
			t = 1.5;
		
		double p = c*t*l*b/5;
		double g = (l+2)/(lp+2);
		exp = (int)(p * g) + 1;
		
		if(weather == raining && Action.type() == electric)
			chance = 0;
		
		if(chance < toHit)
		{
			double multiplier1 = elemental(tar.type1(), Action.type()); //DEALS WITH ELEMENTAL DAMAGE
			double multiplier2 = elemental(tar.type2(), Action.type());
			double random = rand.nextDouble() + .85;
			if(random > 1)
				random = 1;
			if(a.type1() == Action.type() || a.type2 == Action.type());
			stab = 1.5;
			
			
			if(Action.effect() == highCrit)
			{
				if(chance < Action.chance() +1)
					critical = 2;
			}
			else
			{
				if(chance < 11)
					critical = 2;
			}
			if(weather == Action.type() && weather != 0)
				weatherMod = 1.5;
			
			double modification = (stab)*multiplier1*multiplier2*weatherMod*critical*random;
			
	////////////////////////////////////////////////////////////////////////////////
			
			if(Action.contact() == true)// FOR PHYSICAL ATTACKS
			{
					
				if(Action.power() == 0) // for attacks like poison powder
					damage = 0;
				else
					damage = (int)((((2*(double)a.level()+10)/250)*((double)a.tempAtt()/(double)tar.tempDef())*(double)Action.power()+2)*modification) + 1;
				if(tar.tempHp() - damage > 0)
				{
					if(damage > 0)
					{
						tar.setTempHp(tar.tempHp() - damage);
						System.out.println(a.name() + " used " + Action.name()+ " on " + tar.name() + " for " + damage + " hp!");
						if(critical == 2)
							System.out.println("Critical hit!");
						if(multiplier1  == 2 || multiplier2 == 2)
							System.out.println("It's super effective!");
						if(multiplier1 == .5 || multiplier2 == .5)
							System.out.println("It's not very effective");
						if(Action.effect > 0)//deal with effects//
						{
							int eChance = rand.nextInt(100) + 1;
							if(eChance <= Action.chance())
							{
								tar.setStatus(Action.effect());
							}
							if(Action.effect2() > 0)
							{
								eChance = rand.nextInt(100)+1;
								if(eChance <= Action.chance2())
								{
									tar.setStatus(Action.effect2());
								}
							}
						}
					}
					if(damage == 0)
					{
						System.out.println(a.name() + " used " + Action.name() + " on " + tar.name());
						if(Action.effect > 0)//deal with effects//
						{
							int eChance = rand.nextInt(100) + 1;
							if(eChance <= Action.chance())
							{
								tar.setStatus(Action.effect());
							}
							if(Action.effect2() > 0)
							{
								eChance = rand.nextInt(100)+1;
								if(eChance <= Action.chance2())
								{
									tar.setStatus(Action.effect2());
								}
							}
						}
					}
				}
				else
				{
					tar.setTempHp(0);
					System.out.println(a.name() + " used " + Action.name() + " on " + tar.name() + " for " + damage + " hp!");
					if(critical == 2)
						System.out.println("Critical hit!");
					if(multiplier1  == 2 || multiplier2 == 2)
						System.out.println("It's super effective!");
					if(multiplier1 == .5 || multiplier2 == .5)
						System.out.println("It's not very effective");
					System.out.println(tar.name() + " feinted!");
					a.newExp(a.exp + exp);
					for(int i = 0; i < 6; i++)
						a.addEV(tar.EVY(i), i);
					System.out.println(a.name + " got " + exp + " xp!");
				}	
			}
			
			///////////////////////////////////////////////////////////////////////////////////
			else //FOR SPECIAL ATTACKS
			{
				damage = (int) ((((2*(double)a.level()+10)/250)*((double)a.tempSpA()/(double)tar.tempSpD())*(double)Action.power()+2)*modification) + 1;
					
				if(Action.power() == 0)
						damage = 0; // for attacks like poison powder
				
				if(tar.tempHp() - damage > 0)
				{
					if(damage > 0)
					{
						tar.setTempHp(tar.tempHp() - damage);
						System.out.println(a.name() + " used " + Action.name()+ " on " + tar.name() + " for " + damage + " hp!");
						if(critical == 2)
							System.out.println("Critical hit!");
						if(multiplier1  == 2 || multiplier2 == 2)
							System.out.println("It's super effective!");
						if(multiplier1 == .5 || multiplier2 == .5)
							System.out.println("It's not very effective");
						if(Action.effect > 0)//deal with effects//
						{
							int eChance = rand.nextInt(100) + 1;
							if(eChance <= Action.chance())
							{
								tar.setStatus(Action.effect());
							}
							if(Action.effect2() > 0)
							{
								eChance = rand.nextInt(100)+1;
								if(eChance <= Action.chance2())
								{
									tar.setStatus(Action.effect2());
								}
							}
						}
					}
					if(damage == 0)
					{
						System.out.println(a.name() + " used " + Action.name() + " on " + tar.name());
						if(Action.effect > 0)//deal with effects//
						{
							int eChance = rand.nextInt(100) + 1;
							if(eChance <= Action.chance())
							{
								tar.setStatus(Action.effect());
							}
							if(Action.effect2() > 0)
							{
								eChance = rand.nextInt(100)+1;
								if(eChance <= Action.chance2())
								{
									tar.setStatus(Action.effect2());
								}
							}
						}
					}
				}
				else
				{
					tar.setTempHp(0);
					System.out.println(a.name() + " used " + Action.name()+ " on " + tar.name() + " for " + damage + " hp!");
					if(critical == 2)
						System.out.println("Critical hit!");
					if(multiplier1  == 2 || multiplier2 == 2)
						System.out.println("It's super effective!");
					if(multiplier1 == .5 || multiplier2 == .5)
						System.out.println("It's not very effective");
					System.out.println(tar.name() + " feinted!");
					a.newExp(a.exp + exp);
					for(int i = 0; i < 6; i++)
						a.addEV(tar.EVY(i), i);
					System.out.println(a.name + " got " + exp + " xp!");
				}
			}
		}
		else
			System.out.println(a.name() + " missed " + tar.name() + " with " + Action.name()+"!");
	}
	////////////////////////////////////////////////////////////////////
	//************Stat raising or Other self Actions******************//
	///////////////////////////////////////////////////////////////////
	public static void action(pokemon a, action Action)
	{
		Random rand = new Random();
		int eChance;
		eChance = rand.nextInt(100)+1;
		if(eChance <= Action.chance())
			a.setStatus(Action.effect());
		if(Action.effect2() > 0)
		{
			eChance = rand.nextInt(100)+1;
			if(eChance <= Action.chance2())
				a.setStatus(Action.effect2());
		}
	}
	

	////////////////////////////////////////////////
	//************Print World and Poke************//
	///////////////////////////////////////////////
	public static void printWorld()
	{
		System.out.println(currentLoc.getTitle() + "\r	" + currentLoc.getDes());
		if(currentLoc.weather() == true)
			PrintWeather();
		System.out.println();
		currentLoc.getList();
		System.out.println();
		currentLoc.printExits();
		System.out.println("");
		System.out.println(tempU.getPoke(0).name() + "-" 
				+ "level " + tempU.getPoke(0).level()  + " " + tempU.getPoke(0).tempHp() 
				+ "/" + tempU.getPoke(0).hp() + tempU.getPoke(0).PrintStatus());
	}
	/////////////////////////////////////////////////////
	//***********Status Effects************************//
	////////////////////////////////////////////////////
	public static void effectDealer(pokemon a, int effect)
	{
		Random rand = new Random();
		int time =rand.nextInt(5) + 2;
		a.setStatusDrop(time);
		
		
		if(effect == poisonE)
		{
			a.setStatus(poisonE);
			a.StartStatusCounter();
		}
		if(effect == sleep)
		{
			a.setStatus(sleep);
			a.StartStatusCounter();
		}
		if(effect == frozen)
		{	
			a.setStatus(frozen);
			a.StartStatusCounter();
		}	
		if(effect == burn)
		{	
			a.setStatus(burn);
			a.StartStatusCounter();
		}	
		if(effect == stun)
		{	
			a.StartStatusCounter();
			a.setStatus(stun);
		}	
		//////////////////////////////////STAT EFFECTS////////////////////
		
		if(effect == attU)
		{
			a.StartAttCounter(0);
			int change;
			change = (int)((double)a.tempAtt()*1.2);
			a.settempAtt(a.tempAtt() + change);
			a.setAttDrop(time, 0);
			System.out.println(change);
			System.out.println(a.name() + "'s attack went up!");
		}
		if(effect == defU)
		{
			a.StartAttCounter(1);
			int change;
			change = (int)((double)a.tempDef()*1.2);
			a.settempAtt(a.tempDef() + change);
			a.setAttDrop(time, 1);
			System.out.println(change);
			System.out.println(a.name() + "'s defense went up!");
		}
		if(effect == spattU)
		{
			a.StartAttCounter(2);
			int change;
			change = (int)((double)a.tempSpA()*1.2);
			a.settempAtt(a.tempSpA() + change);
			a.setAttDrop(time, 2);
			System.out.println(change);
			System.out.println(a.name() + "'s special attack went up!");
		}
		if(effect == spdefU)
		{
			a.StartAttCounter(3);
			int change;
			change = (int)((double)a.tempSpD()*1.2);
			a.settempAtt(a.tempSpD() + change);
			a.setAttDrop(time, 3);
			System.out.println(change);
			System.out.println(a.name() + "'s special defense went up!");
		}
		if(effect == speedU)
		{
			a.StartAttCounter(4);
			int change;
			change = (int)((double)a.tempSp()*1.2);
			a.settempAtt(a.tempSp() + change);
			a.setAttDrop(time, 4);
			System.out.println(change);
			System.out.println(a.name() + "'s speed went up!");
		}
		if(effect == attD)
		{
			a.StartAttCounter(0);
			int change;
			change = (int)((double)a.tempAtt()*.2);
			a.settempAtt(a.tempAtt() - change);
			a.setAttDrop(time, 0);
			System.out.println(change);
			System.out.println(a.name() + "'s attack went down!");
		}
		if(effect == defD)
		{
			a.StartAttCounter(1);
			int change;
			change = (int)((double)a.tempDef()*.2);
			a.settempAtt(a.tempDef() - change);
			a.setAttDrop(time, 1);
			System.out.println(change);
			System.out.println(a.name() + "'s defense went down!");
		}
		if(effect == spattD)
		{
			a.StartAttCounter(2);
			int change;
			change = (int)((double)a.tempSpA()*.2);
			a.settempAtt(a.tempSpA() - change);
			a.setAttDrop(time, 2);
			System.out.println(change);
			System.out.println(a.name() + "'s special attack went down!");
		}
		if(effect == spdefD)
		{
			a.StartAttCounter(3);
			int change;
			change = (int)((double)a.tempSpD()*.2);
			a.settempAtt(a.tempSpD() - change);
			a.setAttDrop(time, 3);
			System.out.println(change);
			System.out.println(a.name() + "'s special defense went down!");
		}
		if(effect == speedD)
		{
			a.StartAttCounter(4);
			int change;
			change = (int)((double)a.tempSp()*.2);
			a.settempAtt(a.tempSp() - change);
			a.setAttDrop(time, 4);
			System.out.println(change);
			System.out.println(a.name() + "'s speed went down!");
		}
		/////////////////////////Special///////////////////
		if(effect == heal)
		{
			a.setTempHp(a.hp()/2 + a.tempHp());
			System.out.println(a.name() + " recovered some health.");
		}
		if(effect == recharge)
		{
			BACounter = -10;
		}
		if(effect == allStatus)
		{
			a.setStatus(0);
		}
		if(effect == evadeU)
		{
			a.setStatus(evadeU);
			a.StartStatusCounter();
		}
		if(effect == accuracyU)
		{
			a.setStatus(accuracyU);
			a.StartStatusCounter();
		}
		if(effect == accuracyD)
		{
			a.setStatus(accuracyD);
			a.StartStatusCounter();
		}
		if(effect == hurt)
		{
			a.setStatus(hurt);
			a.StartStatusCounter();
		}
		if(effect == wait)
			a.setStatus(wait);
			
		if(effect == killChance)
		{
			int chance = rand.nextInt(100)+1;
			if(chance <= 25)
			{
				a.setTempHp(0);
				System.out.println("it was a one-hit ko");
			}
		}
			///////////////////////////////////////////////////
		
	}
	public static double elemental(int type, int attack)
	{
		double dam = 1;
		
		if(attack == normal) //normal
		{
			if(type == fighting)
				dam = .5;
			if(type == ghost)
				dam = 0;
			else
				dam = 1;
		}
		if(attack == fire) //fire
		{
			if(type == fire)
				dam = .5;
			if(type == water)
				dam = .5;
			if(type == grass)
				dam = 2;
			if(type == rock)
				dam = .5;
			if(type == ground)
				dam = .5;
			if(type == ice)
				dam = 2;
			if(type == bug)
				dam = 2;
			if(type == dragon)
				dam = .5;
			else
				dam = 1;
		}
		if(attack == water) //water
		{
			if(type == fire)
				dam = 2;
			if(type == water)
				dam = .5;
			if(type == grass)
				dam = .5;
			if(type == rock)
				dam = 2;
			if(type == ground)
				dam = 2;
			if(type == ice)
				dam = .5;
			if(type == dragon)
				dam = .5;
			else
				dam = 1;
		}
		if(attack == grass)//grass - fix
		{
			if(type == fire)
				dam = .5;
			if(type == water)
				dam = 2;
			if(type == grass)
				dam = .5;
			if(type == rock)
				dam = 2;
			if(type == ground)
				dam = 2;
			if(type == flying)
				dam = .5;
			if(type == poison)
				dam = .5;
			if(type == ice)
				dam = .5;
			if(type == dragon)
				dam = .5;
			else
				dam = 1;
		}
		if(attack == electric)// electric
		{
			if(type == water)
				dam = 2;
			if(type == electric)
				dam = .5;
			if(type == grass)
				dam = .5;
			if(type == ground);
				dam = 0;
			if(type == rock)
				dam = 0;
			if(type == flying)
				dam = 2;
			if(type == dragon)
				dam = .5;
			else
				dam = 1;
		}
		if(attack == rock)//rock - fix
		{
			if(type == fire)
				dam = 2;
			if(type == rock)
				dam = .5;
			if(type == flying)
				dam = 2;
			if(type == fighting)
				dam = .5;
			if(type == ice)
				dam = 2;
			else
				dam = 1;
		}
		if(attack == ground)// ground
		{
			if(type == electric)
				dam = 2;
			if(type == rock)
				dam = 2;
			if(type == flying)
				dam = 0;
			if(type == fire)
				dam = 2;
			if(type == poison)
				dam = 2;
			else
				dam = 1;
		}
		if(attack == flying)//flying - fix
		{
			if(type == grass)
				dam = 2;
			if(type == electric)
				dam = .5;
			if(type == rock)
				dam = .5;
			if(type == bug)
				dam = 2;
			else
				dam = 1;
		}
		if(attack == psychic)//psychic
		{
			if(type == psychic)
				dam = .5;
			if(type == bug)
				dam = .5;
			if(type == ghost)
				dam = .5;
			if(type == poison)
				dam = 2;
			if(type == fighting)
				dam = 2;
			else
				dam = 1;
		}
		if(attack == ghost)//ghost
		{
			if(type == psychic)
				dam = 2;
			else
				dam = 1;
		}
		if(attack == bug)//bug
		{
			if(type == psychic)
				dam = 2;
			if(type == grass)
				dam = 2;
			if(type == fire)
				dam = .5;
			if(type == flying)
				dam = .5;
			else
				dam = 1;
		}
		if(attack == poison)//poison
		{
			if(type == grass)
				dam = 2;
			if(type == poison)
				dam = .5;
			else
				dam = 1;
		}
		if(attack == ice)//ice
		{
			if(type == grass)
				dam = 2;
			if(type == water)
				dam = .5;
			if(type == fire)
				dam = .5;
			if(type == ice)
				dam = .5;
			if(type == rock)
				dam = 2;
			if(type == ground)
				dam = 2;
			if(type == flying)
				dam = 2;
			if(type == bug)
				dam = 2;
			if(type == dragon)
				dam = 2;
			else
				dam = 1;
		}
		if(attack == fighting)//fighting
		{
			if(type == psychic)
				dam = .5;
			if(type == ghost)
				dam = 0;
			if(type == rock)
				dam = 2;
			else
				dam = 1;
		}
		if(attack == dragon)//fighting
		{

			if(type == dragon)
				dam = 2;
			else
				dam = 1;
		}
		return dam;
		
	}
	
//////////////////////////////////////////////
//***********The Heart Beat*****************//
/////////////////////////////////////////////
	
	public class hb extends TimerTask
	{	 
		public void run()
		{
			TimeCounter++;
			
			//Move Mobiles//
			Random rand = new Random();
			int dormant = 1; //commented out - TimeCounter%8;//
			if(dormant == 0 && HostileEnvironment == false)
			{
				printWorld();
			}
			
			/////////////////////////////////////////////////////
			//***************Auto Attacks**********************//
			////////////////////////////////////////////////////
			
			int battle = TimeCounter%2;
			if(battle == 0)
			{
			if(HostileEnvironment == true )
			{
				if(hostile.tempSp() >= batPoke.tempSp())
				{	
					if(HACounter == 0)
					{
						int move = rand.nextInt(hostile.totalMoves());
						action Action = hostile.getMove(move);
						try {
							action(hostile, batPoke, Action);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						HACounter = 1;
						
						if(batPoke.tempHp() > 0) 
						{
							try {
								attack(batPoke, hostile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(hostile.tempHp() < 1)
							{
								currentLoc.removeCreature(hostile);
								HostileEnvironment = false;
							}
						}
						else
							HostileEnvironment = false;
					}
					else
					{
						try {
							attack(hostile, batPoke);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(batPoke.tempHp() < 1)
							HostileEnvironment = false;
						try {
							attack(batPoke, hostile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(hostile.tempHp() < 1)
						{
							currentLoc.removeCreature(hostile);
							HostileEnvironment = false;
						}
					}
				}
				else if (hostile.tempSp() < batPoke.tempSp())
				{
					if(HACounter == 0)
					{
						try {
							attack(batPoke, hostile);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(hostile.tempHp() < 1)
						{
							currentLoc.removeCreature(hostile);
							HostileEnvironment = false;
						}
						if(hostile.tempHp() > 0)
						{
							int move = rand.nextInt(hostile.totalMoves());
							action Action = hostile.getMove(move);
							try {
								action(hostile, batPoke, Action);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(batPoke.tempHp() < 0)
								HostileEnvironment = false;
							HACounter = 1;
						}
					}
					else
					{
						try {
							attack(hostile, batPoke);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						if(batPoke.tempHp() < 1)
							HostileEnvironment = false;
						if(batPoke.tempHp() > 0)
							try {
								attack(batPoke, hostile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						if(hostile.tempHp() < 1)
						{
							currentLoc.removeCreature(hostile);
							HostileEnvironment = false;
						}
					}
				}
				System.out.println(batPoke.name() + "-" + "level " + batPoke.level()  
						+ " " + batPoke.tempHp() + "/" + batPoke.hp() + batPoke.PrintStatus());
			}
			}
			
			//////////////////////////////////////////
			//*********Check Weather****************//
			/////////////////////////////////////////
			
			int changeWeather = TimeCounter%120;
			int weatherChance = rand.nextInt(100) + 1;
			if(changeWeather == 0)
			{
				if(weatherChance < 51)
					weather = cloudy;
				if(weatherChance > 50 && weatherChance < 66)
					weather = raining;
				if(weatherChance > 65 && weatherChance < 96)
					weather = hail;
				if(weatherChance > 95 && weatherChance < 101)
					weather = sandstorm;
			}
			
			///////////////////////////////////////////
			//**********Check Status Counter*********//
			//////////////////////////////////////////
			
			if(batPoke.getStatusCounter() > 0)
			{
				if(batPoke.getStatusCounter() == batPoke.statusDrop())
				{
					batPoke.setStatus(noEffect);
					batPoke.setStatusCounter(0);
				}
				else
				{
					batPoke.setStatusCounter(batPoke.getStatusCounter() + 1);
				}
			}
			if(TrainerBattle == true || HostileEnvironment == true)
			{
				if(hostile.getStatusCounter() > 0)
				{
					if(hostile.getStatusCounter() == hostile.statusDrop())
					{
						hostile.setStatus(noEffect);
						hostile.setStatusCounter(0);
					}
					else
					{
						hostile.setStatusCounter(hostile.getStatusCounter() + 1);
					}
				}
			}
			//******************ATTRIBUTES****************//
			for(int i = 0; i < 5; i++)
			{
			if(batPoke.getAttCounter(i) > 0)
			{
				if(batPoke.getAttCounter(i) == batPoke.attDrop(i))
				{
					
				}
				else
				{
					batPoke.setAttCounter(batPoke.getAttCounter(i) + 1);
				}
			}
			if(TrainerBattle == true || HostileEnvironment == true)
			{
				if(hostile.getAttCounter(i) > 0)
				{
					if(hostile.getAttCounter(i) == hostile.attDrop(i))
					{
						/*
						 * FIX THIS TOO!
						 */
					}
					else
					{
						hostile.setAttCounter(hostile.getAttCounter(i) + 1);
					}
				}
			}
			}
			/////////////////////////////////////////////
			//****Check Action and Switch Counters*****//
			////////////////////////////////////////////
			
			int actionCounterDrop = 5;
			int switchCounterDrop = 3;
			
			if(BACounter != 0)
			{
				if(BACounter == actionCounterDrop)
					BACounter = 0;
				else
					BACounter++;
			}
			if(HACounter != 0)
			{
				if(HACounter == actionCounterDrop)
					HACounter = 0;
				else
					HACounter++;
			}
			if(SWCounter > 0)
			{
				if(SWCounter == switchCounterDrop)
					SWCounter = 0;
				else
					SWCounter++;
			}
			if(TrainerCounter.size() > 0)
			{
				for(int i = 0; i < TrainerCounter.size(); i++)
				{
					trainer temp = TrainerCounter.get(i);
					if(temp.counter() == 180)
					{
						temp.setCounter(0);
						TrainerCounter.remove(i);
					}
					else
						temp.setCounter(temp.counter()+1);
						
				}
			}
			
			//////////////////////////////////////////////
			//***********Trainer Battle Handler*********//
			/////////////////////////////////////////////
			if(TrainerBattle == true)
			{
				battle = TimeCounter%2;
				if(battle == 0)
				{
				hostile = hostileT.getPoke(0);
				if(hostile.speed() >= batPoke.speed())
					{	
						if(HACounter == 0)
						{
							int move = rand.nextInt(hostile.totalMoves());
							action Action = hostile.getMove(move);
							try {
								System.out.println(hostileT.name() + " - " + hostile.name() +
										" use " +Action.name() + " on " + batPoke.name() + "!");
								
								action(hostile, batPoke, Action);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							HACounter = 1;
							if(batPoke.tempHp() == 0)
								TrainerBattle = false;
							if(batPoke.tempHp() > 0) 
							{
								try {
									attack(batPoke, hostile);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(hostile.tempHp() < 1)
								{	
									boolean pokeFound = false;
									for(int i = 0; i < hostileT.partySize() && pokeFound == false; i++)
									{
										if(hostileT.getPoke(i).tempHp() != 0)
										{
											pokeFound = true;
											pokemon set = hostileT.getPoke(i);
											hostileT.setP(0, set);
											hostileT.setP(i, hostile);
											System.out.println(hostileT.name() + " took back " + hostile.name() +
													" and sent out " + set.name() + "!");
										}
									}
									if(pokeFound == false)
									{
										hostileT.setCounter(1);
										tempU.setGold(hostileT.gold() + tempU.gold());
										System.out.println(hostileT.name() + " - " + hostileT.lose());
										System.out.println("You got " + "$" + hostileT.gold() + "!");
										TrainerCounter.add(hostileT);
										if(hostileT.isLeader() == true)
										{
											tempU.addBadge(hostileT.badgeNum());
											System.out.println(hostileT.name() + " gave you their badge!");
										}
										TrainerBattle = false;
										
										
									}
									
								}
							}
						}
						else
						{
							try {
								attack(hostile, batPoke);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(batPoke.tempHp() == 0)
								TrainerBattle = false;
							try {
								attack(batPoke, hostile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(hostile.tempHp() < 1)
							{
								boolean pokeFound = false;
								for(int i = 0; i < hostileT.partySize() && pokeFound == false; i++)
								{
									if(hostileT.getPoke(i).tempHp() != 0)
									{
										pokeFound = true;
										pokemon set = hostileT.getPoke(i);
										hostileT.setP(0, set);
										hostileT.setP(i, hostile);
										System.out.println(hostileT.name() + " took back " + hostile.name() +
												" and sent out " + set.name() + "!");
									}
								}
								if(pokeFound == false)
								{
									hostileT.setCounter(1);
									tempU.setGold(hostileT.gold() + tempU.gold());
									System.out.println(hostileT.name() + " - " + hostileT.lose());
									System.out.println("You got " + "$" + hostileT.gold() + "!");
									TrainerCounter.add(hostileT);
									if(hostileT.isLeader() == true)
									{
										tempU.addBadge(hostileT.badgeNum());
										System.out.println(hostileT.name() + " gave you their badge!");
									}
									TrainerBattle = false;
									
								}
							}
						}
					}
					else if (hostile.speed() < batPoke.speed())
					{
						if(HACounter == 0)
						{
							try {
								attack(batPoke, hostile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(hostile.tempHp() < 1)
							{
								boolean pokeFound = false;
								for(int i = 0; i < hostileT.partySize() && pokeFound == false; i++)
								{
									if(hostileT.getPoke(i).tempHp() != 0)
									{
										pokeFound = true;
										pokemon set = hostileT.getPoke(i);
										hostileT.setP(0, set);
										hostileT.setP(i, hostile);
										System.out.println(hostileT.name() + " took back " + hostile.name() +
												" and sent out " + set.name() + "!");
									}
								}
								if(pokeFound == false)
								{
									hostileT.setCounter(1);
									tempU.setGold(hostileT.gold() + tempU.gold());
									System.out.println(hostileT.name() + " - " + hostileT.lose());
									System.out.println("You got " + "$" + hostileT.gold() + "!");
									TrainerCounter.add(hostileT);
									if(hostileT.isLeader() == true)
									{
										tempU.addBadge(hostileT.badgeNum());
										System.out.println(hostileT.name() + " gave you their badge!");
									}
									TrainerBattle = false;
									
								}
							}
							if(hostile.tempHp() > 0)
							{
								int move = rand.nextInt(hostile.totalMoves());
								action Action = hostile.getMove(move);
								try {
									System.out.println(hostileT.name() + " - " + hostile.name() +
											" use " +Action.name() + " on " + batPoke.name() + "!");
									action(hostile, batPoke, Action);
									HACounter = 1;
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								if(batPoke.tempHp() == 0)
									TrainerBattle = false;
								
							}
						}
						else
						{
							try {
								attack(hostile, batPoke);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if(batPoke.tempHp() == 0)
								TrainerBattle = false;
							if(batPoke.tempHp() > 0)
								try {
									attack(batPoke, hostile);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							if(hostile.tempHp() < 1)
							{
								boolean pokeFound = false;
								for(int i = 0; i < hostileT.partySize() && pokeFound == false; i++)
								{
									if(hostileT.getPoke(i).tempHp() != 0)
									{
										pokeFound = true;
										pokemon set = hostileT.getPoke(i);
										hostileT.setP(0, set);
										hostileT.setP(i, hostile);
										System.out.println(hostileT.name() + " took back " + hostile.name() +
												" and sent out " + set.name() + "!");
									}
								}
								if(pokeFound == false)
								{
									hostileT.setCounter(1);
									tempU.setGold(hostileT.gold() + tempU.gold());
									System.out.println(hostileT.name() + " - " + hostileT.lose());
									System.out.println("You got " + "$" + hostileT.gold() + "!");
									TrainerCounter.add(hostileT);
									if(hostileT.isLeader() == true)
									{
										tempU.addBadge(hostileT.badgeNum());
										System.out.println(hostileT.name() + " gave you their badge!");
									}
									TrainerBattle = false;
									
								}
							}
						}
					}
					System.out.println(batPoke.name() + "-" + "level " + batPoke.level()  
							+ " " + batPoke.tempHp() + "/" + batPoke.hp() + batPoke.PrintStatus());
				}
			}
		}
	}
}
