package org.springframework.studybuddies.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
//import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import net.bytebuddy.NamingStrategy.SuffixingRandom.BaseNameResolver.ForGivenType;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.matcher.RestAssuredMatchers;
import org.hamcrest.Matchers.*;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@RunWith(SpringRunner.class)
@ContextConfiguration
public class TestingSystemTest {

  @LocalServerPort
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost";
	}

	@Test
	public void usersTest() {
		// Send request and receive response
		Response checkUser = RestAssured.given().get("/users/13");
		int statusCode = checkUser.getStatusCode();
		assertEquals(200, statusCode);
		String returnString = checkUser.getBody().asString();
		//assertEquals("{\"id\":13,\"username\":\"demo2\",\"password\":\"pass\",\"email\":\"email\",\"location\":\"Waverly\"}", returnString);
		

		String requestBody = "{\n" +
	            "  \"email\": \"sp@.\",\n" +
	            "  \"id\": \"13\",\n" +
	            "  \"location\": \"Ames, Iowa\",\n" +
	            "  \"password\": \"pass\",\n" +
	            "  \"username\": \"moresp\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(requestBody)
		.when()
		.put("/users/13")
		.then()
		.extract().response();
		
		Response updatedUser = RestAssured.given().get("/users/13");
		int updatedCode = updatedUser.getStatusCode();
		assertEquals(200, updatedCode);
		String returnUpdatedString = updatedUser.getBody().asString();
		//assertEquals("{\"id\":13,\"username\":\"moresp\",\"password\":\"pass\",\"email\":\"sp@.\",\"location\":\"Ames, Iowa\"}", returnUpdatedString);
		
		
		String revertPlz = "{\n" +
		        "  \"email\": \"email\",\n" +
		        "  \"id\": \"13\",\n" +
		        "  \"location\": \"Waverly\",\n" +
		        "  \"password\": \"password\",\n" +
		        "  \"username\": \"demo2\" \n}";
				
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(revertPlz)
		.when()
		.put("/users/13");
		
		
		Response checkAllUser = RestAssured.given().get("/users");
		int allUsersCode = checkAllUser.getStatusCode();
		assertEquals(200, allUsersCode);
		
		
		String addNew = "{\n" +
	            "  \"email\": \"testing@iastate.edu\",\n" +
	            "  \"id\": \"27\",\n" +
	            "  \"location\": \"Ames, Iowa\",\n" +
	            "  \"password\": \"donthack\",\n" +
	            "  \"username\": \"hellome\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(addNew)
		.when()
		.post("/users/new");
		
		Response checkNewUser = RestAssured.given().get("/users");
		int newUsersCode = checkNewUser.getStatusCode();
		assertEquals(200, newUsersCode);
		
	}

	@Test
	public void membersTest() {
		// Send request and receive response
		Response checkMembers = RestAssured.given().get("/members");
		int membersCode = checkMembers.getStatusCode();
		assertEquals(200, membersCode);
		
		Response checkMember = RestAssured.given().get("/members/44");
		int memberCode = checkMember.getStatusCode();
		assertEquals(200, memberCode);

		String requestBody = "{\n" +
	            "  \"memberId\": \"44\",\n" +
	            "  \"groupId\": \"10\",\n" +
	            "  \"permission\": \"2\",\n" +
	            "  \"userId\": \"18\" \n}";
		
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(requestBody)
		.when()
		.put("/members/44")
		.then()
		.extract().response();
		
		Response updatedUser = RestAssured.given().get("/members/44");
		int updatedCode = updatedUser.getStatusCode();
		assertEquals(200, updatedCode);
		
		String revertPlz = "{\n" +
	            "  \"memberId\": \"44\",\n" +
	            "  \"groupId\": \"43\",\n" +
	            "  \"permission\": \"1\",\n" +
	            "  \"userId\": \"13\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(revertPlz)
		.when()
		.put("/members/44");
		
		
//		String memberNew = "{\n" +
//	            "  \"memberId\": \"23\",\n" +
//	            "  \"groupId\": \"10\",\n" +
//	            "  \"permission\": \"1\",\n" +
//	            "  \"userId\": \"14\",\n}";
//		
//		RestAssured.given().header("Content-type", "application/json")
//		.and()
//		.body(memberNew)
//		.when()
//		.post("/members/new");
		
		Response checkNewMember = RestAssured.given().get("/members");
		int newMemberCode = checkNewMember.getStatusCode();
		assertEquals(200, newMemberCode);
		
	}
	
	@Test
	public void announcementTest() {
		// Send request and receive response
		Response checkAs = RestAssured.given().get("/announcements/18");
		int asCode = checkAs.getStatusCode();
		assertEquals(200, asCode);
//		String returnAString = checkAs.getBody().asString();
//		assertEquals("{\"id\":18,\"time\":\"1\",\"memberId\":\"3\",\"message\":\"Welcome to COM S 309!!!\",\"time\":\"12:10\"}", returnAString);
		

		String requestBody = "{\n" +
	            "  \"id\": \"18\",\n" +
	            "  \"groupId\": \"2\",\n" +
	            "  \"memberId\": \"4\",\n" +
	            "  \"message\": \"Hello Friend\",\n" +
	            "  \"time\": \"12:10\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(requestBody)
		.when()
		.put("/announcements/18")
		.then()
		.extract().response();
		
		Response updatedUser = RestAssured.given().get("/announcements/18");
		int updatedCode = updatedUser.getStatusCode();
		assertEquals(200, updatedCode);
	//	String returnUpdatedString = updatedUser.getBody().asString();
	//	assertEquals("{\"id\":18,\"groupId\":\"1\",\"memberId\":\"3\",\"message\":\"Hello Friend\",\"time\":\"12:10\"}", returnUpdatedString);
		
		String revertPlz = "{\n" +
        "  \"id\": \"18\",\n" +
        "  \"groupId\": \"1\",\n" +
        "  \"memberId\": \"3\",\n" +
        "  \"message\": \"Welcome to COM S 309!!!\",\n" +
        "  \"time\": \"12:10\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(revertPlz)
		.when()
		.put("/announcements/18");
		
		
		Response checkAllUser = RestAssured.given().get("/announcements");
		int allUsersCode = checkAllUser.getStatusCode();
		assertEquals(200, allUsersCode);
		
		
//		String addNew = "{\n" +
//	            "  \"email\": \"testing@iastate.edu\",\n" +
//	            "  \"id\": \"27\",\n" +
//	            "  \"location\": \"Ames, Iowa\",\n" +
//	            "  \"password\": \"donthack\",\n" +
//	            "  \"username\": \"hellome\" \n}";
//		
//		RestAssured.given().header("Content-type", "application/json")
//		.and()
//		.body(addNew)
//		.when()
//		.post("/users/new");
//		
//		Response checkNewUser = RestAssured.given().get("/users");
//		int newUsersCode = checkNewUser.getStatusCode();
//		assertEquals(200, newUsersCode);
		
	}
	
	@Test
	public void groupETest() {
		// Send request and receive response
		Response checkGE = RestAssured.given().get("/group/event/6");
		int geCode = checkGE.getStatusCode();
		assertEquals(200, geCode);
//		String returnString = checkGE.getBody().asString();
//		assertEquals("{\"id\":13,\"username\":\"moresp\",\"password\":\"pass\",\"email\":\"sp@.\",\"location\":\"Ames, Iowa\"}", returnString);
		

		String requestBody = "{\n" +
	            "  \"groupEventid\": \"7\",\n" +
	            "  \"GeventMemberId\": \"23\",\n" +
	            "  \"eventGroupId\": \"5\",\n" +
	            "  \"message\": \"testing\",\n" +
	            "  \"time\": \"05/05/2122 15:00\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(requestBody)
		.when()
		.put("/group/event/6")
		.then()
		.extract().response();
		
		Response updatedUser = RestAssured.given().get("/group/event/6");
		int updatedCode = updatedUser.getStatusCode();
		assertEquals(200, updatedCode);
//		String returnUpdatedString = updatedUser.getBody().asString();
//		assertEquals("{\"id\":13,\"username\":\"demo2\",\"password\":\"pass\",\"email\":\"sp@.\",\"location\":\"Ames, Iowa\"}", returnUpdatedString);
//		
		String revertPlz = "{\n" +
	            "  \"groupEventid\": \"7\",\n" +
	            "  \"GeventMemberId\": \"3\",\n" +
	            "  \"eventGroupId\": \"2\",\n" +
	            "  \"message\": \"Meeting with the team Group\",\n" +
	            "  \"time\": \"05/05/2022 15:00\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(revertPlz)
		.when()
		.put("/group/event/6");
//		
//		
		Response checkAllGEvents = RestAssured.given().get("/group/events");
		int allGEventCode = checkAllGEvents.getStatusCode();
		assertEquals(200, allGEventCode);
//		
//		
		/*String addNew =  "{\n" +
	            "  \"groupEventid\": \"7\",\n" +
	            "  \"GeventMemberId\": \"18\",\n" +
	            "  \"eventGroupId\": \"5\",\n" +
	            "  \"message\": \"testing\",\n" +
	            "  \"time\": \"05/05/2122 15:00\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(addNew)
		.when()
		.post("/group/event/new");
		
		Response checkNewUser = RestAssured.given().get("/group/events");
		int newUsersCode = checkNewUser.getStatusCode();
		assertEquals(200, newUsersCode);
		*/
	}
	
	@Test
	public void groupsTest() {
		// Send request and receive response
		Response checkG = RestAssured.given().get("/groups/6");
		int gCode = checkG.getStatusCode();
		assertEquals(200, gCode);
//		String returnString = checkAs.getBody().asString();
//		assertEquals("{\"id\":13,\"username\":\"moresp\",\"password\":\"pass\",\"email\":\"sp@.\",\"location\":\"Ames, Iowa\"}", returnString);
//		
//
		String requestBody = "{\n" +
	            "  \"id\": \"6\",\n" +
	            "  \"title\": \"Title\",\n" +
	            "  \"isPublic\": \"false\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(requestBody)
		.when()
		.put("/groups/6")
		.then()
		.extract().response();
		
		Response updatedUser = RestAssured.given().get("/groups/6");
		int updatedCode = updatedUser.getStatusCode();
		assertEquals(200, updatedCode);
		//String returnUpdatedString = updatedUser.getBody().asString();
		//assertEquals("{\"id\":13,\"username\":\"demo2\",\"password\":\"pass\",\"email\":\"sp@.\",\"location\":\"Ames, Iowa\"}", returnUpdatedString);
		
		String revertPlz = "{\n" +
	            "  \"id\": \"6\",\n" +
	            "  \"title\": \"Different title\",\n" +
	            "  \"isPublic\": \"true\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(revertPlz)
		.when()
		.put("/groups/6");
		
		
		Response checkAllGroups = RestAssured.given().get("/groups");
		int allGroupCode = checkAllGroups.getStatusCode();
		assertEquals(200, allGroupCode);
//		
//		
		String addNew = "{\n" +
	            "  \"id\": \"6\",\n" +
	            "  \"title\": \"Title 2\",\n" +
	            "  \"isPublic\": \"false\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(addNew)
		.when()
		.post("/groups/new");
		
		Response checkNewUser = RestAssured.given().get("/groups");
		int newUsersCode = checkNewUser.getStatusCode();
		assertEquals(200, newUsersCode);
		
	}
	
	@Test
	public void peTest() {
		// Send request and receive response
		Response checkPE = RestAssured.given().get("/personal/event/5");
		int peCode = checkPE.getStatusCode();
		assertEquals(200, peCode);
//		String returnString = checkAs.getBody().asString();
//		assertEquals("{\"id\":13,\"username\":\"moresp\",\"password\":\"pass\",\"email\":\"sp@.\",\"location\":\"Ames, Iowa\"}", returnString);
//		
//
		String requestBody = "{\n" +
	            "  \"groupEventid\": \"5\",\n" +
	            "  \"userid\": \"17\",\n" +
	            "  \"message\": \"Message\",\n" +
	            "  \"time\": \"05/06/2022 04:09\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(requestBody)
		.when()
		.put("/personal/event/5")
		.then()
		.extract().response();
//		
		Response updatedUser = RestAssured.given().get("/personal/event/5");
		int updatedCode = updatedUser.getStatusCode();
		assertEquals(200, updatedCode);
		//String returnUpdatedString = updatedUser.getBody().asString();
		//assertEquals("{\"id\":13,\"username\":\"demo2\",\"password\":\"pass\",\"email\":\"sp@.\",\"location\":\"Ames, Iowa\"}", returnUpdatedString);
		
		String revertPlz = "{\n" +
	            "  \"groupEventid\": \"5\",\n" +
	            "  \"userid\": \"12\",\n" +
	            "  \"message\": \"Message again\",\n" +
	            "  \"time\": \"05/06/2022 05:09\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(revertPlz)
		.when()
		.put("/personal/event/5");
//		
//		
		Response checkAllPEvents = RestAssured.given().get("/personal/events");
		int allPEventCode = checkAllPEvents.getStatusCode();
		assertEquals(200, allPEventCode);
//		
//		
		/*
		String addNew = "{\n" +
	            "  \"groupEventid\": \"5\",\n" +
	            "  \"userid\": \"37\",\n" +
	            "  \"message\": \"Message 2\",\n" +
	            "  \"time\": \"05/05/2022 05:09\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(addNew)
		.when()
		.post("/personal/event/new");
		
		Response checkNewUser = RestAssured.given().get("/personal/events");
		int newUsersCode = checkNewUser.getStatusCode();
		assertEquals(200, newUsersCode);
		*/
	}
	
	@Test
	public void permissionTest() {
		// Send request and receive response
		Response checkPerm = RestAssured.given().get("/permissions/3");
		int permCode = checkPerm.getStatusCode();
		assertEquals(200, permCode);
		String returnString = checkPerm.getBody().asString();
		assertEquals("{\"id\":3,\"level\":\"Read/Write/Edit\"}", returnString);
		

		String requestPerm = "{\n" +
        "  \"id\": \"3\",\n" +
        "  \"level\": \"Read/Write\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(requestPerm)
		.when()
		.put("/permissions/3")
		.then()
		.extract().response();
		
		Response updatedPerm = RestAssured.given().get("/permissions/3");
		int updatedPCode = updatedPerm.getStatusCode();
		assertEquals(200, updatedPCode);
		String returnUpdatedString = updatedPerm.getBody().asString();
		assertEquals("{\"id\":3,\"level\":\"Read/Write\"}", returnUpdatedString);
		
		String revertPlz = "{\n" +
	            "  \"id\": \"3\",\n" +
	            "  \"level\": \"Read/Write/Edit\" \n}";
		
		RestAssured.given().header("Content-type", "application/json")
		.and()
		.body(revertPlz)
		.when()
		.put("/permissions/3");
		
		
		Response checkAllPermissions = RestAssured.given().get("/permissions/");
		int allPermCode = checkAllPermissions.getStatusCode();
		assertEquals(200, allPermCode);
	}



	
	
}
