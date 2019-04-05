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

package de.topobyte.overflow4j.db;

import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.jdbc.JdbcConnection;

public class DatabaseTask
{

	final static Logger logger = LoggerFactory.getLogger(DatabaseTask.class);

	protected Path pathDatabase;

	protected Connection jdbcConnection = null;
	protected IConnection connection = null;

	public DatabaseTask(Path pathDatabase)
	{
		this.pathDatabase = pathDatabase;
	}

	protected void setupConnection()
	{
		logger.debug("configuring output connection");
		try {
			jdbcConnection = DriverManager
					.getConnection("jdbc:sqlite:" + pathDatabase);
			jdbcConnection.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("unable to create jdbc connection", e);
			System.exit(1);
		}
		try {
			connection = new JdbcConnection(jdbcConnection);
		} catch (SQLException e) {
			logger.error("unable to create jdbc connection", e);
			System.exit(1);
		}
	}

	protected void closeConnection(boolean commit)
	{
		logger.info("closing connection");
		try {
			if (commit) {
				jdbcConnection.commit();
			}
			jdbcConnection.close();
		} catch (SQLException e) {
			logger.error("error while closing connection", e);
			System.exit(1);
		}
	}

}
