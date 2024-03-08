# Database Encryption

## Overview

This project aims to provide a secure data encryption solution using PostgreSQL's pgcrypto extension in combination with Spring Data JPA's @ColumnTransformer annotation. The encryption strategy involves storing sensitive data in an encrypted format directly within the database, enhancing security and protecting against unauthorized access.

## Data Encryption

### PostgreSQL pgcrypto Extension

To enable data encryption within the PostgreSQL database, the project utilizes the pgcrypto extension. This extension provides a set of cryptographic functions that enable the encryption and decryption of data directly within the database.

To enable the pgcrypto extension, the following SQL script is executed during database initialization:

```sql
-- enable-extensions.sql

CREATE EXTENSION IF NOT EXISTS "pgcrypto";

```
### Spring Data JPA's @ColumnTransformer
To handle the encryption and decryption of specific columns in the database, Spring Data JPA's @ColumnTransformer annotation is used. This annotation allows for the customization of read and write operations for entity attributes, enabling seamless integration with the pgcrypto extension.

## Example Usage
Here's an example of how the @ColumnTransformer annotation is used in the entity class:

```java

@Entity
@Table(name = "user_details")
public class UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ColumnTransformer(forColumn = "email_id", read = "pgp_sym_decrypt(email_id, 'password')", write = "pgp_sym_encrypt(?, 'password')")
    @Column(name = "email_id", columnDefinition = "bytea")
    private String emailId;

    // Other encrypted fields...

    // Getters and setters...
}
```

# Advantages of this Approach
#### Enhanced Security:
Storing encrypted data directly in the database enhances security by protecting sensitive information from unauthorized access, even at the database level.
#### Simplicity:
Leveraging PostgreSQL's built-in encryption capabilities and Spring Data JPA's @ColumnTransformer annotation simplifies the implementation and maintenance of data encryption within the application codebase.
#### Performance:
Performing encryption and decryption operations within the database can result in better performance compared to application-level encryption, especially for large datasets.
