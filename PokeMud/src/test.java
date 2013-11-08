import java.awt.Toolkit;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;


public class test 
{
	public static void main(String args[])
	{
		double perc = (77.0/88.0)*150;
		System.out.println(perc);
    	int q = (int)perc;
    	System.out.println(q);
		Scanner scan = new Scanner(System.in);
		String p = scan.nextLine();
	
		if(p.contains(" ") == true)
		{
			System.out.println(p.indexOf(' '));
			int a = p.indexOf(' ');
			String sub = p.substring(a+1, p.length());
			System.out.println(sub);
		
			if(sub.contains(" ") == true)
			{
				System.out.println(sub.indexOf(' '));
				int d = sub.indexOf(' ');
				String b = sub.substring(0, d);
				String c = sub.substring(d+1, sub.length());
				
				System.out.println(b);
				System.out.println(c);
			}
			
		
		
		}
	}
	
}
