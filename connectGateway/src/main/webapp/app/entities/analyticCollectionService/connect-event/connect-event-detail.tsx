import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './connect-event.reducer';
import { IConnectEvent } from 'app/shared/model/analyticCollectionService/connect-event.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConnectEventDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConnectEventDetail = (props: IConnectEventDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { connectEventEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectEvent.detail.title">ConnectEvent</Translate> [
          <b>{connectEventEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="analyticId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectEvent.analyticId">Analytic Id</Translate>
            </span>
          </dt>
          <dd>{connectEventEntity.analyticId}</dd>
          <dt>
            <span id="connectEvent">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectEvent.connectEvent">Connect Event</Translate>
            </span>
          </dt>
          <dd>{connectEventEntity.connectEvent}</dd>
          <dt>
            <span id="reference">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectEvent.reference">Reference</Translate>
            </span>
          </dt>
          <dd>{connectEventEntity.reference}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectEvent.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {connectEventEntity.createdDate ? (
              <TextFormat value={connectEventEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/connect-event" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/connect-event/${connectEventEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ connectEvent }: IRootState) => ({
  connectEventEntity: connectEvent.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConnectEventDetail);
