package com.example.cmpe275.openhack;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import com.example.cmpe275.openhack.dao.OrganizationDao;
import com.example.cmpe275.openhack.dao.OrganizationDaoImpl;
import com.example.cmpe275.openhack.entity.Address;
import com.example.cmpe275.openhack.entity.Organization;
import com.example.cmpe275.openhack.entity.User;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class OpenhackApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenhackApplication.class, args);
		EntityManagerFactory emfactory;
		emfactory = Persistence.createEntityManagerFactory("openhack");
		EntityManager em = emfactory.createEntityManager();
		
		Address address1 = new Address("33 S", "SJ", "CA", "95113", "USA");
		Address address2 = new Address("201 S", "SJ", "CA", "95112", "USA");
		Address address3 = new Address("101 N", "SJ", "CA", "95111", "USA");
		
		Organization org1 = new Organization();
		org1.setAddress(address3);
		org1.setName("Org1");
		org1.setDescription("This organiztion was founded in 2010");
//		org1.setOwner(user1);
		Organization org2 = new Organization();
		org2.setAddress(address2);
		org2.setName("Org2");
//		org2.setOwner(user2);
		org2.setDescription("This was founded in 2012");
		Organization org3 = new Organization();
		org3.setAddress(new Address("901 San Carlos", "Milpitas", "CA", "95671", "USA"));
		org3.setName("Org3");
		org3.setDescription("This is a new organization");
		
		OrganizationDao orgdao = new OrganizationDaoImpl();
//		Organization deleted_organization = orgdao.delete(1);
//		System.out.println("\nThe organization that was deleted was : \n"+deleted_organization.toString());
		Organization result_org1 = orgdao.create(org1);
		Organization result_org2 = orgdao.create(org2);
		Organization result_org3 = orgdao.create(org3);
		
		System.out.println("\nOrganization 1 created with id : "+result_org1.getId());
		System.out.println("\nOrganization 2 created with id : "+result_org2.getId());
		System.out.println("\nOrganization 3 created with id : "+result_org3.getId());
		
		Organization result1 = orgdao.findOrganizationById(result_org1.getId());
		Organization result2 = orgdao.findOrganizationById(result_org2.getId());
		Organization result3 = orgdao.findOrganizationById(result_org3.getId());
		
		System.out.println("\nBefore updation, org2 is : \n"+result2.toString());
		
		result2.setName("Organization2");
		result2.setAddress(new Address("100 San Salvador", "SJ", "CA", "95116", "USA"));
		Organization updated_org = orgdao.update(result2);
		
		System.out.println("\nAfter updation, org2 is : \n"+updated_org.toString());
		
		
		User user1 = new User();
		user1.setEmail("darshil@gmail.com");
		user1.setName("Darshil");
		user1.setAddress(address1);
		user1.setScreenName("Darshil");
		user1.setAboutMe("SJSU Graduate student");
		User user2 = new User();
		user2.setEmail("kavina@gmail.com");
		user2.setName("Kavina");
		user2.setAddress(address2);
		user2.setScreenName("DesaiKavina");
		user2.setAboutMe("Software Engineering graduate student");
		User user3 = new User();
		user3.setEmail("sayalee@gmail.com");
		user3.setName("Sayalee");
		user3.setAddress(address3);
		user3.setScreenName("Sayaleee");
		user3.setAboutMe("Grad student at SJSU");
		
		
		em.getTransaction().begin();
		em.persist(user1);
		em.persist(user2);
		em.persist(user3);
//		em.getTransaction().commit();
		
		
		
//		Organization result1 = orgdao.findOrganizationById(result_org1.getId());
//		System.out.println("\nResult1 : "+result1);
//		Organization result2 = orgdao.findOrganizationById(result_org2.getId());
//		System.out.println("\nResult2 : "+result2);
//		if(result1!=null)
//			System.out.println("\n__________________Printing organization1 : \n"+result1.toString()+"\n");
//		if(result2!=null)
//			System.out.println("\n__________________Printing organization2 : \n"+result2.toString()+"\n");
		
		
		
//		System.out.println("\nListing members of Organization1 : "+org1.getMembers().toString());

//		em.getTransaction().begin();
		user2.setOrganization(result_org1);
		User updated_user = em.merge(user2);
		em.getTransaction().commit();
		em.close();
		
		if(updated_user!=null)
			System.out.println("\nAfter the updating the user : \n"+updated_user.toString());
//		
		List <Organization> org_result = orgdao.findAllOrganization();
		for(Organization org : org_result)
		{
			System.out.println(org.toString());
		}
	}
    
}
