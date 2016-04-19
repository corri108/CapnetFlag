package com.capnet.share;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/9/16.
 */
public class GameRoomData {

        public  int RoomId = 0;
        public int MaxClients = 0;
        public int Clients= 0;
        public String Name= "";
        public  int size()
        {
            return 4+4+4+4+ (Name.length()*2);
        }

        public String getName() {
            return Name;
        }

        public String getClientString() {
            return Clients + " / " + MaxClients;
        }

        public int getRoomId() {
            return RoomId;
        }

        public  void encode(ByteBuffer buffer)
        {
            buffer.putInt(RoomId);
            buffer.putInt(MaxClients);
            buffer.putInt(Clients);

            buffer.putInt(Name.length());
            for (int x =  0; x < Name.length(); x++)
            {
                buffer.putChar(Name.charAt(x));
            }
        }
        public  void decode(ByteBuffer buffer)
        {
            this.RoomId = buffer.getInt();
            this.MaxClients = buffer.getInt();
            this.Clients = buffer.getInt();

            int cout = buffer.getInt();
            for(int x = 0; x < cout; x++)
            {
                this.Name += buffer.getChar();
            }
        }

}
