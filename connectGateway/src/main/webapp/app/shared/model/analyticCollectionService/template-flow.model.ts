import { Moment } from 'moment';

export interface ITemplateFlow {
  id?: number;
  connectDetails?: any;
  userId?: string;
  createdBy?: string;
  createdDate?: string;
}

export const defaultValue: Readonly<ITemplateFlow> = {};
