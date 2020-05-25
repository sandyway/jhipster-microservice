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
 * A Intent.
 */
@Entity
@Table(name = "intent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Intent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "intent_id", nullable = false)
    private String intentId;

    @Column(name = "flow_id")
    private UUID flowId;

    @NotNull
    @Column(name = "channel", nullable = false)
    private String channel;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "type", nullable = false)
    private String type;

    
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "messages", nullable = false)
    private String messages;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "reminder")
    private String reminder;

    @NotNull
    @Column(name = "created_date", nullable = false)
    private ZonedDateTime createdDate;

    @Column(name = "updated_date")
    private ZonedDateTime updatedDate;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntentId() {
        return intentId;
    }

    public Intent intentId(String intentId) {
        this.intentId = intentId;
        return this;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public UUID getFlowId() {
        return flowId;
    }

    public Intent flowId(UUID flowId) {
        this.flowId = flowId;
        return this;
    }

    public void setFlowId(UUID flowId) {
        this.flowId = flowId;
    }

    public String getChannel() {
        return channel;
    }

    public Intent channel(String channel) {
        this.channel = channel;
        return this;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDescription() {
        return description;
    }

    public Intent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public Intent type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessages() {
        return messages;
    }

    public Intent messages(String messages) {
        this.messages = messages;
        return this;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getReminder() {
        return reminder;
    }

    public Intent reminder(String reminder) {
        this.reminder = reminder;
        return this;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public Intent createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public Intent updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public UUID getCreatedBy() {
        return createdBy;
    }

    public Intent createdBy(UUID createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(UUID createdBy) {
        this.createdBy = createdBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Intent)) {
            return false;
        }
        return id != null && id.equals(((Intent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Intent{" +
            "id=" + getId() +
            ", intentId='" + getIntentId() + "'" +
            ", flowId='" + getFlowId() + "'" +
            ", channel='" + getChannel() + "'" +
            ", description='" + getDescription() + "'" +
            ", type='" + getType() + "'" +
            ", messages='" + getMessages() + "'" +
            ", reminder='" + getReminder() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
