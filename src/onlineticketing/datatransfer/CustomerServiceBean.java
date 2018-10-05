package onlineticketing.datatransfer;

import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onlineticketing.domain.User;
import onlineticketing.service.CustomerService;

public class CustomerServiceBean {
	
	public String getAllCustomerJson() {
		ArrayList<User> customerList = new CustomerService().viewAllUserInformation();
		ArrayList<CustomerDTO> customerDTOList = new CustomerAssembler().writeDTO(customerList);
		JSONArray jsonArray = getCustomerJsonArray(customerDTOList);
		return jsonArray.toString();
	}
	
	public JSONArray getCustomerJsonArray(ArrayList<CustomerDTO> customerDTOList) {
		JSONArray jsonArray = new JSONArray();
		for (CustomerDTO customerDTO : customerDTOList) {
			JSONObject jsonObject = customerDTO.toJSONObject();
			jsonArray.add(jsonObject);
		}
		
		return jsonArray;
	}
}
