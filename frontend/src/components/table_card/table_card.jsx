import './table_card.css';

function TableCard({table, onReserve, isRecommended, isViewOnly = false, isDimmed = false}) {
    const isAvailable = table.available;

    return (
        <div className={`table-card ${!isViewOnly && !isAvailable ? 'table-card--unavailable' : ''} ${isRecommended ? 'table-card--recommended' : ''} ${isDimmed ? 'table-card--dimmed' : ''}`}>
            <div className="table-card-header">
                <span className="table-card-number">Table {table.id}</span>
                {!isViewOnly && (
                    <span className={`table-card-status ${isAvailable ? 'status--available' : 'status--reserved'}`}>
            {isAvailable ? 'Available' : 'Reserved'}
          </span>
                )}
            </div>
            {isRecommended && (
                <div className="recommendation-badge">Recommended</div>
            )}
            <div className="table-card-details">
                <div className="table-card-detail">
                    <span className="detail-label">Seats</span>
                    <span className="detail-value">{table.capacity}</span>
                </div>
                <div className="table-card-detail">
                    <span className="detail-label">Location</span>
                    <span className="detail-value">{table.location}</span>
                </div>
                <div className="table-card-attributes">
                    {table.isByTheWindow && <span className="attribute-badge">Window</span>}
                    {table.isPrivate && <span className="attribute-badge">Private</span>}
                    {table.smokingAllowed && <span className="attribute-badge">Smoking</span>}
                </div>
            </div>
            {!isViewOnly && (
                <button
                    className="table-card-reserve"
                    onClick={() => isAvailable && onReserve(table)}
                    disabled={!isAvailable}
                >
                    {isAvailable ? 'Reserve' : 'Unavailable'}
                </button>
            )}
        </div>
    );
}

export default TableCard;
