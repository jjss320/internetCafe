package internetCafe.my;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import internetCafe.my.model.Guest;

public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "h2";//h2 -> 휘발성 DB 메모리에 올리는 것, 디스크 등 저장장치에 저장 시 MySQL 등 사용
    private static EntityManagerFactory factory; // DB의 영속성(비휘발성)과 관련된 부분으로 Entity Manager를 관리해주는 역할(여러 스레드가 동시에 접근해주는 것을 막아주게)

    @SuppressWarnings("unchecked")// 미확인 오퍼레이션과 관련된 경고를 억제
	public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // PERSISTENCE_UNIT_NAME(h2)이라는 이름의 persistence-unit을 persistence.xml에서 찾아 엔티티 매니저 팩토리 생성
        
        // DB 인스턴스 생성
        EntityManager em = factory.createEntityManager();
        
        // 사용자 데이터 생성
        Guest[] userArr = new Guest[5];
        for(int i=0; i<userArr.length; i++) {
        	userArr[i] = new Guest();
        	userArr[i].setId(i + "");
        	userArr[i].setName("student-" + i);
        }
        
        // 트랜잭션 시작 & DB로 커밋
        EntityTransaction transaction = em.getTransaction(); // 한번에 처리되기를 원하는 작업일 경우 트랜잭션을 사용
        transaction.begin(); 
        for(int i=0; i<userArr.length; i++) {
        	em.persist(userArr[i]);
        }
        transaction.commit(); 
//        for(int i=0; i<userArr.length; i++) {
//        	transaction.begin();
//        	em.persist(userArr[i]);
//        	transaction.commit(); 
//        } // 이렇게 할 경우 1~2까지는 성공했는데 3에서 에러 시 -> 2까지는 작업이 수행됨. 이는 원자성(작업이 수행된다면 전부 되거나 전부 안되야하는 특성)에 위배. 이를 방지 하기 위해 위와 같은 방식으로 진행
        
        // DB에서 사용자 읽기
        Query query = em.createQuery("select t from Guest t"); // 여기서 t는 Guest를 t로 보겠다는 의미, select t를 하면 select *과 같은 의미로 전부 가져오게 된다. 이는 여기서 사용하는 라이브러리의 규칙
        List<Guest> resultList = query.getResultList();
        
        for (Guest result : resultList) { // resultList에서 하나씩 가져와서 수행하라 i로 for문 돌리지 않아도 된다.
            System.out.println(result.toString());
        }
        System.out.println("Size: " + resultList.size());
        
        // DB 종료
        em.close();
    }
}