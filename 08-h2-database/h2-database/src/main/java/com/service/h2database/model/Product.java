package com.service.h2database.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Version;

@Entity
@Table(name = "product")
public class Product {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Version
    private Integer version;
    private String productId;
    private String description;
    private String imageUrl;
    
	public Integer getId() {
		return id;
	}
	public Integer getVersion() {
		return version;
	}
	public String getProductId() {
		return productId;
	}
	public String getDescription() {
		return description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

}
