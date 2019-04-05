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

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.topobyte.jsqltables.dialect.SqliteDialect;
import de.topobyte.jsqltables.table.QueryBuilder;
import de.topobyte.jsqltables.table.Table;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.overflow4j.model.User;

public class DatabaseCreator
{

	final static Logger logger = LoggerFactory.getLogger(DatabaseCreator.class);

	private IConnection connection;
	private Connection jdbcConnection;
	private Dao dao;

	public DatabaseCreator(IConnection connection, Connection jdbcConnection)
	{
		this.connection = connection;
		this.jdbcConnection = jdbcConnection;
		dao = new Dao(connection);
	}

	public void createTables()
	{
		QueryBuilder qb = new QueryBuilder(new SqliteDialect());

		String createUsers = qb.create(Tables.USERS, true);

		logger.info(createUsers);

		logger.info("creating tables");
		try {
			connection.execute(createUsers);

			jdbcConnection.commit();
		} catch (QueryException | SQLException e) {
			logger.error("error while creating tables", e);
			System.exit(1);
		}
	}

	private int n = 0;

	private void commit() throws SQLException
	{
		if (++n == 1000) {
			jdbcConnection.commit();
			n = 0;
		}
	}

	public void insertUser(User user) throws QueryException, SQLException
	{
		dao.insertUser(user);
		commit();
	}

	public void createIndexes() throws QueryException
	{
		logger.info("creating indexes");

		createIndex(Tables.USERS, "users_id", Tables.COLNAME_ID);
		createIndex(Tables.USERS, "users_location", Tables.COLNAME_LOCATION);
		createIndex(Tables.USERS, "users_reputation",
				Tables.COLNAME_REPUTATION);
	}

	private void createIndex(Table table, String name, String... columns)
			throws QueryException
	{
		String statement = table.createIndex(name, true, columns);
		connection.execute(statement);
	}

}
