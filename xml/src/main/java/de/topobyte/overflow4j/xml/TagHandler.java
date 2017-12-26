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

package de.topobyte.overflow4j.xml;

import de.topobyte.overflow4j.model.Tag;
import de.topobyte.xml.dynsax.Child;
import de.topobyte.xml.dynsax.ChildType;
import de.topobyte.xml.dynsax.Data;
import de.topobyte.xml.dynsax.DynamicSaxHandler;
import de.topobyte.xml.dynsax.Element;

public class TagHandler extends DynamicSaxHandler
{
	private static final String ATTR_ID = "Id";

	private static final String ATTR_TAG_NAME = "TagName";

	private static final String ATTR_COUNT = "Count";

	private static final String ATTR_EXCERPT_POST_ID = "ExcerptPostId";

	private static final String ATTR_WIKI_POST_ID = "WikiPostId";

	private Element eTags;

	private Element eRow;

	private Consumer consumer;

	public TagHandler(Consumer consumer)
	{
		this.consumer = consumer;

		eTags = new Element("tags", false);
		eRow = new Element("row", false);

		eTags.addChild(new Child(eRow, ChildType.LIST, true));

		eRow.addAttribute(ATTR_ID);
		eRow.addAttribute(ATTR_TAG_NAME);
		eRow.addAttribute(ATTR_COUNT);
		eRow.addAttribute(ATTR_EXCERPT_POST_ID);
		eRow.addAttribute(ATTR_WIKI_POST_ID);

		setRoot(eTags, false);
	}

	@Override
	public void emit(Data data)
	{
		if (data.getElement() == eRow) {
			String id = data.getAttribute(ATTR_ID);
			String tagName = data.getAttribute(ATTR_TAG_NAME);
			String count = data.getAttribute(ATTR_COUNT);
			String excerptPostId = data.getAttribute(ATTR_EXCERPT_POST_ID);
			String wikiPostId = data.getAttribute(ATTR_WIKI_POST_ID);

			Tag tag = new Tag();

			tag.setId(Integer.parseInt(id));
			tag.setTagName(tagName);
			tag.setCount(Integer.parseInt(count));
			if (excerptPostId != null) {
				tag.setExcerptPostId(Integer.parseInt(excerptPostId));
			}
			if (wikiPostId != null) {
				tag.setWikiPostId(Integer.parseInt(wikiPostId));
			}

			consumer.handle(tag);
		}
	}

	public interface Consumer
	{
		void handle(Tag tag);
	}
}
