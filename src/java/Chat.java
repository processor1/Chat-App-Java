/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import javax.websocket.OnMessage;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import javax.websocket.OnOpen;

/**
 *
 * @author Mobile Apps
 */
@ServerEndpoint(value = "/chatDemo")
public class Chat {

    private Set<Session> webClients = Collections.synchronizedSet(new HashSet<>());

    @OnMessage
    public void onMessage(String message, Session session) throws java.io.IOException {

        synchronized (webClients) {
            for (Session client : webClients) {
                if (!client.equals(session)) {
                    session.getBasicRemote().sendText(message);
                }
            }
        }
    }

    @OnError
    public void onError() {
        System.out.println("Connection Error ");
    }

    @OnClose
    public void onClose(Session session) {
        webClients.remove(session);
        System.out.println("Connection Close");
    }

    @OnOpen
    public void onOpen(Session session) {
        webClients.add(session);
        System.out.println("Connection Open");
    }

}
