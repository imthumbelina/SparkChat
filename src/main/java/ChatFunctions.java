import org.eclipse.jetty.websocket.api.Session;

import javax.servlet.http.Cookie;
import java.net.HttpCookie;
import java.util.List;


/**
 * Created by aga on 25.01.17.
 */


public class ChatFunctions {

    public String getUsernameFromCookies(Session session){
        List<HttpCookie> j = session.getUpgradeRequest().getCookies();
           for (HttpCookie cookie : j) {
               if(cookie.getName().equals("username")){
                   return cookie.getValue();
                }
            }
            return null;
    }

}
