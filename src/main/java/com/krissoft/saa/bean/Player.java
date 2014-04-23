package com.krissoft.saa.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Player implements Serializable {

	public Player(long id, String name, String location, String pos, int stars,
			String height, Double fortyDash, int weight, int gradYear,
			Double rating) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.pos = pos;
		this.stars = stars;
		this.height = height;
		this.fortyDash = fortyDash;
		this.weight = weight;
		this.gradYear = gradYear;
		this.rating = rating;
	}

	public Player() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = true)
	private String name;
	@Column(nullable = true)
	private String location;
	@Column(nullable = true)
	private String pos;
	@Column(nullable = true)
	private int stars;
	@Column(nullable = true)
	private String height;
	@Column(nullable = true)
	private Double fortyDash;
	@Column(nullable = true)
	private int weight;
	@Column(nullable = true)
	private int gradYear;
	@Column(nullable = true)
	private Double rating;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public int getStars() {
		return stars;
	}

	public void setStars(int stars) {
		this.stars = stars;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Double getFortyDash() {
		return fortyDash;
	}

	public void setFortyDash(Double fortyDash) {
		this.fortyDash = fortyDash;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getGradYear() {
		return gradYear;
	}

	public void setGradYear(int gradYear) {
		this.gradYear = gradYear;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "Player [id=" + id + ", name=" + name + ", location=" + location
				+ ", pos=" + pos + ", stars=" + stars + ", height=" + height
				+ ", fortyDash=" + fortyDash + ", weight=" + weight
				+ ", gradYear=" + gradYear + ", rating=" + rating + "]";
	}

}
