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

import java.io.IOException;
import java.nio.file.Path;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import de.topobyte.overflow4j.model.User;
import de.topobyte.overflow4j.xml.OverflowXml;
import de.topobyte.overflow4j.xml.UserHandler;

public class ShowUsers
{

	private Path pathInput;

	public ShowUsers(Path pathInput)
	{
		this.pathInput = pathInput;
	}

	public void execute()
			throws IOException, ParserConfigurationException, SAXException
	{
		OverflowXml.readUsers(pathInput, new UserHandler.Consumer() {

			@Override
			public void handle(User user)
			{
				StringBuilder strb = new StringBuilder();
				strb.append(String.format("%8d: %6d rep, %s", user.getId(),
						user.getReputation(), user.getDisplayName()));
				if (user.getLocation() != null
						&& !user.getLocation().isEmpty()) {
					strb.append(" (");
					strb.append(user.getLocation());
					strb.append(")");
				}
				System.out.println(strb.toString());
			}

		});
	}

}
