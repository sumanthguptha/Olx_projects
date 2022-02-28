package com.zensar.olx.bean;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "advertises")
public class AdvertisementPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	private String title;

	@Column
	private String description;

	@Column
	private double price;

	@Embedded
	private Category category;

	@Embedded
	private OlxUser olxUser;

	@Embedded
	@Column(name = "advertise")
	private AdvertisementStatus advertismentStatus;

	@Column
	private LocalDate createdDate;

	@Column
	private LocalDate modifiedDate;

	@Lob
	private byte[] photo;

	public AdvertisementPost() {
		super();
		this.createdDate = LocalDate.now();
		this.modifiedDate = LocalDate.now();

	}

	public AdvertisementPost(int id) {
		super();
		this.id = id;
	}

	public AdvertisementPost(String title, String description, double price, Category category, OlxUser olxUser,
			AdvertisementStatus advertismentStatus, LocalDate createdDate, LocalDate modifiedDate, byte[] photo) {
		super();
		this.title = title;
		this.description = description;
		this.price = price;
		this.category = category;
		this.olxUser = olxUser;
		this.advertismentStatus = advertismentStatus;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.photo = photo;
	}

	public AdvertisementPost(int id, String title, String description, double price, Category category, OlxUser olxUser,
			AdvertisementStatus advertismentStatus, LocalDate createdDate, LocalDate modifiedDate, byte[] photo) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.price = price;
		this.category = category;
		this.olxUser = olxUser;
		this.advertismentStatus = advertismentStatus;
		this.createdDate = createdDate;
		this.modifiedDate = modifiedDate;
		this.photo = photo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public OlxUser getOlxUser() {
		return olxUser;
	}

	public void setOlxUser(OlxUser olxUser) {
		this.olxUser = olxUser;
	}

	public AdvertisementStatus getAdvertismentStatus() {
		return advertismentStatus;
	}

	public void setAdvertismentStatus(AdvertisementStatus advertismentStatus) {
		this.advertismentStatus = advertismentStatus;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDate modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}

	@Override
	public String toString() {
		return "AdvertisementPost [id=" + id + ", title=" + title + ", description=" + description + ", price=" + price
				+ ", category=" + category + ", olxUser=" + olxUser + ", advertismentStatus=" + advertismentStatus
				+ ", createdDate=" + createdDate + ", modifiedDate=" + modifiedDate + ", photo="
				+ Arrays.toString(photo) + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AdvertisementPost other = (AdvertisementPost) obj;
		return id == other.id;
	}
	
	

}