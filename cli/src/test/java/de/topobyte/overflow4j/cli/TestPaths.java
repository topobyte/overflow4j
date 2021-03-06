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

package de.topobyte.overflow4j.cli;

import java.nio.file.Path;
import java.nio.file.Paths;

public class TestPaths
{

	public static Path pathInput = Paths.get("/tmp/stackoverflow");

	public static Path pathInputUsers = pathInput.resolve("Users.xml");
	public static Path pathInputTags = pathInput.resolve("Tags.xml");

	public static Path pathUserDatabase = pathInput.resolve("users.sqlite");

}
