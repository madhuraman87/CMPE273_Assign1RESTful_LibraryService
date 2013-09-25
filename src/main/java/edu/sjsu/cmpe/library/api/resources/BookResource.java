package edu.sjsu.cmpe.library.api.resources;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.yammer.metrics.annotation.Timed;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Optional;

import edu.sjsu.cmpe.library.domain.Review;
import edu.sjsu.cmpe.library.domain.Book;
import edu.sjsu.cmpe.library.dto.*;

import java.util.ArrayList;



@Path("/books")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BookResource 
{
	@POST
    @Timed(name = "create-book")
    public Response createBook(@Valid Book book) 
    {
    	Long isbn = Book.generateISBNKey(); 
    	book.setIsbn(isbn);
    	book.fetchLinks();
    	BookRepository.bookInMemoryMap.put(isbn, book);
		return Response.status(201).entity(new LinksDto(book.genLinks())).build();
    }	
	
	@GET
    @Path("/{isbn}")
    @Timed(name = "view-book")
    public Response viewBook(@PathParam("isbn") long isbn) 
    {
	    return Response.status(200).entity(BookRepository.bookInMemoryMap.get(isbn)).build();
    }

    @DELETE
	@Path("/{isbn}")
	public Response deleteBook(@PathParam("isbn") long isbn)
    {
    	BookRepository.bookInMemoryMap.remove(isbn);
    	LinksDto links = new LinksDto();
		links.addLink(new LinkDto("create-book","/books","POST"));
		return Response.status(200).entity(links).build();
	}
    
    @PUT
	@Path("/{isbn}")
	public Response updateBook(@PathParam("isbn") long isbn,@QueryParam("status") Optional<String> status) 
    {		
		Book book = BookRepository.bookInMemoryMap.get(isbn);		
		if (!status.or("").equalsIgnoreCase("")) 
		{
			book.setstatus(status.or(""));
		}
		
		return Response.status(200).entity(new LinksDto(book.genLinks())).build();
	}
    
    @POST
	@Path("/{isbn}/reviews")
	public Response createReview(@PathParam("isbn") long isbn,@Valid Review review)
    {
    	Book book = BookRepository.bookInMemoryMap.get(isbn);	
		if (!BookRepository.bookInMemoryMap.containsKey(isbn))
			return null;
		int id = book.addBookReview(review);
		//Response Body
		LinksDto links = new LinksDto();	
		links.addLink(new LinkDto("view-reviews","/books/"+isbn+"/reviews/"+id,"GET"));
		return Response.status(201).entity(links).build();
	}
	
    @GET
    @Path("/{isbn}/reviews/{id}")
	public Response viewBookReview(@PathParam("isbn") long isbn,@PathParam("id") int id)
    {
		long key = isbn;
		Book book = BookRepository.bookInMemoryMap.get(key);
		ArrayList<LinkDto> links = new ArrayList<LinkDto>();
		links.add(new LinkDto("view-review", "/books/" + key + "/reviews/" + id, "GET"));
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		JsonNode node1 = mapper.convertValue(book.getReviews(id - 1),JsonNode.class);
		JsonNode node2 = mapper.convertValue(links, JsonNode.class);
		node.put("reviews", node1);
		node.put("links", node2);
        return Response.status(200).entity(node).build();
	}
    
    @GET
    @Path("/{isbn}/reviews")
	public Response viewAllReviews(@PathParam("isbn") long isbn)
    {
		long key = isbn;
		Book book = BookRepository.bookInMemoryMap.get(key);		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		JsonNode node1 = mapper.convertValue(book.getallReviews(),JsonNode.class);
		JsonNode node2 = mapper.convertValue(new ArrayList<String>(), JsonNode.class);
		node.put("reviews", node1);
		node.put("links", node2);
        return Response.status(200).entity(node).build();
	}
    
    @GET
    @Path("/{isbn}/authors/{id}")
	public Response viewABookAuthor(@PathParam("isbn") long isbn,@PathParam("id") int id)
    {
		long key = isbn;
		Book book = BookRepository.bookInMemoryMap.get(key);		
		ArrayList<LinkDto> links = new ArrayList<LinkDto>();
		links.add(new LinkDto("view-author", "/books/" + key + "/authors/" + id, "GET"));		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		JsonNode node1 = mapper.convertValue(book.getanAuthor(id - 1),JsonNode.class);
		JsonNode node2 = mapper.convertValue(links, JsonNode.class);
		node.put("authors", node1);
		node.put("links", node2);
        return Response.status(200).entity(node).build();
	}
    
    @GET
    @Path("/{isbn}/authors/")
	public Response viewAllBookAuthors(@PathParam("isbn") long isbn)
    {
		long key = isbn;
		Book book = BookRepository.bookInMemoryMap.get(key);		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode node = mapper.createObjectNode();
		JsonNode node1 = mapper.convertValue(book.getallAuthors(),JsonNode.class);
		JsonNode node2 = mapper.convertValue(new ArrayList<String>(), JsonNode.class);
		node.put("authors", node1);
		node.put("links", node2);
        return Response.status(200).entity(node).build();
	}
}
