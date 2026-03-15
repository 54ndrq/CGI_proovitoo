import { useState } from 'react';
import './attribute_filter.css';

function AttributeFilter({ filters, onChange }) {
  const handleChange = (field, checked) => {
    onChange({ ...filters, [field]: checked });
  };

  return (
    <div className="attribute-filter">
      <label className="checkbox-label">
        <input
          type="checkbox"
          checked={filters.isByTheWindow || false}
          onChange={(e) => handleChange('isByTheWindow', e.target.checked)}
        />
        Window Seat
      </label>
      <label className="checkbox-label">
        <input
          type="checkbox"
          checked={filters.isPrivate || false}
          onChange={(e) => handleChange('isPrivate', e.target.checked)}
        />
        Private
      </label>
      <label className="checkbox-label">
        <input
          type="checkbox"
          checked={filters.smokingAllowed || false}
          onChange={(e) => handleChange('smokingAllowed', e.target.checked)}
        />
        Smoking Allowed
      </label>
    </div>
  );
}

export default AttributeFilter;
