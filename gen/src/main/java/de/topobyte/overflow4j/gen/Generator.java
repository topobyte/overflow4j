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
		Builder builder = TypeSpec.classBuilder(className)
				.addModifiers(Modifier.PUBLIC);

		// TODO: implement this

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
