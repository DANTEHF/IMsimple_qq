package com.example.hf876.qqsimple.server;


import android.util.Log;

import org.jivesoftware.smack.Connection;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketCollector;
import org.jivesoftware.smack.SmackConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketIDFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.IQ;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.filetransfer.FileTransfer;

import static org.jivesoftware.smackx.filetransfer.FileTransfer.Error.connection;

/**
 * Created by hf876 on 2016/12/3.
 */

public class ServerControl {

    XMPPConnection  connection;
    public boolean conServer(){
        ConnectionConfiguration config=new ConnectionConfiguration("127.0.0.1", 5222);
        config.setSASLAuthenticationEnabled(false);

        try{
           connection= new XMPPConnection(config);
           connection.connect();
            return true;
        }catch (XMPPException e) {
            e.printStackTrace();
        }

        return false;
    }

    public String regist(String account,String password){
        if(connection ==null ){
            return "0";
        }
        Registration reg = new Registration();
        reg.setType(IQ.Type.SET);
        reg.setTo(connection.getServiceName());
        reg.setUsername(account);
        reg.setPassword(password);
        reg.addAttribute("android","dante_createUser_android");
        PacketFilter filter= new AndFilter(new PacketIDFilter(reg.getPacketID()),new PacketTypeFilter(IQ.class));
        PacketCollector collector = connection.createPacketCollector(filter);
        connection.sendPacket(reg);
        IQ result = (IQ) collector.nextResult(SmackConfiguration.getPacketReplyTimeout());

        collector.cancel();
        if(result==null){
            Log.e("RegistActivity","No response from Server");
            return "0";
        }else  if (result.getType()==IQ.Type.RESULT){
             return "1";
        }


        return  " ";   }

}





