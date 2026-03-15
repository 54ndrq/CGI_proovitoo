import {useCallback, useEffect, useState} from 'react';
import {useSearchParams} from 'react-router-dom';
import FilterBar from '../components/filter_bar/filter_bar';
import AttributeFilter from '../components/attribute_filter/attribute_filter';
import TableLayout from '../components/table_layout/table_layout';
import ReservationForm from '../components/reservation_form/reservation_form';
import {createReservation, fetchRecommendations, fetchTables, fetchTimeSlots} from '../services/reservation_service';

function ResultsPage() {
    const [searchParams, setSearchParams] = useSearchParams();
    const [filters, setFilters] = useState({
        date: searchParams.get('date') || '',
        time: searchParams.get('time') || '',
        partySize: searchParams.get('partySize') || ''
    });

    const [attributes, setAttributes] = useState({
        isByTheWindow: false,
        isPrivate: false,
        smokingAllowed: false
    });

    const [tables, setTables] = useState([]);
    const [recommendedTableId, setRecommendedTableId] = useState(null);
    const [timeSlots, setTimeSlots] = useState([]);
    const [loading, setLoading] = useState(true);
    const [selectedTable, setSelectedTable] = useState(null);

    useEffect(() => {
        async function loadTimeSlots() {
            const slots = await fetchTimeSlots();
            setTimeSlots(slots);
        }

        loadTimeSlots();
    }, []);

    useEffect(() => {
        setSearchParams(filters);
    }, [filters, setSearchParams]);

    const loadTables = useCallback(async () => {
        setLoading(true);
        const allFilters = {...filters, ...attributes};

        try {
            const [data, recommendation] = await Promise.all([
                fetchTables(filters),
                (filters.date && filters.time)
                    ? fetchRecommendations(allFilters)
                    : Promise.resolve(null)
            ]);

            setTables(data);
            setRecommendedTableId(recommendation ? recommendation.id : null);
        } catch (e) {
            console.error("Failed to load tables or recommendations", e);
        }
        setLoading(false);
    }, [filters, attributes]);

    useEffect(() => {
        if (filters.date && filters.time) {
            loadTables();
        }
    }, [loadTables]);

    async function handleReservation(reservation) {
        try {
            await createReservation(reservation);
            setSelectedTable(null);
            await loadTables(); // Refresh availability
            alert("Reservation Confirmed!");
        } catch (error) {
            alert(`Reservation Failed: ${error.message}`);
        }
    }

    return (
        <div className="page results-page">
            <div className="filters-section">
                <FilterBar
                    filters={filters}
                    onChange={setFilters}
                    timeSlots={timeSlots}
                />
                <AttributeFilter
                    filters={attributes}
                    onChange={setAttributes}
                />
            </div>

            <div className="results-container">
                {loading ? (
                    <div>Checking availability...</div>
                ) : (
                    <TableLayout
                        tables={tables}
                        mode="reserve"
                        onReserve={setSelectedTable}
                        recommendedTableId={recommendedTableId}
                        activeFilters={attributes}
                    />
                )}
            </div>

            {selectedTable && (
                <ReservationForm
                    table={selectedTable}
                    filters={filters}
                    timeSlots={timeSlots}
                    onSubmit={handleReservation}
                    onCancel={() => setSelectedTable(null)}
                />
            )}
        </div>
    );
}

export default ResultsPage;
