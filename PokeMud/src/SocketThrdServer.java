import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import javax.swing.*;

import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;




@SuppressWarnings("serial")
//////////////////////////////////////////////////////////////////
//************SERVER SIDE***************************************//
/////////////////////////////////////////////////////////////////
class SocketThrdServer extends JFrame 
{

	/**
	 * 
	 */
	JLabel label = new JLabel("Text received over socket:");
	JPanel panel;
	static JTextArea textArea = new JTextArea();
	ServerSocket server = null;
	
	static LinkedList<pokemon> faintedPokemon = new LinkedList<pokemon>();
	static LinkedList<location> world = new LinkedList<location>();
	static int TimeCounter = 0;
	static LinkedList<trainer> TrainerCounter = new LinkedList<trainer>();
	static LinkedList<location> LocationCounter = new LinkedList<location>();
	static int numU = 0;
	static String[] ULI = new String[100];
	
/////////////////////////////////////////////
	///////////Weather/////////////////
	static int weather = 0;
	static int cloudy = 0;
	static int ignite = 1;
	static int raining = 2;
	static int overgrowth = 3;
	static int sandstorm = 6;
	static int hail = 12;
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
	static int hurt = 23; //from shields
	static int wait = 24;
	static int killChance = 25;
	static int possessed = 26;
	static int noDeath = 27;
	
	SocketThrdServer() 
	{ // Begin Constructor
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBackground(Color.white);
		getContentPane().add(panel);
		panel.add("North", label);
		panel.add("Center", textArea);
		Timer time = new Timer();
		time.scheduleAtFixedRate(new hb(), 1000, 1000);
	} 
	// End Constructor

	public void listenSocket() throws InterruptedException 
	{
		try 
		{
			InetAddress IP = InetAddress.getLocalHost();
			server = new ServerSocket(4001, 10, IP);
		} 
		catch (IOException e) 
		{
			System.out.println("Could not listen on port 4001");
			System.exit(-1);
		}
		while (true) 
		{
			
			try 
			{
				ClientHandler c;
				Socket user = server.accept();
				PrintWriter out = new PrintWriter(user.getOutputStream(), true); 
				out.println("Welcome to Pokemud v1");
				out.println("name: ");
				Thread t;
				c = new ClientHandler(user);
				t = new Thread(c);
				t.start();
				if(user.isClosed() == true)
					textArea.setText("user logged out");
				
			} 
			catch (IOException e)
			{
				System.out.println("Accept failed: 4444");
				System.exit(-1);
			}
		}
	}
	protected void finalize()
	{
		// Objects created in run method are finalized when
		// program terminates and thread exits
		try 
		{
			textArea.setText("user logged out");
			server.close();
			
		} 
		catch (IOException e) 
		{
			System.out.println("Could not close socket");
			System.exit(-1);
		}
	}

	public static void main(String[] args) throws Exception
	{
		SocketThrdServer server = new SocketThrdServer();
		server.setTitle("Server Program");
		WindowListener l = new WindowAdapter() 
		{
			public void windowClosing(WindowEvent e) 
			{
				System.exit(0);
			}
		};
		
		server.addWindowListener(l);
		server.pack();
		server.setVisible(true);
		loadWorld();
		server.listenSocket();
		
	}
	
	
	
	/////////////////////////////////////////////////////
	//***********CLIENT HANDLER************************//
	////////////////////////////////////////////////////
	class ClientHandler implements Runnable
	{
		private Socket client;
		public User tempU;
		public location currentLoc;
		public int ClientTimeCounter = 0;
		public PrintWriter out;
		
/////////////////////////////////////////
		/////////Status Tools////////////
		int StatusCounter = 0;
		int StatusDrop = 0;
		
		pokemon batPoke = new pokemon();
		pokemon hostile = new pokemon();
		trainer hostileT = new trainer();
		boolean HostileEnvironment = false;
		boolean TrainerBattle = false;
		boolean stop = false;
		int BACounter = 0;
		int HACounter = 0;
		int SWCounter = 0;
		int charge = 0;
		boolean flashFire, fly, grapple, focusedMind, possess, web, stomachAcid;
		LinkedList<pokemon> PCPoke = new LinkedList<pokemon>();
		LinkedList<Item> shop = new LinkedList<Item>();
		
		
		ClientHandler(Socket client) 
		{
			this.client = client;
			Timer clientTimer = new Timer();
			clientTimer.scheduleAtFixedRate(new hbClient(), 1000,1000);
			
		}

		public void run() 
		{
			
			BufferedReader in = null;
			String Input = null;
			Logger login = new Logger(client);
			try 
			{
				in = new BufferedReader(new InputStreamReader(client.getInputStream()));
				out = new PrintWriter(client.getOutputStream(), true);
			} 
			catch (IOException e) 
			{
				System.out.println("in or out failed");
				System.exit(-1);
			}

			while (true) 
			{
				
					
					//////////////////////////////////
					//********LOG IN****************//
					/////////////////////////////////
					
					
					//TODO nothing =)

							try 
							{
								String input;
								
								while((login.state() != login.loggedIn())&&(input = in.readLine()) != null)
								{
									if(login.state() == 1)
									{
										if(checkULI(input) == true)
										{
											out.println("That user is already logged in");
											login.setState(7);
										}
									}
									if(login.state() == login.loggedIn())
										break;
									out.println(login.process(input));
									if(input.equals("quit"))
										break;	
								}
							
						
					///////////////////////////////////
					//***********PLAY****************//
					//////////////////////////////////
								tempU = login.user();
								if(client.isClosed())
								{
									break;
								}
								ULI[numU] = tempU.name();
								numU++;
								//out.println(ULI[0]);
								loadUser();
								tempU.setSocket(client);
								currentLoc = world.get(tempU.roomNum());
								currentLoc.addPlayer(tempU);
								printWorld();
							while((Input = in.readLine()) != null && (login.state() == login.loggedIn()))
							{

								batPoke = tempU.getPoke(0);
								boolean foundAction = false;
								String sub = "";
								int partyCheck = 0;
								
										
							////////////////////////////////////////////////////////////////	
							//**********Check for actions and substrings*****************///
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
											if(tempLoc.roomNum() == currentLoc.bush || tempLoc.roomNum() == currentLoc.rock 
													|| tempLoc.roomNum() == currentLoc.lockedRoom)
											{
												if(currentLoc.hasBrush == false && currentLoc.hasStone == false 
														&& currentLoc.isLock == false)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else if(currentLoc.isLock == true)
												{
													out.println("The door is locked");
													break;
												}
												else
												{
													out.println("You cannot go that way yet!");
													break;
												}
											}
											else if(tempLoc.isWater == true)
											{
												if(batPoke.checkMoves("surf") == true)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot walk on water!");
													break;
												}
											}
											else
											{
												currentLoc.removePlayer(tempU);
												currentLoc = null;
												currentLoc = (location)world.get(a);
												currentLoc.addPlayer(tempU);
												IDCHECK = true;
												printWorld();
											}
										}
										if(a < world.size() - 1)
										a++;
									}
									}
									else
									out.println("You can't walk away from a battle!");
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
											if(tempLoc.roomNum() == currentLoc.bush || tempLoc.roomNum() == currentLoc.rock 
													|| tempLoc.roomNum() == currentLoc.lockedRoom)
											{
												if(currentLoc.hasBrush == false && currentLoc.hasStone == false 
														&& currentLoc.isLock == false)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot go that way yet!");
													break;
												}
											}
											else if(tempLoc.isWater == true)
											{
												if(batPoke.checkMoves("surf") == true)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot walk on water!");
													break;
												}
											}
											else
											{
												currentLoc.removePlayer(tempU);
												currentLoc = null;
												currentLoc = (location)world.get(a);
												currentLoc.addPlayer(tempU);
												IDCHECK = true;
												printWorld();
											}
										}
										if(a < world.size() - 1)
										a++;
									}
									}
									else
									out.println("You can't walk away from a battle!");
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
											if(tempLoc.roomNum() == currentLoc.bush || tempLoc.roomNum() == currentLoc.rock 
													|| tempLoc.roomNum() == currentLoc.lockedRoom)
											{
												if(currentLoc.hasBrush == false && currentLoc.hasStone == false 
														&& currentLoc.isLock == false)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot go that way yet!");
													break;
												}
											}
											else if(tempLoc.isWater == true)
											{
												if(batPoke.checkMoves("surf") == true)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot walk on water!");
													break;
												}
											}
											else
											{
												currentLoc.removePlayer(tempU);
												currentLoc = null;
												currentLoc = (location)world.get(a);
												currentLoc.addPlayer(tempU);
												IDCHECK = true;
												printWorld();
											}
										}
										if(a < world.size() - 1)
										a++;
									}
									}
									else
									out.println("You can't walk away from a battle!");
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
											if(tempLoc.roomNum() == currentLoc.bush || tempLoc.roomNum() == currentLoc.rock 
													|| tempLoc.roomNum() == currentLoc.lockedRoom)
											{
												if(currentLoc.hasBrush == false && currentLoc.hasStone == false 
														&& currentLoc.isLock == false)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot go that way yet!");
													break;
												}
											}
											else if(tempLoc.isWater == true)
											{
												if(batPoke.checkMoves("surf") == true)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot walk on water!");
													break;
												}
											}
											else
											{
												currentLoc.removePlayer(tempU);
												currentLoc = null;
												currentLoc = (location)world.get(a);
												currentLoc.addPlayer(tempU);
												IDCHECK = true;
												printWorld();
											}
										}
										if(a < world.size() - 1)
										a++;
									}
									}
									else
									out.println("You can't walk away from a battle!");
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
											if(tempLoc.roomNum() == currentLoc.bush || tempLoc.roomNum() == currentLoc.rock 
													|| tempLoc.roomNum() == currentLoc.lockedRoom)
											{
												if(currentLoc.hasBrush == false && currentLoc.hasStone == false 
														&& currentLoc.isLock == false)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot go that way yet!");
													break;
												}
											}
											else if(tempLoc.isWater == true)
											{
												if(batPoke.checkMoves("surf") == true)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot walk on water!");
													break;
												}
											}
											else
											{
												currentLoc.removePlayer(tempU);
												currentLoc = null;
												currentLoc = (location)world.get(a);
												currentLoc.addPlayer(tempU);
												IDCHECK = true;
												printWorld();
											}
										}
										if(a < world.size() - 1)
										a++;
									}
									}
									else
									out.println("You can't walk away from a battle!");
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
											if(tempLoc.roomNum() == currentLoc.bush || tempLoc.roomNum() == currentLoc.rock 
													|| tempLoc.roomNum() == currentLoc.lockedRoom)
											{
												if(currentLoc.hasBrush == false && currentLoc.hasStone == false 
														&& currentLoc.isLock == false)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot go that way yet!");
													break;
												}
											}
											else if(tempLoc.isWater == true)
											{
												if(batPoke.checkMoves("surf") == true)
												{
													currentLoc.removePlayer(tempU);
													currentLoc = null;
													currentLoc = (location)world.get(a);
													currentLoc.addPlayer(tempU);
													IDCHECK = true;
													printWorld();
												}
												else
												{
													out.println("You cannot walk on water!");
													break;
												}
											}
											else
											{
												currentLoc.removePlayer(tempU);
												currentLoc = null;
												currentLoc = (location)world.get(a);
												currentLoc.addPlayer(tempU);
												IDCHECK = true;
												printWorld();
											}
										}
										if(a < world.size() - 1)
										a++;
									}
									}
									else
										out.println("You can't walk away from a battle!");
										
								}
							
