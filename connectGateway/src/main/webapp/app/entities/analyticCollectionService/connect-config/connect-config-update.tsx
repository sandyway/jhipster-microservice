import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './connect-config.reducer';
import { IConnectConfig } from 'app/shared/model/analyticCollectionService/connect-config.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IConnectConfigUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ConnectConfigUpdate = (props: IConnectConfigUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { connectConfigEntity, loading, updating } = props;

  const { facebook, viber } = connectConfigEntity;

  const handleClose = () => {
    props.history.push('/connect-config' + props.location.search);
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
        ...connectConfigEntity,
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
          <h2 id="connectGatewayApp.analyticCollectionServiceConnectConfig.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.home.createOrEditLabel">
              Create or edit a ConnectConfig
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : connectConfigEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="connect-config-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="connect-config-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="userIdLabel" for="connect-config-userId">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.userId">User Id</Translate>
                </Label>
                <AvField
                  id="connect-config-userId"
                  type="text"
                  name="userId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="facebookLabel" for="connect-config-facebook">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.facebook">Facebook</Translate>
                </Label>
                <AvInput id="connect-config-facebook" type="textarea" name="facebook" />
              </AvGroup>
              <AvGroup>
                <Label id="viberLabel" for="connect-config-viber">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.viber">Viber</Translate>
                </Label>
                <AvInput id="connect-config-viber" type="textarea" name="viber" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="connect-config-createdDate">
                  <Translate contentKey="connectGatewayApp.analyticCollectionServiceConnectConfig.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="connect-config-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.connectConfigEntity.createdDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/connect-config" replace color="info">
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
  connectConfigEntity: storeState.connectConfig.entity,
  loading: storeState.connectConfig.loading,
  updating: storeState.connectConfig.updating,
  updateSuccess: storeState.connectConfig.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(ConnectConfigUpdate);
