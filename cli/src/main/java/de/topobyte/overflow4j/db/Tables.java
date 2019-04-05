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

import de.topobyte.jsqltables.table.ColumnClass;
import de.topobyte.jsqltables.table.Table;

public class Tables
{

	public static Table USERS = new Table("users");

	public static String COLNAME_ID = "id";
	public static String COLNAME_REPUTATION = "reputation";
	public static String COLNAME_CREATION = "creation";
	public static String COLNAME_DISPLAY_NAME = "name";
	public static String COLNAME_LAST_ACCESS = "last_access";
	public static String COLNAME_WEBSITE = "website";
	public static String COLNAME_LOCATION = "location";
	public static String COLNAME_AGE = "age";
	// public static String COLNAME_ABOUT_ME = "about-me";
	public static String COLNAME_VIEWS = "views";
	public static String COLNAME_UPVOTES = "upvotes";
	public static String COLNAME_DOWNVOTES = "downvotes";

	static {
		USERS.addColumn(ColumnClass.LONG, COLNAME_ID);
		USERS.addColumn(ColumnClass.INT, COLNAME_REPUTATION);
		USERS.addColumn(ColumnClass.LONG, COLNAME_CREATION);
		USERS.addColumn(ColumnClass.VARCHAR, COLNAME_DISPLAY_NAME);
		USERS.addColumn(ColumnClass.LONG, COLNAME_LAST_ACCESS);
		USERS.addColumn(ColumnClass.VARCHAR, COLNAME_WEBSITE);
		USERS.addColumn(ColumnClass.VARCHAR, COLNAME_LOCATION);
		USERS.addColumn(ColumnClass.INT, COLNAME_AGE);
		USERS.addColumn(ColumnClass.INT, COLNAME_VIEWS);
		USERS.addColumn(ColumnClass.INT, COLNAME_UPVOTES);
		USERS.addColumn(ColumnClass.INT, COLNAME_DOWNVOTES);
	}

}
