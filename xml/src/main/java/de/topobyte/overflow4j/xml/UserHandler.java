package de.topobyte.overflow4j.xml;

import java.util.ArrayList;
import java.util.List;

import de.topobyte.overflow4j.model.User;
import de.topobyte.xml.dynsax.Child;
import de.topobyte.xml.dynsax.ChildType;
import de.topobyte.xml.dynsax.Data;
import de.topobyte.xml.dynsax.DynamicSaxHandler;
import de.topobyte.xml.dynsax.Element;

public class UserHandler extends DynamicSaxHandler
{

	public static final String ATTR_ID = "Id";
	public static final String ATTR_REPUTATION = "Reputation";
	public static final String ATTR_CREATION_DATE = "CreationDate";
	public static final String ATTR_DISPLAY_NAME = "DisplayName";
	public static final String ATTR_LAST_ACCESS_DATE = "LastAccessDate";
	public static final String ATTR_WEBSITE_URL = "WebsiteUrl";
	public static final String ATTR_LOCATION = "Location";
	public static final String ATTR_AGE = "Age";
	public static final String ATTR_ABOUT_ME = "AboutMe";
	public static final String ATTR_VIEWS = "Views";
	public static final String ATTR_UP_VOTES = "UpVotes";
	public static final String ATTR_DOWN_VOTES = "DownVotes";

	private Element eUsers;
	private Element eRow;

	private List<User> users = new ArrayList<>();

	public UserHandler()
	{
		eUsers = new Element("users", false);
		eRow = new Element("row", false);

		eUsers.addChild(new Child(eRow, ChildType.LIST, true));

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

	public List<User> getUsers()
	{
		return users;
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
			// TODO: parse creationDate
			user.setDisplayName(displayName);
			// TODO: parse lastAccessDate
			user.setWebsiteUrl(websiteUrl);
			user.setLocation(location);
			if (age != null) {
				user.setAge(Integer.parseInt(age));
			}
			user.setAboutMe(aboutMe);
			user.setViews(Integer.parseInt(views));
			user.setUpVotes(Integer.parseInt(upVotes));
			user.setDownVotes(Integer.parseInt(downVotes));
			users.add(user);
		}
	}

}
