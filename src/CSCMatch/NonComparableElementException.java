package CSCMatch;

@SuppressWarnings("serial")
public class NonComparableElementException extends RuntimeException
{
    public NonComparableElementException (String collection)
    {
        super ("The " + collection + " requires comparable elements.");
    }
}
