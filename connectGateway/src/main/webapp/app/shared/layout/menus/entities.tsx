import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.entities.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/campaign">
      <Translate contentKey="global.menu.entities.connectServiceCampaign" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/campaign-flow">
      <Translate contentKey="global.menu.entities.connectServiceCampaignFlow" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/connect-config">
      <Translate contentKey="global.menu.entities.connectServiceConnectConfig" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/connect-event">
      <Translate contentKey="global.menu.entities.connectServiceConnectEvent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/connect-intent">
      <Translate contentKey="global.menu.entities.connectServiceConnectIntent" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/recipient">
      <Translate contentKey="global.menu.entities.connectServiceRecipient" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/reminder">
      <Translate contentKey="global.menu.entities.connectServiceReminder" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/connect-state">
      <Translate contentKey="global.menu.entities.connectServiceConnectState" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/template-flow">
      <Translate contentKey="global.menu.entities.connectServiceTemplateFlow" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
