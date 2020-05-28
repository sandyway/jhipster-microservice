package online.kehan.connect.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A CampaignFlow.
 */
@Entity
@Table(name = "campaign_flow")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class CampaignFlow implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "campaign_id", nullable = false)
    private String campaignId;

    @Column(name = "template_flow_id")
    private String templateFlowId;

    @Column(name = "created_date")
    private ZonedDateTime createdDate;

    @Column(name = "created_by")
    private String createdBy;

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

    public CampaignFlow campaignId(String campaignId) {
        this.campaignId = campaignId;
        return this;
    }

    public void setCampaignId(String campaignId) {
        this.campaignId = campaignId;
    }

    public String getTemplateFlowId() {
        return templateFlowId;
    }

    public CampaignFlow templateFlowId(String templateFlowId) {
        this.templateFlowId = templateFlowId;
        return this;
    }

    public void setTemplateFlowId(String templateFlowId) {
        this.templateFlowId = templateFlowId;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public CampaignFlow createdDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(ZonedDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public CampaignFlow createdBy(String createdBy) {
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
        if (!(o instanceof CampaignFlow)) {
            return false;
        }
        return id != null && id.equals(((CampaignFlow) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CampaignFlow{" +
            "id=" + getId() +
            ", campaignId='" + getCampaignId() + "'" +
            ", templateFlowId='" + getTemplateFlowId() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            "}";
    }
}
