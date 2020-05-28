import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './connect-event.reducer';
import { IConnectEvent } from 'app/shared/model/connectService/connect-event.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConnectEventUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConnectEventUpdate = (props: IConnectEventUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { connectEventEntity, loading, updating } = props;

  const { connectEvent, reference } = connectEventEntity;

  const handleClose = () => {
    props.history.push('/connect-event' + props.location.search);
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

    if (errors.length === 0) {
      const entity = {
        ...connectEventEntity,
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
          <h2 id="connectGatewayApp.connectServiceConnectEvent.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.connectServiceConnectEvent.home.createOrEditLabel">
              Create or edit a ConnectEvent
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : connectEventEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="connect-event-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="connect-event-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="analyticIdLabel" for="connect-event-analyticId">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectEvent.analyticId">Analytic Id</Translate>
                </Label>
                <AvField
                  id="connect-event-analyticId"
                  type="text"
                  name="analyticId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="connectEventLabel" for="connect-event-connectEvent">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectEvent.connectEvent">Connect Event</Translate>
                </Label>
                <AvInput id="connect-event-connectEvent" type="textarea" name="connectEvent" />
              </AvGroup>
              <AvGroup>
                <Label id="referenceLabel" for="connect-event-reference">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectEvent.reference">Reference</Translate>
                </Label>
                <AvInput id="connect-event-reference" type="textarea" name="reference" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="connect-event-createdDate">
                  <Translate contentKey="connectGatewayApp.connectServiceConnectEvent.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="connect-event-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.connectEventEntity.createdDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/connect-event" replace color="info">
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
  connectEventEntity: storeState.connectEvent.entity,
  loading: storeState.connectEvent.loading,
  updating: storeState.connectEvent.updating,
  updateSuccess: storeState.connectEvent.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ConnectEventUpdate);
