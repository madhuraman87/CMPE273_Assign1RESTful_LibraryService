package edu.sjsu.cmpe.library.dto;
import edu.sjsu.cmpe.library.domain.*;
import java.util.HashMap;

public class BookRepository 
{
    public static HashMap<Long, Book> bookInMemoryMap;    
    static
    {
		bookInMemoryMap = new HashMap<Long, Book>();			
	}

}   
        

