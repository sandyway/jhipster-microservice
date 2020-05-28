import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './connect-state.reducer';
import { IConnectState } from 'app/shared/model/connectService/connect-state.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConnectStateUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConnectStateUpdate = (props: IConnectStateUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { connectStateEntity, loading, updating } = props;

  const { connectDetails, connectEvent } = connectStateEntity;

  const handleClose = () => {
    props.history.push('/connect-state' + props.location.search);
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
        ...connectStateEntity,
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
          <h2 id="connectGatewayApp.connectServiceConnectState.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.connectServiceConnectState.home.createOrEditLabel">
              Create or edit a ConnectState
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : connectStateEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="connect-state-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="connect-state-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="campaignIdLabel" for="connect-state-campaignId">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.campaignId">Campaign Id</Translate>
                </Label>
                <AvField
                  id="connect-state-campaignId"
                  type="text"
                  name="campaignId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="recipientIdLabel" for="connect-state-recipientId">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.recipientId">Recipient Id</Translate>
                </Label>
                <AvField
                  id="connect-state-recipientId"
                  type="text"
                  name="recipientId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="connectDetailsLabel" for="connect-state-connectDetails">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.connectDetails">Connect Details</Translate>
                </Label>
                <AvInput
                  id="connect-state-connectDetails"
                  type="textarea"
                  name="connectDetails"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="channelLabel" for="connect-state-channel">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.channel">Channel</Translate>
                </Label>
                <AvInput
                  id="connect-state-channel"
                  type="select"
                  className="form-control"
                  name="channel"
                  value={(!isNew && connectStateEntity.channel) || 'SMS'}
                >
                  <option value="SMS">{translate('connectGatewayApp.Channel.SMS')}</option>
                  <option value="MESSENGER">{translate('connectGatewayApp.Channel.MESSENGER')}</option>
                  <option value="WHATSAPP">{translate('connectGatewayApp.Channel.WHATSAPP')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="intentIdLabel" for="connect-state-intentId">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.intentId">Intent Id</Translate>
                </Label>
                <AvField id="connect-state-intentId" type="text" name="intentId" />
              </AvGroup>
              <AvGroup>
                <Label id="intentTypeLabel" for="connect-state-intentType">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.intentType">Intent Type</Translate>
                </Label>
                <AvInput
                  id="connect-state-intentType"
                  type="select"
                  className="form-control"
                  name="intentType"
                  value={(!isNew && connectStateEntity.intentType) || 'BROADCAST'}
                >
                  <option value="BROADCAST">{translate('connectGatewayApp.IntentTypes.BROADCAST')}</option>
                  <option value="START">{translate('connectGatewayApp.IntentTypes.START')}</option>
                  <option value="OPTION">{translate('connectGatewayApp.IntentTypes.OPTION')}</option>
                  <option value="FALLBACK">{translate('connectGatewayApp.IntentTypes.FALLBACK')}</option>
                  <option value="EXPIRE">{translate('connectGatewayApp.IntentTypes.EXPIRE')}</option>
                  <option value="COMPLETE">{translate('connectGatewayApp.IntentTypes.COMPLETE')}</option>
                  <option value="PAYMENT">{translate('connectGatewayApp.IntentTypes.PAYMENT')}</option>
                </AvInput>
              </AvGroup>
              <AvGroup>
                <Label id="connectEventLabel" for="connect-state-connectEvent">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.connectEvent">Connect Event</Translate>
                </Label>
                <AvInput id="connect-state-connectEvent" type="textarea" name="connectEvent" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="connect-state-createdDate">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="connect-state-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.connectStateEntity.createdDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="updatedDateLabel" for="connect-state-updatedDate">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectState.updatedDate">Updated Date</Translate>
                </Label>
                <AvInput
                  id="connect-state-updatedDate"
                  type="datetime-local"
                  className="form-control"
                  name="updatedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.connectStateEntity.updatedDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/connect-state" replace color="info">
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
  connectStateEntity: storeState.connectState.entity,
  loading: storeState.connectState.loading,
  updating: storeState.connectState.updating,
  updateSuccess: storeState.connectState.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ConnectStateUpdate);
