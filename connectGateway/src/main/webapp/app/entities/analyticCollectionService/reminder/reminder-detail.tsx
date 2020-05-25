import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './reminder.reducer';
import { IReminder } from 'app/shared/model/analyticCollectionService/reminder.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IReminderDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ReminderDetail = (props: IReminderDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { reminderEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.detail.title">Reminder</Translate> [
          <b>{reminderEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="recipientId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.recipientId">Recipient Id</Translate>
            </span>
          </dt>
          <dd>{reminderEntity.recipientId}</dd>
          <dt>
            <span id="campaignId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.campaignId">Campaign Id</Translate>
            </span>
          </dt>
          <dd>{reminderEntity.campaignId}</dd>
          <dt>
            <span id="intentId">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.intentId">Intent Id</Translate>
            </span>
          </dt>
          <dd>{reminderEntity.intentId}</dd>
          <dt>
            <span id="channel">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.channel">Channel</Translate>
            </span>
          </dt>
          <dd>{reminderEntity.channel}</dd>
          <dt>
            <span id="connectDetails">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.connectDetails">Connect Details</Translate>
            </span>
          </dt>
          <dd>{reminderEntity.connectDetails}</dd>
          <dt>
            <span id="done">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.done">Done</Translate>
            </span>
          </dt>
          <dd>{reminderEntity.done ? 'true' : 'false'}</dd>
          <dt>
            <span id="executions">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.executions">Executions</Translate>
            </span>
          </dt>
          <dd>{reminderEntity.executions}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {reminderEntity.createdDate ? <TextFormat value={reminderEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="updatedDate">
              <Translate contentKey="connectGatewayApp.analyticCollectionServiceReminder.updatedDate">Updated Date</Translate>
            </span>
          </dt>
          <dd>
            {reminderEntity.updatedDate ? <TextFormat value={reminderEntity.updatedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
        </dl>
        <Button tag={Link} to="/reminder" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/reminder/${reminderEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ reminder }: IRootState) => ({
  reminderEntity: reminder.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReminderDetail);
