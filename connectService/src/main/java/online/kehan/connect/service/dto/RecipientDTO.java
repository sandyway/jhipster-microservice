package online.kehan.connect.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import javax.persistence.Lob;

/**
 * A DTO for the {@link online.kehan.connect.domain.Recipient} entity.
 */
public class RecipientDTO implements Serializable {
    
    private Long id;

    private String campaignId;

    private String ref;

    
    @Lob
    private String connectDetails;

    @NotNull
    private ZonedDateTime createdDate;

    
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

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getConnectDetails() {
        return connectDetails;
    }

    public void setConnectDetails(String connectDetails) {
        this.connectDetails = connectDetails;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RecipientDTO)) {
            return false;
        }

        return id != null && id.equals(((RecipientDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RecipientDTO{" +
            "id=" + getId() +
            ", campaignId='" + getCampaignId() + "'" +
            ", ref='" + getRef() + "'" +
            ", connectDetails='" + getConnectDetails() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
