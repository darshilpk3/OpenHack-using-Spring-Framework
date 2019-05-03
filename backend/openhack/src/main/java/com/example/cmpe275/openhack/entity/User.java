package com.example.cmpe275.openhack.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	@Column(nullable=false)
	private String name;
	@Column(nullable=false,unique=true)
	private String email;
	private String password;
	private String screenName;
	
	@Embedded
	private Address address;
	
	private String aboutMe;
	private String title;
	private String imageurl;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="organisation_id")
	private Organization organization;

	
//	@ManyToMany(fetch=FetchType.EAGER)
	@ManyToMany
	@JoinTable(
			name="Judge_Hackathons",
			joinColumns= {@JoinColumn(name="User",referencedColumnName="id")},
			inverseJoinColumns= {@JoinColumn(name="Hackathon",referencedColumnName="id")})
	private List<Hackathon> judgedHackathons;
	
//	@ManyToMany(fetch=FetchType.EAGER)
	@ManyToMany
	@JoinTable(
			name="User_Teams",
			joinColumns= {@JoinColumn(name="User",referencedColumnName="id")},
			inverseJoinColumns= {@JoinColumn(name="Team",referencedColumnName="id")})
	private List<Team> teams;
	
	public User() {
		
	}
	
	public User(long id, String name, String email, String password, String screenName, Address address, String aboutMe,
			String title, String imageurl) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.screenName = screenName;
		this.address = address;
		this.aboutMe = aboutMe;
		this.title = title;
		this.imageurl = imageurl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImageurl() {
		return imageurl;
	}

	public void setImageurl(String imageurl) {
		this.imageurl = imageurl;
	}

}