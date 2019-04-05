// Copyright 2017-2019 Sebastian Kuerten
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

import de.topobyte.overflow4j.model.User;
import de.topobyte.xml.dynsax.Child;
import de.topobyte.xml.dynsax.ChildType;
import de.topobyte.xml.dynsax.Data;
import de.topobyte.xml.dynsax.DynamicSaxHandler;
import de.topobyte.xml.dynsax.Element;

public class UserHandler extends DynamicSaxHandler
{
	private static final String ATTR_ID = "Id";

	private static final String ATTR_REPUTATION = "Reputation";

	private static final String ATTR_CREATION_DATE = "CreationDate";

	private static final String ATTR_DISPLAY_NAME = "DisplayName";

	private static final String ATTR_LAST_ACCESS_DATE = "LastAccessDate";

	private static final String ATTR_WEBSITE_URL = "WebsiteUrl";

	private static final String ATTR_LOCATION = "Location";

	private static final String ATTR_AGE = "Age";

	private static final String ATTR_ABOUT_ME = "AboutMe";

	private static final String ATTR_VIEWS = "Views";

	private static final String ATTR_UP_VOTES = "UpVotes";

	private static final String ATTR_DOWN_VOTES = "DownVotes";

	private Element eUsers;

	private Element eRow;

	private Consumer consumer;

	public UserHandler(Consumer consumer)
	{
		this.consumer = consumer;

		eUsers = new Element("users", false);
		eRow = new Element("row", false);

		eUsers.addChild(new Child(eRow, ChildType.IGNORE, true));

		eRow.addAttribute(ATTR_ID);
		eRow.addAttribute(ATTR_REPUTATION);
		eRow.addAttribute(ATTR_CREATION_DATE);
		eRow.addAttribute(ATTR_DISPLAY_NAME);
		eRow.addAttribute(ATTR_LAST_ACCESS_DATE);
		eRow.addAttribute(ATTR_WEBSITE_URL);
		eRow.addAttribute(ATTR_LOCATION);
		eRow.addAttribute(ATTR_AGE);
		eRow.addAttribute(ATTR_ABOUT_ME);
		eRow.addAttribute(ATTR_VIEWS);
		eRow.addAttribute(ATTR_UP_VOTES);
		eRow.addAttribute(ATTR_DOWN_VOTES);

		setRoot(eUsers, false);
	}

	@Override
	public void emit(Data data)
	{
		if (data.getElement() == eRow) {
			String id = data.getAttribute(ATTR_ID);
			String reputation = data.getAttribute(ATTR_REPUTATION);
			String creationDate = data.getAttribute(ATTR_CREATION_DATE);
			String displayName = data.getAttribute(ATTR_DISPLAY_NAME);
			String lastAccessDate = data.getAttribute(ATTR_LAST_ACCESS_DATE);
			String websiteUrl = data.getAttribute(ATTR_WEBSITE_URL);
			String location = data.getAttribute(ATTR_LOCATION);
			String age = data.getAttribute(ATTR_AGE);
			String aboutMe = data.getAttribute(ATTR_ABOUT_ME);
			String views = data.getAttribute(ATTR_VIEWS);
			String upVotes = data.getAttribute(ATTR_UP_VOTES);
			String downVotes = data.getAttribute(ATTR_DOWN_VOTES);

			User user = new User();

			user.setId(Long.parseLong(id));
			user.setReputation(Integer.parseInt(reputation));
			user.setCreationDate(Parsers.parseDate(creationDate));
			user.setDisplayName(displayName);
			user.setLastAccessDate(Parsers.parseDate(lastAccessDate));
			user.setWebsiteUrl(websiteUrl);
			user.setLocation(location);
			if (age != null) {
				user.setAge(Integer.parseInt(age));
			}
			user.setAboutMe(aboutMe);
			user.setViews(Integer.parseInt(views));
			user.setUpVotes(Integer.parseInt(upVotes));
			user.setDownVotes(Integer.parseInt(downVotes));

			consumer.handle(user);
		}
	}

	public interface Consumer
	{
		void handle(User user);
	}
}