/////////////////////////////////////////////////////////////////////////////////////TODO
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
											out.println( temp.name() + " - " + temp.heal());
											for(int i = 0; i < tempU.partySize(); i++)
											{
												tempU.getPoke(i).setTempHp(tempU.getPoke(i).hp());
												tempU.getPoke(i).setStatus(0);
											}
											out.println("Your Pokemon were fully healed!");
										}
										else
										{	
											for(int i = 0; i < currentLoc.mobNum(); i++)
											{
												temp = currentLoc.getMob(i);
												out.println(temp.name() + " - " + temp.heal());
											}
										}
										
											
									}
									else
										out.println("There's no one around!");
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
															out.println(currentLoc.getMob(i).battle());
															TrainerBattle = true;
															hostileT = currentLoc.getMob(i);
															out.println("Go " + currentLoc.getMob(i).getPoke(0).name() + "!");
															}
															else
															{
																if(tempU.countBadges() >= temp.badgeReq())
																{
																	out.println(currentLoc.getMob(i).battle());
																	TrainerBattle = true;
																	hostileT = currentLoc.getMob(i);
																	out.println("Go " + currentLoc.getMob(i).getPoke(0).name() + "!");
																}
																else
																	out.println(currentLoc.getMob(i).name() + " - " + currentLoc.getMob(i).noBattle());
															}
														}
														else
															out.println(temp.name() + " - " + temp.beat());
													}
													else
														out.println(currentLoc.getMob(i).name() + " - " + currentLoc.getMob(i).battle());
												}
											}
										}
										else
										{
											for(int i = 0; i < currentLoc.mobNum(); i++)
											{
												if(currentLoc.getMob(i).pokeNum() > 0)
												{
													out.println(currentLoc.getMob(i).battle());
													TrainerBattle = true;
												}
												else
													out.println(currentLoc.getMob(i).battle());
											}
										}
									}
									else
										out.println("There is no one around!");
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
											out.println(k);
											if(k.contains(" "))
											{
												a = k.indexOf(' ');
												String what = k.substring(0, a);
												String who = k.substring(a+1, k.length());
												boolean itemF = false;
												boolean pokeF = false;
												out.println(what);
												out.println(who);
												for(int i = 0; i < tempU.invSize() && itemF == false; i++)
												{	
													if(tempU.getItem(i).name().startsWith(what))
													{
														itemF = true;
														for(int c = 0; c < tempU.partySize() && pokeF == false; c++)
														{
															if(tempU.getPoke(c).name().startsWith(who))
															{
																pokeF = true;
																itemDealer(tempU.getItem(i), tempU.getPoke(c));
															}
														}
														if(itemF == false)
															out.println("Use " + k + " on what?");
													}
												}
											}	
										}
									}
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
																out.println("Thats not a pokeball!");
														}
													}
												}
											}
										}
									}
									else
										out.println("throw what?");
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
													out.println(temp.name() + " - " + temp.speak());
												}
											}
										}
									}
									
									if(Input.equals("ask"))
										out.println("Who are you asking?");
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
												out.println(currentLoc.getMob(i).name() + " - " + currentLoc.getMob(i).buy());
												for(int a = 0; a < shop.size(); a++)
												{	
													out.println(a+1 + ". " + shop.get(a).name() + " - " + shop.get(a).price());
												}
											}	
										}
									}
									else
										out.println("There is no one around!");
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
															out.println("You bought a " + temp.name() + "!");
														}
														else
															out.println("You don't have the money for that!");
													}
												}
											}
												
										}
									}
									else
										out.println("There is no one around!");
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
														if(temp.itemType == 0 || temp.itemType == 1)
														{
															tempU.setGold(temp.price() + tempU.gold());
															tempU.removeItem(b);
														}
														else
															out.println("You cannot sell that");
													}
												}
											}
												
										}
									}
									else
										out.println("There is no one around!");
								}
////////////////////////////////////////////////////////////////////////////
								if(Input.equals("run"))
								{
									if(TrainerBattle != true)
									{
										if(HostileEnvironment == true)
										{
											HostileEnvironment = false;
											out.println("You runaway!!!");
										}
										else
											out.println("There's no need to run!");
									}
									else
										out.println("You can't run from a trainer!");
								}

///////////////////////////////////////////////////////////////////////////////////////////////
								if(Input.equals("take") || sub.equals("take"))
								{
										if(Input.endsWith(" ") != true)
										{
											int a = Input.indexOf(' ') + 1;
											String k = Input.substring(a, Input.length());
											String what = k;
											boolean objFound = false;
											for(int b = 0; b < currentLoc.obj.size() && objFound == false; b++)
											{
												if(currentLoc.obj.get(b).name().startsWith(what))
												{
													objFound = true;
													Item i = currentLoc.obj.get(b);
													tempU.addItem(i);
													currentLoc.obj.remove(b);
													out.println("You picked up the " + i.name());
												}
											}
										}
										else
											out.println("Pick up what?");
											
								}
