package internetCafe.my.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Guest {
	@Id
	@Column
	private String id; // id
	
	@Column
	private String pwd; // ��й�ȣ
	
	@Column
	private boolean member; // ȸ�� / ��ȸ��
	
	@Column
	private String name; // �̸�
	
	@Column
	private int age; // ����
	
	@Column
	private String phoneNumber; // ��ȭ��ȣ
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public boolean member() {
		return member;
	}
	public void setMember(boolean member) {
		this.member = member;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int age() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public String toString() {
		return "id:" + id + ",name:" + name;
	}
}
