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

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

public class UtilGenerator
{

	private String packageName;
	private Path xmlSrc;

	private ClassName classUtil;

	private Builder builder;

	public UtilGenerator(String packageName, Path xml)
	{
		this.packageName = packageName;
		xmlSrc = xml.resolve("src/main/java");

		classUtil = ClassName.bestGuess(packageName + "." + "OverflowXml");

		builder = TypeSpec.classBuilder(classUtil)
				.addModifiers(Modifier.PUBLIC);

		init();
	}

	private void init()
	{
		// TODO: implement this
	}

	public void generate(TypeGenerator typeGen)
	{
		// TODO: implement this
	}

	public void finish() throws IOException
	{
		TypeSpec result = builder.build();

		JavaFile javaFile = JavaFile.builder(packageName, result).build();

		javaFile.writeTo(xmlSrc);
	}

}
