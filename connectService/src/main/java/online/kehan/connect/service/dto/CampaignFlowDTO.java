package online.kehan.connect.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link online.kehan.connect.domain.CampaignFlow} entity.
 */
public class CampaignFlowDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String campaignId;

    private String templateFlowId;

    private ZonedDateTime createdDate;

    private String createdBy;

    
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

    public String getTemplateFlowId() {
        return templateFlowId;
    }

    public void setTemplateFlowId(String templateFlowId) {
        this.templateFlowId = templateFlowId;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
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
        if (!(o instanceof CampaignFlowDTO)) {
            return false;
        }

        return id != null && id.equals(((CampaignFlowDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignFlowDTO{" +
            "id=" + getId() +
            ", campaignId='" + getCampaignId() + "'" +
            ", templateFlowId='" + getTemplateFlowId() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
