import { Moment } from 'moment';

export interface ICampaign {
  id?: number;
  campaignDetails?: any;
  campaignType?: string;
  startDate?: string;
  endDate?: string;
  createdBy?: string;
  createdDate?: string;
}

export const defaultValue: Readonly<ICampaign> = {};
