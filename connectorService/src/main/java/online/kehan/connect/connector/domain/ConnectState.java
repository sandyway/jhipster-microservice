package online.kehan.connect.connector.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ConnectState.
 */
@Entity
@Table(name = "connect_state")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConnectState implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "campaign_id", nullable = false)
    private String campaignId;

    @NotNull
    @Column(name = "recipient_id", nullable = false)
    private String recipientId;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "connect_details", nullable = false)
    private String connectDetails;

    @NotNull
    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "intent_id")
    private String intentId;

    @Column(name = "intent_type")
    private String intentType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "connect_event")
    private String connectEvent;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

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

    public ConnectState campaignId(String campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public ConnectState recipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getConnectDetails() {
        return connectDetails;
    }

    public ConnectState connectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
        return this;
    }

    public void setConnectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
    }

    public String getChannel() {
        return channel;
    }

    public ConnectState channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIntentId() {
        return intentId;
    }

    public ConnectState intentId(String intentId) {
        this.intentId = intentId;
        return this;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public String getIntentType() {
        return intentType;
    }

    public ConnectState intentType(String intentType) {
        this.intentType = intentType;
        return this;
    }

    public void setIntentType(String intentType) {
        this.intentType = intentType;
    }

    public String getConnectEvent() {
        return connectEvent;
    }

    public ConnectState connectEvent(String connectEvent) {
        this.connectEvent = connectEvent;
        return this;
    }

    public void setConnectEvent(String connectEvent) {
        this.connectEvent = connectEvent;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ConnectState createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public ConnectState updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectState)) {
            return false;
        }
        return id != null && id.equals(((ConnectState) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectState{" +
            "id=" + getId() +
            ", campaignId='" + getCampaignId() + "'" +
            ", recipientId='" + getRecipientId() + "'" +
            ", connectDetails='" + getConnectDetails() + "'" +
            ", channel='" + getChannel() + "'" +
            ", intentId='" + getIntentId() + "'" +
            ", intentType='" + getIntentType() + "'" +
            ", connectEvent='" + getConnectEvent() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
