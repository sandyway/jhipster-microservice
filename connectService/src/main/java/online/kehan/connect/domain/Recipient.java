package online.kehan.connect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Recipient.
 */
@Entity
@Table(name = "recipient")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Recipient implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "campaign_id")
    private String campaignId;

    @Column(name = "ref")
    private String ref;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "connect_details", nullable = false)
    private String connectDetails;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public Recipient campaignId(String campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getRef() {
        return ref;
    }

    public Recipient ref(String ref) {
        this.ref = ref;
        return this;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getConnectDetails() {
        return connectDetails;
    }

    public Recipient connectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
        return this;
    }

    public void setConnectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Recipient createdDate(ZonedDateTime createdDate) {
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
        if (!(o instanceof Recipient)) {
            return false;
        }
        return id != null && id.equals(((Recipient) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Recipient{" +
            "id=" + getId() +
            ", campaignId='" + getCampaignId() + "'" +
            ", ref='" + getRef() + "'" +
            ", connectDetails='" + getConnectDetails() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
