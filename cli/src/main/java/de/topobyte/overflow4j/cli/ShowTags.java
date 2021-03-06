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

import de.topobyte.overflow4j.model.Tag;
import de.topobyte.overflow4j.xml.OverflowXml;
import de.topobyte.overflow4j.xml.TagHandler;

public class ShowTags
{

	private Path pathInput;

	public ShowTags(Path pathInput)
	{
		this.pathInput = pathInput;
	}

	public void execute()
			throws IOException, ParserConfigurationException, SAXException
	{
		OverflowXml.readTags(pathInput, new TagHandler.Consumer() {

			@Override
			public void handle(Tag tag)
			{
				System.out.println(String.format("%8d: %6d count, %s",
						tag.getId(), tag.getCount(), tag.getTagName()));
			}

		});
	}

}
