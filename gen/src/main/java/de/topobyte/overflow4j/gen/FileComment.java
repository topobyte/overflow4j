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

package de.topobyte.overflow4j.gen;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Joiner;
import com.squareup.javapoet.JavaFile.Builder;

public class FileComment
{

	private static List<String> lines = new ArrayList<>();
	private static String text;

	static {
		lines.add("Copyright $L Sebastian Kuerten\n");
		lines.add("\n");
		lines.add("This file is part of overflow4j.\n");
		lines.add("\n");
		lines.add(
				"overflow4j is free software: you can redistribute it and/or modify\n");
		lines.add(
				"it under the terms of the GNU Lesser General Public License as published by\n");
		lines.add(
				"the Free Software Foundation, either version 3 of the License, or\n");
		lines.add("(at your option) any later version.\n");
		lines.add("\n");
		lines.add(
				"overflow4j is distributed in the hope that it will be useful,\n");
		lines.add(
				"but WITHOUT ANY WARRANTY; without even the implied warranty of\n");
		lines.add(
				"MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the\n");
		lines.add("GNU Lesser General Public License for more details.\n");
		lines.add("\n");
		lines.add(
				"You should have received a copy of the GNU Lesser General Public License\n");
		lines.add(
				"along with overflow4j. If not, see <http://www.gnu.org/licenses/>.");
	}

	static {
		text = Joiner.on("").join(lines);
	}

	public static String getCommentPattern()
	{
		return text;
	}

	public static void addFileComment(Builder javaFileBuiler)
	{
		javaFileBuiler.addFileComment(FileComment.getCommentPattern(),
				"2017-2019");
	}

}
