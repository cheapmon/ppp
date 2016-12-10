package representation;

public class Texts{
	private String text;
	private String date;
	
	private Texts(TextBuilder tb){
		this.text = tb.text;
		this.date = tb.date;
	}
	
	public Texts(String text, String date){
		Texts t = new Texts.TextBuilder().gettext(text).getdate(date).build();
		this.text = t.gettext();
		this.date = t.getdate();
	}
	
	public String gettext(){
		return this.text;
	}
	
	public String getdate(){
		return this.date;
	}
	
	@Override
	public String toString(){
		return "Text: " + text + " Datum: " + date;
	}
	
	public static class TextBuilder{
		private String text = "";
		private String date = "";
		
		public TextBuilder gettext(String text){
			this.text = text;
			return this;
		}
		public TextBuilder getdate(String date){
			this.date = date;
			return this;
		}
		
		public Texts build(){
		      return new Texts(this);
		    }
		
	}
	
public static void main (String[] args){
	Texts a = new Texts("Hallo","09. Januar 2016");
	System.out.println(a);
}
}