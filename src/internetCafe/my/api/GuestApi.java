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
	
	public int signUp(String id, String pwd) {
		try {
//			Guest guest = new Guest();
//			guest.setId(id);
//			guest.setPwd(pwd);
//			
//			EntityTransaction transaction = em.getTransaction();
//	        transaction.begin();
//			em.persist(guest);
//			transaction.commit();
			
			em.createNativeQuery("insert into Guest(id, pwd) values(?,?)", Guest.class)
			.setParameter(1, id)
			.setParameter(2, pwd)
			.executeUpdate();
			
		} catch(Exception e) {
			return -1;
		} finally {
			em.close();
		}
		return 0;
	}
	
	public int login(String id, String pwd) {
		try {
			em.createNativeQuery("select Guest(id, pwd) from Guest where id = ? and pwd = ?)", Guest.class)
			.setParameter(1, id)
			.setParameter(2, pwd)
			.executeUpdate();
			
		} catch(Exception e) {
			return -1;
		} finally {
			em.close();
		}
		return 0;
	}
	
	public int pwdUpdate(String pwd) {
		try {
			Guest guest = new Guest();
			guest.setPwd(pwd);
			
			EntityTransaction transaction = em.getTransaction();
	        transaction.begin();
			em.persist(guest);
			transaction.commit();
			
		} catch (Exception e) {
			return -1;
		} finally {
			em.close();
		}
		return 0;
	}
}
