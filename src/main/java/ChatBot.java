import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Created by aga on 26.01.17.
 */
public class ChatBot {

    public ChatBot(){

    }

    public String getAnswer(String question){

        if(question.equals("Która godzina?")){
            String time=LocalDateTime.now().toString();
            return time;
        }

        if(question.equals("Jaki mamy dzień tygodnia?")){

            Calendar calendar = Calendar.getInstance();
            int day=calendar.get(Calendar.DAY_OF_WEEK);
            String today = "";

            switch(day){
                case 1:
                    today = "niedziela";
                    break;
                case 2:
                    today ="poniedziałek";
                    break;
                case 3:
                    today ="wtorek";
                    break;
                case 4:
                    today ="środa";
                    break;
                case 5:
                    today="czwartek";
                    break;
                case 6:
                    today="piątek";
                    break;
                case 7:
                    today="sobota";
                    break;

            }
            return today;

        }

        if(question.equals("Jaka jest pogoda?")){
            try {
                JsonMaker jsonmaker = new JsonMaker();
                String weather = jsonmaker.getWeather();
                return weather;
            }
            catch(Exception e){
                System.out.println("zlapano");
            }


        }

        return "Nie znam odpowiedzi na twoje pytanie :c";
    }

}
