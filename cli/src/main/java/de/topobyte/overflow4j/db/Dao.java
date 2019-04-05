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
import de.topobyte.jsqltables.dialect.SqliteDialect;
import de.topobyte.luqe.iface.IConnection;
import de.topobyte.luqe.iface.QueryException;
import de.topobyte.overflow4j.model.User;

public class Dao
{

	private Dialect dialect = new SqliteDialect();

	private Inserter inserter;

	public Dao(IConnection connection)
	{
		inserter = new Inserter(connection, dialect);
	}

	public Dialect getDialect()
	{
		return dialect;
	}

	public void insertUser(User user) throws QueryException
	{
		inserter.insertUser(user);
	}

}
