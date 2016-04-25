import java.io.IOException;
import java.util.ArrayList;

public class BasicInterpretor {
	
	static String[] lines ;				// store the data of file
	String[] tok;							// to store variable name
	int value=0, flag=0, i=0, j=0, first=0, second=0, result=0, number=0;						// to store value of variable
	String name, temp, var, path;					
	
	ArrayList<BasicInterpretor>  list = new ArrayList<BasicInterpretor>() ;			//Array list to store key value for variables and their values
	
	//for storing key value pair
	public BasicInterpretor(String x, int y)
	{
		var=x;												
		value=y;
	}
	
	// setting file path
	public BasicInterpretor(String f_path)
    {
        path = f_path;
    }

	
    public void Interpret() throws IOException
    {
    	try
    	{
    		ReadFile file = new ReadFile(path);					//calling read file class
    		lines = file.OpenFile();							// storing file data in lines through calling function of ReadFile class
    		
    		
    		//Interpreting the code i.e line by line
    		for(i=0; i<lines.length;i++)
    		{
    			tok=lines[i].split(" ");						//splitting on the basis of space
    			
    			if(tok[0].equals("Let"))						//checking Let declarative to initialize
    			{	
    				//System.out.println("Initialize");
    				if(!("=".equals(tok[2])))					// check if assignment operation is not present
    				{
    					System.err.println("Syntax Error at line: "+(i+1) + " ----> equal sign is missing");
    					continue;
    				}
    				
    				String name= tok[1];						//variable name
    				int val= Integer.parseInt(tok[3]);			//value of variable
    				list.add(new BasicInterpretor(name,val));		//storing variable name and its value in key value form in ArrayList
    			}

    			else if(tok[0].equals("print"))					//checking print declarative to print value
    			{
    				//System.out.println("Printing");
    				String name = tok[1];						// varialble name
    				
    				//printing immediate value 
    				if(isInteger(tok[1]))						
    				{
						System.out.println(tok[1]);
    				}
    				
    				else
    				{
    					//traversing complete ArrayList to print value of required variable
    					for(j=0; j<list.size();j++)
    					{
    					
    					
    						//printing if variable is initialized by checking its presence in ArrayList
    						if(name.contains(list.get(j).var))
    						{
    							//System.out.println(name);
    							System.out.println(list.get(j).value);		
    							flag=1;										//setting flag 1 if variable is already initialized
    						}
    					}
    			
    					//if variable is not initialized so, its not present in the ArrayList
    					if(flag==0)
    					{
    						System.err.println("Error at line: "+(i+1)+" ----> " + name +" is not intialized.");
    					}
    				
    					flag=0;											//setting flag back to zero
    				}
    			}
    			
    			else											//arithmetic operations statements
    			{
    				if(!("=".equals(tok[1])))					// check if assignment operation is not present
    				{
    					System.err.println("Syntax Error at line: "+(i+1) + " ----> equal sign is missing");
    					continue;
    				}
    				
    				// L.H.S of = must be variable , it can't be number
    				if(isInteger(tok[0]))						//checking L.H.S of = is integer or not
    				{
    					System.err.println("Syntax Error at line: "+(i+1) + " ----> Value can't be assigned to immediate value");
    					continue;
    				}
    				else										// if L.H.S of = is variable , then checking is it initialized or not
    				{
    					if(InitializationCheck(tok[0],i)!=-1)
    					{
    						name = tok[0];
    					}
    					
    				}

    				//InitializationCheck(name);
    				
    				if(isInteger(tok[2]))								//if first operand is just number
    				{
    					first=Integer.parseInt(tok[2]);
    				}
    				else												// if first operand is variable
    				{
    					if((number=InitializationCheck(tok[2],i))!=-1)		//if variable then checking its existence in ArrayList
    					{
    						first = number;
    					}
    					
    				}
    				
    				if(isInteger(tok[4]))								//if second operand is just number	
    	    		{
    					second = Integer.parseInt(tok[4]);
    	    		}
    				else												// if second operand is variable
    				{
    					if((number=InitializationCheck(tok[4],i))!=-1)   	//if variable then checking its existence in ArrayList
    					{
    						second = number;
    					}
    				}
    				
    				
    				// checking and applying arithmetic operations
    				switch (tok[3])								
    				{
    				case "+":
    					//System.out.println("addition");
    					result=first + second;
    					SetValue(name , result);
    				
    					
    					break;
    			
    				case "-":
    					//System.out.println("subtraction");
    					result=first - second;
    					SetValue(name , result);
    					
    					break;
    					
    				case "*":
    					//System.out.println("Multiplication");
    					result=first * second;
    					SetValue(name , result);
    					
    					break;
    					
    				case "/":
    					//System.out.println("Division");
    					result=first / second;
    					SetValue(name , result);
    					break;
    					
    				default:
    					System.err.println("Syntax Error at line: "+(i+1)+" -----> Invalid aritmetic operation");
    					break;
    				
    				}
    				
    			}
    		}
	
    	}
    	

        catch(IOException e){
            System.out.println( e.getMessage() );
        }

        
    }
    
    //checking if string is numeric or not
    public  boolean isInteger(String str)  
    {  
      try  
      {  
    	  Integer.parseInt(str);
    	  return true;
      }  
      catch(NumberFormatException e)  
      {  
        return false;  
      }  
     
    }

    //checking if variable is initialized or not by checking its presence in ArrayList
	
    public int InitializationCheck(String name, int k)
    {
    	int num=0;
    	for(j=0; j<list.size();j++)
		{
			if(name.contains(list.get(j).var))
			{
				num=list.get(j).value;
				flag=1;								//setting flag 1 if variable is already initialized
			}
		}
		
		if(flag==0)									//if variable is not initialized so, its not present in the ArrayList
		{
			System.err.println("Error at line: "+(k+1)+" ----> "  + name +" is not initialized");
			return -1;
		}
		flag=0;
		return num;								// returning value against variable name if its present
    }
    
    
    //Setting the value against its variable name
    public void SetValue(String name , int result)
    {
    	//traversing the ArrayList to store value against its variable
    	for(j=0; j<list.size(); j++)
		{
			if(name.contains(list.get(j).var))
			{
				list.set(j, new BasicInterpretor(name,result));
			}
		}
    }
}
