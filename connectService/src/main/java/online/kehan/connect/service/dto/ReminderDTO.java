package online.kehan.connect.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;
import online.kehan.connect.domain.enumeration.Channel;

/**
 * A DTO for the {@link online.kehan.connect.domain.Reminder} entity.
 */
public class ReminderDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String recipientId;

    @NotNull
    private String campaignId;

    @NotNull
    private String intentId;

    @NotNull
    private Channel channel;

    
    @Lob
    private String connectDetails;

    @NotNull
    private Boolean done;

    @NotNull
    private Integer executions;

    @NotNull
    private ZonedDateTime createdDate;

    private ZonedDateTime updatedDate;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getIntentId() {
        return intentId;
    }

    public void setIntentId(String intentId) {
        this.intentId = intentId;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public String getConnectDetails() {
        return connectDetails;
    }

    public void setConnectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
    }

    public Boolean isDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Integer getExecutions() {
        return executions;
    }

    public void setExecutions(Integer executions) {
        this.executions = executions;
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
        if (!(o instanceof ReminderDTO)) {
            return false;
        }

        return id != null && id.equals(((ReminderDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReminderDTO{" +
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
