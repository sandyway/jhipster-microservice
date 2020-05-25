import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './campaign.reducer';
import { ICampaign } from 'app/shared/model/analyticCollectionService/campaign.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICampaignDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CampaignDetail = (props: ICampaignDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { campaignEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaign.detail.title">Campaign</Translate> [
          <b>{campaignEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="campaignDetails">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaign.campaignDetails">Campaign Details</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.campaignDetails}</dd>
          <dt>
            <span id="campaignType">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaign.campaignType">Campaign Type</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.campaignType}</dd>
          <dt>
            <span id="startDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaign.startDate">Start Date</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.startDate ? <TextFormat value={campaignEntity.startDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="endDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaign.endDate">End Date</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.endDate ? <TextFormat value={campaignEntity.endDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaign.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{campaignEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaign.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {campaignEntity.createdDate ? <TextFormat value={campaignEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/campaign" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/campaign/${campaignEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ campaign }: IRootState) => ({
  campaignEntity: campaign.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CampaignDetail);
