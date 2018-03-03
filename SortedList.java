
// Rahul Mody

import java.util.Iterator;
import java.util.Random;

public class SortedList<E extends Comparable<? super E>> extends List<E> 
{
	private boolean guard = true;
	
	private Node<E> proxy;
	
	private Node<E> sCurr;

    // Inserts the given data into the list, recursively.
	public void insert(E data)
	{
		Node<E> temp = new Node<E>(data);
		
        // if the list is empty in the beginning, then head = null. hence this is the beginning. 
		if (head == null)
		{
			head = temp;
			return;
		}
		
        // if the current data is less than the head's data. It will replace head and its next node will become head value.
		if(head.data.compareTo(data) >= 0)
		{
			temp.next = head;
			head = temp;
			return;
		}
		
        // When head is the sole data in the list, and the given data is larger than head's data, it is placed next to head.
		if (head.next == null)
		{
			head.next = temp;
			return;
		}
		
        // If node after haed has a value, and the current value is greater than head, then we move further.
		if(head.next != null) 
		{	
            // if that node's data is greater than current, then current is placed between head and head-next.
			if(head.next.data.compareTo(data) >= 0)
			{
				temp.next = head.next;
				head.next = temp;
				return;
			}
			
			else
			{    
                // guard measure is used to maintain consistency of head node when the data is set recursively.
				if (guard)
				{
					proxy = head;
					guard = false;
				}
				
				head = head.next;
				insert(data);
				
				if (!guard)
				{
					head = proxy;
					guard = true;
				}
				return;
			}
		}
		
		
	}
	
    //removes the given data out of the list
	public void remove(E data)
    {
        // All if conditions are placed to specify actions at the ends of the list if a when macth is found
		if(head == null)
		{
			return;
		}
    	
    	if (head != null )
    	{
    		if (data.compareTo(head.data) == 0)
    		{
    			if (head.next != null)
    			{
    				head = head.next;
    				return;
    			}
    			else
    			{
    				head = null;
    				return;
    			}
    		}
    	}

    	if (head.next != null)
    	{
    		if (data.compareTo(head.next.data) == 0)
    		{
    			if(head.next.next != null)
    			{
    				head.next = head.next.next;
    				return;
    			}
    			else 
    			{
    				return;
    			}
    		}
    		else
    		{
    			if(guard)
    			{
    				proxy = head;
    				guard = false;
    			}
    			
    			head = head.next;
    			remove(data);
    			
    			if(!guard)
    			{
    				head = proxy;
    				guard = true;
    			}
    			return;
    		}
    	}
    }

    //Returns the data at a given index. Uses numeric counter simultaneously with head node to get to the 'index'.
    public  E retrieve(int index)
    {
    	int i = index;
    	
    	if (i == 0)
    	{
    		return head.data;
    	}
    	else 
    	{
    		try
    		{
    			if(guard)
    			{
    				proxy = head;
    				guard = false;
    			}
    			head = head.next;
    			retrieve(i--);
    			
    			if(!guard)
    			{
    				head = proxy;
    				guard = true;
    			}
    		
    		}
    		catch(NullPointerException e)
    		{
    			return null;
    		}
    	}
    	return null;
    }
    
    // Searches for a number in the list. If found, returns ture. False otherwise. 
    //The list traversal is the simplest, the method is called recursively. Each time the number is not found, head is set to head-next.	
    public  boolean search(E data)
    {
    	Node<E> temp = new Node<E>(data);
    	
    	if (head.data == null)
    	{
    		return false;
    	}
    	else  
    	{
    		try
    		{
    			if (temp.data.compareTo(sCurr.data) == 0)
    			{
    				return true;
    			}
    		}
    		catch (NullPointerException e)
    		{
    			return false;
    		}
    		if (temp.data.compareTo(sCurr.data) != 0)
    		{
    			sCurr = sCurr.next;
    			search(data);
    		}
    		return false;
    	}
    }

    public Iterator<E> iterator() 
    {
    	return new Iterator<E>() 
    	{
       
    		public boolean hasNext() 
    		{
                return curr != null;
            }
            public E next()
            {
                E temp = curr.data;
                curr = curr.next;
                return temp;
            }
            public void remove()
            {
                throw new UnsupportedOperationException();
            }
      private Node<E> curr = (Node<E>)head;
            
      };
    }
    

    // code runner
	public static void main(String[] args) 
	{
		
		Random rand = new Random(1);
        SortedList<Integer> list = new SortedList<Integer>();
        int m = args.length == 1 ? Integer.parseInt(args[0]) : 10;

        System.out.println("insert");
        for (int i = 0; i < m; ++i) {
            int n = rand.nextInt(m);
            list.insert(n);
            System.out.print(n + ": ");
            for (Integer j : list) {
                System.out.print(j + " ");
            }
            System.out.println();
        }

        System.out.println("remove");
        for (int i = 0; i < m; ++i) {
            int n = rand.nextInt(m);
            System.out.print(n + ": ");
            list.remove(n);
           for (Integer j : list) {
                System.out.print(j + " ");
            }
            System.out.println();
        }
	
	}
}

/*\
 Output
insert
5: 5 
8: 5 8 
7: 5 7 8 
3: 3 5 7 8 
4: 3 4 5 7 8 
4: 3 4 4 5 7 8 
4: 3 4 4 4 5 7 8 
6: 3 4 4 4 5 6 7 8 
8: 3 4 4 4 5 6 7 8 8 
8: 3 4 4 4 5 6 7 8 8 8 
remove
9: 3 4 4 4 5 6 7 8 8 8 
3: 4 4 4 5 6 7 8 8 8 
7: 4 4 4 5 6 8 8 8 
3: 4 4 4 5 6 8 8 8 
2: 4 4 4 5 6 8 8 8 
4: 4 4 5 6 8 8 8 
2: 4 4 5 6 8 8 8 
2: 4 4 5 6 8 8 8 
6: 4 4 5 8 8 8 
9: 4 4 5 8 8 8 
*/
