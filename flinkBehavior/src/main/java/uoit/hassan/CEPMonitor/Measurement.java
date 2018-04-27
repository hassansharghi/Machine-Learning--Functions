package uoit.hassan.CEPMonitor;
import com.google.gson.annotations.SerializedName;
import java.sql.Timestamp;

/**
 * Created by Hassan Sharghi 
 * 24/5/2017
 */

public  class Measurement {
    //{"UserID": 53, "Type": "BP", "Value": 15}
	
//	id = line[0]
//	seqId = line[1]
//	eventId = line[2]
//	attnum = line[3]
//	userid = line[4]
//	d = line[5]  #date
//	t = line[6]  #time
//	role = line[7]
//	location = line[8]
//	action = line[9]
//	res = line[10]
//	patientId = line[11]   

	
	@SerializedName("id") //global id of the event
    private int ID;
	
	@SerializedName("seqid") //sequence id 
    private int SeqID;
	
	@SerializedName("eventid") //event id in a sequence 
    private int EventID;
	
	@SerializedName("attnum") //number of attribute inside of an event
    private int Attnum;
	
	@SerializedName("date") 
    private String d;
	
	@SerializedName("time") 
    private String t;
	
	@SerializedName("role") 
    private String Role;
	
	@SerializedName("location") 
    private String Location;
	
	@SerializedName("action") 
    private String Action;
	
	@SerializedName("res") 
    private String Res;
	
	@SerializedName("patientid") 
    private String PatientID;
	
	
    @SerializedName("userid")
    private String UserID;

    @SerializedName("type")
    private String Type;

//    @SerializedName("value")
//    private float Value;
    
    public int getID() {
        return this.ID;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    
    public int getSeqID() {
        return this.SeqID;
    }
    public void setSeqID(int SeqID) {
        this.SeqID = SeqID;
    }
    
    public int getEventID() {
        return this.EventID;
    }
    public void setEventID(int EventID) {
        this.EventID = EventID;
    }
    
    public int getAttnum() {
        return this.Attnum;
    }
    public void setAttnum(int Attnum) {
        this.Attnum = Attnum;
    }
    
    public String getd() {
        return this.d;
    }
    public void setd(String d) {
        this.d = d;
    }    
    
    public String gett() {
        return this.t;
    }
    public void sett(String t) {
        this.t = t;
    }
    
    public String getRole() {
        return this.Role;
    }
    public void setRole(String Role) {
        this.Role = Role;
    }
    
    public String getLocation() {
        return this.Location;
    }
    public void setLocation(String Location) {
        this.Location = Location;
    }
    
    public String getAction() {
        return this.Action;
    }
    public void setAction(String Action) {
        this.Action = Action;
    }
    
    public String getRes() {
        return this.Res;
    }
    public void setRes(String Res) {
        this.Res = Res;
    }
    public String getPatientID() {
        return this.PatientID;
    }
    public void setPatientID(String PatientID) {
        this.PatientID = PatientID;
    }
    

    public String getUserID() {
        return this.UserID;
    }

//    public float getValue() {
//        return this.Value;
//    }

    public String getType() {
        return this.Type;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

//    public void setValue(float Value) {
//        this.Value = Value;
//    }

    public int getRisk() {

        return 0;
    }
    public String toString(){
        return new String("");
    }
	public long getCreationTime() {
		// TODO Auto-generated method stub
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp.getTime();
		
	}

}

