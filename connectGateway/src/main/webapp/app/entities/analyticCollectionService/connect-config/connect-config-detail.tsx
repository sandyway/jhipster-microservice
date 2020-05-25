import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './connect-config.reducer';
import { IConnectConfig } from 'app/shared/model/analyticCollectionService/connect-config.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IConnectConfigDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConnectConfigDetail = (props: IConnectConfigDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { connectConfigEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.detail.title">ConnectConfig</Translate> [
          <b>{connectConfigEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="userId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{connectConfigEntity.userId}</dd>
          <dt>
            <span id="facebook">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.facebook">Facebook</Translate>
            </span>
          </dt>
          <dd>{connectConfigEntity.facebook}</dd>
          <dt>
            <span id="viber">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.viber">Viber</Translate>
            </span>
          </dt>
          <dd>{connectConfigEntity.viber}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {connectConfigEntity.createdDate ? (
              <TextFormat value={connectConfigEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/connect-config" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/connect-config/${connectConfigEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ connectConfig }: IRootState) => ({
  connectConfigEntity: connectConfig.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ConnectConfigDetail);
