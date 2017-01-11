package representation;


/**
 * Class for dates
 * ID and date as String
 */
public class Date {
	private final String dateValue;
	private final int id;
	
	/**
	 * constructor
	 * @param b
	 */
	private Date(DateBuilder b){
		this.id = b.id; 
		this.dateValue = b.dateValue;
	}
	
	/**
	 * constructor
	 * @param id: ID of the date
	 */
	public Date(int id){
		Date d = new Date.DateBuilder().id(id).build();
		this.id = d.getId();
		this.dateValue = d.getDateValue();
	}
	
	/**
	 * constructor
	 * @param id
	 * @param dateValue
	 */
	public Date(int id, String dateValue){
		Date d = new Date.DateBuilder().id(id).dateValue(dateValue).build();
		this.id = d.getId();
		this.dateValue = d.getDateValue();
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
	public String getDateValue(){
		return this.dateValue;
	}
	
	/**
	 * convert to String
	 */
	@Override
	public String toString(){
		return "ID: " + id + " Datum: " + dateValue;
	}
	
	/**
	 * builds the Date object
	 *
	 */
	public static class DateBuilder{
	    private int id;
	    private String dateValue = "";	    
		public DateBuilder id(int id){
		      this.id = id;
		      return this;
		    }
		public DateBuilder dateValue(String dateValue){
			  this.dateValue = dateValue;
		      return this;
		    }
		    
		public Date build(){
		      return new Date(this);
		    }    
	}    
}