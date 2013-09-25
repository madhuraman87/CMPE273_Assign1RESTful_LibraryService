package edu.sjsu.cmpe.library.domain;

import java.util.ArrayList;
import edu.sjsu.cmpe.library.dto.LinkDto;
import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"isbn","title", "publication-date", "language","num-pages","status" })
public class Book 
{
    private long isbn;
    @NotEmpty
    private String title;
    private String language;
    @NotEmpty
    @JsonProperty("publication-date") 
    private String publicationdate;
    @JsonProperty("num-pages")
    private String numpages;
    @Pattern(regexp="available|not-available|lost")
    @JsonProperty("status")
    private String status;
    @JsonProperty("links")
    private ArrayList<LinkDto> links;
    @JsonProperty("authors")
    private ArrayList<LinkDto> author_links;
    @JsonProperty("reviews")
    private ArrayList<LinkDto> review_links;
	@Valid
    private ArrayList<Author> authors;
	private ArrayList<Review> reviews;
	private static long isbnkey = 0;
    
	public Book() 
    {
    	authors = new ArrayList<Author>();	
    	reviews = new ArrayList<Review>();
    	author_links = new ArrayList<LinkDto>();
        review_links = new ArrayList<LinkDto>();
        links = new ArrayList<LinkDto>();
    	status = "available";
    }
    public long getIsbn() 
    {
    	return isbn;
    }
    
    public void setIsbn(long isbn)
    {
    	this.isbn = isbn;
    }
    
    public String getTitle()
    {
    	return title;
    }
    
    public void setTitle(String title) 
    {
    	this.title = title;
    }
    
    public String getLanguage()
    {
    	return language;
    }
    
    public void setLanguage(String language)
    {
    	this.language = language;
    }
    
    public String getpublicationdate()
    {
    	return publicationdate; 	
    }
    
    public void setpublicationdate(String publicationdate)
    {
    	this.publicationdate = publicationdate;
    }
    
    public String getnumpages()
    {
    	return numpages;
    }
    
    public void setnumpages(String numpages)
    {
    	this.numpages = numpages;
    }
    
    public String getstatus()
    {
    	return status;
    }
    
    public void setstatus(String status)
    {
    	this.status = status;
    }
    
    public int addBookReview(Review review) 
    {
    	if (reviews.size() == 0)
    	{
    		links.add(new LinkDto("view-all-reviews","/books/"+isbn+"/reviews","GET"));
    	}
    	review.setId(reviews.size() + 1);
    	reviews.add(review);
    	review_links.add(new LinkDto("view-review", "/books/" + isbn + "/reviews/" + reviews.size(), "GET"));
    	return reviews.size();
    }
    
    public Review getReviews(int i)
    {
    	return reviews.get(i);
    }
    
    public ArrayList<Review> getallReviews()
    {
    	return reviews;
    }

    public Author getanAuthor(int i) 
    {
    	return authors.get(i);
    }
    
    public ArrayList<Author> getallAuthors() 
    {
    	return authors;
    }
    
    public ArrayList<LinkDto> getReviews() 
    {
    	return review_links;
    }
    
    public void setReviews(Review[] reviews) 
    {
    	return;
    }
    
    public ArrayList<LinkDto> getAuthors() 
    {
    	return author_links;
    }
    
    public ArrayList<LinkDto> genLinks() 
    {
    	return links;
    }
    
    public void setAuthors(Author[] authors)
    {
    	for (int i = 0; i < authors.length; i++) 
    	{
    		authors[i].setId(i + 1);
			this.authors.add(authors[i]);
		}
    }

    public static long generateISBNKey()
    {
    	return ++isbnkey;
    }
    
    public void fetchLinks() 
    {
    	links.add(new LinkDto("view-book","/books/"+isbn,"GET"));
    	links.add(new LinkDto("update-book","/books/"+isbn,"PUT"));
    	links.add(new LinkDto("delete-book","/books/"+isbn,"DELETE"));
    	links.add(new LinkDto("create-book","/books/"+isbn,"POST"));
    	for (int i = 0; i < authors.size(); i++)
    	{
    		Author author = authors.get(i);
    		author_links.add(new LinkDto("view-author", "/books/" + isbn + "/authors/" + author.getId(), "GET"));
    	}
    }
}
