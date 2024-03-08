package com.adria.dbencryption.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Getter
@Setter
@Entity
@Table(name = "user_details")
public class UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ColumnTransformer(forColumn = "email_id", read = "pgp_sym_decrypt(email_id, 'password')", write = "pgp_sym_encrypt(?, 'password')")
    @Column(name = "email_id", columnDefinition = "bytea")
    private String emailId;

    @ColumnTransformer(forColumn = "mobile_number", read = "pgp_sym_decrypt(mobile_number, 'password')", write = "pgp_sym_encrypt(?, 'password')")
    @Column(name = "mobile_number", columnDefinition = "bytea")
    private String mobileNumber;

    @ColumnTransformer(forColumn = "first_name", read = "pgp_sym_decrypt(first_name, 'password')", write = "pgp_sym_encrypt(?, 'password')")
    @Column(name = "first_name", columnDefinition = "bytea")
    private String firstName;

    @ColumnTransformer(forColumn = "last_name", read = "pgp_sym_decrypt(last_name, 'password')", write = "pgp_sym_encrypt(?, 'password')")
    @Column(name = "last_name", columnDefinition = "bytea")
    private String lastName;

    @ColumnTransformer(forColumn = "address", read = "pgp_sym_decrypt(address, 'password')", write = "pgp_sym_encrypt(?, 'password')")
    @Column(name = "address", columnDefinition = "bytea")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "created_date", updatable = false)
    private ZonedDateTime createdDate = ZonedDateTime.now();

    @Column(name = "updated_date", insertable = false)
    @UpdateTimestamp
    private ZonedDateTime updatedDate;
}
