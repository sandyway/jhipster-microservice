package online.kehan.connect.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import online.kehan.connect.domain.enumeration.Channel;
import online.kehan.connect.domain.enumeration.CampaignType;

/**
 * A DTO for the {@link online.kehan.connect.domain.ConnectIntent} entity.
 */
public class ConnectIntentDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String intentId;

    private String flowId;

    @NotNull
    private Channel connectChannel;

    private String description;

    @NotNull
    private CampaignType connectType;

    
    @Lob
    private String messages;

    @Lob
    private String reminder;

    @NotNull
    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    @NotNull
    private String createdBy;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIntentId() {
        return intentId;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public Channel getConnectChannel() {
        return connectChannel;
    }

    public void setConnectChannel(Channel connectChannel) {
        this.connectChannel = connectChannel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CampaignType getConnectType() {
        return connectType;
    }

    public void setConnectType(CampaignType connectType) {
        this.connectType = connectType;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String reminder) {
        this.reminder = reminder;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public ZonedDateTime getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(ZonedDateTime updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectIntentDTO)) {
            return false;
        }

        return id != null && id.equals(((ConnectIntentDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectIntentDTO{" +
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
