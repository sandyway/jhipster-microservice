import { Moment } from 'moment';

export interface IReminder {
  id?: number;
  recipientId?: string;
  campaignId?: string;
  intentId?: string;
  channel?: string;
  connectDetails?: any;
  done?: boolean;
  executions?: number;
  createdDate?: string;
  updatedDate?: string;
}

export const defaultValue: Readonly<IReminder> = {
  done: false,
};
