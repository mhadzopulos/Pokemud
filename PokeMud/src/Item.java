import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
   
   public class Item 
   {
      int attUp, defUp, spattUp, spdefUp, speedUp, heal, status, ID, badgrq, price, itemType;
      int type1, type2, catchrate, moveID, keyID;
      String name, moveName;
      boolean revive, allHealth, allStatus, isBall, isHeld, isPC, isUse, isHM;
      public Item()
      {
      }
      public Item(String NAME,int TYPE,int HEAL,int DEF,int ATT, int SPATT, int SPDEF, int SPEED, int STATUS
    		  , boolean REVIVE, boolean ALLHEALTH, boolean ALLSTATUS)
      {
         name = NAME;
         heal = HEAL;
         defUp = DEF;
         attUp = ATT;
         spattUp = SPATT;
         spdefUp = SPDEF;
         speedUp = SPEED;
         status = STATUS;
         revive = REVIVE;
         allHealth = ALLHEALTH;
         allStatus = ALLSTATUS;

      }
      public Item(int id) throws IOException
      {
    	  FileReader ObjLoad = new FileReader("Obj.txt");
    	  BufferedReader ol = new BufferedReader(ObjLoad);
    	  
    	  
    	  Integer a = id;
    	  String k = ol.readLine();
    	  ID = a;
    	  
    	  
    	  while(k.equals("#" + a.toString()) != true)
    	  {
    		  k = ol.readLine();
    	  }
    	  
          name = ol.readLine();
          itemType = Integer.valueOf(ol.readLine());
          
          if(itemType == 0) // pokeball
          {
        	isBall = true;
        	type1 = Integer.valueOf(ol.readLine());
      		type2 = Integer.valueOf(ol.readLine());
      		catchrate = Integer.valueOf(ol.readLine());
      		badgrq = Integer.valueOf(ol.readLine());
      		price = Integer.valueOf(ol.readLine());
          }
          if(itemType == 1)// usable item
          {
        	isUse = true;
        	heal = Integer.valueOf(ol.readLine());
        	defUp = Integer.valueOf(ol.readLine());
        	attUp = Integer.valueOf(ol.readLine());
        	spattUp = Integer.valueOf(ol.readLine());
        	spdefUp = Integer.valueOf(ol.readLine());
        	speedUp = Integer.valueOf(ol.readLine());
        	status = Integer.valueOf(ol.readLine());
        	badgrq = Integer.valueOf(ol.readLine());
        	revive = Boolean.valueOf(ol.readLine());
        	allHealth = Boolean.valueOf(ol.readLine());
          	allStatus = Boolean.valueOf(ol.readLine());
            price = Integer.valueOf(ol.readLine());
          }
          if(itemType == 2)// held item
          {
        	  
          }
          if(itemType == 3)//HM
          {
        	  moveID = Integer.valueOf(ol.readLine());
        	  moveName = ol.readLine();
          }
          if(itemType == 4)//Keys
          {
        	  keyID = Integer.valueOf(ol.readLine());
          }
          ObjLoad.close();
    	  
      }
      public String name()
      {
         return name;
      }
      public int def()
      {
         return defUp;
      }
      public int att()
      {
         return attUp;
      }
      public int heal()
      {
         return heal;
      }
      public int spatt()
      {
    	  return spattUp;
      }
      public int spdef()
      {
    	  return spdefUp;
      }
      public int speed()
      {
    	  return speedUp;
      }
      public int status()
      {
    	  return status;
      }
      public boolean revive()
      {
    	  return revive;
      }
      public boolean allStatus()
      {
    	  return allStatus;
      }
      public boolean allHealth()
      {
    	  return allHealth;
      }
      public int badgrq()
      {
    	  return badgrq;
      }
  	public int type1()
	{
		return type1;
	}
	public int type2()
	{
		return type2;
	}
	public int catchrate()
	{
		return catchrate;
	}
	public int price()
	{
		return price;
	}
	public int ID()
	{
		return ID;
	}
	public boolean isBall()
	{
		return isBall;
	}
	public boolean isPC()
	{
		return isPC;
	}
	public boolean isUse()
	{
		return isUse;
	}
   
   }