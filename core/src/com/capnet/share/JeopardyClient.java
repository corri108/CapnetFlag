package com.capnet.share;

import java.nio.ByteBuffer;

public class JeopardyClient {
	public String Name = "";
	public int Score = 0;
	public JeopardyClient(String name)
	{
		this.Name = name;
	}

	public  JeopardyClient()
	{

	}

	public  int size()
	{
		return 4+4+ (Name.length()*2);
	}

	public void encode(ByteBuffer buffer)
	{
		buffer.putInt(Score);
		buffer.putInt(Name.length());
		for(int x = 0; x < Name.length(); x++)
			buffer.putChar( Name.charAt(x));
	}

	public  void decode(ByteBuffer buffer)
	{
		Score = buffer.getInt();
		int size = buffer.getInt();
		String name = new String();
		for(int x = 0; x < size; x++)
			name += buffer.getChar();

		this.Name = name;
	}
}
