package org.pk.rolebasedauth.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class User implements Serializable {

    @Id
    @GeneratedValue
    private int userId;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String roles;
    private boolean isActive;

}
