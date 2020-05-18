import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Qualtrics {
	
	public static String h1="<html> <p style=\\\"font-family:verdana;font-size:50px;color:blue;\\\"> Employee Experience Expert - Training Documentation</p>";
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	
		
		 // Provide full path for directory(change accordingly)   
        String maindirpath = "/Users/baps/Desktop/QA"; 
                  
        // File object 
        File maindir = new File(maindirpath); 
        int i = 0;
        if(maindir.exists() && maindir.isDirectory()) 
        { 
            // array for files and sub-directories  
            // of directory pointed by maindir 
            File arr[] = maindir.listFiles(); 
              
            System.out.println("**********************************************"); 
            System.out.println("Files from main directory : " + maindir); 
            System.out.println("**********************************************"); 
              
            // Calling recursive method 
            RecursivePrint(arr,0,0);  
       }  
        h1 = h1 + "</html>";
        System.out.println(h1);

        
        try {
            FileWriter myWriter = new FileWriter("/Users/baps/Desktop/QA/QUALTRICS.html");
            myWriter.write(h1);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
        
    } 
	
		

	

	     static void RecursivePrint(File[] arr,int index,int level)  
	     { 
	         // terminate condition 
	         if(index == arr.length) 
	             return; 
	           
	         // tabs for internal levels 
	         for (int i = 0; i < level; i++) 
	             System.out.print("\t"); 
	           
	         // for files 
	         if(arr[index].isFile()) {
	        	
	        		 String fn = arr[index].toString();
	        		 String ext = fn.substring(fn.lastIndexOf(".") + 1);
	        		// if (!((ext.equalsIgnoreCase("js")) || (ext.equalsIgnoreCase("png")) || (ext.equalsIgnoreCase("css")) ))
	        		
	        			 if (ext.equalsIgnoreCase("htm")==true || ext.equalsIgnoreCase("mp4")==true)
	        				 {

	        		 System.out.println(arr[index].getName()+ "---->"); 
	        		 String file = arr[index].getName();
	        		 System.out.println(arr[index].getPath().substring(fn.lastIndexOf("QA") + 2));
	        		 String path = "."+arr[index].getPath().substring(fn.lastIndexOf("QA") + 2);
	        		 h1 = h1 + "<a href=\""+path + "\" target=\"_blank\">"+file+"</a>";
	        		 h1 = h1 +"<p>";
	        		 }
	      
	            
	         }
	         // for sub-directories 
	         else if(arr[index].isDirectory()) 
	         { 
	             System.out.println("[" + arr[index].getName() + "]"); 
	               
	             String folder = arr[index].getName();
	             
	             if (level == 0) {
	            	 h1 = h1 + "<p style=\"font-family:verdana;font-size:30px;color:blue;\">"+folder+"</p>";
        		 h1 = h1 +"<p>";
	             }
	             
	             if (level == 1) {
	            	 h1 = h1 + "<p style=\"font-family:verdana;font-size:20px;color:blue;\">"+folder+"</p>";
        		 h1 = h1 +"<p>";
	             }
	             
	             if (level == 2) {
	            //	 h1 = h1 + "<p style=\"font-family:verdana;font-size:10px;color:blue;\">"+folder+"</p>";
	        	//	 h1 = h1 +"<p>";
		             }
	             
	             
        		 
	             // recursion for sub-directories 
	             RecursivePrint(arr[index].listFiles(), 0, level + 1); 
	         } 
	         
	       
	            
	         // recursion for main directory 
	         RecursivePrint(arr,++index, level); 
	         
	         
	    } 
	     

}
