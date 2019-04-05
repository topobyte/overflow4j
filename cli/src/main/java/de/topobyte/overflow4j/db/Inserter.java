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

import de.topobyte.jsqltables.dialect.Dialect;
import de.topobyte.jsqltables.table.QueryBuilder;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.IPreparedStatement;
import de.topobyte.luqe.iface.IResultSet;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.overflow4j.model.User;

class Inserter
{

	private IConnection connection;
	private Dialect dialect;
	private QueryBuilder qb = new QueryBuilder(dialect);

	public Inserter(IConnection connection, Dialect dialect)
	{
		this.connection = connection;
		this.dialect = dialect;
	}

	private int INDEX_USERS_ID = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_ID);
	private int INDEX_USERS_REPUTATION = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_REPUTATION);
	private int INDEX_USERS_CREATION = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_CREATION);
	private int INDEX_USERS_DISPLAY_NAME = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_DISPLAY_NAME);
	private int INDEX_USERS_LAST_ACCESS = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_LAST_ACCESS);
	private int INDEX_USERS_WEBSITE = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_WEBSITE);
	private int INDEX_USERS_LOCATION = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_LOCATION);
	private int INDEX_USERS_AGE = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_AGE);
	private int INDEX_USERS_VIEWS = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_VIEWS);
	private int INDEX_USERS_UPVOTES = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_UPVOTES);
	private int INDEX_USERS_DOWNVOTES = Tables.USERS
			.getColumnIndexSafe(Tables.COLNAME_DOWNVOTES);

	private IPreparedStatement stmtInsertUser = null;

	public void insertUser(User user) throws QueryException
	{
		if (stmtInsertUser == null) {
			String insert = qb.insert(Tables.USERS);
			stmtInsertUser = connection.prepareStatement(insert);
		}
		stmtInsertUser.setLong(INDEX_USERS_ID, user.getId());
		stmtInsertUser.setInt(INDEX_USERS_REPUTATION, user.getReputation());
		stmtInsertUser.setLong(INDEX_USERS_CREATION,
				user.getCreationDate().getMillis());
		stmtInsertUser.setString(INDEX_USERS_DISPLAY_NAME,
				user.getDisplayName());
		stmtInsertUser.setLong(INDEX_USERS_LAST_ACCESS,
				user.getLastAccessDate().getMillis());
		stmtInsertUser.setString(INDEX_USERS_WEBSITE, user.getWebsiteUrl());
		stmtInsertUser.setString(INDEX_USERS_LOCATION, user.getLocation());
		stmtInsertUser.setInt(INDEX_USERS_AGE, user.getAge());
		stmtInsertUser.setInt(INDEX_USERS_VIEWS, user.getViews());
		stmtInsertUser.setInt(INDEX_USERS_UPVOTES, user.getUpVotes());
		stmtInsertUser.setInt(INDEX_USERS_DOWNVOTES, user.getDownVotes());

		IResultSet results = stmtInsertUser.executeQuery();
		results.close();
	}

}
