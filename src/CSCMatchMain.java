import java.util.Scanner;

/*
 * The main should prompt user to make a user member profile so that we may use it to compare to other members
 * 
 * THe main should also be able to add interests to members
 * 
 */

public class CSCMatchMain 
{
	private static String fileName;
	private static Member user;
	public static Member members = null;
	private static Scanner in = new Scanner(System.in);
	private static boolean changesMade = true;

	public static void load()
	{
		System.out.print("\nInput Existing File: ");
		fileName = in.nextLine() + ".ser";
		
		try{
		    Member.memberLoad(fileName);
		}catch(Exception e){
			System.out.println("\nAn error has occured while loading members. " + e.getMessage());
		}
		System.out.println();
		changesMade = false;
	}
	
	public static void save()
	{
		System.out.print("\nWhere would you like to save it?: ");
		fileName = in.nextLine()+ ".ser";
		
		try{
			members.memberSave(fileName);
		}catch(Exception e){
			System.out.println("An error has occured while saving members. " + e.getMessage());
		}
		changesMade = false;
	}
	
	public static void mainListAll()
	{
		if(members == null)
			System.out.println("\n*No Members*\n");
		else
		{
			System.out.println("\nMembers:");
			members.listAll();
		}
	}
	
	public static void mainListMember()
	{
		if(members == null)
			System.out.println("\n*No Members*\n");
		else
		{
			System.out.println("\nWhich Member would you like to list?");
			System.out.print("Input existing Member: ");
			String s = in.nextLine();	
			
			if (!members.contains(s))
			{
				System.out.println("\n*Member does not exist*\n");
				return;
			} else
			{
				Member m = members.getMember(s);
				System.out.println();
				members.listMember(m);
				System.out.println();
			}
		}
	}
	
	public static void mainAddMember()
	{
		System.out.println("\nWhat is the new Member's name?");
		
		String name = "";
		int grade = 0;
		boolean memberExists = false;

		System.out.print("Input new Member's name: ");
		name = in.nextLine();
			
		if (members == null)
			memberExists = false;
		else if (members.contains(name))
			memberExists = true;
			
		if (memberExists)
		{
			System.out.println("\t\n*Member Already Exists*\n");
			return;
		}
		
		String userGrade = "";
		while(!userGrade.equalsIgnoreCase("Freshman") && !userGrade.equalsIgnoreCase("Sophomore") && !userGrade.equalsIgnoreCase("Junior") && !userGrade.equalsIgnoreCase("Senior") && !userGrade.equalsIgnoreCase("Graduate"))
		{
			System.out.print("What grade is this Member in? (Freshman, Sophomore, Junior, Senior, or Graduate): ");
			userGrade = in.nextLine().toLowerCase();
		}
	
		switch(userGrade)
		{
			case "freshman":
				grade = 1; break;
			case "sophomore":
				grade = 2; break;
			case "junior":
				grade = 3; break;
			case "senior":
				grade = 4; break;
			case "graduate":
				grade = 5; break;
		}
		
		members =  new Member(name, grade);
		System.out.println("\t\n~Member has been added~\n");
		changesMade = true;
	}
	
	public static void mainRemoveMember()
	{
		if (members == null)
			System.out.println("\n*No Members*\n");
		else
		{
			System.out.println("\nWhich Member would you like to remove?");
			
			System.out.print("Input existing Member: ");
			String s = in.nextLine();
				
			if (!members.contains(s))
			{
				System.out.println("\n*Member does not exist*\n");
				return;
			}
			
			Member m = members.getMember(s);
			
			if (m.getName().equalsIgnoreCase(user.getName()))
				members.removeMember(user.getName());
			else
				members.removeMember(m.getName());
			
			if (members.isEmpty())
				members = null;
			
			System.out.println("\t\n~Member has been removed~\n");
			changesMade = true;
		}
	}

