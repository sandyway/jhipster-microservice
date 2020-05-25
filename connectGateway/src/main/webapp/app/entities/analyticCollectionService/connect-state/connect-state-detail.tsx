import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './connect-state.reducer';
import { IConnectState } from 'app/shared/model/analyticCollectionService/connect-state.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConnectStateDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConnectStateDetail = (props: IConnectStateDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { connectStateEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.detail.title">ConnectState</Translate> [
          <b>{connectStateEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="campaignId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.campaignId">Campaign Id</Translate>
            </span>
          </dt>
          <dd>{connectStateEntity.campaignId}</dd>
          <dt>
            <span id="recipientId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.recipientId">Recipient Id</Translate>
            </span>
          </dt>
          <dd>{connectStateEntity.recipientId}</dd>
          <dt>
            <span id="connectDetails">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.connectDetails">Connect Details</Translate>
            </span>
          </dt>
          <dd>{connectStateEntity.connectDetails}</dd>
          <dt>
            <span id="channel">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.channel">Channel</Translate>
            </span>
          </dt>
          <dd>{connectStateEntity.channel}</dd>
          <dt>
            <span id="intentId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.intentId">Intent Id</Translate>
            </span>
          </dt>
          <dd>{connectStateEntity.intentId}</dd>
          <dt>
            <span id="intentType">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.intentType">Intent Type</Translate>
            </span>
          </dt>
          <dd>{connectStateEntity.intentType}</dd>
          <dt>
            <span id="connectEvent">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.connectEvent">Connect Event</Translate>
            </span>
          </dt>
          <dd>{connectStateEntity.connectEvent}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {connectStateEntity.createdDate ? (
              <TextFormat value={connectStateEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectState.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {connectStateEntity.updatedDate ? (
              <TextFormat value={connectStateEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/connect-state" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/connect-state/${connectStateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ connectState }: IRootState) => ({
  connectStateEntity: connectState.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConnectStateDetail);
