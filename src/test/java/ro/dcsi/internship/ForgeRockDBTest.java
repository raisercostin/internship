package ro.dcsi.internship;

import static org.junit.Assert.*;
import java.util.Hashtable;
import java.util.Optional;
import org.junit.AfterClass;
import org.junit.Test;

public class ForgeRockDBTest {
  /* TODO add required field checks */
  private static String openIDMServer = "http://localhost:8080";
  private static String openIDMUsername = "openidm-admin";
  private static String openIDMPassword = "openidm-admin";
  private static String existingUserId = "ForgeRockDBTestExistingUser";
  private static String nonExistingUserId = "ForgeRockDBTestNonExistingUser";

  @AfterClass
  public static void prepareDatabase() {
    ForgeRockDB db = new ForgeRockDB(openIDMServer, openIDMUsername, openIDMPassword);
    db.deleteUser(existingUserId);
    db.deleteUser(nonExistingUserId);
    Hashtable<String, String> existingUserAttributes = new Hashtable<String, String>();
    existingUserAttributes.put("_id", existingUserId);
    existingUserAttributes.put("userName", existingUserId);
    existingUserAttributes.put("mail", "ExistingUser@ex.com");
    existingUserAttributes.put("sn", existingUserId);
    existingUserAttributes.put("givenName", existingUserId);
    User existingUser = new User(existingUserId, existingUserAttributes);
    db.addUser(existingUser);
  }

  @Test
  public void prepareDatabaseTest() {
    ForgeRockDBTest.prepareDatabase();
    ForgeRockDB db = new ForgeRockDB(openIDMServer, openIDMUsername, openIDMPassword);
    Optional<User> user = db.getUser(existingUserId);
    assertTrue(user.isPresent());
    assertEquals(existingUserId, db.getUser(existingUserId).get().getId());
  }

  @Test
  public void getUserTest() {
    ForgeRockDBTest.prepareDatabase();
    ForgeRockDB db = new ForgeRockDB(openIDMServer, openIDMUsername, openIDMPassword);

    // get an existing user
    Optional<User> user = db.getUser(existingUserId);
    assertTrue(user.isPresent());
    assertEquals(existingUserId, user.get().getAttributeValue("sn"));

    // get a non existing user
    Optional<User> user2 = db.getUser(nonExistingUserId);
    assertFalse(user2.isPresent());
  }

  @Test
  public void userExistsTest() {
    ForgeRockDBTest.prepareDatabase();
    ForgeRockDB db = new ForgeRockDB(openIDMServer, openIDMUsername, openIDMPassword);
    assertTrue(db.userExists(existingUserId));
    assertFalse(db.userExists(nonExistingUserId));
  }

  @Test
  public void deleteUserTest() {
    ForgeRockDBTest.prepareDatabase();
    ForgeRockDB db = new ForgeRockDB(openIDMServer, openIDMUsername, openIDMPassword);
    assertTrue(db.deleteUser(existingUserId));
    assertFalse(db.deleteUser(nonExistingUserId));
  }

  @Test
  public void updateUserTest() {
    ForgeRockDBTest.prepareDatabase();
    ForgeRockDB db = new ForgeRockDB(openIDMServer, openIDMUsername, openIDMPassword);

    // update an existing user
    Hashtable<String, String> attributes = new Hashtable<String, String>();
    attributes.put("_id", existingUserId);
    attributes.put("mail", "ExistingUser@ex.com");
    attributes.put("sn", existingUserId);
    attributes.put("givenName", existingUserId);
    attributes.put("userName", "joe");
    attributes.put("city", "Bucharest");
    attributes.put("customAttr", "customVal");
    User user = new User(existingUserId, attributes);
    assertTrue(db.updateUser(user));
    assertTrue(db.getUser(existingUserId).isPresent());
    assertEquals("joe", db.getUser(existingUserId).get().getAttributeValue("userName"));
    assertEquals("Bucharest", db.getUser(existingUserId).get().getAttributeValue("city"));
    assertEquals("customVal", db.getUser(existingUserId).get().getAttributeValue("customAttr"));

    // update a non existing user
    Hashtable<String, String> attributes2 = new Hashtable<String, String>();
    attributes.put("_id", nonExistingUserId);
    attributes.put("mail", "NonExistingUser@ex.com");
    attributes.put("sn", nonExistingUserId);
    attributes.put("givenName", nonExistingUserId);
    attributes.put("userName", "joe");
    attributes.put("city", "Bucharest");
    attributes.put("customAttr", "customVal");
    User user2 = new User(nonExistingUserId, attributes2);
    assertFalse(db.updateUser(user2));
  }

  @Test
  public void addUserTest() {
    ForgeRockDBTest.prepareDatabase();
    ForgeRockDB db = new ForgeRockDB(openIDMServer, openIDMUsername, openIDMPassword);

    // add a non existing user
    Hashtable<String, String> attributes = new Hashtable<String, String>();
    attributes.put("_id", nonExistingUserId);
    attributes.put("mail", "NonExistingUser@ex.com");
    attributes.put("sn", nonExistingUserId);
    attributes.put("givenName", nonExistingUserId);
    attributes.put("userName", nonExistingUserId);
    User user = new User(nonExistingUserId, attributes);
    assertTrue(db.addUser(user));
    assertTrue(db.getUser(nonExistingUserId).isPresent());
    assertEquals(nonExistingUserId, db.getUser(nonExistingUserId).get().getAttributeValue("userName"));

    // add an existing user
    Hashtable<String, String> attributes2 = new Hashtable<String, String>();
    attributes2.put("_id", existingUserId);
    attributes2.put("mail", "ExistingUser@ex.com");
    attributes2.put("sn", existingUserId);
    attributes2.put("givenName", existingUserId);
    attributes2.put("userName", "joe");
    User user2 = new User(existingUserId, attributes2);
    assertFalse(db.addUser(user2));
    assertTrue(db.getUser(existingUserId).isPresent());
    assertEquals(existingUserId, db.getUser(existingUserId).get().getAttributeValue("userName"));
  }
}
