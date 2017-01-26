import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aga on 25.01.17.
 */


@WebSocket
public class ChatWebSocketHandler {
    ChatFunctions chat = new ChatFunctions();
    private String sender, msg;

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {

    }

    @OnWebSocketClose
    public void onClose(Session user, int statuscode,String reason ){
        String username = Chat.userUsernameMap.get(user);
        Chat.userUsernameMap.remove(user);
        Chat.broadcastMessage(sender = "Server", msg = (username + " left the chat"),null);
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) throws JSONException {
        System.out.println(message);
        JSONObject jsonObj = new JSONObject(message);
        String type = jsonObj.getString("type");
        String channel = jsonObj.getString("chatName");

        if (type.equals("login")) {
            String login = jsonObj.getString("username");
            if(login == null) login="Anonimowy";
            Chat.userUsernameMap.put(user, login);
           Chat.broadcastMessage(msg = (login + " joined the chat"), sender = "This is my first message",null);
        }
        if (type.equals("message")){
            if(channel.equals("chatbox")){

                String bot = jsonObj.getString("text");
                ChatBot chatbot = new ChatBot();
                String answer = chatbot.getAnswer(bot);
                Chat.broadcastMessage(sender = "bot", msg = answer, "chatbox");
            }
            else {
                String mes = jsonObj.getString("text");
                String chatName = jsonObj.getString("chatName");
                Chat.broadcastMessage(sender = Chat.userUsernameMap.get(user), msg = mes, chatName);
            }

        }

        if(type.equals("createRoom")){
            String name = jsonObj.getString("text");
            Chat.chatNamesMap.put(name,true);
            Chat.broadcastMessage(sender = null, msg = null,null);
        }



    }


}
