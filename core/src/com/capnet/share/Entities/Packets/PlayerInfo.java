package com.capnet.share.Entities.Packets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.capnet.server.PlayerHostService;
import com.capnet.share.Entities.Player;
import com.capnet.share.packets.ByteHelper;
import com.capnet.share.packets.IPacket;

import java.nio.ByteBuffer;

/**
 * Created by michaelpollind on 4/25/16.
 */
public class PlayerInfo implements IPacket {

    private  Player player;
    public  PlayerInfo()
    {

    }

    public PlayerInfo(Player player)
    {
        this.player = player;
    }

    public  Player GetPlayer() {
        return player;
    }


    @Override
    public ByteBuffer Encode() {
        ByteBuffer buffer = ByteBuffer.allocate(ByteHelper.SizeOfString(player.GetName())+ByteHelper.INT +
                ByteHelper.VECTOR2 + ByteHelper.VECTOR2 + ByteHelper.COLOR + ByteHelper.INT + ByteHelper.BOOL + ByteHelper.BOOL);
        buffer.putInt(player.GetPlayerId());
        ByteHelper.EncodeString(buffer,player.GetName());
        ByteHelper.EncodeVector2(buffer,player.Location);
        ByteHelper.EncodeVector2(buffer,player.Velocity);
        ByteHelper.EncodeColor(buffer,player.color);
        buffer.putInt(player.Wins);
        ByteHelper.EncodeBool(buffer, player.isInPlay);
        ByteHelper.EncodeBool(buffer, player.isReady);
        return  buffer;

    }

    @Override
    public void Decode(ByteBuffer data) {

        int id = data.getInt();
        String name = ByteHelper.DecodeString(data);
        Vector2 location = ByteHelper.DecodeVector2(data);
        Vector2 velocity = ByteHelper.DecodeVector2(data);
        Color color = ByteHelper.DecodeColor(data);
        int wins = data.getInt();
        boolean isInPlay = ByteHelper.DecodeBool(data);
        boolean isReady = ByteHelper.DecodeBool(data);
        this.player = new Player(id,name);
        this.player.Location = location ;
        this.player.Velocity = velocity;
        this.player.color = color;
        this.player.Wins = wins;
        this.player.isInPlay = isInPlay;
        this.player.isReady = isReady;

        if(!isInPlay && isReady)
        {
            PlayerHostService.SetWinningPlayer(this.player);
        }
    }
}
