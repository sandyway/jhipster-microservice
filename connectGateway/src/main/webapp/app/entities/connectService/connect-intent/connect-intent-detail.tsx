import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './connect-intent.reducer';
import { IConnectIntent } from 'app/shared/model/connectService/connect-intent.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConnectIntentDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConnectIntentDetail = (props: IConnectIntentDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { connectIntentEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.detail.title">ConnectIntent</Translate> [
          <b>{connectIntentEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="intentId">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.intentId">Intent Id</Translate>
            </span>
          </dt>
          <dd>{connectIntentEntity.intentId}</dd>
          <dt>
            <span id="flowId">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.flowId">Flow Id</Translate>
            </span>
          </dt>
          <dd>{connectIntentEntity.flowId}</dd>
          <dt>
            <span id="connectChannel">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.connectChannel">Connect Channel</Translate>
            </span>
          </dt>
          <dd>{connectIntentEntity.connectChannel}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.description">Description</Translate>
            </span>
          </dt>
          <dd>{connectIntentEntity.description}</dd>
          <dt>
            <span id="connectType">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.connectType">Connect Type</Translate>
            </span>
          </dt>
          <dd>{connectIntentEntity.connectType}</dd>
          <dt>
            <span id="messages">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.messages">Messages</Translate>
            </span>
          </dt>
          <dd>{connectIntentEntity.messages}</dd>
          <dt>
            <span id="reminder">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.reminder">Reminder</Translate>
            </span>
          </dt>
          <dd>{connectIntentEntity.reminder}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {connectIntentEntity.createdDate ? (
              <TextFormat value={connectIntentEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {connectIntentEntity.updatedDate ? (
              <TextFormat value={connectIntentEntity.updatedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="connectGatewayApp.connectServiceConnectIntent.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{connectIntentEntity.createdBy}</dd>
        </dl>
        <Button tag={Link} to="/connect-intent" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/connect-intent/${connectIntentEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ connectIntent }: IRootState) => ({
  connectIntentEntity: connectIntent.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConnectIntentDetail);
