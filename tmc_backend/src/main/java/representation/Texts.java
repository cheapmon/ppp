package representation;

/**
 * Class for texts (policies)
 * text as String and date as String
 */
public class Texts{
	private String text;
	private String date;
	
	/**
	 * constructor
	 * @param tb
	 */
	private Texts(TextBuilder tb){
		this.text = tb.text;
		this.date = tb.date;
	}
	
	/**
	 * constructor
	 * @param text: policy
	 * @param date: date of the saved policy
	 */
	public Texts(String text, String date){
		Texts t = new Texts.TextBuilder()
            .gettext(text)
            .getdate(date)
            .build();
		this.text = t.gettext();
		this.date = t.getdate();
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
	 * convert to String
	 */
	@Override
	public String toString(){
		return "Text: " + text + " Datum: " + date;
	}
	
	/**
	 * builds the Texts object
	 */
	public static class TextBuilder{
		private String text = "";
		private String date = "";
		
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
         * builds Texts
         */
		public Texts build(){
			return new Texts(this);
		}
	}
}