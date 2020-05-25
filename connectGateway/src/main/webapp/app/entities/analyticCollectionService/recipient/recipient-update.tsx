import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './recipient.reducer';
import { IRecipient } from 'app/shared/model/analyticCollectionService/recipient.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IRecipientUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const RecipientUpdate = (props: IRecipientUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { recipientEntity, loading, updating } = props;

  const { connectDetails } = recipientEntity;

  const handleClose = () => {
    props.history.push('/recipient' + props.location.search);
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
        ...recipientEntity,
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
          <h2 id="connectGatewayApp.analyticCollectionServiceRecipient.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.home.createOrEditLabel">
              Create or edit a Recipient
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : recipientEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="recipient-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="recipient-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="campaignIdLabel" for="recipient-campaignId">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.campaignId">Campaign Id</Translate>
                </Label>
                <AvField id="recipient-campaignId" type="text" name="campaignId" />
              </AvGroup>
              <AvGroup>
                <Label id="refLabel" for="recipient-ref">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.ref">Ref</Translate>
                </Label>
                <AvField id="recipient-ref" type="text" name="ref" />
              </AvGroup>
              <AvGroup>
                <Label id="connectDetailsLabel" for="recipient-connectDetails">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.connectDetails">Connect Details</Translate>
                </Label>
                <AvInput
                  id="recipient-connectDetails"
                  type="textarea"
                  name="connectDetails"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="recipient-createdDate">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceRecipient.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="recipient-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.recipientEntity.createdDate)}
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/recipient" replace color="info">
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
  recipientEntity: storeState.recipient.entity,
  loading: storeState.recipient.loading,
  updating: storeState.recipient.updating,
  updateSuccess: storeState.recipient.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(RecipientUpdate);
