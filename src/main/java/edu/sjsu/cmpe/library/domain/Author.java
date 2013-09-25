package edu.sjsu.cmpe.library.domain;

import org.hibernate.validator.constraints.NotEmpty;

public class Author
{
	@NotEmpty
	public String name;
	private int id;
	
	public String getname()
	{
		return this.name;
	}
	public void setname(String name)
	{
		this.name = name;
	}
	public int getId()
	{
		return this.id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
}