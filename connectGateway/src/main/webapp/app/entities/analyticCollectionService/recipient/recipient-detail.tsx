import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './recipient.reducer';
import { IRecipient } from 'app/shared/model/analyticCollectionService/recipient.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IRecipientDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipientDetail = (props: IRecipientDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { recipientEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.detail.title">Recipient</Translate> [
          <b>{recipientEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="campaignId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.campaignId">Campaign Id</Translate>
            </span>
          </dt>
          <dd>{recipientEntity.campaignId}</dd>
          <dt>
            <span id="ref">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.ref">Ref</Translate>
            </span>
          </dt>
          <dd>{recipientEntity.ref}</dd>
          <dt>
            <span id="connectDetails">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.connectDetails">Connect Details</Translate>
            </span>
          </dt>
          <dd>{recipientEntity.connectDetails}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {recipientEntity.createdDate ? <TextFormat value={recipientEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/recipient" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/recipient/${recipientEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ recipient }: IRootState) => ({
  recipientEntity: recipient.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(RecipientDetail);
