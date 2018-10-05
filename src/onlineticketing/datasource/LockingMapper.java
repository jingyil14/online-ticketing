package onlineticketing.datasource;

import onlineticketing.domain.DomainObject;
import onlineticketing.onlineticketing.Session;

public class LockingMapper implements DataMapper{
	
	private DataMapper mapper;
	private ExclusiveWriteManager lockingManager;
	private int userId;
	
	public LockingMapper(DataMapper mapper) {
		this.mapper = mapper;
		this.lockingManager = ExclusiveWriteManager.getInstance();
		this.userId = Session.getInstance().getUserid();
	}
	
	@Override
	public void insert(DomainObject obj) {
		// TODO Auto-generated method stub
		mapper.insert(obj);		
	}

	@Override
	public void update(DomainObject obj) {
		// TODO Auto-generated method stub
		if(lockingManager.hasLock(obj.getId(), userId)) {
			mapper.update(obj);
		} else {
			System.err.println(userId + "does not have the lock of " + obj.getId() + " to update!");
		}
		
	}

	@Override
	public void delete(DomainObject obj) {
		// TODO Auto-generated method stub
		if(lockingManager.hasLock(obj.getId(), userId)) {
			mapper.delete(obj);
			lockingManager.releaseLock(obj.getId());
		} else {
			System.err.println(userId + "does not have the lock of " + obj.getId() + " to delete!");
		}
	}

}
