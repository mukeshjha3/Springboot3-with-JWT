//package com.loginSystem.Model;
//
//import java.util.Objects;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//
//@Entity
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Builder
//@ToString
//public class Role {
//
//	@Id
//	@Column(name = "Role_Id")
//	private String id ;
//	
//	@Column(name = "Role_Name")
//	private String roleName;
//	
//	@ManyToOne(cascade = CascadeType.ALL)
//	@JoinColumn(name="User_Id")
//	@JsonBackReference
//	private User user;
//	
//	 @Override
//	    public int hashCode() {
//	        return Objects.hash(id); 
//	    }
//}
