package ro.dcsi.internship;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.*;

import com.google.common.collect.Lists;

public class ForgeRockUserDaoTest extends CsvFileUserDaoTest {
	private final static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(ForgeRockUserDaoTest.class);

	@Override
	ForgeRockUsersDao exporter() {
		return new ForgeRockUsersDao("dcs-xps:8080");
	}
	@Override
	@Test
	public void readUsersTest() throws IOException {
		List<User> users = exporter().load("doesn't matter the name");
	}
	@Test
	public void addOneUserTest() throws IOException {
		List<User> users = exporter().load("doesn't matter the name2");
		logger.debug(users.toString());
		User x = new User("raisercostin","raisercostin+dcsi@gmail.com");
		exporter().deleteIfExists(x.username);
		exporter().save(Lists.newArrayList(x),"doesn't matter the name1");
		List<User> users2 = exporter().load("doesn't matter the name2");
		List<User> res = users2.stream().filter(u->u.username.equals(x.username)).collect(Collectors.toList());
		Assert.assertEquals(x.toString(),res.get(0).toString());
		Assert.assertEquals(1,res.size());
	}
	@Test
	public void create10Users() throws IOException {
		List<User> users = generateUsers(2000,"testuser4create10Users",10,"@gmail.com");
		ForgeRockUsersDao fr = exporter();
		int alreadyExistingUsers = fr.load("doesn't matter the name2").size();
		fr.forcedCreate(users);
		List<User> actual = fr.load("");
		assertEquals(alreadyExistingUsers+users.size(),actual.size());
	}
	@Test
	public void exportUsersFromFile() throws IOException {
		List<User> users = new OpenCsvFileUserDao().load("src/test/resources/sample3"
				+ ".csv");
		ForgeRockUsersDao fr = new ForgeRockUsersDao("dcs-xps:8080");
		fr.forcedCreate(users);
	}
	@Test
	public void importUsersFromForgeRock() throws IOException {
		List<User> users = new ForgeRockUsersDao("dcs-xps:8080").load("nu conteaza");
		new OpenCsvFileUserDao().save(users, "target/2017-05-31--forge-rock-backup.csv");
	}
	@Test
	public void syncUsers() throws IOException {
		ForgeRockUsersDao fr = new ForgeRockUsersDao("http","dcs-xps:8080");
		OpenCsvFileUserDao file = new OpenCsvFileUserDao();

		List<User> users = fr.load("nu conteaza");
		file.save(users, "target/2017-05-31--forge-rock-backup.csv");
		//fr.delete(users);
		//file
	}

	@Test
	public void testQuotesAreSaved() throws IOException {
		super.testQuotesAreSaved();
	}
}

