import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConnectConfig, defaultValue } from 'app/shared/model/connectService/connect-config.model';

export const ACTION_TYPES = {
  FETCH_CONNECTCONFIG_LIST: 'connectConfig/FETCH_CONNECTCONFIG_LIST',
  FETCH_CONNECTCONFIG: 'connectConfig/FETCH_CONNECTCONFIG',
  CREATE_CONNECTCONFIG: 'connectConfig/CREATE_CONNECTCONFIG',
  UPDATE_CONNECTCONFIG: 'connectConfig/UPDATE_CONNECTCONFIG',
  DELETE_CONNECTCONFIG: 'connectConfig/DELETE_CONNECTCONFIG',
  SET_BLOB: 'connectConfig/SET_BLOB',
  RESET: 'connectConfig/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConnectConfig>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConnectConfigState = Readonly<typeof initialState>;

// Reducer

export default (state: ConnectConfigState = initialState, action): ConnectConfigState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONNECTCONFIG_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONNECTCONFIG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONNECTCONFIG):
    case REQUEST(ACTION_TYPES.UPDATE_CONNECTCONFIG):
    case REQUEST(ACTION_TYPES.DELETE_CONNECTCONFIG):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONNECTCONFIG_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONNECTCONFIG):
    case FAILURE(ACTION_TYPES.CREATE_CONNECTCONFIG):
    case FAILURE(ACTION_TYPES.UPDATE_CONNECTCONFIG):
    case FAILURE(ACTION_TYPES.DELETE_CONNECTCONFIG):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONNECTCONFIG_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONNECTCONFIG):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONNECTCONFIG):
    case SUCCESS(ACTION_TYPES.UPDATE_CONNECTCONFIG):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONNECTCONFIG):
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

const apiUrl = 'services/connectservice/api/connect-configs';

// Actions

export const getEntities: ICrudGetAllAction<IConnectConfig> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONNECTCONFIG_LIST,
    payload: axios.get<IConnectConfig>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConnectConfig> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONNECTCONFIG,
    payload: axios.get<IConnectConfig>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConnectConfig> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONNECTCONFIG,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConnectConfig> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONNECTCONFIG,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConnectConfig> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONNECTCONFIG,
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
