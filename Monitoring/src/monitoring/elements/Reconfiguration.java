package monitoring.elements;



public class Reconfiguration {
    private Configuration confSource;
    private Configuration confDestination;
    private String event;



    private Boolean validateReconfig;


    public Reconfiguration(Configuration confSource,Configuration confDestination){
        this.confSource=confSource;
        this.confDestination=confDestination;
      
    }


    public Configuration getConfSource() {
        return confSource;
    }

    public Configuration getConfDestination() {
        return confDestination;
    }

    public void setConfSource(Configuration confSource) {
        this.confSource = confSource;
    }

    public void setConfDestination(Configuration confDestination) {
        this.confDestination = confDestination;
    }
    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
    public Boolean getValidateReconfig() {
        return validateReconfig;
    }

    public void setValidateReconfig(Boolean validateReconfig) {
        this.validateReconfig = validateReconfig;
    }


}
