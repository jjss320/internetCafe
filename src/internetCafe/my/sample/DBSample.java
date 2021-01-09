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

		// DB 인스턴스 생성
		EntityManager em = factory.createEntityManager();

		// 사용자 데이터 생성
		Guest[] guestArr = new Guest[5];
		for (int i = 0; i < guestArr.length; i++) {
			guestArr[i] = new Guest();
			guestArr[i].setId(i + "");
			guestArr[i].setName("student-" + i);
		}

		// 트랜잭션 시작 & DB로 커밋
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		for (int i = 0; i < guestArr.length; i++) {
			em.persist(guestArr[i]);
		}
		transaction.commit();

		// DB에서 사용자 읽기
		Query query = em.createQuery("select u from User u where id = " + 2);
		List<Guest> resultList = query.getResultList();

		for (Guest result : resultList) {
			System.out.println(result.toString());
		}
		System.out.println("Size: " + resultList.size());

		// DB 종료
		em.close();
	}
}