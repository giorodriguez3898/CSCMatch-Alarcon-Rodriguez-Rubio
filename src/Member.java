import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;

import CSCMatch.ArrayList;
import CSCMatch.ArrayOrderedList;
import CSCMatch.ArrayUnorderedList;

@SuppressWarnings("serial")
public class Member extends ArrayList<Member>
	implements Comparable<Member>
{
	private String name;
	private int grade;
	private int compareScore;
	private ArrayOrderedList<Interest> interestList;
	private static ArrayUnorderedList<Member> memberList = new ArrayUnorderedList<Member>();
	
	public Member(String newName, int newGrade)
	{
		name = newName;
		grade = newGrade;
		compareScore = 0;
		interestList = new ArrayOrderedList<Interest>();
		memberList.addToRear(this);
	}

	public boolean isEmpty()
	{
		return memberList.isEmpty();
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getGrade()
	{
		return grade;
	}
	
	public int getCompareScore()
	{
		return compareScore;
	}
	
	public void addInterest(int newLevel, String newTopic)
	{
		Iterator<Interest> it = this.interestList.iterator();
		Interest other = null;
		while (it.hasNext())
		{
			other = it.next();
			if(newTopic.equalsIgnoreCase(other.getTopic()))
			{
				this.interestList.remove(other);
				break;
			}
		}
		interestList.add(new Interest(newLevel, newTopic));
	}
	
	public Member getMember(String memberName)
	{	
		Member m = null;
			
		for(Member n : memberList)
		{
			if(n.getName().equalsIgnoreCase(memberName))
				m = n;
		}
		return m;
	}
	
	public void removeMember(String memberName)
	{
		Iterator<Member> it = memberList.iterator();
		Member other = null;
		
		while (it.hasNext())
		{
			other = it.next();
			if (memberName.equalsIgnoreCase(other.getName().toLowerCase()))
				break;
		}
		memberList.remove(other);
	}
	
	public void listMember(Member o)
	{
		System.out.println("Name: " + o.name);
		System.out.println("Grade: " + o.grade);
		System.out.print("Interests:");
		
		for (Interest x : o.interestList)
		{
			System.out.print("\n  " + x.getTopic() + ": ");
			System.out.print(x.getScore());
		}
		System.out.println("\n\nTop 5 Matches:");
		
		ArrayOrderedList<Member> matchList = new ArrayOrderedList<Member>();
		compareScore = this.compareMember(o);
		
		for(Member m : memberList)
		{
			m.compareScore = o.compareMember(m);
			matchList.add(m);
		}
			
		matchList.remove(o);
		
		int i = 0;
		for(Member m : matchList)
		{	
			if (i < 5)
				System.out.println("  " + m.getName());
			i++;
		}
	}
	
	public void listAll()
	{	
		String result = "";
		for (Member ml : memberList)
			result += "\n" + ml.getName();
		System.out.println(result + "\n");
	}
	
	@SuppressWarnings("unchecked")
	public static void memberLoad(String fileName) throws IOException, ClassNotFoundException
	{
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
		memberList = (ArrayUnorderedList<Member>) ois.readObject();
		
		ois.close();
	}
	
	public void memberSave(String fileName) throws IOException
	{
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
		oos.writeObject(memberList);
		
		oos.close();
	}

	public int compareMember(Member o) 
	{	
		int num = 0; 
		boolean match = true; 
		
		for (Interest i : o.interestList)
		{
			String otherInterest = i.getTopic();
			
			for (Interest x : interestList)
			{
				String myInterest = x.getTopic();
				
				if (otherInterest.equalsIgnoreCase(myInterest) )
				{
					num = num + ( x.getScore() * i.getScore() );
					match = true; 
					break;
				}
				else
					match = false; 
			}
			if (!match)
				num = num + ((int)i.getScore()/2);
		}
		return num; 
	}
	
	public boolean contains(String memberName) 
	{
		boolean memberExists = false;
		
		for (Member o : memberList)
		{
			if(o.getName().equalsIgnoreCase(memberName))
			{
				memberExists = true;
				break;
			}
		}
		return memberExists;
	}
	
	@Override
	public int compareTo(Member o)
	{
		return o.getCompareScore() - this.compareScore;
	}
}