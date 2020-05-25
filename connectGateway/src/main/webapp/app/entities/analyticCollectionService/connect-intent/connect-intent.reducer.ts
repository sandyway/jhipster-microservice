import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IConnectIntent, defaultValue } from 'app/shared/model/analyticCollectionService/connect-intent.model';

export const ACTION_TYPES = {
  FETCH_CONNECTINTENT_LIST: 'connectIntent/FETCH_CONNECTINTENT_LIST',
  FETCH_CONNECTINTENT: 'connectIntent/FETCH_CONNECTINTENT',
  CREATE_CONNECTINTENT: 'connectIntent/CREATE_CONNECTINTENT',
  UPDATE_CONNECTINTENT: 'connectIntent/UPDATE_CONNECTINTENT',
  DELETE_CONNECTINTENT: 'connectIntent/DELETE_CONNECTINTENT',
  SET_BLOB: 'connectIntent/SET_BLOB',
  RESET: 'connectIntent/RESET',
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IConnectIntent>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

export type ConnectIntentState = Readonly<typeof initialState>;

// Reducer

export default (state: ConnectIntentState = initialState, action): ConnectIntentState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_CONNECTINTENT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_CONNECTINTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true,
      };
    case REQUEST(ACTION_TYPES.CREATE_CONNECTINTENT):
    case REQUEST(ACTION_TYPES.UPDATE_CONNECTINTENT):
    case REQUEST(ACTION_TYPES.DELETE_CONNECTINTENT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true,
      };
    case FAILURE(ACTION_TYPES.FETCH_CONNECTINTENT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_CONNECTINTENT):
    case FAILURE(ACTION_TYPES.CREATE_CONNECTINTENT):
    case FAILURE(ACTION_TYPES.UPDATE_CONNECTINTENT):
    case FAILURE(ACTION_TYPES.DELETE_CONNECTINTENT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload,
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONNECTINTENT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10),
      };
    case SUCCESS(ACTION_TYPES.FETCH_CONNECTINTENT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.CREATE_CONNECTINTENT):
    case SUCCESS(ACTION_TYPES.UPDATE_CONNECTINTENT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data,
      };
    case SUCCESS(ACTION_TYPES.DELETE_CONNECTINTENT):
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

const apiUrl = 'services/analyticcollectionservice/api/connect-intents';

// Actions

export const getEntities: ICrudGetAllAction<IConnectIntent> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_CONNECTINTENT_LIST,
    payload: axios.get<IConnectIntent>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`),
  };
};

export const getEntity: ICrudGetAction<IConnectIntent> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_CONNECTINTENT,
    payload: axios.get<IConnectIntent>(requestUrl),
  };
};

export const createEntity: ICrudPutAction<IConnectIntent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_CONNECTINTENT,
    payload: axios.post(apiUrl, cleanEntity(entity)),
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IConnectIntent> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_CONNECTINTENT,
    payload: axios.put(apiUrl, cleanEntity(entity)),
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IConnectIntent> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_CONNECTINTENT,
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
