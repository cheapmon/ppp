package representation;



public class Date {
	private final String dateValue;
	private final int id;
	
	
	private Date(DateBuilder b){
		this.id = b.id; 
		this.dateValue = b.dateValue;
		
	}
	
	public Date(int id){
		Date d = new Date.DateBuilder().id(id).build();
		this.id = d.getId();
		this.dateValue = d.getDateValue();
	}
	
	public Date(int id, String dateValue){
		Date d = new Date.DateBuilder().id(id).dateValue(dateValue).build();
		this.id = d.getId();
		this.dateValue = d.getDateValue();
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getDateValue(){
		return this.dateValue;
	}
	
	@Override
	public String toString(){
		return "ID: " + id + " Datum: " + dateValue;
	}
	
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
	
  /*private final long id;
  private final String dateValue;
  private static final AtomicLong counter = new AtomicLong(100);
 
  private Date(DateBuilder builder){
    this.id = builder.id;
    this.dateValue = builder.dateValue;
  }
  
  public Date(){
    Date cust = new Date.DateBuilder().id().build();
      this.id = cust.getId();
      this.dateValue = cust.getdateValue();
  }
  
  public Date(long id, String dateValue){
      Date cust = new Date.DateBuilder().id()
           .dateValue(dateValue)
           .build();
      this.id = cust.getId();
      this.dateValue = cust.getdateValue();
  }
  
  public long getId(){
    return this.id;
  }

  public String getdateValue() {
    return this.dateValue;
  }

  
  @Override
  public String toString(){
    return "ID: " + id 
        + " Datum: " + dateValue;
  }  
  
  public static class DateBuilder{
    private long id;
    private String dateValue = "";
    
    public DateBuilder id(){
      this.id = Date.counter.getAndIncrement();
      return this;
    }
    
    public DateBuilder dateValue(String dateValue){
      this.dateValue = dateValue;
      return this;
    }
    
    public Date build(){
      return new Date(this);
    }
    
  }*/    
}