///////////////////////////////////////////////////////////////////////////////////////////////
								if(Input.equals("give") || sub.equals("give"))
								{
									if(sub.equals("give"))
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
													
													boolean tfound = false;
													boolean itemfound = false;
													
													for(int i = 0; i < tempU.invSize() && itemfound == false; i++)
													{
														if(tempU.getItem(i).name().startsWith(what))
														{
															
															if(tempU.getItem(i).itemType != 3 || tempU.getItem(i).itemType != 4)
															{
																
																itemfound = true;
																for(int b = 0; b < currentLoc.plr.size() && tfound == false; b++)
																{
																	if(currentLoc.plr.get(b).name().startsWith(who))
																	{
																		tfound = true;
																		User r = currentLoc.plr.get(b);
																		Item o = tempU.getItem(i);
																		r.addItem(o);
																		tempU.removeItem(i);
																		out.println("You gave " + r.name() + " your " + o.name());
																		PrintWriter rout = new PrintWriter(r.client.getOutputStream(), true);
																		rout.println(tempU.name() + " gave you their " + o.name());
														
																	}
																}
															}
															else
																out.println("Thats not a pokeball!");
														}
													}
												}
											}
										}
									}
								}
///////////////////////////////////////////////////////////////////////////////////////////////								
								if(Input.equalsIgnoreCase("stop"))
								{
									stop = true;
									out.println(batPoke.name() + " stopped wanting to attack.");
								}
///////////////////////////////////////////////////////////////////////////////////////////////								
								if(Input.equalsIgnoreCase("drop") || sub.equalsIgnoreCase("drop"))
								{
									if(Input.endsWith(" ") != true)
									{
										int a = Input.indexOf(' ') + 1;
										String k = Input.substring(a, Input.length());
										String what = k;
										boolean objFound = false;
										for(int b = 0; b < tempU.invSize() && objFound == false; b++)
										{
											if(tempU.getItem(b).name().startsWith(what))
											{
												objFound = true;
												Item i = tempU.getItem(b);
												tempU.removeItem(i);
												currentLoc.obj.add(i);
												out.println("You dropped the " + i.name());
												toRoom(tempU, tempU.name() + " dropped a " + i.name());
											}
										}
									}
									else
										out.println("Pick up what?");
								}
//////////////////////////////////////////////////////////////////////////////
								if(Input.startsWith("inv"))
								{
									tempU.inventory(client);
								}
//////////////////////////////////////////////////////////////////////////////								
								if(Input.equals("unlock"))
								{
									if(currentLoc.isLocked() == true)
									{
										boolean itemFound = false;
										for(int i = 0; i < tempU.invSize() && itemFound == false; i++)
										{
											Item temp = tempU.getItem(i);
											if(temp.keyID == currentLoc.keyID)
											{
												currentLoc.setLock(false);
												LocationCounter.add(currentLoc);
												out.println("The door was unlocked");
												itemFound = true;
											}
										}
									}
									else
										out.println("there's nothing locked");
								}
//////////////////////////////////////////////////////////////////////////////////////
//************************PC Commands***********************************************//
/////////////////////////////////////////////////////////////////////////////////////
								if(Input.equals("pc"))
								{
									if(currentLoc.isPC())
									{
										out.println("What would you like to do? \rDeposit \rWithdraw \rRelase");
										out.println("These are Pokemon stored in your PC:");
										if(tempU.PCSize() < 1)
											out.println("empty...");
										else
											for(int i = 0; i < tempU.PCSize(); i++)
											{
												out.println(tempU.getPC(i).name() + tempU.getPC(i).level());
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
														out.println("You deposited " + p.name() + " in your pc.");
													}
												}
											}
											else
												out.println("You need at least one pokemon in your party!");
										}
										else
											out.println("You need a PC to do that!");
									}
									else
										out.println("Deposit who?");
								}
/////////////////////////////////////////////////////////////////////////
							
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
														out.println("You withdrew " + p.name() + ".");
													}
												}
											}
											else
											out.println("You need at least one open space in your party!");
										}
										else
											out.println("You need a PC to do that!");
									}
									else
										out.println("Withdraw who?");
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
													out.print("Are you sure you want to delete " + tempU.getPC(i).name() + "?");
													out.println(" - y/n");
													Input = in.readLine();
													if(Input.startsWith("y"))
													{
														out.println("Alright " + tempU.getPC(i) + " was be released");
														tempU.removePC(i);
													}
													if(Input.startsWith("n"))
														out.println("Alright " + tempU.getPC(i).name() + " won't be released");
													else
														out.println("try again...");
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
												batPoke.isFlash = false;
												clearPrereqs();
												tempU.setP(0, b);
												tempU.setP(i, c);
												out.println("You switched out " + c.name() + " for " + b.name() + ".");
											}
											else
												out.println("You can't switch with a fainted pokemon!");
											
										}
									}
								}
							//////////////////////////////////////
							//*******CUT************************//
							/////////////////////////////////////
								if(Input.equalsIgnoreCase("cut"))
								{
									if(currentLoc.hasBrush == true)
									{
										currentLoc.setBrush(false);
										LocationCounter.add(world.get(world.indexOf(currentLoc)));
										out.println("The brush was hacked away!");
										toRoom(tempU, tempU.name() + " cut a bush away!");
									}
									else
									{
										out.println("There's nothing to cut");
									}
								}
								//////////////////////////////////////
								//*******FLY************************//
								/////////////////////////////////////
								if(sub.equalsIgnoreCase("fly"))
								{
									if(batPoke.checkMoves("fly") == true)
									{
										
											int a = Input.indexOf(' ') + 1;
											String where = Input.substring(a, Input.length());
											if(where.startsWith("op") == true)
											{
												currentLoc = null;
												currentLoc = (location)world.get(17);
												out.println("You hop on " + batPoke.name() + "'s back and go to Opal City");
												toRoom(tempU, tempU.name() + "flew away!");
												printWorld();
											}
											if(where.startsWith("pal") == true)
											{
												currentLoc = null;
												currentLoc = (location)world.get(1);
												out.println("You hop on " + batPoke.name() + "'s back and go to Pal Town");
												toRoom(tempU, tempU.name() + "flew away!");
												printWorld();
											}
									}
									else
										out.println("You cannot fly!");
								}
								//////////////////////////////////////
								//*******STRENGTH*******************//
								/////////////////////////////////////
								if(Input.equalsIgnoreCase("strength"))
								{
									if(batPoke.checkMoves("strength"))
									{
										if(currentLoc.hasStone == true)
										{
											currentLoc.setStone(false);
											LocationCounter.add(world.get(world.indexOf(currentLoc)));
											out.println("The stone was hurled into the distance!");
											toRoom(tempU, tempU.name() + "threw away a boulder");
										}
										else
										{
											out.println("There's nothing to cut");
										}
									}
								}
								//////////////////////////////////////
								//*******FLASH*******************//
								/////////////////////////////////////
								if(Input.equalsIgnoreCase("flash"))
								{
									if(batPoke.checkMoves("flash"))
									{
										batPoke.isFlash = true;
										if(currentLoc.ISDARK == true)
										{
											currentLoc.ISDARK = false;
											printWorld();
										}
									}
								}
	//////////////////////////////////////////////////////////////							
    //**********************Action!*****************************//	
	/////////////////////////////////////////////////////////////							
								if(foundAction == true)
								{
									if(BACounter == 0)
									{
									if(batPoke.tempHp() > 0)
									{
										
									int a = 0;
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
												out.println("you can't do that!");
										}
										else if(tempA.special() == 4)
											weather = tempA.weather();
										else
											action(batPoke, tempA);
									}
									if (tempA.target() == true)
									{
										if(tempA.special == 3 || tempA.special == 0)
										{
											if(tempA.weatherReq() == weather || tempA.weatherReq == 0)
											{
												findTarget(currentLoc, Input);
											}
										}
									}
									else
										out.println("Your Pokemon cannot do that!");
									}
									
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
													stop = false;
													hostile = targetP;
													if(targetP.tempHp() < 1)
													{
														currentLoc.removeCreature(targetP);
														faintedPokemon.add(targetP);
														HostileEnvironment = false;
														if(TrainerBattle != true)
														{
															for(int b = 0; b < tempU.partySize(); b++)
																clearStats(tempU.getPoke(b));
														}
														HACounter = 0;
														checkLevel(tempU.getPoke(0));
													}
													else
														attack(targetP, batPoke);
													out.println();
												}
											
											}
										}
									}
									else if(Input.contains(" ") != true)
									{
										out.println("attack who?");
									}
								}
								else
									out.println("Your Pokemon cannot do that!");
								}		
								

