package uoit.hassan.CEPMonitor;

/**
 * Hassan
 * 28/5/2017
 */
public class SuspiciousPatternAlarm {

    private String UserID;
    private String EventsID;    
    private	String Res;
    //private	int secandEvent;
    
    //private int Severity;

    public SuspiciousPatternAlarm(String userid, String eventsid) {
        this.UserID = userid;
        this.EventsID = eventsid;    	
    }
    
    public SuspiciousPatternAlarm(String userid, String eventsid, String res) {
        this.UserID = userid;
        this.EventsID = eventsid;
    	this.Res = res;
    }

    public SuspiciousPatternAlarm() {
        this("","");
    	//this(0,0);
    }

    
    
    
    public String getUserID() {
        return UserID;
    }
    public String getEventsID() {
        return EventsID;
    }
//    public int getID1() {
//        return firstEvent;
//    }
//    
//    public int getID2() {
//        return secandEvent;
//    }

//    public int getSeverity() {
//        return Severity;
//    }

//    public void setSeverity(int Severity) {
//        this.Severity = Severity;
//    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SuspiciousPatternAlarm) {
            SuspiciousPatternAlarm other = (SuspiciousPatternAlarm) obj;
            return UserID == other.UserID; //&& Res == other.Res;
        } else {
            return false;
        }
    }


    @Override
    public String toString() {
       // return "Stroke Risk Alert : { Patient : " + getUserID()+ ", Severity : " + getSeverity() + " }";
        return "Suspicious Behavior Alert : { UserID : " + getUserID() + " --> Events :" + getEventsID()+ "}";
    }

}
