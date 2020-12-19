package internetCafe.my;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import internetCafe.my.model.Guest;

public class Main {
	private static final String PERSISTENCE_UNIT_NAME = "h2";//h2 -> �ֹ߼� DB �޸𸮿� �ø��� ��, ��ũ �� ������ġ�� ���� �� MySQL �� ���
    private static EntityManagerFactory factory; // DB�� ���Ӽ�(���ֹ߼�)�� ���õ� �κ����� Entity Manager�� �������ִ� ����(���� �����尡 ���ÿ� �������ִ� ���� �����ְ�)

    @SuppressWarnings("unchecked")// ��Ȯ�� ���۷��̼ǰ� ���õ� ��� ����
	public static void main(String[] args) {
        factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME); // PERSISTENCE_UNIT_NAME(h2)�̶�� �̸��� persistence-unit�� persistence.xml���� ã�� ��ƼƼ �Ŵ��� ���丮 ����
        
        // DB �ν��Ͻ� ����
        EntityManager em = factory.createEntityManager();
        
        // ����� ������ ����
        Guest[] userArr = new Guest[5];
        for(int i=0; i<userArr.length; i++) {
        	userArr[i] = new Guest();
        	userArr[i].setId(i + "");
        	userArr[i].setName("student-" + i);
        }
        
        // Ʈ����� ���� & DB�� Ŀ��
        EntityTransaction transaction = em.getTransaction(); // �ѹ��� ó���Ǳ⸦ ���ϴ� �۾��� ��� Ʈ������� ���
        transaction.begin(); 
        for(int i=0; i<userArr.length; i++) {
        	em.persist(userArr[i]);
        }
        transaction.commit(); 
//        for(int i=0; i<userArr.length; i++) {
//        	transaction.begin();
//        	em.persist(userArr[i]);
//        	transaction.commit(); 
//        } // �̷��� �� ��� 1~2������ �����ߴµ� 3���� ���� �� -> 2������ �۾��� �����. �̴� ���ڼ�(�۾��� ����ȴٸ� ���� �ǰų� ���� �ȵǾ��ϴ� Ư��)�� ����. �̸� ���� �ϱ� ���� ���� ���� ������� ����
        
        // DB���� ����� �б�
        Query query = em.createQuery("select t from Guest t"); // ���⼭ t�� Guest�� t�� ���ڴٴ� �ǹ�, select t�� �ϸ� select *�� ���� �ǹ̷� ���� �������� �ȴ�. �̴� ���⼭ ����ϴ� ���̺귯���� ��Ģ
        List<Guest> resultList = query.getResultList();
        
        for (Guest result : resultList) { // resultList���� �ϳ��� �����ͼ� �����϶� i�� for�� ������ �ʾƵ� �ȴ�.
            System.out.println(result.toString());
        }
        System.out.println("Size: " + resultList.size());
        
        // DB ����
        em.close();
    }
}