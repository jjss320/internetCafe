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

import internetCafe.my.model.Head;

public class HeadAPI {
	private static final String PERSISTENCE_UNIT_NAME = "h2";
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	private static EntityManager em = factory.createEntityManager();
	
	public boolean signUp(String id, String password, String name) {
		try {
			Head head = new Head();
			head.setId(id);
			head.setName(name);
			head.setPassword(password);
			
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			em.persist(head);
			transaction.commit();
			
		} catch(Exception e) {
			return false;
		} 
		return true;
	}
	
	public Head login(String id, String password) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<Head> cQuery = criteriaBuilder.createQuery(Head.class);
		Root<Head> from = cQuery.from(Head.class);
		Predicate where1 = criteriaBuilder.equal(from.get("id"), id);
		Predicate where2 = criteriaBuilder.equal(from.get("password"), password);
		Predicate whereFinal = criteriaBuilder.and(where1, where2);
		cQuery.where(whereFinal);
		
		Query query = em.createQuery(cQuery);
		List<Head> resultList = query.getResultList();

		if (resultList.size() == 1) {
			return resultList.get(0);
		}
		else
			return null;
	}
	
	public boolean update(String id, String name, String password, String address) {
		try {
			Head head =  em.find(Head.class, id);
			
			EntityTransaction transaction = em.getTransaction();
			transaction.begin();
			head.setName(name);
			head.setPassword(password);
			head.setAddress(address);
			transaction.commit();
			
		} catch (Exception e) {
			return false;
		} 
		return true;
	}
	
	public boolean logout(){
		return true;
	}
	
	public Head read(String id) {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<Head> cQuery = criteriaBuilder.createQuery(Head.class);
		Root<Head> from = cQuery.from(Head.class);
		Predicate where = criteriaBuilder.equal(from.get("id"), id);
		cQuery.where(where);
		
		Query query = em.createQuery(cQuery);
		List<Head> resultList = query.getResultList();
			
		if (resultList.size() == 1)
			return resultList.get(0);
		else
			return null;
	}
}
