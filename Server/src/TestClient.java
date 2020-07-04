import java.net.*; 
import java.io.*; 
  
public class TestClient 
{ 
    // initialize socket and input output streams 
    /*
	private Socket socket            = null; 
    private DataInputStream  input   = null; 
    private DataOutputStream out     = null; 
    */
  
    // constructor to put ip address and port 
    public TestClient(String address, int port) 
    { 
        /*
    	// establish a connection 
        try
        { 
            socket = new Socket(address, port); 
            System.out.println("Connected"); 
  
            // takes input from terminal 
            input  = new DataInputStream(System.in); 
  
            // sends output to the socket 
            out    = new DataOutputStream(socket.getOutputStream()); 
        } 
        catch(UnknownHostException u) 
        { 
            System.out.println(u); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        } 
  
        // string to read message from input 
        String line = ""; 
  
        // keep reading until "Over" is input 
        while (!line.equals("Over")) 
        { 
            try
            { 
                line = input.readLine(); 
                out.writeUTF(line); 
            } 
            catch(IOException i) 
            { 
                System.out.println(i); 
            } 
        } 
  
        // close the connection 
        try
        { 
            input.close(); 
            out.close(); 
            socket.close(); 
        } 
        catch(IOException i) 
        { 
            System.out.println(i); 
        }
        */
    	
    	Socket socket = null;
        DataOutputStream dataOut = null;

        // establish a connection
        try {
            socket = new Socket(address, port);
            System.out.println("called");
            // sends output to the socket
            dataOut = new DataOutputStream(socket.getOutputStream());
        } catch(Exception e) {System.out.println("Failed to connect");}

        try {
            dataOut.writeUTF("test");
            System.out.println("Sent");
        } catch(Exception e) {System.out.println("Failed to send");}

        // close the connection
        try {
            dataOut.close();
            socket.close();
        } catch(Exception e) {System.out.println("Failed to close");}
    } 
  
    public static void main(String args[]) 
    { 
        TestClient client = new TestClient("127.0.0.1", 5000); 
    }
}