/////////////////////////////////////////////////////////////////////////////////////
//******************************info commands**************************************//
////////////////////////////////////////////////////////////////////////////////////
								/*TODO
								*Info commands
								*/
								
								
								if(Input.startsWith("mov") == true)
								{
									batPoke.moves(client);
								}
								if(Input.equalsIgnoreCase("h") || Input.equalsIgnoreCase("help") == true)
								{
									out.println("Here is a list of commands so far: ");
									out.println("Pokemon - attack, hit, any action, go, stop");
									out.println("User/Trainer - use, take, buy, sell, run, battle, heal, drop");
									out.println("Info - help, movelist, stats, self, poke");
									out.println("PC - pc, withdraw, deposit, release");
								}
								if(Input.equalsIgnoreCase("stat") || Input.equalsIgnoreCase("stats"))
								{
									out.println(batPoke.DEX() + ". " + batPoke.name() + "(" + batPoke.level() + ") " + batPoke.tempHp() + "/" + batPoke.hp()
											+ "hp");
									out.println("att: " + batPoke.att() + "     speed: " + batPoke.speed() + "     exp: " + batPoke.exp() );
									out.println("def: " + batPoke.def() + "     type1: " + "      next: " + batPoke.nextLevel());
									out.println("sp.att: " + batPoke.spatt() + "   type2: " + "    O/T: " + batPoke.UserNum());
									out.println("sp.def: " + batPoke.spdef() + "   ID: " + batPoke.ID());
								}
								if(Input.equalsIgnoreCase("self"))
								{
									out.println(tempU.name());
									out.println(tempU.ID());
									out.println("Gold - " + tempU.gold());
									out.println("badges:");
									for(int i = 0; i < 6; i++)
									{
										if(tempU.badge(i) == false)
											out.print(i+1 + ".? ");
										else
										{
											out.print(i+1 + ".obtained ");	
										}
											
									}
									out.println();
									for(int i = 6; i < tempU.badgeSize(); i++)
									{
										if(tempU.badge(i) == false)
											out.print(i+1 + ".? ");
										else
										{
											out.print(i+1 + ".obtained ");	
										}
											
									}
									out.println();
								}
								if(Input.equals("poke"))
								{
									out.println("Your party:");
									for(int i = 0; i < tempU.partySize(); i++)
									{
										pokemon temp = tempU.getPoke(i);
										out.println(temp.name() + "(" + temp.level() + ") - " + temp.tempHp() + 
												"/" + temp.hp());
									}
								}

