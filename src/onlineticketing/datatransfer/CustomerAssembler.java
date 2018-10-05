package onlineticketing.datatransfer;

import java.util.ArrayList;

import onlineticketing.domain.User;

public class CustomerAssembler {
	
	public ArrayList<CustomerDTO> writeDTO(ArrayList<User> userList) {
		ArrayList<CustomerDTO> customerDTOList = new ArrayList<CustomerDTO>();
		
		for(User user : userList) {
			CustomerDTO customerDTO = new CustomerDTO(user.getId(), 
					user.getUserName(), user.getPhoneNumber());
			customerDTOList.add(customerDTO);
		}
		
		return customerDTOList;
	}
}
