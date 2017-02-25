package representation;


/**
 * Class for dates
 * ID and systemDate  and  displayDate as String
 */
public class Date {
    private final String systemDateValue;
    private final String displayDateValue;
    private final int id;
    
    /**
     * constructor
     * @param b
     */
    private Date(DateBuilder b){
        this.id = b.id;
        this.systemDateValue = b.systemDateValue;
        this.displayDateValue = b.displayDateValue;
        
    }
    
    /**
     * constructor
     * @param id: ID of the date
     */
    public Date(int id){
        Date d = new Date.DateBuilder().id(id).build();
        this.id = d.getId();
        this.systemDateValue = d.getsystemDateValue();
        this.displayDateValue = d.getdisplayDateValue();
    }
    
    /**
     * constructor
     * @param id
     * @param systemDateValue better for coding
     * @param displayDateValue more readable for human
     */
    public Date(int id, String systemDateValue, String displayDateValue){
        Date d = new Date.DateBuilder()
            .id(id)
            .systemDateValue(systemDateValue)
            .displayDateValue(displayDateValue)
            .build();
        this.id = d.getId();
        this.systemDateValue = d.getsystemDateValue();
        this.displayDateValue = d.getdisplayDateValue();
    }
    
    /**
     * get method for ID
     * @return ID
     */
    public int getId(){
        return this.id;
    }
    
    /**
     * get method for date
     * @return date
     */
    public String getsystemDateValue(){
        return this.systemDateValue;
    }
    
    /**
     * get method for date
     * @return date
     */
    public String getdisplayDateValue(){
        return this.displayDateValue;
    }
    
    /**
     * convert to String
     */
    @Override
    public String toString(){
        return "ID: " + id + " Systemdatum: " + systemDateValue + " Abzeigedatum: " + displayDateValue;
    }
    
    /**
     * builds the Date object
     *
     */
    public static class DateBuilder{
        private int id;
        private String systemDateValue = "";
        private String displayDateValue = "";
        /**
         * builds id
         *
         */
        public DateBuilder id(int id){
            this.id = id;
            return this;
        }
        /**
         * builds system date value
         *
         */
        public DateBuilder systemDateValue(String systemDateValue){
            this.systemDateValue = systemDateValue;
            return this;
        }
        /**
         * builds display date value
         *
         */
        public DateBuilder displayDateValue(String displayDateValue){
            this.displayDateValue = displayDateValue;
            return this;
        }
        /**
         * builds Date
         *
         */
        public Date build(){
            return new Date(this);
        }    
    }    
}