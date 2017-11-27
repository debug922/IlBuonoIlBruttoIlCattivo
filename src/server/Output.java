package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class Output extends ObjectOutputStream {

	public Output(OutputStream out) throws IOException {
		super(out);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected boolean enableReplaceObject(boolean enable) throws SecurityException {
		// TODO Auto-generated method stub
		return super.enableReplaceObject(enable);
	}
	
	@Override
	protected Object replaceObject(Object obj) throws IOException {
		// TODO Auto-generated method stub
		return super.replaceObject(obj);
	}
	
	@Override
	protected void writeObjectOverride(Object obj) throws IOException {
		// TODO Auto-generated method stub
		super.writeObjectOverride(obj);
	}
	
	@Override
	public void flush() throws IOException {
		// TODO Auto-generated method stub
		super.flush();
	}
}
