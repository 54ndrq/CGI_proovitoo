import './table_layout.css';
import TableCard from '../table_card/table_card';

function TableLayout({tables, onReserve, mode = 'view', recommendedTableId = null, activeFilters = {}}) {
    // Group tables by area
    const areas = tables.reduce((acc, table) => {
        const area = table.location || 'Unknown Area';
        if (!acc[area]) {
            acc[area] = [];
        }
        acc[area].push(table);
        return acc;
    }, {});

    const isMatch = (table) => {
        if (recommendedTableId === table.id) return true;
        if (!table.available) return false;

        if (activeFilters.isByTheWindow && !table.isByTheWindow) return false;
        if (activeFilters.isPrivate && !table.isPrivate) return false;
        if (activeFilters.smokingAllowed && !table.smokingAllowed) return false;
        return true;
    };

    return (
        <div className="table-layout">
            {Object.entries(areas).map(([areaName, areaTables]) => (
                <div key={areaName} className="restaurant-area">
                    <h3 className="area-title">{areaName}</h3>
                    <div className="area-tables">
                        {areaTables.map(table => (
                            <TableCard
                                key={table.id}
                                table={table}
                                onReserve={mode === 'reserve' ? onReserve : undefined}
                                isRecommended={recommendedTableId === table.id}
                                isViewOnly={mode === 'view'}
                                isDimmed={!isMatch(table)}
                            />
                        ))}
                    </div>
                </div>
            ))}
        </div>
    );
}

export default TableLayout;
