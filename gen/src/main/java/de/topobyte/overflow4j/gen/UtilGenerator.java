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
import java.io.InputStream;
import java.nio.file.Path;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
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
		ClassName classStreamUtil = ClassName
				.bestGuess("de.topobyte.melon.io.StreamUtil");

		builder.addMethod(MethodSpec.methodBuilder("reader")
				.addException(IOException.class).returns(InputStream.class)
				.addModifiers(Modifier.PRIVATE).addModifiers(Modifier.STATIC)
				.addParameter(Path.class, "path")
				.addStatement("return $T.bufferedInputStream(path)",
						classStreamUtil)
				.build());
	}

	public void generate(TypeGenerator typeGen, Spec spec)
	{
		String single = Defs.upperCamel(spec.name);
		String multiple = Defs.upperCamel(spec.multiple);

		MethodSpec.Builder createReader = MethodSpec
				.methodBuilder("create" + single + "Reader")
				.addException(IOException.class)
				.returns(typeGen.getClassReader()).addModifiers(Modifier.PUBLIC)
				.addModifiers(Modifier.STATIC).addParameter(Path.class, "path");

		createReader.addStatement("$T is = reader(path)", InputStream.class);
		createReader.addCode("\n");
		createReader.addStatement("$1T reader = new $1T(is)",
				typeGen.getClassReader());
		createReader.addStatement("return reader");

		ParameterizedTypeName list = ParameterizedTypeName
				.get(ClassName.get(List.class), typeGen.getClassModel());

		MethodSpec.Builder read1 = MethodSpec.methodBuilder("read" + multiple)
				.addException(IOException.class)
				.addException(ParserConfigurationException.class)
				.addException(SAXException.class).returns(list)
				.addModifiers(Modifier.PUBLIC).addModifiers(Modifier.STATIC)
				.addParameter(Path.class, "path");

		read1.addStatement("$T is = reader(path)", InputStream.class);
		read1.addCode("\n");
		read1.addStatement("$1T reader = new $1T(is)",
				typeGen.getClassReader());
		read1.addStatement("$T list = reader.readAll()", list);
		read1.addStatement("reader.close()");
		read1.addStatement("return list");

		MethodSpec.Builder read2 = MethodSpec.methodBuilder("read" + multiple)
				.addException(IOException.class)
				.addException(ParserConfigurationException.class)
				.addException(SAXException.class).returns(void.class)
				.addModifiers(Modifier.PUBLIC).addModifiers(Modifier.STATIC)
				.addParameter(Path.class, "path")
				.addParameter(typeGen.getClassConsumer(), "consumer");

		read2.addStatement("$T is = reader(path)", InputStream.class);
		read2.addCode("\n");
		read2.addStatement("$1T reader = new $1T(is)",
				typeGen.getClassReader());
		read2.addStatement("reader.read(consumer)");
		read2.addStatement("reader.close()");

		builder.addMethod(createReader.build());
		builder.addMethod(read1.build());
		builder.addMethod(read2.build());
	}

	public void finish() throws IOException
	{
		TypeSpec result = builder.build();

		JavaFile javaFile = JavaFile.builder(packageName, result).build();

		javaFile.writeTo(xmlSrc);
	}

}
