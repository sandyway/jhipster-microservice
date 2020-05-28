import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, setFileData, byteSize, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, setBlob, reset } from './template-flow.reducer';
import { ITemplateFlow } from 'app/shared/model/connectService/template-flow.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITemplateFlowUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TemplateFlowUpdate = (props: ITemplateFlowUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { templateFlowEntity, loading, updating } = props;

  const { connectDetails } = templateFlowEntity;

  const handleClose = () => {
    props.history.push('/template-flow' + props.location.search);
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
        ...templateFlowEntity,
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
          <h2 id="connectGatewayApp.connectServiceTemplateFlow.home.createOrEditLabel">
            <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.home.createOrEditLabel">
              Create or edit a TemplateFlow
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : templateFlowEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="template-flow-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="template-flow-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="connectDetailsLabel" for="template-flow-connectDetails">
                  <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.connectDetails">Connect Details</Translate>
                </Label>
                <AvInput
                  id="template-flow-connectDetails"
                  type="textarea"
                  name="connectDetails"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="template-flow-userId">
                  <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.userId">User Id</Translate>
                </Label>
                <AvField
                  id="template-flow-userId"
                  type="text"
                  name="userId"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="template-flow-createdBy">
                  <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.createdBy">Created By</Translate>
                </Label>
                <AvField
                  id="template-flow-createdBy"
                  type="text"
                  name="createdBy"
                  validate={{
                    required: { value: true, errorMessage: translate('entity.validation.required') },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="template-flow-createdDate">
                  <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.createdDate">Created Date</Translate>
                </Label>
                <AvInput
                  id="template-flow-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.templateFlowEntity.createdDate)}
                />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/template-flow" replace color="info">
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
  templateFlowEntity: storeState.templateFlow.entity,
  loading: storeState.templateFlow.loading,
  updating: storeState.templateFlow.updating,
  updateSuccess: storeState.templateFlow.updateSuccess,
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

export default connect(mapStateToProps, mapDispatchToProps)(TemplateFlowUpdate);
