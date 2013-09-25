package edu.sjsu.cmpe.library.domain;

import org.hibernate.validator.constraints.NotEmpty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@JsonPropertyOrder({"id","rating","comment"})
public class Review
{
	@Max(5)
	@Min(1)
	private int rating;
	@NotEmpty
	private String comment;
	private int id;
	
	public String getComment()
	{
		return this.comment;
	}
	public void setComment(String comment)
	{
		this.comment = comment;
	}
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public int getRating()
	{
		return this.rating;
	}
	public void setRating(int rating)
	{
		this.rating = rating;
	}
}