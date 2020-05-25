import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './campaign-flow.reducer';
import { ICampaignFlow } from 'app/shared/model/analyticCollectionService/campaign-flow.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICampaignFlowDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CampaignFlowDetail = (props: ICampaignFlowDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { campaignFlowEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaignFlow.detail.title">CampaignFlow</Translate> [
          <b>{campaignFlowEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="campaignId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaignFlow.campaignId">Campaign Id</Translate>
            </span>
          </dt>
          <dd>{campaignFlowEntity.campaignId}</dd>
          <dt>
            <span id="templateFlowId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaignFlow.templateFlowId">Template Flow Id</Translate>
            </span>
          </dt>
          <dd>{campaignFlowEntity.templateFlowId}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaignFlow.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {campaignFlowEntity.createdDate ? (
              <TextFormat value={campaignFlowEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceCampaignFlow.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{campaignFlowEntity.createdBy}</dd>
        </dl>
        <Button tag={Link} to="/campaign-flow" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/campaign-flow/${campaignFlowEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ campaignFlow }: IRootState) => ({
  campaignFlowEntity: campaignFlow.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CampaignFlowDetail);
