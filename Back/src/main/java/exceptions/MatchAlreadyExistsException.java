package exceptions;

public class MatchAlreadyExistsException extends Exception{

    private  static final long serialVersionUID =1L;

    public MatchAlreadyExistsException(int idMatch){
        super("The match with id="+ idMatch + " already exists.");
    }

    public MatchAlreadyExistsException(int idMatch, Throwable t){
        super("The match with id="+ idMatch + " already exists.",t);
    }
}
