import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;

   public class location
   {
   
      String title, description;
      int roomNum;
      int west;
      int north;
      int south;
      int east;
      int up;
      int down, lockedRoom, keyID, rock, bush, counter, roomType;
      public int[] exits = new int[6];
   	  LinkedList<pokemon> crt = new LinkedList<pokemon>();
   	  boolean weather = false;
   	  boolean isPC = false;
   	  boolean isWater = false;
   	  boolean isLock, hasStone, hasBrush, HASLOCK, HASSTONE, HASBRUSH, ISDARK;
   	  LinkedList<trainer> mob = new LinkedList<trainer>();
   	  LinkedList<Item> obj = new LinkedList<Item>();
   	  LinkedList<User> plr = new LinkedList<User>();
      public location(int ROOMNUM) throws NumberFormatException, IOException
      {
    	 FileReader WorldLoader = new FileReader("Wld.txt");
    	 BufferedReader wl = new BufferedReader(WorldLoader);
    	 
    	 Integer a = ROOMNUM;
    	 String b = wl.readLine();
    	 
    	 while(b.equals("#" + a.toString()) != true)
    	 {
    		 b = wl.readLine();
    	 }
    		 
    	 roomNum = ROOMNUM;	
         title = wl.readLine();
         description = wl.readLine();
         west = Integer.valueOf(wl.readLine());
         north = Integer.valueOf(wl.readLine());
         south = Integer.valueOf(wl.readLine());
         east = Integer.valueOf(wl.readLine());
         up = Integer.valueOf(wl.readLine());
         down = Integer.valueOf(wl.readLine());
         roomType = Integer.valueOf(wl.readLine());
         if(roomType == 0)
         {
        	 weather = true;
        	 ISDARK = false;
         }
         if(roomType == 1)
         {
        	 weather = false;
        	 ISDARK = false;
         }
         if(roomType == 2)
         {
        	 weather = false;
        	 ISDARK = true;
         }
         isPC = Boolean.valueOf(wl.readLine());
         isWater = Boolean.valueOf(wl.readLine());
         HASLOCK = Boolean.valueOf(wl.readLine());
         if(HASLOCK == true)
         {
        	 lockedRoom = Integer.valueOf(wl.readLine());
        	 keyID = Integer.valueOf(wl.readLine());
        	 isLock = true;
         }
         HASSTONE = Boolean.valueOf(wl.readLine());
         if(HASSTONE == true)
         {
        	 rock = Integer.valueOf(wl.readLine());
        	 hasStone = true;
         }
         HASBRUSH = Boolean.valueOf(wl.readLine());
         if(HASBRUSH == true)
         {
        	 bush = Integer.valueOf(wl.readLine());
        	 hasBrush = true;
         }
         wl.mark(3);
         if(wl.read() != -1)
         {
         wl.reset();
         if(wl.readLine().equals("I"))
         {
        	 String k = "";
        	 while(k.equals("&") != true)
        	 {
        		 k = wl.readLine();
        		 if(k.equals("~"))
        		 {
        			 Item i = new Item(Integer.valueOf(wl.readLine()));
        			 obj.add(i);
        		 }
        	 }
         }
         }
         WorldLoader.close();
         exits[0] = west;
         exits[1] = north;
         exits[2] = south;
         exits[3] = east;
         exits[4] = up;
         exits[5] = down;
         
         
         
      }
      public location()
      {
    	  
      }
   
      public String getTitle()
      {
         return title;
      }
      public void addCreature(pokemon Pokemon)
      {
    	  crt.add(Pokemon);
      }
      public void removeCreature(pokemon Pokemon)
      {
    	  crt.remove(Pokemon);
      }
      public String getCreatureName(int i)
      {
    	  	return ((pokemon) crt.get(i)).name();
      }
      public pokemon getCreature(int i)
      {
    	  return (pokemon) crt.get(i);
      }
      public void getList(User user) throws IOException
      {
    	  PrintWriter out = new PrintWriter(user.client.getOutputStream(), true);
    	  for(int i = 0; i < crt.size(); i++)
    	  {
    		  out.println("(" + ((pokemon) crt.get(i)).level() + ") " + ((pokemon) crt.get(i)).description());
    	  }
    	  for(int i = 0; i < mob.size(); i++)
    	  {
    		  out.println("      " + mob.get(i).des());
    	  }
    	  for(int i = 0; i < obj.size(); i++)
    	  {
    		  out.println(" A " + obj.get(i).name() + " is laying here");
    	  }
    	  for(int i = 0; i < plr.size(); i++)
    	  {
    		  if(user != plr.get(i))
    			  out.println(plr.get(i).name() + " is standing here with their " + plr.get(i).getPoke(0).name());
    	  }
      }
      public void getList()
      {
    	  
    	  for(int i = 0; i < crt.size(); i++)
    	  {
    		  System.out.println("(" + ((pokemon) crt.get(i)).level() + ") " + ((pokemon) crt.get(i)).description());
    	  }
    	  for(int i = 0; i < mob.size(); i++)
    	  {
    		  System.out.println("      " + mob.get(i).des());
    	  }
    	  for(int i = 0; i < obj.size(); i++)
    	  {
    		  System.out.println(" A " + obj.get(i).name() + " is laying here");
    	  }
    	  for(int i = 0; i < plr.size(); i++)
    	  {
    		  System.out.println(plr.get(i).name() + " is standing here with their " + plr.get(i).getPoke(0));
    	  }
      }
      public int crtNum()
      {
    	  return crt.size();
      }
      public void addTrainer(trainer tr)
      {
    	  mob.add(tr);
      }
      public int mobNum()
      {
    	  return mob.size();
      }
      public trainer getMob(int i)
      {
    	  return mob.get(i);
      }
      public String getDes()
      {
         return description;
      }
      public int roomNum()
      {
         return roomNum;
      }
      public int east()
      {
    	  return east;
      }
      public int west()
      {
    	  return west;
      }
      public int south()
      {
    	  return south;
      }
      public int north()
      {
    	  return north;
      }
      public int up()
      {
    	  return up;
      }
      public int down()
      {
    	  return down;
      }
      public void printExits(User user) throws IOException
      {
    	 PrintWriter out = new PrintWriter(user.client.getOutputStream(), true);
    	 String exit = "Exits: ";
    	  if(exits[0] != 0)
    		  if(exits[0] == rock || exits[0] == bush || exits[0] == lockedRoom)
    		  {
    			  if(hasStone != true && hasBrush != true && isLock != true)
    				  exit = exit + ("w ");
    		  }
    		  else
    			  exit = exit + ("w ");
    	  if(exits[1] != 0)
    		  if(exits[1] == rock || exits[1] == bush || exits[1] == lockedRoom)
    		  {
    			  if(hasStone != true && hasBrush != true&& isLock != true)
    			  exit = exit + ("n ");
    		  }
    		  else
    			  exit = exit + ("n ");
    	  if(exits[2] != 0)
    		  if(exits[2] == rock ||exits[2] == bush|| exits[2] == lockedRoom)
    		  {
    			  if(hasStone != true && hasBrush != true&& isLock != true)
    				  exit = exit + ("s ");
    		  }
    		  else
    			  exit = exit + ("s ");
    	  if(exits[3] != 0)
    		  if(exits[3] == rock ||exits[3] == bush|| exits[3] == lockedRoom)
    		  {
    			  if(hasStone != true && hasBrush != true&& isLock != true)
    				  exit = exit + ("e ");  
    		  }
    		  else
    			  exit = exit + ("e ");
    	  if(exits[4] != 0)
    		  if(exits[4] == rock ||exits[4] == bush|| exits[4] == lockedRoom)
    		  {
    			  if(hasStone != true && hasBrush != true&& isLock != true)
    				  exit = exit + ("u ");
    		  }
    		  else
    			  exit = exit + ("u ");
    	  if(exits[5] != 0)
    		  if(exits[5] == rock ||exits[5] == bush|| exits[5] == lockedRoom)
    		  {
    			  if(hasStone != true && hasBrush != true&& isLock != true)
    				  exit = exit + ("d ");
    		  }
    		  else
    			  exit = exit + ("d ");
    	  
    	  out.println(exit);
      }
      public void printExits()
      {
    	 
    	 String exit = "Exits: ";
    	  if(exits[0] != 0)
    		  exit = exit + ("w ");
    	  if(exits[1] != 0)
    		  exit = exit + ("n ");
    	  if(exits[2] != 0)
    		  exit = exit + ("s ");
    	  if(exits[3] != 0)
    		  exit = exit + ("e ");
    	  if(exits[4] != 0)
    		  exit = exit + ("u ");
    	  if(exits[5] != 0)
    		  exit = exit + ("d ");
    	  
    	  System.out.println(exit);
      }
      public boolean weather()
      {
    	 return weather;
      }
      public boolean isPC()
      {
    	  return isPC;
      }
      public boolean HASBRUSH()
      {
    	  return HASBRUSH;
      }
      public boolean HASSTONE()
      {
    	  return HASSTONE;
      }
      public boolean HASLOCK()
      {
    	  return HASLOCK;
      }
      public boolean isLocked()
      {
    	  return isLock;
      }
      public int key()
      {
    	  return keyID;
      }
      public void setStone(boolean i)
      {
    	  hasStone = i;
      }
      public void setBrush(boolean i)
      {
    	  hasBrush = i;
      }
      public void setLock(boolean i)
      {
    	  isLock = i;
      }
      public int counter()
      {
    	  return counter;
      }
      public void setCounter(int c)
      {
    	  counter = c;
      }
      public void addObj(Item i)
      {
    	  obj.add(i);
      }
      public void removeObj(Item i)
      {
    	  obj.remove(i);
      }
      public void setDark(boolean i)
      {
    	  ISDARK = i;
      }
      public void addPlayer(User user) throws IOException
      {
    	  plr.add(user);
    	  if(user.getPoke(0).isFlash == true && ISDARK == true)
    		  ISDARK = false;
    	  
    	  for(int i = 0; i < plr.size(); i++)
    	  {
    		  if(plr.get(i) != user)
    		  {
    			  PrintWriter out = new PrintWriter(plr.get(i).client.getOutputStream(), true);
    			  out.println(user.name() + " has arrived");
    		  }
    			  
    	  }
      }
      public void removePlayer(User user) throws IOException
      {
    	  plr.remove(user);
    	  if(user.getPoke(0).isFlash == true && roomType == 2)
    	  {
    		  boolean otherL = false;
    		  for(int i = 0; i < plr.size() && otherL == false; i++)
    		  {
    			  if(plr.get(i).getPoke(0).isFlash == true)
    				  otherL = true;
    		  }
    		  if(otherL != true)
    			  ISDARK = true;
    	  }
    	  for(int i = 0; i < plr.size(); i++)
    	  {
    		  if(plr.get(i) != user)
    		  {
    			  PrintWriter out = new PrintWriter(plr.get(i).client.getOutputStream(), true);
    			  out.println(user.name() + " has left");
    		  }
    			  
    	  }
      }
   }