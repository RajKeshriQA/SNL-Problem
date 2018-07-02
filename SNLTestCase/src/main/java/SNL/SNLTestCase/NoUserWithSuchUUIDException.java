package SNL.SNLTestCase;

public class NoUserWithSuchUUIDException extends Exception{
    
    public NoUserWithSuchUUIDException(String uuid){
        super("No Player with uuid '"+uuid+"' on board");
    }
}