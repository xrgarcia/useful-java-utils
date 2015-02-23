package net.ssh;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * Created by ray on 2/23/15.
 */
public class SshClient {

    private boolean strictHostKeyChecking;
    private Session session;
    private JSch jSch;

    public SshClient(boolean strictHostKeyChecking) {
        this.strictHostKeyChecking = strictHostKeyChecking;
        this.jSch = createJsch();
    }

    public void disconnect() {
        this.session.disconnect();
    }
    public void connect(String host, int port, String username, String password) throws JSchException {
        this.session=this.jSch.getSession(username, host, port);
        this.session.setPassword(password);
        this.session.connect(30000);   // making a connection with timeout.
    }

    public Channel createShell() throws JSchException {
        Channel channel=session.openChannel("shell");
        channel.setInputStream(System.in);
        channel.setOutputStream(System.out);
        channel.connect(3*1000);

        return channel;
    }

    public JSch createJsch() {
        if(this.strictHostKeyChecking)
            JSch.setConfig("StrictHostKeyChecking", "yes");
        else
            JSch.setConfig("StrictHostKeyChecking", "no");
        JSch jsch=new JSch();

        return jsch;
    }
}
