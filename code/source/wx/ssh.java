package wx;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.File;
import java.io.InputStream;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
// --- <<IS-END-IMPORTS>> ---

public final class ssh

{
	// ---( internal utility methods )---

	final static ssh _instance = new ssh();

	static ssh _newInstance() { return new ssh(); }

	static ssh _cast(Object o) { return (ssh)o; }

	// ---( server methods )---




	public static final void execCmd (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(execCmd)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required host
		// [i] field:0:required port
		// [i] field:0:required user
		// [i] field:0:required password
		// [i] field:0:optional privateKeyFilePath
		// [i] field:0:optional strictHostKeyChecking {"true","false"}
		// [i] field:0:required command
		// [i] field:0:required waitForMessage {"true","false"}
		// [o] field:0:required message
		// [o] field:0:required returnCode
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	host = IDataUtil.getString( pipelineCursor, "host" );
			int	port = IDataUtil.getInt( pipelineCursor, "port", 22 );
			String	user = IDataUtil.getString( pipelineCursor, "user" );
			String	password = IDataUtil.getString( pipelineCursor, "password" );
			IDataUtil.remove(pipelineCursor, "password");
			String	privateKeyFilePath = IDataUtil.getString( pipelineCursor, "privateKeyFilePath" );
			boolean strictHostKeyChecking = IDataUtil.getBoolean(pipelineCursor, "strictHostKeyChecking", true);
			String	command = IDataUtil.getString( pipelineCursor, "command" );
			boolean waitForMessage = IDataUtil.getBoolean( pipelineCursor, "waitForMessage", false);
		pipelineCursor.destroy();
		
		// Initialize output
		String message = new String();
		int returnCode = -1;
		
		try{
			JSch jsch=new JSch();
			if (privateKeyFilePath != null) {
				File privateKeyFile = new File(privateKeyFilePath);
				jsch.addIdentity(privateKeyFile.getAbsolutePath(), password);
			}
			Session session=jsch.getSession(user, host, port);
			session.setPassword(password);
		
			// Strict host key checking
			java.util.Properties config=new java.util.Properties();
			String strictHostKeyCheckingStr = null;
			if (strictHostKeyChecking) {
				strictHostKeyCheckingStr = "yes";
			} else {
				strictHostKeyCheckingStr = "no";
			}
			config.put("StrictHostKeyChecking", strictHostKeyCheckingStr);
			session.setConfig(config);
			
			session.connect();
		
			Channel channel=session.openChannel("exec");
			((ChannelExec)channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec)channel).setErrStream(System.err);
		
			InputStream in=channel.getInputStream();
		
			channel.connect();
		
			// Retrieve output, if applicable
			if (waitForMessage) {
				byte[] tmp=new byte[1024];
				while(true) {
					while(in.available()>0) {
						int i=in.read(tmp, 0, 1024);
						if(i<0)
							break;
						message = message + new String(tmp, 0, i);
					}
					if(channel.isClosed()) {
						returnCode = channel.getExitStatus();
						break;
					}
					try {
						Thread.sleep(1000);
					} catch(Exception ee) {}
				}
			}
		
			channel.disconnect();
			session.disconnect();
		} catch(Exception e) {
			throw new ServiceException(e);
		}
		
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "message", message );
		IDataUtil.put( pipelineCursor_1, "returnCode", "" + returnCode );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	
	
	// --- <<IS-END-SHARED>> ---
}

