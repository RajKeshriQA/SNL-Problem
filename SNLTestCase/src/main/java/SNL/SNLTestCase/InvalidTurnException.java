package SNL.SNLTestCase;

import java.util.UUID;

public class InvalidTurnException extends Exception{
    
    public InvalidTurnException(UUID playerUuid){
        super("Player '"+playerUuid.toString()+"' does not have the turn");
    }
}