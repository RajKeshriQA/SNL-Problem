package SNL.SNLTestCase;

import java.util.UUID;
import java.util.Random;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONObject;
import org.json.JSONArray;


public class Board {
    
    UUID uuid;
    JSONObject data;
  
    public Board()
            throws FileNotFoundException, UnsupportedEncodingException,
            IOException{
        uuid = UUID.randomUUID();
        BoardModel.init(uuid);
        data = BoardModel.data(uuid);
    }
   
    public Board(UUID uuid) throws IOException {
        this.uuid = uuid;
        data = BoardModel.data(uuid);
    }


    public JSONArray registerPlayer(String name) 
            throws PlayerExistsException, GameInProgressException,
                FileNotFoundException, UnsupportedEncodingException,
                MaxPlayersReachedExeption, IOException {
        if(data.getJSONArray("players").length()==4){
            throw new MaxPlayersReachedExeption(4);
        }
        for(Object playerObject:data.getJSONArray("players")){
            JSONObject player = (JSONObject)playerObject;
            if(player.getString("name").equals(name)){
                throw new PlayerExistsException(name);
            }
            if(player.getInt("position")!=0){
                throw new GameInProgressException();
            }
        }
        JSONObject newPlayer = new JSONObject();
        newPlayer.put("name", name);
        newPlayer.put("uuid", UUID.randomUUID());
        newPlayer.put("position", 0);
        data.getJSONArray("players").put(newPlayer);
        BoardModel.save(uuid, data);
        return BoardModel.data(uuid).getJSONArray("players");
    }

    public JSONArray deletePlayer(UUID playerUuid)
            throws NoUserWithSuchUUIDException, FileNotFoundException,
                UnsupportedEncodingException{
        Boolean response = false;
        for(int i = 0; i < data.getJSONArray("players").length(); i++){
            JSONObject player = data.getJSONArray("players").getJSONObject(i);
            
            if(player.getString("uuid").equals(playerUuid.toString())){
                data.getJSONArray("players").remove(i);
                data.put("turn", 0);
                BoardModel.save(uuid, data);
                response = true;
            }
        }
        if(!response){
            throw new NoUserWithSuchUUIDException(playerUuid.toString());
        }
        return data.getJSONArray("players");
    }
    
   
    public JSONObject rollDice(UUID playerUuid) 
            throws InvalidTurnException, FileNotFoundException,
                UnsupportedEncodingException{
        JSONObject response = new JSONObject();
        Integer turn = data.getInt("turn");
        if(playerUuid.equals((UUID)data.getJSONArray("players").getJSONObject(turn).get("uuid"))){
            JSONObject player = data.getJSONArray("players").getJSONObject(turn);
            
            Integer dice = new Random().nextInt(6) + 1;
            Integer currentPosition = player.getInt("position");
            Integer newPosition = currentPosition + dice;
            String message = "";
            String playerName = player.getString("name");
            if(newPosition <= 100){
                JSONObject step = data.getJSONArray("steps").getJSONObject(newPosition);
                newPosition = step.getInt("target");
                if(step.getInt("type")==0){
                    message = "Player moved to " + newPosition;
                }else if(step.getInt("type")==1){
                    message = "Player was bit by a snake, moved back to " + newPosition;
                }else if(step.getInt("type")==2){
                    message = "Player climbed a ladder, moved to " + newPosition;
                }
                data.getJSONArray("players").getJSONObject(turn).put("position", newPosition);
            }else{
                message = "Incorrect roll of dice. Player did not move";
            }
            Integer newTurn = turn+1;
            if(newTurn >= data.getJSONArray("players").length()){
                newTurn = 0;
            }
            data.put("turn", newTurn);
            BoardModel.save(uuid, data);
            response.put("message", message);
            response.put("playerUuid", playerUuid);
            response.put("playerName", playerName);
            response.put("dice", dice);
            
        }else{
            throw new InvalidTurnException(playerUuid);
        }
        return response;
    }

    @Override
    public String toString(){
        return "UUID:" + uuid.toString() + "\n" + data.toString();
    }
    
 
    public JSONObject getData(){
        return data;
    }
    

    public UUID getUUID(){
        return uuid;
    }
}