	public static void addInterestMember(Member m)
	{
		if (members == null)
			System.out.println("\n*No Members*\n");
		else
		{
			String topic = "";
			int score = -1;
			
			System.out.print("What is the interest topic?: ");
			topic  = in.nextLine();
				
			while(score < 0 || score > 10)
			{
				System.out.print("What is the interest score? (0-10): ");
				score = in.nextInt();
			}
			m.addInterest(score, topic);
			
			System.out.println("\t\n~Interest added to " + m.getName() + "~\n");
			changesMade = true;
		}
	}
	
	public static void help()
	{
	    System.out.print("\nHelp Commands:\nTo see all avaliable commands, type: \n\t\"help\"\nTo add Interest to Members, type: \n\t\"add interest\"\n"
        		+ "To load Members, type: \n\t\"load\"\nTo save Members, type: \n\t\"save\"\nTo list all Members, type: \n\t\"list all\"\nTo list an "
        		+ "individual member, type: \n\t\"list member\"\nTo add a member to the list, type: \n\t\"add member\"\nTo remove a member from the list, type: \n\t\"remove member\"\nTo exit, type: \n\t\"quit\","
        		+ " \"stop\", or \"exit\"\n\n");
	}
	
	
	public static void main(String[] args) 
	{
		System.out.println("CSC Match - A. Rubio, S. Rodriguez, D. Alarcon\n");
		System.out.print("What is your name?: ");
		String userName = in.nextLine();
		
		String userGrade = "";
		while(!userGrade.equalsIgnoreCase("Freshman") && !userGrade.equalsIgnoreCase("Sophomore") && !userGrade.equalsIgnoreCase("Junior") && !userGrade.equalsIgnoreCase("Senior") && !userGrade.equalsIgnoreCase("Graduate"))
		{
			System.out.print("What grade is this Member in? (Freshman, Sophomore, Junior, Senior, or Graduate): ");
			userGrade = in.nextLine().toLowerCase();
		}
		int grade = 0;
		
		switch(userGrade)
		{
			case "freshman":
				grade = 1; break;
			case "sophomore":
				grade = 2; break;
			case "junior":
				grade = 3; break;
			case "senior":
				grade = 4; break;
			case "graduate":
				grade = 5; break;
		}
		members = user = new Member(userName, grade);
	    String input = ""; 
		
	    help();
	      
	    while(!input.equalsIgnoreCase("exit") && !input.equalsIgnoreCase("stop") && !input.equalsIgnoreCase("quit"))
	    {
	    	input = in.nextLine();
		    
	    	if(input.equalsIgnoreCase("load"))
		     	load();	        		
	    	else if(input.equalsIgnoreCase("list all"))
	       		mainListAll();	
	       	else if(input.equalsIgnoreCase("list member"))
	       		mainListMember();
        	else if(input.equalsIgnoreCase("add member"))
	       		mainAddMember(); 		
	       	else if(input.equalsIgnoreCase("remove member"))
	       		mainRemoveMember();	        		
	       	else if(input.equalsIgnoreCase("save"))
	       	{
	       		save();	        
	    		System.out.println();
	       	}
	       	else if(input.equalsIgnoreCase("help"))       
	       		help();	        		
	    	else if(input.equalsIgnoreCase("add interest"))
	    	{  		
	    		if (members == null)
	    			System.out.println("\n*No Members*\n");
	    		else
	    		{
	    			System.out.print("\nInput existing Member: ");
		    		String s = in.nextLine();	
		    		
		    		if (members.contains(s))
		    		{
		    			Member m = members.getMember(s);
	    				addInterestMember(m);
		    		} else
		    			System.out.println("\n*Member does not exist*\n");
	    		}
	    	}
	    }
	    char ans = 'a';
	    
	    if (changesMade)
	    {	
	    	while (ans != 'y' && ans != 'n')
	    	{
	    		System.out.print("\nWould you like to save before exiting? (Y/N): ");  
	    		ans = in.nextLine().toLowerCase().charAt(0);
	        
	    		if(ans == 'y')
	    		{
	    			save();
	    			break;
	    		}
	    	}
	    	in.close();
	    }
	    System.out.println("\nGoodbye!");
	}
}