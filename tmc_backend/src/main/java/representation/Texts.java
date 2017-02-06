package representation;

/**
 * Class for texts (policies)
 * text as String and date as String
 */
public class Texts{
    private String text;
    private String date;
    private String link;
    
    /**
     * constructor
     * @param tb
     */
    private Texts(TextBuilder tb){
        this.text = tb.text;
        this.date = tb.date;
        this.link = tb.link;
    }
    
    /**
     * constructor
     * @param text: policy
     * @param date: date of the saved policy
     * @param link: link of the saved policy
     */
    public Texts(String text, String date, String link){
        Texts t = new Texts.TextBuilder()
        .gettext(text)
        .getdate(date)
        .getlink(link)
        .build();
        this.text = t.gettext();
        this.date = t.getdate();
        this.link = t.getlink();
    }
    
    /**
     * get method for text
     * @return
     */
    public String gettext(){
        return this.text;
    }
    
    /**
     * get method for date
     * @return
     */
    public String getdate(){
        return this.date;
    }
    
    /**
     * get method for link
     * @return
     */
    public String getlink(){
        return this.link;
    }
    
    /**
     * convert to String
     */
    @Override
    public String toString(){
        return "Text: " + text + " Datum: " + date + " Link: " + link;
    }
    
    /**
     * builds the Texts object
     */
    public static class TextBuilder{
        private String text = "";
        private String date = "";
        private String link = "";
        
        /**
         * builds the text
         */
        public TextBuilder gettext(String text){
            this.text = text;
            return this;
        }
        /**
         * get the date
         */
        public TextBuilder getdate(String date){
            this.date = date;
            return this;
        }
        /**
         * get the link
         */
        public TextBuilder getlink(String link){
            this.link = link;
            return this;
        }
        /**
         * builds Texts
         */
        public Texts build(){
            return new Texts(this);
        }
    }
}