package bdd.accessBD;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("serial")
public class BDExceptionParamError extends Exception {

	ArrayList<Integer> params = new ArrayList<Integer>();
	String errors = "";
	
	public BDExceptionParamError(){
		super();
	}
	
	public BDExceptionParamError (String message) {
		super("Erreur : "+message+".");
	}
	
	public void addError(int indiceParam, String error){
		params.add(indiceParam);
		errors+=error+"<br>";
	}
	
	public Collection<Integer> getParamsError(){
		return (Collection<Integer>)params ;
	}
	
	public String getMessageError(){
		return errors;
	}
}
