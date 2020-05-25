package online.kehan.connect.analytic.collection.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A ConnectIntent.
 */
@Entity
@Table(name = "connect_intent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ConnectIntent implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "intent_id", nullable = false)
    private String intentId;

    @Column(name = "flow_id")
    private String flowId;

    @NotNull
    @Column(name = "connect_channel", nullable = false)
    private String connectChannel;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "connect_type", nullable = false)
    private String connectType;

    
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
    private String createdBy;

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

    public ConnectIntent intentId(String intentId) {
        this.intentId = intentId;
        return this;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public String getFlowId() {
        return flowId;
    }

    public ConnectIntent flowId(String flowId) {
        this.flowId = flowId;
        return this;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getConnectChannel() {
        return connectChannel;
    }

    public ConnectIntent connectChannel(String connectChannel) {
        this.connectChannel = connectChannel;
        return this;
    }

    public void setConnectChannel(String connectChannel) {
        this.connectChannel = connectChannel;
    }

    public String getDescription() {
        return description;
    }

    public ConnectIntent description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConnectType() {
        return connectType;
    }

    public ConnectIntent connectType(String connectType) {
        this.connectType = connectType;
        return this;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public String getMessages() {
        return messages;
    }

    public ConnectIntent messages(String messages) {
        this.messages = messages;
        return this;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getReminder() {
        return reminder;
    }

    public ConnectIntent reminder(String reminder) {
        this.reminder = reminder;
        return this;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public ConnectIntent createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public ConnectIntent updatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
        return this;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ConnectIntent createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectIntent)) {
            return false;
        }
        return id != null && id.equals(((ConnectIntent) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectIntent{" +
            "id=" + getId() +
            ", intentId='" + getIntentId() + "'" +
            ", flowId='" + getFlowId() + "'" +
            ", connectChannel='" + getConnectChannel() + "'" +
            ", description='" + getDescription() + "'" +
            ", connectType='" + getConnectType() + "'" +
            ", messages='" + getMessages() + "'" +
            ", reminder='" + getReminder() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedDate='" + getUpdatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
