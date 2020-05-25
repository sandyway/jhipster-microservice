import { Moment } from 'moment';

export interface IConnectIntent {
  id?: number;
  intentId?: string;
  flowId?: string;
  connectChannel?: string;
  description?: string;
  connectType?: string;
  messages?: any;
  reminder?: any;
  createdDate?: string;
  updatedDate?: string;
  createdBy?: string;
}

export const defaultValue: Readonly<IConnectIntent> = {};