/////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////
								
								
								try {
									save();
								} catch (Exception e) {
									//  Auto-generated catch block
									e.printStackTrace();
								}
								
								
								if(Input.equals("quit") == true)
								{
									boolean found = false;
									for(int i = 0; i < ULI.length && found == false; i++)
									{
										if(ULI[i]!= null)
										{
											if(ULI[i].equals(tempU.name()))
												ULI[i] = null;
										}
									}
									
									currentLoc.removePlayer(tempU);
									client.shutdownInput();
									client.close();	
									break;
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
									out.println("You ran out of Pokemon! To Professor Jacey!");
									currentLoc = world.get(0);
								}
							}
						}
						
						catch (IOException e) 
						{
							// Auto-generated catch block
							e.printStackTrace();
						} 
						catch (Exception e) 
						{
							//  Auto-generated catch block
							e.printStackTrace();
						} catch (Throwable e) {
							// Auto-generated catch block
							e.printStackTrace();
						}
					} 

				}		
			

								
					
			/////////////////////////////////////////////////////////////////////////////////////
				
				

		////////////////////////////////////////////////
		//************Print World and Poke************//
		///////////////////////////////////////////////
		public void printWorld() throws IOException
		{
			if(currentLoc.ISDARK == false)
			{
			out.println(currentLoc.getTitle() + "\r	" + currentLoc.getDes());
			if(currentLoc.weather() == true)
				PrintWeather();
			out.println();
			currentLoc.getList(tempU);
			out.println();
			currentLoc.printExits(tempU);
			out.println("");
			out.println(tempU.getPoke(0).name() + "-" 
					+ "level " + tempU.getPoke(0).level()  + " " + tempU.getPoke(0).tempHp() 
					+ "/" + tempU.getPoke(0).hp() + tempU.getPoke(0).PrintStatus());
			}
			else
			{
				out.println("The area is completely dark!");
				currentLoc.printExits(tempU);
				out.println("");
				out.println(tempU.getPoke(0).name() + "-" 
						+ "level " + tempU.getPoke(0).level()  + " " + tempU.getPoke(0).tempHp() 
						+ "/" + tempU.getPoke(0).hp() + tempU.getPoke(0).PrintStatus());
				out.println("");
			}
		}
		
		public void findTarget(location Loc, String Inp) throws IOException
		{
			
			int a = 0;
			boolean pokeFound = false;
			pokemon targetP = new pokemon();
			a = Inp.indexOf(' ');
			String action = Inp.substring(0, a);
			action tempA = batPoke.getMove(action);
			
			if(Inp.contains(" ") == true) //check if there is more than the action
			{
			if(Inp.endsWith(" ") != true) //check if the last thing is the space//
			{
				a = Inp.indexOf(' ') + 1;
				String tar = Inp.substring(a, Inp.length());
				for(int i = 0; i < Loc.crtNum() && pokeFound == false; i++)
				{
					if(Loc.getCreatureName(i).startsWith(tar))
					{
						pokeFound = true;
						targetP = Loc.getCreature(i);
					}
				}
				if(TrainerBattle == true)
				{
					if(Loc.mobNum() > 0)
					{
						boolean mobFound = false;
					for(int i = 0; i < Loc.mobNum() && mobFound == false; i++)
					{
						if(Loc.getMob(i).pokeNum() > 0)
						{
							trainer temp = Loc.getMob(i);
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
				stop = false;
				hostile = targetP;
				action(batPoke, targetP, tempA);
				BACounter = 1;
			
				if(targetP.tempHp() < 1)
				{
					Loc.removeCreature(targetP);
					faintedPokemon.add(targetP);
					HostileEnvironment = false;
					if(TrainerBattle != true)
					{
						for(int b = 0; b < tempU.partySize(); b++)
							clearStats(tempU.getPoke(b));
					}
					HACounter = 0;
					checkLevel(tempU.getPoke(0));
				
				}
				else
					attack(targetP, batPoke);
			
				out.println();
			}
	
			}
			else if(Loc.crtNum() < 1)
			{
				out.println("There's no one around");
			}
		}
		}
		public void PrintWeather()
		{
			if(currentLoc.weather() == true)
			{
				if(weather == cloudy)
					out.println("There are a few clouds");
				if(weather == ignite)
					out.println("The entire area is on fire!");
				if(weather == raining)
					out.println("Rain is pouring down");
				if(weather == hail)
					out.println("Hail is falling!");
				if(weather == sandstorm)
					out.println("A sandstorm is whipping around!");
				if(weather == overgrowth)
					out.println("There are vines and shrubs grown over the area!");
			}
		}
		public void toRoom(User user, String n) throws IOException//user possessive form
		{
			for(int i = 0; i < currentLoc.plr.size(); i++)
	    	  {
	    		  if(currentLoc.plr.get(i) != user)
	    		  {
	    			  PrintWriter out = new PrintWriter(currentLoc.plr.get(i).client.getOutputStream(), true);
	    			  out.println(n);
	    		  }  
	    	  }
		}
		public void toRoom(String n) throws IOException//to everyone, not user possessive
		{
			for(int i = 0; i < currentLoc.plr.size(); i++)
	    	  {
	    			  PrintWriter out = new PrintWriter(currentLoc.plr.get(i).client.getOutputStream(), true);
	    			  out.println(n);
	    	  }
		}
		///////////////////////////////////////////////
		//****************CLIENT TIME****************//
		//////////////////////////////////////////////
		public class 
hbClient extends TimerTask
		{	
			public void run()
			{
				ClientTimeCounter++;
				Random rand = new Random();
				int battle = ClientTimeCounter%2;
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
								if(Action.target == true)
									action(hostile, batPoke, Action);
								else
									action(hostile, Action);
							}
							catch (IOException e)
							{
								// Auto-generated catch block
								e.printStackTrace();
							}
							HACounter = 1;
							
							if(batPoke.tempHp() > 0) 
							{
								try 
								{
									if(stop == false)
										attack(batPoke, hostile);
								} 
								catch (IOException e) 
								{
									// Auto-generated catch block
									e.printStackTrace();
								}
								if(hostile.tempHp() < 1)
								{
									currentLoc.removeCreature(hostile);
									HostileEnvironment = false;
									for(int b = 0; b < tempU.partySize(); b++)
										clearStats(tempU.getPoke(b));
								}
							}
							else
								HostileEnvironment = false;
						}
						else
						{
							try 
							{
								attack(hostile, batPoke);
							}
							catch (IOException e)
							{
								//  Auto-generated catch block
								e.printStackTrace();
							}
							if(batPoke.tempHp() < 1)
								HostileEnvironment = false;
							try {
								if(stop == false)
									attack(batPoke, hostile);
							} 
							catch (IOException e) 
							{
								//  Auto-generated catch block
								e.printStackTrace();
							}
							if(hostile.tempHp() < 1)
							{
								currentLoc.removeCreature(hostile);
								HostileEnvironment = false;
								for(int b = 0; b < tempU.partySize(); b++)
									clearStats(tempU.getPoke(b));
							}
						}
					}
					else if (hostile.tempSp() < batPoke.tempSp())
					{
						if(HACounter == 0)
						{
							try 
							{
								if(stop == false)
								attack(batPoke, hostile);
							} 
							catch (IOException e) 
							{
								//  Auto-generated catch block
								e.printStackTrace();
							}
							if(hostile.tempHp() < 1)
							{
								currentLoc.removeCreature(hostile);
								HostileEnvironment = false;
								for(int b = 0; b < tempU.partySize(); b++)
									clearStats(tempU.getPoke(b));
							}
							if(hostile.tempHp() > 0)
							{
								int move = rand.nextInt(hostile.totalMoves());
								action Action = hostile.getMove(move);
								try 
								{
									if(Action.target == true)
										action(hostile, batPoke, Action);
									else
										action(hostile, Action);
								} 
								catch (IOException e)
								{
									//  Auto-generated catch block
									e.printStackTrace();
								}
								if(batPoke.tempHp() < 0)
									HostileEnvironment = false;
								
								HACounter = 1;
							}
						}
						else
						{
							try 
							{
								attack(hostile, batPoke);
							} 
							catch (IOException e) 
							{
								// Auto-generated catch block
								e.printStackTrace();
							}
							if(batPoke.tempHp() < 1)
								HostileEnvironment = false;
							if(batPoke.tempHp() > 0)
								try 
								{
									if(stop == false)
										attack(batPoke, hostile);
								} 
								catch (IOException e) 
								{
									//  Auto-generated catch block
									e.printStackTrace();
								}
							if(hostile.tempHp() < 1)
							{
								currentLoc.removeCreature(hostile);
								HostileEnvironment = false;
								for(int b = 0; b < tempU.partySize(); b++)
									clearStats(tempU.getPoke(b));
							}
						}
					}
					out.println(batPoke.name() + "-" + "level " + batPoke.level()  
							+ " " + batPoke.tempHp() + "/" + batPoke.hp() + batPoke.PrintStatus());
				}
				
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
						if(batPoke.status() == 1)
						{
							batPoke.setTempHp((batPoke.tempHp() - (int)(batPoke.hp() * .05)));
							out.println("Your " + batPoke.name() + " was hurt by poison");
						}
						if(batPoke.status() == 2)
						{
							batPoke.setTempHp((batPoke.tempHp() - (int)(batPoke.hp() * .05)));
							out.println("Your " + batPoke.name() + " was hurt by its burns");
						}
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
							if(hostile.status() == 1)
							{
								hostile.setTempHp((hostile.tempHp() - (int)(hostile.hp() * .05)));
								out.println(hostile.name() + " was hurt by poison");
							}
							if(hostile.status() == 2)
							{
								hostile.setTempHp((hostile.tempHp() - (int)(hostile.hp() * .05)));
								out.println(hostile.name() + " was hurt by its burns");
							}
						}
					}
				}
				//******************ATTRIBUTES****************//
					
				
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
								try 
								{
									if(Action.target == true)
									{
										toRoom(hostileT.name() + " - " + hostile.name() +
												" use " +Action.name() + " on " + batPoke.name() + "!");
										action(hostile, batPoke, Action);
										
									}
									else
									{
										toRoom(hostileT.name() + " - " + hostile.name() +
												" use " +Action.name() + "!");
										action(hostile, Action);
	
									}
								} 
								catch (IOException e)
								{
									//  Auto-generated catch block
									e.printStackTrace();
								}
								HACounter = 1;
								if(batPoke.tempHp() == 0)
									TrainerBattle = false;
								if(batPoke.tempHp() > 0) 
								{
									try 
									{
										attack(batPoke, hostile);
									} 
									catch (IOException e) 
									{
										//  Auto-generated catch block
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
												out.println(hostileT.name() + " took back " + hostile.name() +
														" and sent out " + set.name() + "!");
											}
										}
										if(pokeFound == false)
										{
											hostileT.setCounter(1);
											tempU.setGold(hostileT.gold() + tempU.gold());
											out.println(hostileT.name() + " - " + hostileT.lose());
											out.println("You got " + "$" + hostileT.gold() + "!");
											TrainerCounter.add(hostileT);
											if(hostileT.isLeader() == true)
											{
												tempU.addBadge(hostileT.badgeNum());
												out.println(hostileT.name() + " gave you their badge!");
											}
											TrainerBattle = false;
											
											
										}
										
									}
								}
							}
							else
							{
								try 
								{
									attack(hostile, batPoke);
								} 
								catch (IOException e) 
								{
									//  Auto-generated catch block
									e.printStackTrace();
								}
								if(batPoke.tempHp() == 0)
									TrainerBattle = false;
								try 
								{
									attack(batPoke, hostile);
								} 
								catch (IOException e) 
								{
									//  Auto-generated catch block
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
											out.println(hostileT.name() + " took back " + hostile.name() +
													" and sent out " + set.name() + "!");
										}
									}
									if(pokeFound == false)
									{
										hostileT.setCounter(1);
										tempU.setGold(hostileT.gold() + tempU.gold());
										out.println(hostileT.name() + " - " + hostileT.lose());
										out.println("You got " + "$" + hostileT.gold() + "!");
										TrainerCounter.add(hostileT);
										if(hostileT.isLeader() == true)
										{
											tempU.addBadge(hostileT.badgeNum());
											out.println(hostileT.name() + " gave you their badge!");
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
								try
								{
									attack(batPoke, hostile);
								} 
								catch (IOException e) 
								{
									//  Auto-generated catch block
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
											out.println(hostileT.name() + " took back " + hostile.name() +
													" and sent out " + set.name() + "!");
										}
									}
									if(pokeFound == false)
									{
										hostileT.setCounter(1);
										tempU.setGold(hostileT.gold() + tempU.gold());
										out.println(hostileT.name() + " - " + hostileT.lose());
										out.println("You got " + "$" + hostileT.gold() + "!");
										TrainerCounter.add(hostileT);
										if(hostileT.isLeader() == true)
										{
											tempU.addBadge(hostileT.badgeNum());
											out.println(hostileT.name() + " gave you their badge!");
										}
										TrainerBattle = false;
										
									}
								}
								if(hostile.tempHp() > 0)
								{
									int move = rand.nextInt(hostile.totalMoves());
									action Action = hostile.getMove(move);
									try
									{
										if(Action.target == true)
										{
											toRoom(hostileT.name() + " - " + hostile.name() +
													" use " +Action.name() + " on " + batPoke.name() + "!");
											action(hostile, batPoke, Action);
											
										}
										else
										{
											toRoom(hostileT.name() + " - " + hostile.name() +
													" use " +Action.name() + "!");
											action(hostile, Action);
											
										}
									} 
									
									catch (IOException e) 
									{
										//  Auto-generated catch block
										e.printStackTrace();
									}
									HACounter = 1;
									if(batPoke.tempHp() == 0)
										TrainerBattle = false;
									
								}
							}
							else
							{
								try 
								{
									attack(hostile, batPoke);
								} 
								catch (IOException e) 
								{
									//  Auto-generated catch block
									e.printStackTrace();
								}
								if(batPoke.tempHp() == 0)
									TrainerBattle = false;
								if(batPoke.tempHp() > 0)
									try 
									{
										attack(batPoke, hostile);
									}
									catch (IOException e)
									{
										//  Auto-generated catch block
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
											out.println(hostileT.name() + " took back " + hostile.name() +
													" and sent out " + set.name() + "!");
										}
									}
									if(pokeFound == false)
									{
										hostileT.setCounter(1);
										tempU.setGold(hostileT.gold() + tempU.gold());
										out.println(hostileT.name() + " - " + hostileT.lose());
										out.println("You got " + "$" + hostileT.gold() + "!");
										TrainerCounter.add(hostileT);
										if(hostileT.isLeader() == true)
										{
											tempU.addBadge(hostileT.badgeNum());
											out.println(hostileT.name() + " gave you their badge!");
										}
										TrainerBattle = false;
										
									}
								}
							}
						}
						out.println(batPoke.name() + "-" + "level " + batPoke.level()  
								+ " " + batPoke.tempHp() + "/" + batPoke.hp() + batPoke.PrintStatus());
					}
				}
			}
		}
		////////////////////////////////////////
		//************ SAVE ******************//
		///////////////////////////////////////
		public void save() throws Exception
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
		
		////////////////////////////////////////
		//**** load user and pokemon *********//
		///////////////////////////////////////
		public void loadUser() throws Exception
		{
			
			FileReader getPoke = new FileReader(tempU.ID() + ".txt");
			BufferedReader gp = new BufferedReader(getPoke);
			
			int i = 0;
			out.println("loading user info...");
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
			
			/////////////////////////////
			//*******LOAD SHOP*********//
			////////////////////////////
			FileReader shopLoader2 = new FileReader("Obj.txt");
			BufferedReader sl2 = new BufferedReader(shopLoader2);
			
			i = 0;
			int a = Integer.valueOf(sl2.readLine());
			out.println("loading shops...");
			
			while(i < a)
			{
				i++;
				Item item = new Item(i);
				if(item.badgrq() <= tempU.countBadges())
					shop.add(item);
			}
			shopLoader2.close();
			getPoke.close();
			tempU.loadInfo();
			
		}
		
		////////////////////////////////////////////////
		//*********Item Dealer************************//
		///////////////////////////////////////////////
		public void itemDealer(Item item, pokemon p) throws IOException //TODO 
		{
			if(item.isUse() == true || item.itemType == 3)
			{
				if(item.itemType == 3)
				{
					p.addMove(item.moveID);
					out.println(p.name() + " learned " + item.moveName);
				}
				////////////////////////////
				if(item.revive() == true)
				{
					if(p.tempHp() < 1)
					{
						p.setTempHp(p.hp()/2);
						out.println("You revived " + p.name()+ "!");
						toRoom(tempU, tempU.name() + " used an item on " +p.name());
						tempU.removeItem(item);
					}
					else
						out.println("Your pokemon isn't fainted!");
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
								out.println("You healed " + p.name() + " for " + item.heal() + " hp.");
								toRoom(tempU, tempU.name() + " used an item on " +p.name());
								tempU.removeItem(item);
							}
							else
							{
								p.setTempHp(p.hp());
								out.println("You healed " + p.name() + " completely!");
								tempU.removeItem(item);
							}
						}
						else
							out.println(p.name() + " is already at full health!");
					}
					else
						out.println("You can't use that on a fainted pokemon!");
				}
				//////////////////////////////////
				if(item.allHealth() == true)
				{
					if(p.tempHp() > 0)
					{
						if(p.tempHp() != p.hp())
						{
							p.setTempHp(p.hp());
							out.println("You healed " + p.name() + " completely!");
							toRoom(tempU, tempU.name() + " used an item on " +p.name());
							tempU.removeItem(item);
						}
						else
							out.println("Your pokemon is already at full health");
					}
					else
						out.println("You can't use that on a fainted pokemon!");
				}
				//////////////////////////////////
				if(item.allStatus() == true)
				{
					if(p.status() > 0)
					{
						p.setStatus(0);
						out.println("Your pokemon has fully recovered!");
						toRoom(tempU, tempU.name() + " used an item on " +p.name());
						tempU.removeItem(item);
					}
					else
						out.println("Your pokemon is already in perfect health!");
				}
				///////////////////////////////
				if(item.status() > 0)
				{
					if(p.status() > 0)
					{
						if(item.status == p.status())
						{
							p.setStatus(0);
							out.println("Your pokemon has fully recovered!");
							toRoom(tempU, tempU.name() + " used an item on " +p.name());
							tempU.removeItem(item);
						}
						else
							out.println("Your pokemon is not affected by that illness");
					}
					else
						out.println("Your pokemon is already in perfect health!");
				}
				
			}
			else
				out.println("You can't use that on " + p.name());
		}
		
		////////////////////////////////////////////////
		//*********Catch Pokemon**********************//
		///////////////////////////////////////////////
		public void catchP(Item item, pokemon tar) throws IOException
		{
			double health = ((double)tar.tempHp())/((double)tar.hp());
			double levelRate = 1, healthRate = 0, statusRate = 1;
			
			if(health > 0 && health < .26)
				healthRate = 2;
			if(health > .25 && health < .51)
				healthRate = 1.5;
			if(health > .5 && health < .76)
				healthRate = 1;
			if(health > .75 && health < 1.01)
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
			out.println(health);
			out.println(healthRate);
			out.println(item.catchrate());
			out.println(statusRate);
			out.println(levelRate);
			if(item.catchrate()*healthRate*statusRate > levelRate) 
			{
				int level = tar.level();
				int dex = tar.DEX();
				HostileEnvironment = false;
				if(tempU.partySize() < 6)
				{
					pokemon temp = tar;
					temp = new pokemon(level, dex, tempU.ID());
					tempU.addLastP(temp);
					currentLoc.removeCreature(tar);
					out.println("You caught " + temp.name() + "!");
					toRoom(tempU, tempU.name() + " caught " + temp.name() + "!");
					
					
				}
				if(tempU.partySize() > 5)
				{
					pokemon temp = tar;
					temp = new pokemon(level, dex, tempU.ID());
					tempU.addLastPC(temp);
					currentLoc.removeCreature(tar);
					out.println("You caught " + temp.name() + "!");
					toRoom(tempU, tempU.name() + " caught " + temp.name() + "!");
					out.println("But there wasn't enough room so they were sent to your PC");
					
				}
				
				
			}
			else
				out.println("Awwww you didn't catch it!");
		
		}
		
		public void checkLevel(pokemon p) throws IOException
		{
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			if(p.exp() > p.nextLevel())
			{
				p.levelUp(client);
				boolean check = false;
				if(p.level() >= p.evolve())
				{
					out.println(p.name() + " is evolving. Should " + p.name() + " evolve? y/n");
					String evolveCheck = in.readLine();
					while(check == false)
					{
						if(evolveCheck.startsWith("y"))
						{
							pokemon temp = new pokemon(p);
							tempU.removePoke(p);
							tempU.addPoke(temp);
							out.println("Your " + p.name() + " evolved into a " + temp.name() + "!!");
							toRoom(tempU, tempU.name() + "'s " + temp.name() + " evolved!");
							check = true;
						}
						else if(evolveCheck.startsWith("n"))
						{
							out.println(p.name() + " stopped trying to evolve.");
							check = true;
						}
					}
					
				}
			}
		}
		public void clearStats(pokemon a)
		{
			a.settempAtt(a.att);
			a.settempDef(a.def);
			a.settempSpA(a.spatt);
			a.settempSpD(a.spdef);
			a.settempSp(a.speed);
		}
		public void clearPrereqs()
		{
			flashFire = false;
			fly = false;
			grapple = false;
			focusedMind = false;
			possess = false;
			web = false;
			stomachAcid = false;
		}
		//////////////////////////////////////////////////
		//******************Regular Attack**************//
		/////////////////////////////////////////////////
		public void attack(pokemon a, pokemon tar) throws IOException
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
				toRoom(a.name() + " hit " + tar.name() + " for " + damage + " hp!");
				}
				else
				{
					tar.setTempHp(0);
					toRoom(a.name() + " hit " + tar.name() + " for " + damage + " hp!");
					toRoom(tar.name() + " feinted!");
					a.newExp(a.exp + exp);
					for(int i = 0; i < 6; i++)
						a.addEV(tar.EVY(i), i);
					out.println(a.name + " got " + exp + " xp!");
				}	
			}
			else
			{
				toRoom(a.name() + " missed " + tar.name());
			}
		}
		////////////////////////////////////////////////
		//*********Actions****************************//
		///////////////////////////////////////////////
		public void action(pokemon a, pokemon tar, action Action) throws IOException
		{
			//Prereq Check//
			if(Action.special == 2)
			{
				if(Action.name().equalsIgnoreCase("firestorm"))
				{
					if(flashFire == false)
						return;
					else
					{
						flashFire = false;
						out.println(a.name() + " summons a powerful firestorm from the flash fire!");
					}
				}
				if(Action.name().equals("falling missle"))
				{
					if(fly == false)
						return;
					else
						fly = false;
				}
				if(Action.name().equals("grapple"))
				{
					if(fly == false)
						return;
					else
					{
						fly = false;
						out.println(a.name() + " grappled " + tar.name() + " tightly and soared into the sun");
					}
				}
				if(Action.name().equals("release"))
				{
					if(grapple == false)
						return;
					else
					{
						grapple = false;
						out.println(a.name() + " released " + tar.name() + " and let them fall to the ground");
					}
				}
				if(Action.name().equals("ki point strike"))
				{
					if(focusedMind == false)
						return;
					else
						focusedMind = false;
				}
				if(Action.name().equals("chi slash"))
				{
					if(focusedMind == false)
						return;
					else
						focusedMind = false;
				}
				if(Action.name().equals("self attack"))
				{
					if(possess == false)
						return;
					else
					{
						possess = false;
						tar.status = 0;
						out.println(tar.name() + " attack's itself!");
					}
				}
				if(Action.name().equals("soul strike"))
				{
					if(possess == false)
						return;
					else
					{
						possess = false;
						tar.status = 0;
						out.println(tar.name() + " soul has been pierced!");
					}
				}
				if(Action.name().equals("sharpen"))
				{
					if(web == true)
						return;
					else
						web = false;
				}
				if(Action.name().equals("stomach acid"))
				{
					if(web == true)
						return;
					else
					{
						web = false;
						out.println(a.name() + " began decomposing " + tar.name());
					}
				}
				if(Action.name().equals("consume"))
				{
					if(stomachAcid == true)
						return;
					else
					{
						stomachAcid = false;
						out.println(a.name() + " consumed a portion of " + tar.name()+ "!");
					}
				}
			}
////////////////////////////////////////////////////////////////////////////////////////			
			if(Action.special == 5)
			{
				if(Action.name().equals("thunder"))
				{
					if(weather == raining)
						Action.accuracy = 100;
					else
						Action.accuracy = 70;
				}
				if(Action.name().equals("discharge"))
				{
					if(charge > 0)
					{
						charge = charge - 1;
						out.println(a.name() + "discharged its energy upon " + tar.name());
					}
					else
						out.println(a.name() + " does not have enough energy");
				}
				if(Action.name().equals("photonbeam"))
				{
					if(charge > 1)
					{
						charge = charge - 2;
						out.println(a.name() + " unleashed a beam of energy upon " + tar.name() + "!");
					}
					else
						out.println(a.name() + " does not have enough energy");
				}
				if(Action.name().equals("photoncannon"))
				{
					if(charge > 2)
					{
						charge = charge - 3;
						out.println(tar.name() + " was completely blasted away by " + a.name() + "'s cannon!");
					}
					else
						out.println(a.name() + " does not have enough energy");
				}
			}
			
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
				
				////////////////////////////////////////////////
				//**********Other and Prereqs*****************//
				///////////////////////////////////////////////
				
				if(Action.name().equals("flashFire"))
					flashFire = true;
				if(Action.name().equals("grapple"))
					grapple = true;
				if(Action.name().equals("focused mind"))
					focusedMind = true;
				if(Action.name().equals("possess"))
					possess = true;
				if(Action.name().equals("web"))
					web = true;
				if(Action.name().equals("stomachacid"))
					stomachAcid = true;
				
				
				
///////////////////////////////////////////////////////////////////////////////
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
							toRoom(a.name() + " used " + Action.name()+ " on " + tar.name() + " for " + damage + " hp!");
							if(critical == 2)
								out.println("Critical hit!");
							if(multiplier1  == 2 || multiplier2 == 2)
								out.println("It's super effective!");
							if(multiplier1 == .5 || multiplier2 == .5)
								out.println("It's not very effective");
							if(Action.effect > 0)//deal with effects//
							{
								int eChance = rand.nextInt(100) + 1;
								if(eChance <= Action.chance())
								{
									tar.setStatus(Action.effect());
									effectDealer(tar, Action.effect());
								}
								if(Action.effect2() > 0)
								{
									eChance = rand.nextInt(100)+1;
									if(eChance <= Action.chance2())
									{
										tar.setStatus(Action.effect2());
										effectDealer(tar, Action.effect());
									}
								}
							}
						}
						if(damage == 0)
						{
							toRoom(a.name() + " used " + Action.name() + " on " + tar.name());
							if(Action.effect > 0)//deal with effects//
							{
								int eChance = rand.nextInt(100) + 1;
								if(eChance <= Action.chance())
								{
									tar.setStatus(Action.effect());
									effectDealer(tar, Action.effect());
								}
								if(Action.effect2() > 0)
								{
									eChance = rand.nextInt(100)+1;
									if(eChance <= Action.chance2())
									{
										tar.setStatus(Action.effect2());
										effectDealer(tar, Action.effect());
									}
								}
							}
						}
					}
					else
					{
						tar.setTempHp(0);
						toRoom(a.name() + " used " + Action.name() + " on " + tar.name() + " for " + damage + " hp!");
						if(critical == 2)
							out.println("Critical hit!");
						if(multiplier1  == 2 || multiplier2 == 2)
							out.println("It's super effective!");
						if(multiplier1 == .5 || multiplier2 == .5)
							out.println("It's not very effective");
						toRoom(tar.name() + " feinted!");
						a.newExp(a.exp + exp);
						for(int i = 0; i < 6; i++)
							a.addEV(tar.EVY(i), i);
						out.println(a.name + " got " + exp + " xp!");
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
							toRoom(a.name() + " used " + Action.name()+ " on " + tar.name() + " for " + damage + " hp!");
							if(critical == 2)
								out.println("Critical hit!");
							if(multiplier1  == 2 || multiplier2 == 2)
								out.println("It's super effective!");
							if(multiplier1 == .5 || multiplier2 == .5)
								out.println("It's not very effective");
							if(Action.effect > 0)//deal with effects//
							{
								int eChance = rand.nextInt(100) + 1;
								if(eChance <= Action.chance())
								{
									tar.setStatus(Action.effect());
									effectDealer(tar, Action.effect());
								}
								if(Action.effect2() > 0)
								{
									eChance = rand.nextInt(100)+1;
									if(eChance <= Action.chance2())
									{
										tar.setStatus(Action.effect2());
										effectDealer(tar, Action.effect());
									}
								}
							}
						}
						if(damage == 0)
						{
							toRoom(a.name() + " used " + Action.name() + " on " + tar.name());
							if(Action.effect > 0)//deal with effects//
							{
								int eChance = rand.nextInt(100) + 1;
								if(eChance <= Action.chance())
								{
									tar.setStatus(Action.effect());
									effectDealer(tar, Action.effect());
								}
								if(Action.effect2() > 0)
								{
									eChance = rand.nextInt(100)+1;
									if(eChance <= Action.chance2())
									{
										tar.setStatus(Action.effect2());
										effectDealer(tar, Action.effect());
									}
								}
							}
						}
					}
					else
					{
						tar.setTempHp(0);
						toRoom(a.name() + " used " + Action.name()+ " on " + tar.name() + " for " + damage + " hp!");
						if(critical == 2)
							out.println("Critical hit!");
						if(multiplier1  == 2 || multiplier2 == 2)
							out.println("It's super effective!");
						if(multiplier1 == .5 || multiplier2 == .5)
							out.println("It's not very effective");
						toRoom(tar.name() + " feinted!");
						a.newExp(a.exp + exp);
						for(int i = 0; i < 6; i++)
							a.addEV(tar.EVY(i), i);
						out.println(a.name + " got " + exp + " xp!");
					}
				}
			}
			else
				out.println(a.name() + " missed " + tar.name() + " with " + Action.name()+"!");
		}
		////////////////////////////////////////////////////////////////////
		//************Stat raising or Other self Actions******************//
		///////////////////////////////////////////////////////////////////
		public void action(pokemon a, action Action) throws IOException
		{
			if(Action.name().equals("charge"))
			{
				if(charge < 3)
				{
					charge++;
					out.println(a.name() + " charged up some energy!");
				}
				else
					out.println("You cannot charge up anymore energy!");
			}
			Random rand = new Random();
			int eChance;
			eChance = rand.nextInt(100)+1;
			if(eChance <= Action.chance())
			{
				effectDealer(a, Action.effect());
				a.setStatus(Action.effect());
			}
				
			
			if(Action.effect2() > 0)
			{
				eChance = rand.nextInt(100)+1;
				if(eChance <= Action.chance2())
				{
					effectDealer(a, Action.effect());
					a.setStatus(Action.effect2());
				}
					
			}
		}
		public void effectDealer(pokemon a, int effect) throws IOException
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
				if(HostileEnvironment == true)
				{
					int change;
					change = (int)((double)a.tempAtt()*1.2);
					a.settempAtt(a.tempAtt() + change);
					out.println(change);
					out.println(a.name() + "'s attack went up!");
				}
			}
			if(effect == defU)
			{
			
				int change;
				change = (int)((double)a.tempDef()*1.2);
				a.settempAtt(a.tempDef() + change);
				out.println(change);
				toRoom(a.name() + "'s defense went up!");
			}
			if(effect == spattU)
			{
				
				int change;
				change = (int)((double)a.tempSpA()*1.2);
				a.settempAtt(a.tempSpA() + change);
				out.println(change);
				toRoom(a.name() + "'s special attack went up!");
			}
			if(effect == spdefU)
			{
			
				int change;
				change = (int)((double)a.tempSpD()*1.2);
				a.settempAtt(a.tempSpD() + change);
			
				out.println(change);
				toRoom(a.name() + "'s special defense went up!");
			}
			if(effect == speedU)
			{
			
				int change;
				change = (int)((double)a.tempSp()*1.2);
				a.settempAtt(a.tempSp() + change);
			
				
				toRoom(a.name() + "'s speed went up!");
			}
			if(effect == attD)
			{
				
				int change;
				change = (int)((double)a.tempAtt()*.2);
				a.settempAtt(a.tempAtt() - change);

				toRoom(a.name() + "'s attack went down!");
			}
			if(effect == defD)
			{
				
				int change;
				change = (int)((double)a.tempDef()*.2);
				a.settempAtt(a.tempDef() - change);
				out.println(change);
				toRoom(a.name() + "'s defense went down!");
			}
			if(effect == spattD)
			{
			
				int change;
				change = (int)((double)a.tempSpA()*.2);
				a.settempAtt(a.tempSpA() - change);
			
				out.println(change);
				toRoom(a.name() + "'s special attack went down!");
			}
			if(effect == spdefD)
			{
			
				int change;
				change = (int)((double)a.tempSpD()*.2);
				a.settempAtt(a.tempSpD() - change);
				out.println(change);
				toRoom(a.name() + "'s special defense went down!");
			}
			if(effect == speedD)
			{
			
				int change;
				change = (int)((double)a.tempSp()*.2);
				a.settempAtt(a.tempSp() - change);
				out.println(change);
				toRoom(a.name() + "'s speed went down!");
			}
			/////////////////////////Special///////////////////
			if(effect == heal)
			{
				a.setTempHp(a.hp()/2 + a.tempHp());
				toRoom(a.name() + " recovered some health.");
			}
			if(effect == recharge)//FIIIIIIIIIX//
			{

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
					toRoom("it was a one-hit ko");
				}
			}
			if(effect == noDeath)
			{
				a.setStatus(27);
				a.StartStatusCounter();
			}
				///////////////////////////////////////////////////
			
		}
		
	}

	
	
	////////////////////////////////////////////////////////////
	//****** load worlds**************************************//
	///////////////////////////////////////////////////////////
	public static void loadWorld() throws Exception
	{
		
		FileReader WorldLoader = new FileReader("Wld.txt");
		BufferedReader wl = new BufferedReader(WorldLoader);
		int i = 1;
		Integer a = Integer.valueOf(wl.readLine());
		textArea.setText("loading worlds...");
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
		textArea.setText("loading pokemon...");
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
					
					int level = (Integer.valueOf(zl.readLine()));
					Integer b = Integer.valueOf(zl.readLine());
					location temp = (location)world.get(b.intValue() - 1);
					pokemon poke = new pokemon(dexnum, b);
					poke.setStats(level);
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
		textArea.setText("loading trainers...");
		
		
		while(i < a)
		{ 
			i++;
			trainer trn = new trainer(i);
			int roomNum = trn.roomNum();
			world.get(roomNum - 1).addTrainer(trn);
			
		}
		TrainerLoader.close();
		textArea.setText("finished loading");
		System.out.println("Finished Loading!");
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
	
	public boolean checkULI(String n)
	{
		boolean found = false;
		for(int i = 0; i < ULI.length && found == false; i++)
		{
			if(ULI[i]!= null)
			{
				if(ULI[i].equals(n))
					found = true;
			}
		}
		return found;
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
				if(faintedPokemon.size() > 0)
				{
					for(int i = 0; i < faintedPokemon.size(); i++)
					{
						pokemon temp = faintedPokemon.get(i);
						if(temp.faintedCounter() == 180)
						{
							temp.setFaintedCounter(0);
							faintedPokemon.remove(i);
							world.get(temp.roomNum).addCreature(temp);
						}
						else
							temp.setFaintedCounter(temp.faintedCounter()+1);
							
					}
				}
				if(LocationCounter.size() > 0)
				{
					for(int i = 0; i < LocationCounter.size(); i++)
					{
						location temp = LocationCounter.get(i);
						if(temp.counter() == 180)
						{
							temp.setCounter(0);
							if(temp.HASBRUSH() == true)
								temp.setBrush(true);
							if(temp.HASSTONE() == true)
								temp.setStone(true);
							if(temp.HASLOCK() == true)
								temp.setLock(true);
							LocationCounter.remove(i);
						}
						else
							temp.setCounter(temp.counter()+1);
							
					}
				}
				
			}
		}
		
}
