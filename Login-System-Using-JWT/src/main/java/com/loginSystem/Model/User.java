package com.loginSystem.Model;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	@Column(name = "User_ID")
	private String id ;
	
	@Column(name = "Full_Name",nullable = false)
	private String fullName;
	
	@Column(name = "Email",nullable = false,unique = true)
	@Email
	private String email;
	
	@Column(name = "Password")
	@Size(min = 10)
	private String password;
	
//	@OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//	@JsonManagedReference
//	private Set<Role> Roles;
//	
//	
//	 @Override
//	    public int hashCode() {
//	        return Objects.hash(id); // Using only the id field for hash code calculation
//	    }
	
	@Column(name = "Role")
	private String Role;
	
}
