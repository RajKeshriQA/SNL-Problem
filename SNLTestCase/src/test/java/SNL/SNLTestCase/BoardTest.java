package SNL.SNLTestCase;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.*;
public class BoardTest {
	@Test(expectedExceptions =PlayerExistsException.class)
	  public void nameRedundancy_test() throws FileNotFoundException, IOException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
    FileNotFoundException, UnsupportedEncodingException,
    MaxPlayersReachedExeption, IOException {
		Board BoardObject=new Board() ;
	JSONArray name1= BoardObject.registerPlayer("Rahul");
JSONArray name2= BoardObject.registerPlayer("Rahul");
	 }
@Test (expectedExceptions = {MaxPlayersReachedExeption.class})
public void maxPlayer_test() throws FileNotFoundException, IOException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
FileNotFoundException, UnsupportedEncodingException,MaxPlayersReachedExeption, IOException 
{
	Board BoardObject=new Board() ;
	 BoardObject.registerPlayer("Rahul2");
	 BoardObject.registerPlayer("Rahul3");
	 BoardObject.registerPlayer("Rahul4");
	 System.out.println(BoardObject);
	BoardObject.registerPlayer("Rahul5");
	BoardObject.registerPlayer("Rahul6");
}
@Test(expectedExceptions =GameInProgressException.class)
public void position_test() throws FileNotFoundException, IOException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
FileNotFoundException, UnsupportedEncodingException,MaxPlayersReachedExeption, IOException
{
	Board BoardObject=new Board() ;
	BoardObject.registerPlayer("Rahul9");

	//System.out.println(root.getJSONObject("data").getJSONArray("players").getJSONObject(0).get("position"));
	JSONObject attributes=BoardObject.getData().getJSONArray("players").getJSONObject(0);
	System.out.println(attributes);
	System.out.println(attributes.put("position",2));
	BoardObject.registerPlayer("Rahul10");
		
}
@Test(expectedExceptions =InvalidTurnException.class)
public void turn_test() throws FileNotFoundException, IOException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
FileNotFoundException, UnsupportedEncodingException,MaxPlayersReachedExeption, IOException, InvalidTurnException
{
	Board BoardObject=new Board() ;
	 BoardObject.registerPlayer("Rahul");
   BoardObject.registerPlayer("Rahul2");
	JSONObject turn = new JSONObject(BoardObject);
	UUID id=(UUID) turn.getJSONObject("data").getJSONArray("players").getJSONObject(1).get("uuid");
	BoardObject.rollDice(id);
	System.out.println(turn);
}
@Test
public void playerNotExist_test()throws FileNotFoundException, IOException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
FileNotFoundException, UnsupportedEncodingException,MaxPlayersReachedExeption, IOException, InvalidTurnException,NoUserWithSuchUUIDException
{
	Board BoardObject=new Board() ;
	 BoardObject.registerPlayer("Rahul");
		JSONObject root = new JSONObject(BoardObject);
		UUID uuid2=(UUID)root.getJSONObject("data").getJSONArray("players").getJSONObject(0).get("uuid");
		BoardObject.deletePlayer(uuid2);
}
@Test 
public void registerPlayer_test() throws FileNotFoundException, IOException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
FileNotFoundException, UnsupportedEncodingException,MaxPlayersReachedExeption, IOException, InvalidTurnException,NoUserWithSuchUUIDException
{
	Board BoardObject=new Board() ;
	 BoardObject.registerPlayer("Rahul");
}
@Test
public void validRoll_test() throws FileNotFoundException, IOException, UnsupportedEncodingException, PlayerExistsException, GameInProgressException,
FileNotFoundException, UnsupportedEncodingException,MaxPlayersReachedExeption, IOException, InvalidTurnException,NoUserWithSuchUUIDException
{
	Board BoardObject=new Board() ;
	 BoardObject.registerPlayer("Rahul");
		JSONObject root = new JSONObject(BoardObject);
		UUID uuid=(UUID) root.getJSONObject("data").getJSONArray("players").getJSONObject(0).get("uuid");
		BoardObject.rollDice(uuid);
}
}
