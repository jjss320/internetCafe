package internetCafe.my.api;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import internetCafe.my.model.Guest;

public class GuestApi {
	private static final String PERSISTENCE_UNIT_NAME = "h2";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private EntityManager em = factory.createEntityManager();
	
	public boolean signUp(String id, String name, String pwd) {
		try {
			Guest guest = new Guest();
			guest.setId(id);
			guest.setName(name);
			guest.setPwd(pwd);
			
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.persist(guest);
			transaction.commit();
			
		} catch(Exception e) {
			return false;
		} finally {
			em.close();
		}
		return true;
	}
	
	public Guest login(String id, String pwd) {
		Query query = em.createQuery("select g from Guest g where id = " + id + " and pwd = " + pwd);
		List<Guest> resultList = query.getResultList();

		if (resultList.size() == 1) {
			return resultList.get(0);
		}
		else
			return null;
	}
	
	public boolean update(String id, String name, String pwd, String address) {
		try {
			Guest guest =  em.find(Guest.class, id);
			
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			guest.setName(name);
			guest.setPwd(pwd);
			guest.setAddress(address);
			transaction.commit();
			
		} catch (Exception e) {
			return false;
		} finally {
			em.close();
		}
		return true;
	}
	
	public Guest read(String id) {
		Query query = em.createQuery("select g from Guest g where id = " + id);
		List<Guest> resultList = query.getResultList();
			
		if (resultList.size() == 1)
			return resultList.get(0);
		else
			return null;
	}
}
