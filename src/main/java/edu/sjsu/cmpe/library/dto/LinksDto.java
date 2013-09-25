package edu.sjsu.cmpe.library.dto;

import java.util.ArrayList;

public class LinksDto 
{
    ArrayList<LinkDto> links;
    
    public LinksDto()
    {
    	links = new ArrayList<LinkDto>();
    }
    
    public ArrayList<LinkDto> getLinks() 
    {
	return links;
    }

    public void addLink(LinkDto link) 
    {
	links.add(link);
    }

    public LinksDto(ArrayList<LinkDto> links)
    {
    	this.links=links;
    }
    
    /**
     * @param links
     *            the links to set
     */
    public void setLinks(ArrayList<LinkDto> links)
    {
	this.links = links;
    }

}
