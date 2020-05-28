import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, byteSize, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './template-flow.reducer';
import { ITemplateFlow } from 'app/shared/model/connectService/template-flow.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITemplateFlowDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TemplateFlowDetail = (props: ITemplateFlowDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { templateFlowEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.detail.title">TemplateFlow</Translate> [
          <b>{templateFlowEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="connectDetails">
              <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.connectDetails">Connect Details</Translate>
            </span>
          </dt>
          <dd>{templateFlowEntity.connectDetails}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{templateFlowEntity.userId}</dd>
          <dt>
            <span id="createdBy">
              <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.createdBy">Created By</Translate>
            </span>
          </dt>
          <dd>{templateFlowEntity.createdBy}</dd>
          <dt>
            <span id="createdDate">
              <Translate contentKey="connectGatewayApp.connectServiceTemplateFlow.createdDate">Created Date</Translate>
            </span>
          </dt>
          <dd>
            {templateFlowEntity.createdDate ? (
              <TextFormat value={templateFlowEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/template-flow" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/template-flow/${templateFlowEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ templateFlow }: IRootState) => ({
  templateFlowEntity: templateFlow.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TemplateFlowDetail);
