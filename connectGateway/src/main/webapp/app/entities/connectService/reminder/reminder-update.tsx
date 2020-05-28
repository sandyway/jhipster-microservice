import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './reminder.reducer';
import { IReminder } from 'app/shared/model/connectService/reminder.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IReminderUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ReminderUpdate = (props: IReminderUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { reminderEntity, loading, updating } = props;

  const { connectDetails } = reminderEntity;

  const handleClose = () => {
    props.history.push('/reminder' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  const onBlobChange = (isAnImage, name) => event => {
    setFileData(event, (contentType, data) => props.setBlob(name, data, contentType), isAnImage);
  };

  const clearBlob = name => () => {
    props.setBlob(name, undefined, undefined);
  };

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.updatedDate = convertDateTimeToServer(values.updatedDate);

    if (errors.length === 0) {
      const entity = {
        ...reminderEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="connectGatewayApp.connectServiceReminder.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.connectServiceReminder.home.createOrEditLabel">Create or edit a Reminder</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : reminderEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="reminder-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="reminder-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="recipientIdLabel" for="reminder-recipientId">
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.recipientId">Recipient Id</Translate>
                </Label>
                <AvField
                  id="reminder-recipientId"
                  type="text"
                  name="recipientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="campaignIdLabel" for="reminder-campaignId">
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.campaignId">Campaign Id</Translate>
                </Label>
                <AvField
                  id="reminder-campaignId"
                  type="text"
                  name="campaignId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="intentIdLabel" for="reminder-intentId">
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.intentId">Intent Id</Translate>
                </Label>
                <AvField
                  id="reminder-intentId"
                  type="text"
                  name="intentId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="channelLabel" for="reminder-channel">
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.channel">Channel</Translate>
                </Label>
                <AvInput
                  id="reminder-channel"
                  type="select"
                  className="form-control"
                  name="channel"
                  value={(!isNew && reminderEntity.channel) || 'SMS'}
                >
                  <option value="SMS">{translate('connectGatewayApp.Channel.SMS')}</option>
                  <option value="MESSENGER">{translate('connectGatewayApp.Channel.MESSENGER')}</option>
                  <option value="WHATSAPP">{translate('connectGatewayApp.Channel.WHATSAPP')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="connectDetailsLabel" for="reminder-connectDetails">
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.connectDetails">Connect Details</Translate>
                </Label>
                <AvInput
                  id="reminder-connectDetails"
                  type="textarea"
                  name="connectDetails"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup check>
                <Label id="doneLabel">
                  <AvInput id="reminder-done" type="checkbox" className="form-check-input" name="done" />
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.done">Done</Translate>
                </Label>
              </AvGroup>
              <AvGroup>
                <Label id="executionsLabel" for="reminder-executions">
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.executions">Executions</Translate>
                </Label>
                <AvField
                  id="reminder-executions"
                  type="string"
                  className="form-control"
                  name="executions"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                    number: { value: true, errorMessage: translate('entity.validation.number') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="reminder-createdDate">
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="reminder-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.reminderEntity.createdDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedDateLabel" for="reminder-updatedDate">
                  <Translate contentKey="connectGatewayApp.connectServiceReminder.updatedDate">Updated Date</Translate>
                </Label>
                <AvInput
                  id="reminder-updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.reminderEntity.updatedDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/reminder" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  reminderEntity: storeState.reminder.entity,
  loading: storeState.reminder.loading,
  updating: storeState.reminder.updating,
  updateSuccess: storeState.reminder.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  setBlob,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ReminderUpdate);
