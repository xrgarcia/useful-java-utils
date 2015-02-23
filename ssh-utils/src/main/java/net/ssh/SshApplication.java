package net.ssh;

import com.jcraft.jsch.Channel;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by ray on 2/23/15.
 */
public class SshApplication {

    public enum ConnectionProperty {
        Hostname, Username, Password, Port

    }

    public Map<ConnectionProperty, String> promptForConnectionDetails() {
        Map<ConnectionProperty, String> connectionDetails = new HashMap<ConnectionProperty, String>(3);
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter hostname: ");
        connectionDetails.put(ConnectionProperty.Hostname, sc.nextLine());

        System.out.print("Please enter port (Default 22): ");
        connectionDetails.put(ConnectionProperty.Port, sc.nextLine());

        System.out.print("Please enter username: ");
        connectionDetails.put(ConnectionProperty.Username, sc.nextLine());

        System.out.print("Please enter password: ");
        connectionDetails.put(ConnectionProperty.Password, sc.nextLine());

        return connectionDetails;
    }

    public boolean testSshAuth(String[] args) {
        for (String arg : args) {
            if ("-testAuth".equalsIgnoreCase(arg))
                return true;
        }
        return false;
    }

    public static void main(String[] args) throws IOException {

        SshApplication sshShell = new SshApplication();
        if(sshShell.testSshAuth(args))
            sshShell.testSshAuthentication();
        else
            sshShell.createInteractiveShell();

    }

    public void createInteractiveShell() {
        Map<ConnectionProperty, String> connectionDetails = promptForConnectionDetails();
        try {
            SshClient ssh = new SshClient(false);
            ssh.connect(connectionDetails.get(ConnectionProperty.Hostname),
                    tryParse(connectionDetails.get(ConnectionProperty.Port)),
                            connectionDetails.get(ConnectionProperty.Username),
                            connectionDetails.get(ConnectionProperty.Password));
            Channel channel = ssh.createShell();
            // default is System.in and System.out
            // the app will stop right here until the streams are closed
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void testSshAuthentication() {

    }

    public Integer tryParse(String string) {
        try {
            return new Integer(string);
        } catch (Exception e) {
            return 22;
        }
    }
}
