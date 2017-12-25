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

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.topobyte.melon.io.StreamUtil;
import de.topobyte.overflow4j.model.User;

public class OverflowXml
{

	private static InputStream reader(Path path) throws IOException
	{
		return StreamUtil.bufferedInputStream(path);
	}

	public static UsersReader createAgencyReader(Path path) throws IOException
	{
		InputStream is = reader(path);

		UsersReader reader = new UsersReader(is);
		return reader;
	}

	public static List<User> readUsers(Path path)
			throws IOException, ParserConfigurationException, SAXException
	{
		InputStream is = reader(path);

		UsersReader reader = new UsersReader(is);
		List<User> list = reader.readAll();
		reader.close();
		return list;
	}

	public static void readUsers(Path path, UserHandler.Consumer consumer)
			throws IOException, ParserConfigurationException, SAXException
	{
		InputStream is = reader(path);

		UsersReader reader = new UsersReader(is);
		reader.read(consumer);
		reader.close();
	}

}
