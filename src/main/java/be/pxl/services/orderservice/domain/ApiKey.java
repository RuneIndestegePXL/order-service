package be.pxl.services.orderservice.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "api_keys")
public class ApiKey {
    /*
    CREATE TABLE api_keys (
    id SERIAL PRIMARY KEY,
    key_value VARCHAR(255) NOT NULL
);
    * */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String keyValue;

    public ApiKey() {
    }

    public ApiKey(String keyValue) {
        this.keyValue = keyValue;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue;
    }
}