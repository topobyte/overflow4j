// Copyright 2017 Sebastian Kuerten
//
// This file is part of overflow4j.
//
// overflow4j is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// overflow4j is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with overflow4j. If not, see <http://www.gnu.org/licenses/>.

package de.topobyte.overflow4j.model;

import org.joda.time.DateTime;

public class User
{
	private long id;

	private int reputation;

	private DateTime creationDate;

	private String displayName;

	private DateTime lastAccessDate;

	private String websiteUrl;

	private String location;

	private int age;

	private String aboutMe;

	private int views;

	private int upVotes;

	private int downVotes;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getReputation()
	{
		return reputation;
	}

	public void setReputation(int reputation)
	{
		this.reputation = reputation;
	}

	public DateTime getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(DateTime creationDate)
	{
		this.creationDate = creationDate;
	}

	public String getDisplayName()
	{
		return displayName;
	}

	public void setDisplayName(String displayName)
	{
		this.displayName = displayName;
	}

	public DateTime getLastAccessDate()
	{
		return lastAccessDate;
	}

	public void setLastAccessDate(DateTime lastAccessDate)
	{
		this.lastAccessDate = lastAccessDate;
	}

	public String getWebsiteUrl()
	{
		return websiteUrl;
	}

	public void setWebsiteUrl(String websiteUrl)
	{
		this.websiteUrl = websiteUrl;
	}

	public String getLocation()
	{
		return location;
	}

	public void setLocation(String location)
	{
		this.location = location;
	}

	public int getAge()
	{
		return age;
	}

	public void setAge(int age)
	{
		this.age = age;
	}

	public String getAboutMe()
	{
		return aboutMe;
	}

	public void setAboutMe(String aboutMe)
	{
		this.aboutMe = aboutMe;
	}

	public int getViews()
	{
		return views;
	}

	public void setViews(int views)
	{
		this.views = views;
	}

	public int getUpVotes()
	{
		return upVotes;
	}

	public void setUpVotes(int upVotes)
	{
		this.upVotes = upVotes;
	}

	public int getDownVotes()
	{
		return downVotes;
	}

	public void setDownVotes(int downVotes)
	{
		this.downVotes = downVotes;
	}
}
