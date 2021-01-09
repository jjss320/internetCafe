package internetCafe.my.sample;

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

public class DBSample2 {
	private static final String PERSISTENCE_UNIT_NAME = "h2";
	private static EntityManagerFactory factory;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		// DB �ν��Ͻ� ����
		EntityManager em = factory.createEntityManager();

		// Ʈ����� ���� & DB�� Ŀ��
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		for (int i = 0; i < 5; i++) {
			em.createNativeQuery("INSERT INTO User (id, name) VALUES (?,?)").setParameter(1, i + "")
					.setParameter(2, "student-" + i).executeUpdate();
		}
		em.getTransaction().commit();

		// DB���� ����� �б�
		Guest guest = em.find(Guest.class, "0");
		em.getTransaction().begin();
		guest.setName("Update Name");
		em.getTransaction().commit();

		// ���� ���� ����
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Guest> criteriaQuery = criteriaBuilder.createQuery(Guest.class);
		Root<Guest> userRoot = criteriaQuery.from(Guest.class);
		Predicate predicate1 = criteriaBuilder.equal(userRoot.get("name"), "student-2");
		Predicate predicate2 = criteriaBuilder.equal(userRoot.get("name"), "student-3");
		Predicate predicateFinal = criteriaBuilder.or(predicate1, predicate2);

		// ���� ���� �Է�
		criteriaQuery.where(predicateFinal);

		// ���� ����
		Query query = em.createQuery(criteriaQuery);
		List<Guest> resultList = query.getResultList();

		for (Guest result : resultList) {
			System.out.println(result.toString());
		}
		System.out.println("Size: " + resultList.size());

		// DB ����
		em.close();
	}
}