package internetCafe.my.api;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import internetCafe.my.model.Guest;

public class GuestApi {
	private static final String PERSISTENCE_UNIT_NAME = "h2";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();
	
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
		} 
		return true;
	}
	
	public Guest login(String id, String pwd) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<Guest> cQuery = criteriaBuilder.createQuery(Guest.class);
		Root<Guest> from = cQuery.from(Guest.class);
		Predicate where1 = criteriaBuilder.equal(from.get("id"), id);
		Predicate where2 = criteriaBuilder.equal(from.get("pwd"), pwd);
		Predicate whereFinal = criteriaBuilder.and(where1, where2);
		cQuery.where(whereFinal);
		
		Query query = em.createQuery(cQuery);
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
		} 
		return true;
	}
	
	public Guest read(String id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<Guest> cQuery = criteriaBuilder.createQuery(Guest.class);
		Root<Guest> from = cQuery.from(Guest.class);
		Predicate where = criteriaBuilder.equal(from.get("id"), id);
		cQuery.where(where);
		
		Query query = em.createQuery(cQuery);
		List<Guest> resultList = query.getResultList();
			
		if (resultList.size() == 1)
			return resultList.get(0);
		else
			return null;
	}
}
