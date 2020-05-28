import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ICampaignFlow, defaultValue } from 'app/shared/model/connectService/campaign-flow.model';

export const ACTION_TYPES = {
  FETCH_CAMPAIGNFLOW_LIST: 'campaignFlow/FETCH_CAMPAIGNFLOW_LIST',
  FETCH_CAMPAIGNFLOW: 'campaignFlow/FETCH_CAMPAIGNFLOW',
  CREATE_CAMPAIGNFLOW: 'campaignFlow/CREATE_CAMPAIGNFLOW',
  UPDATE_CAMPAIGNFLOW: 'campaignFlow/UPDATE_CAMPAIGNFLOW',
  DELETE_CAMPAIGNFLOW: 'campaignFlow/DELETE_CAMPAIGNFLOW',
  RESET: 'campaignFlow/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ICampaignFlow>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type CampaignFlowState = Readonly<typeof initialState>;

// Reducer

export default (state: CampaignFlowState = initialState, action): CampaignFlowState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CAMPAIGNFLOW_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CAMPAIGNFLOW):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CAMPAIGNFLOW):
    case REQUEST(ACTION_TYPES.UPDATE_CAMPAIGNFLOW):
    case REQUEST(ACTION_TYPES.DELETE_CAMPAIGNFLOW):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CAMPAIGNFLOW_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CAMPAIGNFLOW):
    case FAILURE(ACTION_TYPES.CREATE_CAMPAIGNFLOW):
    case FAILURE(ACTION_TYPES.UPDATE_CAMPAIGNFLOW):
    case FAILURE(ACTION_TYPES.DELETE_CAMPAIGNFLOW):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAMPAIGNFLOW_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CAMPAIGNFLOW):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CAMPAIGNFLOW):
    case SUCCESS(ACTION_TYPES.UPDATE_CAMPAIGNFLOW):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CAMPAIGNFLOW):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'services/connectservice/api/campaign-flows';

// Actions

export const getEntities: ICrudGetAllAction<ICampaignFlow> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CAMPAIGNFLOW_LIST,
    payload: axios.get<ICampaignFlow>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ICampaignFlow> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CAMPAIGNFLOW,
    payload: axios.get<ICampaignFlow>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ICampaignFlow> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CAMPAIGNFLOW,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ICampaignFlow> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CAMPAIGNFLOW,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ICampaignFlow> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CAMPAIGNFLOW,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
