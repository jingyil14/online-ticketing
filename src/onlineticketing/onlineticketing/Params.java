package onlineticketing.onlineticketing;

public class Params {
	
	//The permission of administrators
	public final static int ADMIN_PERMISSION = 0;
	
	//The permission of customers
	public final static int CUSTOMER_PERMISSION = 1;
	
	//The permission of an invalid user
	public final static int INVALID_PERMISSION = 2;
	
	//The maximum number of a film id
	public final static int MAX_FILM_ID = 1000000000;
	
	//The maximum number of a film id
	public final static int MAX_USER_ID = 1000000000;
	
	//The maximum number of an order id
	public final static int MAX_ORDER_ID = 1000000000;
	
	//The status of a ticket is available
	public final static int TICKET_AVAILABLE = 0;
	
	//The status of a ticket is locked
	public final static int TICKET_LOCKED = 1;
	
	//The status of a ticket is sold
	public final static int TICKET_SOLD = 2;
	
	//The status of an order is created
	public final static int ORDER_CREATED = 0;
	
	//The status of an order is paid
	public final static int ORDER_PAID = 1;
	
	//The status of an order is paid
	public final static int ORDER_CANCELED = 2;
	
	//The time interval that a lock of a ticket sustains
	public final static long LOCK_INTERVAL = 15;

	// the url of index page
	public final static String INDEX_URL = "/";
	
	// the url of admin home page
	public final static String ADMIN_HOME_PAGE_URL = "/AdminHomePage.html";
	
	// the url of add film page
	public final static String ADD_FILM_URL = "/AddFilm.html";
	
	// the url of edit film page
	public final static String EDIT_FILM_URL = "/EditFilm.html";
	
	// the url of view film page
	public final static String VIEW_FILM_URL = "/ViewFilm.html";
	
	// the url of view account page
	public final static String VIEW_ACCOUNT_URL = "/ViewAccount.html";
	
	// the url of view all orders page
	public final static String VIEW_ALL_ORDER_URL = "/ViewAllOrder.html";
	
	// the url of customer home page
	public final static String HOME_PAGE_URL = "/HomePage.html";
	
	// the url of view customer order page
	public final static String VIEW_ORDER_URL = "/ViewOrder.html";
	
	// the url of view customer order page
	public final static String BUY_TICKET_URL = "/BuyTicket.html";
	
	// the url of view customer order page
	public final static String PAYMENT_URL = "/Payment.html";
}
