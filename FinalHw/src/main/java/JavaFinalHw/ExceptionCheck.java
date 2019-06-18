package JavaFinalHw;

public class ExceptionCheck extends Exception{
	
	private static final long serialVersionUID = 1L;
	public ExceptionCheck(){
		super("Please put and check a file path.");
		
	}
	ExceptionCheck(String message){
		super (message);
	}
}
