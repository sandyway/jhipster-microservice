import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ITemplateFlow, defaultValue } from 'app/shared/model/connectService/template-flow.model';

export const ACTION_TYPES = {
  FETCH_TEMPLATEFLOW_LIST: 'templateFlow/FETCH_TEMPLATEFLOW_LIST',
  FETCH_TEMPLATEFLOW: 'templateFlow/FETCH_TEMPLATEFLOW',
  CREATE_TEMPLATEFLOW: 'templateFlow/CREATE_TEMPLATEFLOW',
  UPDATE_TEMPLATEFLOW: 'templateFlow/UPDATE_TEMPLATEFLOW',
  DELETE_TEMPLATEFLOW: 'templateFlow/DELETE_TEMPLATEFLOW',
  SET_BLOB: 'templateFlow/SET_BLOB',
  RESET: 'templateFlow/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ITemplateFlow>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type TemplateFlowState = Readonly<typeof initialState>;

// Reducer

export default (state: TemplateFlowState = initialState, action): TemplateFlowState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_TEMPLATEFLOW_LIST):
    case REQUEST(ACTION_TYPES.FETCH_TEMPLATEFLOW):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_TEMPLATEFLOW):
    case REQUEST(ACTION_TYPES.UPDATE_TEMPLATEFLOW):
    case REQUEST(ACTION_TYPES.DELETE_TEMPLATEFLOW):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_TEMPLATEFLOW_LIST):
    case FAILURE(ACTION_TYPES.FETCH_TEMPLATEFLOW):
    case FAILURE(ACTION_TYPES.CREATE_TEMPLATEFLOW):
    case FAILURE(ACTION_TYPES.UPDATE_TEMPLATEFLOW):
    case FAILURE(ACTION_TYPES.DELETE_TEMPLATEFLOW):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEMPLATEFLOW_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_TEMPLATEFLOW):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_TEMPLATEFLOW):
    case SUCCESS(ACTION_TYPES.UPDATE_TEMPLATEFLOW):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_TEMPLATEFLOW):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {},
      };
    case ACTION_TYPES.SET_BLOB: {
      const { name, data, contentType } = action.payload;
      return {
        ...state,
        entity: {
          ...state.entity,
          [name]: data,
          [name + 'ContentType']: contentType,
        },
      };
    }
    case ACTION_TYPES.RESET:
      return {
        ...initialState,
      };
    default:
      return state;
  }
};

const apiUrl = 'services/connectservice/api/template-flows';

// Actions

export const getEntities: ICrudGetAllAction<ITemplateFlow> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_TEMPLATEFLOW_LIST,
    payload: axios.get<ITemplateFlow>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<ITemplateFlow> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_TEMPLATEFLOW,
    payload: axios.get<ITemplateFlow>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<ITemplateFlow> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_TEMPLATEFLOW,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ITemplateFlow> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_TEMPLATEFLOW,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ITemplateFlow> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_TEMPLATEFLOW,
    payload: axios.delete(requestUrl),
  });
  return result;
};

export const setBlob = (name, data, contentType?) => ({
  type: ACTION_TYPES.SET_BLOB,
  payload: {
    name,
    data,
    contentType,
  },
});

export const reset = () => ({
  type: ACTION_TYPES.RESET,
});
