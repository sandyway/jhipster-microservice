import { Moment } from 'moment';

export interface IConnectState {
  id?: number;
  campaignId?: string;
  recipientId?: string;
  connectDetails?: any;
  channel?: string;
  intentId?: string;
  intentType?: string;
  connectEvent?: any;
  createdDate?: string;
  updatedDate?: string;
}

export const defaultValue: Readonly<IConnectState> = {};
