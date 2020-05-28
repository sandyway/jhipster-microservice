package online.kehan.connect.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import online.kehan.connect.domain.enumeration.Channel;
import online.kehan.connect.domain.enumeration.IntentTypes;

/**
 * A DTO for the {@link online.kehan.connect.domain.ConnectState} entity.
 */
public class ConnectStateDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String campaignId;

    @NotNull
    private String recipientId;

    
    @Lob
    private String connectDetails;

    @NotNull
    private Channel channel;

    private String intentId;

    private IntentTypes intentType;

    @Lob
    private String connectEvent;

    @NotNull
    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getConnectDetails() {
        return connectDetails;
    }

    public void setConnectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getIntentId() {
        return intentId;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public IntentTypes getIntentType() {
        return intentType;
    }

    public void setIntentType(IntentTypes intentType) {
        this.intentType = intentType;
    }

    public String getConnectEvent() {
        return connectEvent;
    }

    public void setConnectEvent(String connectEvent) {
        this.connectEvent = connectEvent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ConnectStateDTO)) {
            return false;
        }

        return id != null && id.equals(((ConnectStateDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ConnectStateDTO{" +
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
