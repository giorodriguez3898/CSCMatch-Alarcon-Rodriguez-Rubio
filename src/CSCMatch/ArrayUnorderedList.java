package CSCMatch;

@SuppressWarnings("serial")
public class ArrayUnorderedList<T> extends ArrayList<T> 
         implements UnorderedListADT<T>
{
    public ArrayUnorderedList()
    {
        super();
    }

    public ArrayUnorderedList(int initialCapacity)
    {
        super(initialCapacity);
    }

    public void addToFront(T element)
    {
        if (size() == list.length)
        	expandCapacity();
        
        //shifts all elements up 1
        for (int scan = rear; scan > 0; scan--)
        	list[scan] = list[scan-1];
        
        //insert element to front
        list[0] = element;
        modCount++;
        rear++;
    }

    public void addToRear(T element)
    {
        if (size() == list.length)
        	expandCapacity();
        
        //insert element to rear
        list[rear] = element;
        modCount++;
        rear++;
    }

    public void addAfter(T element, T target)
    {
        if (size() == list.length)
            expandCapacity();

        int scan = 0;
		
		// find the insertion point
        while (scan < rear && !target.equals(list[scan])) 
            scan++;
      
        if (scan == rear)
            throw new ElementNotFoundException("UnorderedList");
    
        scan++;
		
		// shift elements up one
        for (int shift=rear; shift > scan; shift--)
            list[shift] = list[shift-1];

		// insert element
		list[scan] = element;
        rear++;
		modCount++;
    }
}
