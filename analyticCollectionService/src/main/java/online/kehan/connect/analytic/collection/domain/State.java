package online.kehan.connect.analytic.collection.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * A State.
 */
@Entity
@Table(name = "state")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class State implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "campaign_id", nullable = false)
    private UUID campaignId;

    @NotNull
    @Column(name = "recipient_id", nullable = false)
    private UUID recipientId;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "details", nullable = false)
    private String details;

    @NotNull
    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "intent_id")
    private String intentId;

    @Column(name = "intent_type")
    private String intentType;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "event")
    private String event;

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

    public UUID getCampaignId() {
        return campaignId;
    }

    public State campaignId(UUID campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public void setCampaignId(UUID campaignId) {
        this.campaignId = campaignId;
    }

    public UUID getRecipientId() {
        return recipientId;
    }

    public State recipientId(UUID recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public void setRecipientId(UUID recipientId) {
        this.recipientId = recipientId;
    }

    public String getDetails() {
        return details;
    }

    public State details(String details) {
        this.details = details;
        return this;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getChannel() {
        return channel;
    }

    public State channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getIntentId() {
        return intentId;
    }

    public State intentId(String intentId) {
        this.intentId = intentId;
        return this;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public String getIntentType() {
        return intentType;
    }

    public State intentType(String intentType) {
        this.intentType = intentType;
        return this;
    }

    public void setIntentType(String intentType) {
        this.intentType = intentType;
    }

    public String getEvent() {
        return event;
    }

    public State event(String event) {
        this.event = event;
        return this;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public State createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public State updatedDate(ZonedDateTime updatedDate) {
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
        if (!(o instanceof State)) {
            return false;
        }
        return id != null && id.equals(((State) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "State{" +
            "id=" + getId() +
            ", campaignId='" + getCampaignId() + "'" +
            ", recipientId='" + getRecipientId() + "'" +
            ", details='" + getDetails() + "'" +
            ", channel='" + getChannel() + "'" +
            ", intentId='" + getIntentId() + "'" +
            ", intentType='" + getIntentType() + "'" +
            ", event='" + getEvent() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
