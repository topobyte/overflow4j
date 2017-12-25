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
