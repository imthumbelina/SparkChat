import org.eclipse.jetty.websocket.api.Session;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.span;

import java.text.*;
import java.util.*;


import static j2html.TagCreator.*;
import static spark.Spark.*;


/**
 * Created by aga on 25.01.17.
 */
public class Chat {

    public Map<Session, String> userUsernameMap;
    public Map<String,Boolean> chatNamesMap;


   public Chat(){
        this.userUsernameMap = new ConcurrentHashMap<>();
        this.chatNamesMap = new ConcurrentHashMap<>();
        this.chatNamesMap.put("main", true);
    }

    public static void main(String[] args){
        staticFileLocation("/public");
        staticFiles.expireTime(600);
        webSocket("/chat", ChatWebSocketHandler.class);
        init();


    }


    //Sends a message from one user to all users, along with a list of current usernames
    public  void broadcastMessage(String sender, String message,String chatName) {
        userUsernameMap.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(new JSONObject()
                        .put("userMessage", createHtmlMessageFromSender(sender, message))
                        .put("userlist", userUsernameMap.values())
                        .put("chatlist", chatNamesMap.keySet())
                        .put("chatName",chatName)
                ));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    //Builds a HTML element with a sender-name, a message, and a timestamp,
    private  String createHtmlMessageFromSender(String sender, String message) {
        if(message!=null) {
            return article().with(
                    b(sender + " says:"),
                    p(message),
                    span().withClass("timestamp").withText(new SimpleDateFormat("HH:mm:ss").format(new Date()))
            ).render();
        }
        return null;
    }


}
