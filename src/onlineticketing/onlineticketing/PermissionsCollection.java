package onlineticketing.onlineticketing;

import java.util.ArrayList;

public class PermissionsCollection {
	/**
	 * Get the list of valid urls for the passed-in permission
	 * 
	 * @param permission	the permission of a user
	 * @return				a list of urls
	 */
	public static ArrayList<String> getPermissionCollection(int permission) {
		ArrayList<String> permissionCollection = null;
		if (permission == Params.ADMIN_PERMISSION) {
			permissionCollection = getAdminPermission();
		} else if (permission == Params.CUSTOMER_PERMISSION) {
			permissionCollection = getCustomerPermission();
		} else {
			permissionCollection = getInvalidPermission();
		}
		return permissionCollection;
	}
	
	/**
	 * Get the list of valid urls for admin
	 * 
	 * @return	a list of urls
	 */
	private static ArrayList<String> getAdminPermission() {
		ArrayList<String> adminPermission = new ArrayList<String>();
		adminPermission.add(Params.INDEX_URL);
		adminPermission.add(Params.ADMIN_HOME_PAGE_URL);
		adminPermission.add(Params.ADD_FILM_URL);
		adminPermission.add(Params.EDIT_FILM_URL);
		adminPermission.add(Params.VIEW_FILM_URL);
		adminPermission.add(Params.VIEW_ACCOUNT_URL);
		adminPermission.add(Params.VIEW_ALL_ORDER_URL);
		return adminPermission;
	}
	
	/**
	 * Get the list of valid urls for customer
	 * 
	 * @return	a list of urls
	 */
	private static ArrayList<String> getCustomerPermission() {
		ArrayList<String> customerPermission = new ArrayList<String>();
		customerPermission.add(Params.INDEX_URL);
		customerPermission.add(Params.HOME_PAGE_URL);
		customerPermission.add(Params.VIEW_ORDER_URL);
		customerPermission.add(Params.BUY_TICKET_URL);
		customerPermission.add(Params.PAYMENT_URL);
		return customerPermission;
	}
	
	/**
	 * Get the list of valid urls for invalid user
	 * 
	 * @return	a list of urls
	 */
	private static ArrayList<String> getInvalidPermission() {
		ArrayList<String> invalidPermission = new ArrayList<String>();
		invalidPermission.add(Params.INDEX_URL);
		return invalidPermission;
	}
}
