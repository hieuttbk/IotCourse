package multiServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketConnector extends Thread
{
    private ServerSocket serverSocket;
    private int port;
    private boolean running = false;

    public SocketConnector( int port )
    {
        this.port = port;
    }

    public void startServer()
    {
        try
        {
            serverSocket = new ServerSocket( port );
            this.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stopServer()
    {
        running = false;
        this.interrupt();
    }

    @Override
    public void run()
    {
        running = true;
        while( running )
        {
            try
            {
                System.out.println( "> Socket: start server at " + port);

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();

                // Pass the socket to the RequestHandler thread for processing
                RequestHandler requestHandler = new RequestHandler( socket );
                requestHandler.start();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

//    public static void main( String[] args )
//    {
////        if( args.length == 0 )
////        {
////            System.out.println( "Usage: SimpleSocketServer <port>" );
////            System.exit( 0 );
////        }
//    	
//    	
//      //  int port = Integer.parseInt( args[ 0 ] );
//        int port = 8888;
//    	System.out.println( "Start server on port: " + port );
//
//        SocketConnector server = new SocketConnector( port );
//        server.startServer();
//
//        // Automatically shutdown in 1 minute
//        try
//        {
//            Thread.sleep( 60000 );
//        }
//        catch( Exception e )
//        {
//            e.printStackTrace();
//        }
//
//        server.stopServer();
//    }
}

class RequestHandler extends Thread
{
    private Socket socket;
    RequestHandler( Socket socket )
    {
        this.socket = socket;
    }

    @Override
    public void run()
    {
        try
        {
            System.out.println( "> Socket: Received a connection" );

            // Get input and output streams
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            PrintWriter out = new PrintWriter( socket.getOutputStream() );

            // Write out our header to the client
            out.println( "OK from SocketServer" );
            out.flush();

            // Echo lines back to the client until the client closes the connection or we receive an empty line
            String request = "";
            
            String line = in.readLine();
            while( line != null && line.length() > 0 )
            {
            	request+=line;
                out.println( "Echo: " + line );
                out.flush();
                line = in.readLine();
                
            }

            System.out.println( "> Socket: request: " + request);
            
            // Close our connection
            in.close();
            out.close();
            socket.close();

            System.out.println( "> Socket: Connection closed" );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }
}