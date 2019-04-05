// Copyright 2019 Sebastian Kuerten
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

package de.topobyte.overflow4j.cli;

import java.io.IOException;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import de.topobyte.luqe.iface.QueryException;
import de.topobyte.overflow4j.db.DatabaseCreator;
import de.topobyte.overflow4j.db.DatabaseTask;
import de.topobyte.overflow4j.model.User;
import de.topobyte.overflow4j.xml.OverflowXml;
import de.topobyte.overflow4j.xml.UserHandler;

public class BuildUserDatabase extends DatabaseTask
{

	final static Logger logger = LoggerFactory
			.getLogger(BuildUserDatabase.class);

	private Path pathInput;
	private Path pathDatabase;

	private DatabaseCreator creator;

	public BuildUserDatabase(Path pathInput, Path pathDatabase)
	{
		super(pathDatabase);
		this.pathInput = pathInput;
		this.pathDatabase = pathDatabase;
	}

	public void execute() throws IOException, ParserConfigurationException,
			SAXException, QueryException
	{
		logger.info("Creating database at: " + pathDatabase);

		setupConnection();

		logger.info("creating tables");
		creator = new DatabaseCreator(connection, jdbcConnection);
		creator.createTables();

		logger.info("inserting data");
		insertData();

		logger.info("creating indexes");

		creator.createIndexes();

		closeConnection(true);
	}

	private void insertData()
			throws IOException, ParserConfigurationException, SAXException
	{
		OverflowXml.readUsers(pathInput, new UserHandler.Consumer() {

			@Override
			public void handle(User user)
			{
				try {
					creator.insertUser(user);
				} catch (QueryException | SQLException e) {
					logger.error("Error while inserting user", e);
				}
			}

		});
	}

}
