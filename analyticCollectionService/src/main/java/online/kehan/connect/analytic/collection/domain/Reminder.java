package online.kehan.connect.analytic.collection.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Reminder.
 */
@Entity
@Table(name = "reminder")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Reminder implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "recipient_id", nullable = false)
    private String recipientId;

    @NotNull
    @Column(name = "campaign_id", nullable = false)
    private String campaignId;

    @NotNull
    @Column(name = "intent_id", nullable = false)
    private String intentId;

    @NotNull
    @Column(name = "channel", nullable = false)
    private String channel;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "connect_details", nullable = false)
    private String connectDetails;

    @NotNull
    @Column(name = "done", nullable = false)
    private Boolean done;

    @NotNull
    @Column(name = "executions", nullable = false)
    private Integer executions;

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

    public String getRecipientId() {
        return recipientId;
    }

    public Reminder recipientId(String recipientId) {
        this.recipientId = recipientId;
        return this;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public Reminder campaignId(String campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getIntentId() {
        return intentId;
    }

    public Reminder intentId(String intentId) {
        this.intentId = intentId;
        return this;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public String getChannel() {
        return channel;
    }

    public Reminder channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getConnectDetails() {
        return connectDetails;
    }

    public Reminder connectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
        return this;
    }

    public void setConnectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
    }

    public Boolean isDone() {
        return done;
    }

    public Reminder done(Boolean done) {
        this.done = done;
        return this;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Integer getExecutions() {
        return executions;
    }

    public Reminder executions(Integer executions) {
        this.executions = executions;
        return this;
    }

    public void setExecutions(Integer executions) {
        this.executions = executions;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Reminder createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public Reminder updatedDate(ZonedDateTime updatedDate) {
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
        if (!(o instanceof Reminder)) {
            return false;
        }
        return id != null && id.equals(((Reminder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Reminder{" +
            "id=" + getId() +
            ", recipientId='" + getRecipientId() + "'" +
            ", campaignId='" + getCampaignId() + "'" +
            ", intentId='" + getIntentId() + "'" +
            ", channel='" + getChannel() + "'" +
            ", connectDetails='" + getConnectDetails() + "'" +
            ", done='" + isDone() + "'" +
            ", executions=" + getExecutions() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            "}";
    }
}
