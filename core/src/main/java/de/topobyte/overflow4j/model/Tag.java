// Copyright 2017-2019 Sebastian Kuerten
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

public class Tag
{
	private int id;

	private String tagName;

	private int count;

	private int excerptPostId;

	private int wikiPostId;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getTagName()
	{
		return tagName;
	}

	public void setTagName(String tagName)
	{
		this.tagName = tagName;
	}

	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public int getExcerptPostId()
	{
		return excerptPostId;
	}

	public void setExcerptPostId(int excerptPostId)
	{
		this.excerptPostId = excerptPostId;
	}

	public int getWikiPostId()
	{
		return wikiPostId;
	}

	public void setWikiPostId(int wikiPostId)
	{
		this.wikiPostId = wikiPostId;
	}
}
