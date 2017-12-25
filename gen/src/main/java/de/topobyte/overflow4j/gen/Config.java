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

package de.topobyte.overflow4j.gen;

public class Config
{

	static String packageName = "de.topobyte.overflow4j.model";

	static Spec users = new Spec(packageName, "user");

	static {
		users.defs.add(new Def("long", "id"));
		users.defs.add(new Def("int", "reputation"));
		users.defs.add(new Def("org.joda.time.DateTime", "creationDate"));
		users.defs.add(new Def("String", "displayName"));
		users.defs.add(new Def("org.joda.time.DateTime", "lastAccessDate"));
		users.defs.add(new Def("String", "websiteUrl"));
		users.defs.add(new Def("String", "location"));
		users.defs.add(new Def("int", "age"));
		users.defs.add(new Def("String", "aboutMe"));
		users.defs.add(new Def("int", "views"));
		users.defs.add(new Def("int", "upVotes"));
		users.defs.add(new Def("int", "downVotes"));
	}

}
