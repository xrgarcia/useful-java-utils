import com.jcraft.jsch.JSchException;
import junit.framework.TestCase;
import net.ssh.SshApplication;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

/**
 * Created by ray on 2/22/15.
 */
public class SshTest extends TestCase {

    @Test
    public void testSshConnection() throws JSchException, IOException, InterruptedException {


    }

    public void testInteractiveSSHShell() {
        SshApplication shell = new SshApplication();
        Map<SshApplication.ConnectionProperty,String> host = shell.promptForConnectionDetails();
        System.out.println("You entered "+host);
    }

}
