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

package de.topobyte.overflow4j.xml;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import de.topobyte.overflow4j.model.User;

public class UsersReader implements Closeable
{

	private InputStream is;

	public UsersReader(InputStream is)
	{
		this.is = is;
	}

	public List<User> readAll()
			throws ParserConfigurationException, SAXException, IOException
	{
		SAXParser saxParser = SAXParserFactory.newInstance().newSAXParser();

		UserHandler handler = new UserHandler();

		saxParser.parse(is, handler);

		return handler.getUsers();
	}

	@Override
	public void close() throws IOException
	{
		is.close();
	}

}
