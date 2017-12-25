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

import java.util.HashMap;
import java.util.Map;

import javax.lang.model.element.Modifier;

import com.google.common.base.CaseFormat;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.MethodSpec.Builder;
import com.squareup.javapoet.TypeName;

public class Defs
{

	private static Map<String, TypeName> primitives = new HashMap<>();
	static {
		for (TypeName type : new TypeName[] { TypeName.BOOLEAN, TypeName.BYTE,
				TypeName.SHORT, TypeName.INT, TypeName.LONG, TypeName.CHAR,
				TypeName.FLOAT, TypeName.DOUBLE }) {
			primitives.put(type.toString(), type);
		}

	}

	static String upperCamel(String lowerCamel)
	{
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL, lowerCamel);
	}

	private static TypeName typeName(Def def)
	{
		String type = def.getType();
		if (primitives.containsKey(type)) {
			return primitives.get(type);
		}

		return ClassName.bestGuess(type);
	}

	public static FieldSpec field(Def def, Modifier... modifiers)
	{
		return FieldSpec.builder(typeName(def), def.getName())
				.addModifiers(modifiers).build();
	}

	public static MethodSpec getter(Def def)
	{
		Builder builder = MethodSpec
				.methodBuilder("get" + upperCamel(def.getName()));
		builder.addModifiers(Modifier.PUBLIC);
		builder.returns(typeName(def));
		builder.addStatement("return " + def.getName());
		return builder.build();
	}

	public static MethodSpec setter(Def def)
	{
		Builder builder = MethodSpec
				.methodBuilder("set" + upperCamel(def.getName()));
		builder.addModifiers(Modifier.PUBLIC);
		builder.addParameter(typeName(def), def.getName());
		builder.returns(TypeName.VOID);
		builder.addStatement(
				String.format("this.%s = %s", def.getName(), def.getName()));
		return builder.build();
	}

}
