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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

import de.topobyte.system.utils.SystemPaths;

public class Generator
{

	public static void main(String[] args) throws IOException
	{
		generate(Config.users);
	}

	private static void generate(Spec spec) throws IOException
	{
		Path main = SystemPaths.CWD.getParent();
		Path core = main.resolve("core");
		Path xml = main.resolve("xml");
		Generator generator = new Generator(spec, core, xml);

		generator.generate();
	}

	private Spec spec;
	private Path core;
	private Path xml;
	private Path coreSrc;
	private Path xmlSrc;

	private String nameClassModel;
	private String nameClassReader;
	private String nameClassHandler;

	private ClassName classModel;
	private ClassName classReader;
	private ClassName classHandler;
	private ClassName classConsumer;

	private Path pathModel;
	private Path pathReader;
	private Path pathHandler;

	public Generator(Spec spec, Path core, Path xml)
	{
		this.spec = spec;
		this.core = core;
		this.xml = xml;
		coreSrc = core.resolve("src/main/java");
		xmlSrc = xml.resolve("src/main/java");

		nameClassModel = Defs.upperCamel(spec.name);
		nameClassReader = Defs.upperCamel(spec.name) + "Reader";
		nameClassHandler = Defs.upperCamel(spec.name) + "Handler";

		classModel = ClassName
				.bestGuess(spec.packageNameModel + "." + nameClassModel);
		classReader = ClassName
				.bestGuess(spec.packageNameParsing + "." + nameClassReader);
		classHandler = ClassName
				.bestGuess(spec.packageNameParsing + "." + nameClassHandler);
		classConsumer = classHandler.nestedClass("Consumer");

		pathModel = coreSrc.resolve(classFile(classModel));
		pathReader = xmlSrc.resolve(classFile(classReader));
		pathHandler = xmlSrc.resolve(classFile(classHandler));
	}

	private void generate() throws IOException
	{
		System.out.println("Generate: " + pathModel);
		generateModelClass();
		System.out.println("Generate: " + pathHandler);
		generateHandler();
		System.out.println("Generate: " + pathReader);
		generateReader();
	}

	private String classFile(ClassName clazz)
	{
		return clazz.reflectionName().replaceAll("\\.", "/") + ".java";
	}

	private void generateModelClass() throws IOException
	{
		Builder builder = TypeSpec.classBuilder(nameClassModel)
				.addModifiers(Modifier.PUBLIC);

		for (Def def : spec.defs) {
			FieldSpec field = Defs.field(def, Modifier.PRIVATE);
			builder.addField(field);
		}

		for (Def def : spec.defs) {
			MethodSpec getter = Defs.getter(def);
			builder.addMethod(getter);

			MethodSpec setter = Defs.setter(def);
			builder.addMethod(setter);
		}

		TypeSpec result = builder.build();

		JavaFile javaFile = JavaFile.builder(spec.packageNameModel, result)
				.build();

		javaFile.writeTo(coreSrc);
	}

	private void generateHandler() throws IOException
	{
		ClassName classDynamicSaxHandler = ClassName
				.bestGuess("de.topobyte.xml.dynsax.DynamicSaxHandler");
		ClassName classElement = ClassName
				.bestGuess("de.topobyte.xml.dynsax.Element");
		ClassName classData = ClassName
				.bestGuess("de.topobyte.xml.dynsax.Data");
		ClassName classConsumer = ClassName.bestGuess("Consumer");
		ClassName classChild = ClassName
				.bestGuess("de.topobyte.xml.dynsax.Child");
		ClassName classChildType = ClassName
				.bestGuess("de.topobyte.xml.dynsax.ChildType");

		Builder builder = TypeSpec.classBuilder(nameClassHandler)
				.addModifiers(Modifier.PUBLIC);

		builder.superclass(classDynamicSaxHandler);

		/*
		 * nested interface "Consumer"
		 */

		MethodSpec handle = MethodSpec.methodBuilder("handle")
				.addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
				.addParameter(classModel, spec.name).build();

		Builder consumer = TypeSpec.interfaceBuilder("Consumer")
				.addModifiers(Modifier.PUBLIC).addMethod(handle);
		builder.addType(consumer.build());

		/*
		 * ATTR_*
		 */

		for (Def def : spec.defs) {
			FieldSpec field = Defs.attr(def);
			builder.addField(field);
		}

		/*
		 * Element fields
		 */

		String nameRootElement = "e" + Defs.upperCamel(spec.rootElementName);
		String nameRowlement = "eRow";

		builder.addField(FieldSpec
				.builder(classElement, nameRootElement, Modifier.PRIVATE)
				.build());
		builder.addField(
				FieldSpec.builder(classElement, nameRowlement, Modifier.PRIVATE)
						.build());

		/*
		 * Consumer field
		 */

		builder.addField(FieldSpec
				.builder(classConsumer, "consumer", Modifier.PRIVATE).build());

		/*
		 * Constructor
		 */

		MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addParameter(classConsumer, "consumer");
		constructor.addStatement("this.consumer = consumer");
		constructor.addCode("\n");

		constructor.addStatement("$L = new Element($S, false)", nameRootElement,
				spec.rootElementName);
		constructor.addStatement("$L = new Element($S, false)", nameRowlement,
				"row");
		constructor.addCode("\n");

		constructor.addStatement("$L.addChild(new $T($L, $T.LIST, true))",
				nameRootElement, classChild, nameRowlement, classChildType);
		constructor.addCode("\n");

		for (Def def : spec.defs) {
			String attrName = Defs.attributeName(def);
			constructor.addStatement("$L.addAttribute($L)", nameRowlement,
					attrName);
		}
		constructor.addCode("\n");

		constructor.addStatement("setRoot($L, false)", nameRootElement);

		builder.addMethod(constructor.build());

		/*
		 * emit()
		 */

		MethodSpec.Builder emit = MethodSpec.methodBuilder("emit")
				.addModifiers(Modifier.PUBLIC).addParameter(classData, "data")
				.addAnnotation(Override.class);

		emit.beginControlFlow("if (data.getElement() == $L)", nameRowlement);
		for (Def def : spec.defs) {
			emit.addStatement("String $L = data.getAttribute($L)",
					def.getName(), Defs.attributeName(def));
		}
		emit.addCode("\n");

		String var = spec.name;
		emit.addStatement("$1T $2L = new $1T()", classModel, var);
		emit.addCode("\n");

		for (Def def : spec.defs) {
			if (def.isCheckNotNull()) {
				emit.beginControlFlow("if ($L != null)", def.getName());
			}
			switch (def.getType()) {
			case "String":
				emit.addStatement("$L.set$L($L)", var,
						Defs.upperCamel(def.getName()), def.getName());
				break;
			case "int":
				emit.addStatement("$L.set$L(Integer.parseInt($L))", var,
						Defs.upperCamel(def.getName()), def.getName());
				break;
			case "long":
				emit.addStatement("$L.set$L(Long.parseLong($L))", var,
						Defs.upperCamel(def.getName()), def.getName());
				break;
			case "org.joda.time.DateTime":
				emit.addStatement("$L.set$L(Parsers.parseDate($L))", var,
						Defs.upperCamel(def.getName()), def.getName());
				break;
			}
			if (def.isCheckNotNull()) {
				emit.endControlFlow();
			}
		}

		emit.addCode("\n");
		emit.addStatement("consumer.handle($L)", var);

		emit.endControlFlow();

		builder.addMethod(emit.build());

		/*
		 * 
		 */

		TypeSpec result = builder.build();

		JavaFile javaFile = JavaFile.builder(spec.packageNameParsing, result)
				.build();

		javaFile.writeTo(xmlSrc);
	}

