import CSCMatch.ArrayOrderedList;

@SuppressWarnings("serial")
public class Interest extends ArrayOrderedList<Interest> implements Comparable<Interest>
{
	private int score;
	private String topic;
		
	public Interest(int newScore, String newTopic)
	{
		score = newScore;
		topic = newTopic;
		this.add(this);
	}

	@Override
	public int compareTo(Interest compareInterest) 
	{
		return compareInterest.getScore() - this.score;
	}
	
	public String getTopic()
	{
		return topic;
	}
	
	public int getScore()
	{
		return score;
	}
}
