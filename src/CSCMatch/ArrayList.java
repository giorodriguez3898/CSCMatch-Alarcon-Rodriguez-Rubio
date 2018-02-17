package CSCMatch;
import java.io.Serializable;
import java.util.*;

@SuppressWarnings("serial")
public abstract class ArrayList<T> implements ListADT<T>, Iterable<T>, Serializable
{
    private final static int DEFAULT_CAPACITY = 100;
    private final static int NOT_FOUND = -1;
	
    protected int rear;
    protected T[] list;
	protected int modCount;

    public ArrayList()
    {
        this(DEFAULT_CAPACITY);
    }
    
    @SuppressWarnings("unchecked")
	public ArrayList(int initialCapacity)
    {
        rear = 0;
        list = (T[])(new Object[initialCapacity]);
		modCount = 0;
    }

    @SuppressWarnings("unchecked")
	protected void expandCapacity()
    {
    	// copy old array to new array of greater length
    	T[] newArray = (T[]) new Object[list.length + 1];
    	System.arraycopy(list, 0, newArray, 0, list.length);
    	
    	list = newArray;
    }
	
    public T removeLast()
    {
        T result;
    	
    	if (isEmpty())
        	throw new EmptyCollectionException("ArrayList");
        
        result = last();
        rear--;
        
        // removes last element
        list[rear] = null;
        modCount++;
        
        return result;
    }

    public T removeFirst()
    {
        T result;
        
        if (isEmpty())
        	throw new EmptyCollectionException("ArrayList");
        
        result = first();
        rear--;
        
        // shift the appropriate elements 
        for (int scan = 0; scan < rear; scan++)
            list[scan] = list[scan+1];
        
        list[rear] = null;
        modCount++;
        
        return result;
    }

    public T remove(T element)
    {
        T result;
        int index = find(element);

        if (index == NOT_FOUND)
            throw new ElementNotFoundException("ArrayList");

        result = list[index];
        rear--;
		
        // shift the appropriate elements 
        for (int scan=index; scan < rear; scan++)
            list[scan] = list[scan+1];
 
        list[rear] = null;
		modCount++;

        return result;
    }
   
    public T first()
    {
        if (isEmpty())
        	throw new EmptyCollectionException("ArrayList");
        
        return list[0];
    }

    public T last()
    {
        if (isEmpty())
        	throw new EmptyCollectionException("ArrayList");
        
        return list[rear - 1];
    }

    public boolean contains(T target)
    {
        return (find(target) != NOT_FOUND);
    }

    private int find(T target)
    {
        int scan = 0; 
		int result = NOT_FOUND;
 
        if (!isEmpty())
            while (result == NOT_FOUND && scan < rear)
                if (target.equals(list[scan]))
                    result = scan;
                else
                    scan++;

        return result;
    }

    public boolean isEmpty()
    {
        return (rear == 0);
    }
 
    public int size()
    {
        return rear;
    }
	
    public Iterator<T> iterator()
    {
        return new ArrayListIterator();
    }
    
	private class ArrayListIterator implements Iterator<T>
	{
		int iteratorModCount;
		int current;
		
		public ArrayListIterator()
		{
			iteratorModCount = modCount;
			current = 0;
		}
		
		public boolean hasNext() throws ConcurrentModificationException
		{
			if (iteratorModCount != modCount)
				throw new ConcurrentModificationException();
			
			return (current < rear);
		}
		
		public T next() throws ConcurrentModificationException
		{
			if (!hasNext())
				throw new NoSuchElementException();
			
			current++;
			
			return list[current - 1];
		}
		
		public void remove() throws UnsupportedOperationException
		{
			throw new UnsupportedOperationException();
		}
		
	}	
}