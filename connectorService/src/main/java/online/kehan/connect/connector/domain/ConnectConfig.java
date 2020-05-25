package online.kehan.connect.connector.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ConnectConfig.
 */
@Entity
@Table(name = "connect_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConnectConfig implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private String userId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "facebook")
    private String facebook;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "viber")
    private String viber;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public ConnectConfig userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFacebook() {
        return facebook;
    }

    public ConnectConfig facebook(String facebook) {
        this.facebook = facebook;
        return this;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getViber() {
        return viber;
    }

    public ConnectConfig viber(String viber) {
        this.viber = viber;
        return this;
    }

    public void setViber(String viber) {
        this.viber = viber;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ConnectConfig createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectConfig)) {
            return false;
        }
        return id != null && id.equals(((ConnectConfig) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectConfig{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", facebook='" + getFacebook() + "'" +
            ", viber='" + getViber() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
