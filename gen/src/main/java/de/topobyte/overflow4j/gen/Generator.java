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

import javax.lang.model.element.Modifier;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;

public class Generator
{

	public static void main(String[] args) throws IOException
	{
		generate(Config.users);
	}

	private static void generate(Spec spec) throws IOException
	{
		generateModelClass(spec);
		generateHandler(spec);
		generateReader(spec);
	}

	private static void generateModelClass(Spec spec) throws IOException
	{
		String className = Defs.upperCamel(spec.name);
		Builder builder = TypeSpec.classBuilder(className)
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

		javaFile.writeTo(System.out);
	}

	private static void generateHandler(Spec spec) throws IOException
	{
		String className = Defs.upperCamel(spec.name) + "Handler";
		String modelClassName = Defs.upperCamel(spec.name);

		ClassName classModelClassName = ClassName
				.bestGuess(spec.packageNameModel + "." + modelClassName);

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

		Builder builder = TypeSpec.classBuilder(className)
				.addModifiers(Modifier.PUBLIC);

		builder.superclass(classDynamicSaxHandler);

		/*
		 * nested interface "Consumer"
		 */

		MethodSpec handle = MethodSpec.methodBuilder("handle")
				.addModifiers(Modifier.ABSTRACT, Modifier.PUBLIC)
				.addParameter(classModelClassName, spec.name).build();

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
		emit.addStatement("$1T $2L = new $1T()", classModelClassName, var);
		emit.addCode("\n");

		for (Def def : spec.defs) {
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

		javaFile.writeTo(System.out);
	}

	private static void generateReader(Spec spec) throws IOException
	{
		String className = Defs.upperCamel(spec.name) + "Reader";
		Builder builder = TypeSpec.classBuilder(className)
				.addModifiers(Modifier.PUBLIC);

		// TODO: implement this

		TypeSpec result = builder.build();

		JavaFile javaFile = JavaFile.builder(spec.packageNameParsing, result)
				.build();

		javaFile.writeTo(System.out);
	}

}
