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

import de.topobyte.utilities.apache.commons.cli.commands.ArgumentParser;
import de.topobyte.utilities.apache.commons.cli.commands.ExeRunner;
import de.topobyte.utilities.apache.commons.cli.commands.ExecutionData;
import de.topobyte.utilities.apache.commons.cli.commands.RunnerException;
import de.topobyte.utilities.apache.commons.cli.commands.options.DelegateExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptions;
import de.topobyte.utilities.apache.commons.cli.commands.options.ExeOptionsFactory;

public class Overflow4jCli
{

	public static ExeOptionsFactory OPTIONS_FACTORY = new ExeOptionsFactory() {

		@Override
		public ExeOptions createOptions()
		{
			DelegateExeOptions options = new DelegateExeOptions();
			options.addCommand("show-users", RunShowUsers.OPTIONS_FACTORY,
					RunShowUsers.class);
			options.addCommand("show-tags", RunShowTags.OPTIONS_FACTORY,
					RunShowTags.class);
			return options;
		}

	};

	public static void main(String[] args) throws RunnerException
	{
		String name = "overflow4j";

		ExeOptions options = OPTIONS_FACTORY.createOptions();
		ArgumentParser parser = new ArgumentParser(name, options);

		if (args.length == 0) {
			System.out.println("overflow4j Command Line Interface");
			System.out.println();
			options.usage(name);
			System.exit(1);
		}

		ExecutionData data = parser.parse(args);
		if (data != null) {
			ExeRunner.run(data);
		}
	}

}
