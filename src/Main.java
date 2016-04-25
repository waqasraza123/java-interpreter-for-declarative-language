import java.io.IOException;

public class Main {

	public static void main(String[] args) throws IOException {
		String fileName;
    	fileName = "code.txt";
    	
    	BasicInterpretor basicInterpretor= new BasicInterpretor(fileName);
    	basicInterpretor.Interpret();

	}

}
