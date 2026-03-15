import './filter_bar.css';

const PARTY_SIZES = [1, 2, 3, 4, 5, 6, 7, 8];

function FilterBar({filters, onChange, timeSlots}) {
    function handleChange(field, value) {
        onChange({...filters, [field]: value});
    }

    function handleClear() {
        onChange({date: '', time: '', partySize: ''});
    }

    const hasFilters = filters.date || filters.time || filters.partySize;

    return (
        <div className="filter-bar">
            <div className="filter-group">
                <label htmlFor="filter-date">Date</label>
                <input
                    id="filter-date"
                    type="date"
                    min={new Date().toISOString().split('T')[0]}
                    value={filters.date}
                    onChange={e => handleChange('date', e.target.value)}
                />
            </div>

            <div className="filter-group">
                <label htmlFor="filter-time">Time</label>
                <input
                    id="filter-time"
                    type="time"
                    value={filters.time}
                    onChange={e => handleChange('time', e.target.value)}
                    list="time-slots"
                />
                <datalist id="time-slots">
                    {timeSlots.map(slot => (
                        <option key={slot} value={slot}/>
                    ))}
                </datalist>
            </div>

            <div className="filter-group">
                <label htmlFor="filter-party">Party size</label>
                <select
                    id="filter-party"
                    value={filters.partySize}
                    onChange={e => handleChange('partySize', e.target.value)}
                >
                    <option value="">Any size</option>
                    {PARTY_SIZES.map(n => (
                        <option key={n} value={n}>
                            {n} {n === 1 ? 'guest' : 'guests'}
                        </option>
                    ))}
                </select>
            </div>

            {hasFilters && (
                <button className="filter-clear" onClick={handleClear}>
                    Clear filters
                </button>
            )}
        </div>
    );
}

export default FilterBar;
