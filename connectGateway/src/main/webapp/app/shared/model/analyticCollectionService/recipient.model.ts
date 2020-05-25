import { Moment } from 'moment';

export interface IRecipient {
  id?: number;
  campaignId?: string;
  ref?: string;
  connectDetails?: any;
  createdDate?: string;
}

export const defaultValue: Readonly<IRecipient> = {};
