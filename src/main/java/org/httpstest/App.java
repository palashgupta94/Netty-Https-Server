package org.httpstest;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Initializing" );
        LoggerConfig.configureLogger();
        Server.initServer();

    }
}
