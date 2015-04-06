package com.fangger.smark;


import org.jivesoftware.smack.*;
import org.jivesoftware.smack.chat.Chat;
import org.jivesoftware.smack.chat.ChatManager;
import org.jivesoftware.smack.chat.ChatMessageListener;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.StanzaFilter;
import org.jivesoftware.smack.filter.StanzaTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Stanza;
import org.jivesoftware.smack.roster.Roster;
import org.jivesoftware.smack.roster.RosterEntry;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnectionConfiguration;

import java.io.IOException;
import java.util.Collection;

/**
 * Created by p0po on 15-4-4.
 */
public class Test {
    public static void conn() {
        XMPPTCPConnectionConfiguration configuration = XMPPTCPConnectionConfiguration.builder()
                //.setUsernameAndPassword("p0po", "1")
                .setServiceName("p0po-thinkpad-x240")
                .setHost("p0po-thinkpad-x240")
                .setPort(5222)
                .setCompressionEnabled(true)
                .setSecurityMode(ConnectionConfiguration.SecurityMode.disabled)
                        //.setDebuggerEnabled(true)
                .build();

        AbstractXMPPConnection conn2 = new XMPPTCPConnection(configuration);

        try {
            conn2.connect().login("p0po", "1");

            Roster roster = Roster.getInstanceFor(conn2);
            Collection<RosterEntry> entries = roster.getEntries();
            for (RosterEntry entry : entries) {
                System.out.println(entry);
            }

            Chat chat = ChatManager.getInstanceFor(conn2)
                    .createChat("p0po1@p0po-thinkpad-x240");

            chat.sendMessage("i am p0po");

            StanzaFilter filter = new AndFilter(new StanzaTypeFilter(Message.class));

            //PacketCollector myCollector = conn2.createPacketCollector(filter);
            // Normally, you'd do something with the collector, like wait for new packets.

            StanzaListener myListener = new StanzaListener() {
                @Override
                public void processPacket(Stanza packet) throws SmackException.NotConnectedException {
                    System.out.println("packet = [" + packet + "]");
                }
            };
            conn2.addAsyncStanzaListener(myListener, filter);

            while (true) {
            }
            //conn2.disconnect();
        } catch (SmackException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMPPException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        conn();
    }
}
