package onlineticketing.datatransfer;

import net.sf.json.JSONObject;

public class CustomerDTO {
	
	private String userId;
	private String userName;
	private int phoneNumber;
	
	public CustomerDTO(String userId, String userName, int phoneNumber) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
	}
	
	public JSONObject toJSONObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("user_id",userId);
		jsonObject.put("username", userName);
		jsonObject.put("phone_number", phoneNumber);
		return jsonObject;
	}
	
}
