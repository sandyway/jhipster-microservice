import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './connect-intent.reducer';
import { IConnectIntent } from 'app/shared/model/analyticCollectionService/connect-intent.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConnectIntentUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConnectIntentUpdate = (props: IConnectIntentUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { connectIntentEntity, loading, updating } = props;

  const { messages, reminder } = connectIntentEntity;

  const handleClose = () => {
    props.history.push('/connect-intent' + props.location.search);
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
        ...connectIntentEntity,
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
          <h2 id="connectGatewayApp.analyticCollectionServiceConnectIntent.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.home.createOrEditLabel">
              Create or edit a ConnectIntent
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : connectIntentEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="connect-intent-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="connect-intent-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="intentIdLabel" for="connect-intent-intentId">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.intentId">Intent Id</Translate>
                </Label>
                <AvField
                  id="connect-intent-intentId"
                  type="text"
                  name="intentId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="flowIdLabel" for="connect-intent-flowId">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.flowId">Flow Id</Translate>
                </Label>
                <AvField id="connect-intent-flowId" type="text" name="flowId" />
              </AvGroup>
              <AvGroup>
                <Label id="connectChannelLabel" for="connect-intent-connectChannel">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.connectChannel">
                    Connect Channel
                  </Translate>
                </Label>
                <AvField
                  id="connect-intent-connectChannel"
                  type="text"
                  name="connectChannel"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="connect-intent-description">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.description">Description</Translate>
                </Label>
                <AvField id="connect-intent-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="connectTypeLabel" for="connect-intent-connectType">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.connectType">Connect Type</Translate>
                </Label>
                <AvField
                  id="connect-intent-connectType"
                  type="text"
                  name="connectType"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="messagesLabel" for="connect-intent-messages">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.messages">Messages</Translate>
                </Label>
                <AvInput
                  id="connect-intent-messages"
                  type="textarea"
                  name="messages"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="reminderLabel" for="connect-intent-reminder">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.reminder">Reminder</Translate>
                </Label>
                <AvInput id="connect-intent-reminder" type="textarea" name="reminder" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="connect-intent-createdDate">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="connect-intent-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.connectIntentEntity.createdDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedDateLabel" for="connect-intent-updatedDate">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.updatedDate">Updated Date</Translate>
                </Label>
                <AvInput
                  id="connect-intent-updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.connectIntentEntity.updatedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="connect-intent-createdBy">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectIntent.createdBy">Created By</Translate>
                </Label>
                <AvField
                  id="connect-intent-createdBy"
                  type="text"
                  name="createdBy"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/connect-intent" replace color="info">
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
  connectIntentEntity: storeState.connectIntent.entity,
  loading: storeState.connectIntent.loading,
  updating: storeState.connectIntent.updating,
  updateSuccess: storeState.connectIntent.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ConnectIntentUpdate);
