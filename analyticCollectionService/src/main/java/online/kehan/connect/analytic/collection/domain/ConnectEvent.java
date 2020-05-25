package online.kehan.connect.analytic.collection.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ConnectEvent.
 */
@Entity
@Table(name = "connect_event")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConnectEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "analytic_id", nullable = false)
    private String analyticId;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "connect_event")
    private String connectEvent;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "reference")
    private String reference;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAnalyticId() {
        return analyticId;
    }

    public ConnectEvent analyticId(String analyticId) {
        this.analyticId = analyticId;
        return this;
    }

    public void setAnalyticId(String analyticId) {
        this.analyticId = analyticId;
    }

    public String getConnectEvent() {
        return connectEvent;
    }

    public ConnectEvent connectEvent(String connectEvent) {
        this.connectEvent = connectEvent;
        return this;
    }

    public void setConnectEvent(String connectEvent) {
        this.connectEvent = connectEvent;
    }

    public String getReference() {
        return reference;
    }

    public ConnectEvent reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ConnectEvent createdDate(ZonedDateTime createdDate) {
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
        if (!(o instanceof ConnectEvent)) {
            return false;
        }
        return id != null && id.equals(((ConnectEvent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectEvent{" +
            "id=" + getId() +
            ", analyticId='" + getAnalyticId() + "'" +
            ", connectEvent='" + getConnectEvent() + "'" +
            ", reference='" + getReference() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
