package uoit.hassan.CEPMonitor;

/**
 * Hassan 
 * 28/5/2017
 */
public class TempMeasurement extends Measurement {
    public TempMeasurement(Measurement m) {
        this.setUserID(m.getUserID());
//        this.setValue (m.getValue());
        
        this.setID(m.getID());
        this.setSeqID(m.getSeqID());
        this.setEventID(m.getEventID());
        this.setAttnum(m.getAttnum());
        this.setd(m.getd());
        this.sett(m.gett());
        this.setAction(m.getAction());
        this.setLocation(m.getLocation());
        this.setRole(m.getRole());
        this.setRes(m.getRes());
        this.setPatientID(m.getPatientID());
        
        
        
    }
//    public int getRisk() {
//        int risk = 0;
//
//        risk +=  getValue() <= 36 ? 1 : 0;
//        risk +=  getValue() <= 35 ? 2 : 0;
//
//        risk +=  getValue() >= 38.1 ? 1 : 0;
//        risk +=  getValue() >= 39.1 ? 1 : 0;
//
//        return risk;
//    }
    @Override
    public String toString(){
        return new String("Event :"+ getID() + " , UserID: "+ getUserID() + " , Role : " + getRole());
    }
}
