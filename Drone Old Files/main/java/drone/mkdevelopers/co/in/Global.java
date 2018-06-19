package clickbaitstudio.developerstudentclub.com.in;

/**
 *
 * By Developer Student Club
 *
 * K Varshit Ratna (lead)
 * Manikanth P (core team member)
 * Deveraj (core team member)
 */
public class Global {

    public static final String ROOT_URL = "http://griet.ga/drone/";

    public static String udid="",IMEI="",devicename="",phone="";



    public static String webviewurl="";


    public static User au = new User();



   // public static String getsRoll_No() {return Roll_No;}
    //public static String getsName() {return Name;}
    //public static String getsFathers_name() {
      //  return Auth;
    //}

    //For Clickoo Class/////
    public static String id="";
    public static String views="";
    public static String liked="";
    public static String description="";
    public static String  clickooimag="";
    public static String  title="";
    // Till Here FOr CLickoo Class



    public static void clearData()
    {
        Global.au = new User();
    }

}