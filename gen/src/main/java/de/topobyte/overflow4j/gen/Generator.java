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

import java.io.IOException;
import java.nio.file.Path;

import de.topobyte.system.utils.SystemPaths;

public class Generator
{

	public static void main(String[] args) throws IOException
	{
		Path main = SystemPaths.CWD.getParent();
		Path core = main.resolve("core");
		Path xml = main.resolve("xml");

		TypeGenerator genUsers = new TypeGenerator(Config.users, core, xml);
		TypeGenerator genTags = new TypeGenerator(Config.tags, core, xml);

		UtilGenerator utilGenerator = new UtilGenerator(
				Config.packageNameParsing, xml);
		utilGenerator.generate(genUsers);
		utilGenerator.finish();

		genUsers.generate();
		genTags.generate();
	}

}
