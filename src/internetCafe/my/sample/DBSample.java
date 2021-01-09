package internetCafe.my.sample;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import internetCafe.my.model.Guest;

public class DBSample {
	private static final String PERSISTENCE_UNIT_NAME = "h2";
	private static EntityManagerFactory factory;

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);

		// DB �ν��Ͻ� ����
		EntityManager em = factory.createEntityManager();

		// ����� ������ ����
		Guest[] guestArr = new Guest[5];
		for (int i = 0; i < guestArr.length; i++) {
			guestArr[i] = new Guest();
			guestArr[i].setId(i + "");
			guestArr[i].setName("student-" + i);
		}

		// Ʈ����� ���� & DB�� Ŀ��
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		for (int i = 0; i < guestArr.length; i++) {
			em.persist(guestArr[i]);
		}
		transaction.commit();

		// DB���� ����� �б�
		Query query = em.createQuery("select u from User u where id = " + 2);
		List<Guest> resultList = query.getResultList();

		for (Guest result : resultList) {
			System.out.println(result.toString());
		}
		System.out.println("Size: " + resultList.size());

		// DB ����
		em.close();
	}
}