	private void generateReader() throws IOException
	{
		Builder builder = TypeSpec.classBuilder(nameClassReader)
				.addModifiers(Modifier.PUBLIC);

		builder.addSuperinterface(Closeable.class);

		/*
		 * Fields
		 */

		FieldSpec is = FieldSpec
				.builder(InputStream.class, "is", Modifier.PRIVATE).build();
		builder.addField(is);

		/*
		 * Constructor
		 */

		MethodSpec.Builder constructor = MethodSpec.constructorBuilder()
				.addModifiers(Modifier.PUBLIC)
				.addParameter(InputStream.class, "is");
		constructor.addStatement("this.is = is");

		builder.addMethod(constructor.build());

		/*
		 * readAll()
		 */

		ParameterizedTypeName list = ParameterizedTypeName
				.get(ClassName.get(List.class), classModel);

		MethodSpec.Builder readAll = MethodSpec.methodBuilder("readAll")
				.addModifiers(Modifier.PUBLIC).returns(list)
				.addException(ParserConfigurationException.class)
				.addException(SAXException.class)
				.addException(IOException.class);

		readAll.addStatement("final $T results = new $T<>()", list,
				ArrayList.class);
		readAll.addCode("\n");

		String var = spec.name;

		TypeSpec comparator = TypeSpec.anonymousClassBuilder("")
				.addSuperinterface(classConsumer)
				.addMethod(MethodSpec.methodBuilder("handle")
						.addAnnotation(Override.class)
						.addModifiers(Modifier.PUBLIC)
						.addParameter(classModel, var).returns(void.class)
						.addStatement("results.add($L)", var).build())
				.build();

		readAll.addStatement("read($L)", comparator);
		readAll.addCode("\n");

		readAll.addStatement("return results");

		builder.addMethod(readAll.build());

		/*
		 * read()
		 */

		MethodSpec.Builder read = MethodSpec.methodBuilder("read")
				.addModifiers(Modifier.PUBLIC)
				.addParameter(classConsumer, "consumer")
				.addException(ParserConfigurationException.class)
				.addException(SAXException.class)
				.addException(IOException.class);

		read.addStatement("$T saxParser = $T.newInstance().newSAXParser()",
				SAXParser.class, SAXParserFactory.class);
		read.addCode("\n");

		read.addStatement("$1T handler = new $1T(consumer)", classHandler);
		read.addCode("\n");

		read.addStatement("saxParser.parse(is, handler)", comparator);

		builder.addMethod(read.build());

		/*
		 * close()
		 */

		builder.addMethod(MethodSpec.methodBuilder("close")
				.addModifiers(Modifier.PUBLIC).addAnnotation(Override.class)
				.addException(IOException.class).addStatement("is.close()")
				.build());

		/*
		 * 
		 */

		TypeSpec result = builder.build();

		JavaFile javaFile = JavaFile.builder(spec.packageNameParsing, result)
				.build();

		javaFile.writeTo(xmlSrc);
	}

}
