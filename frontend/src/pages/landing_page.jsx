import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import FilterBar from '../components/filter_bar/filter_bar';
import TableLayout from '../components/table_layout/table_layout';
import {fetchTables, fetchTimeSlots} from '../services/reservation_service';

function LandingPage() {
    const navigate = useNavigate();
    const [filters, setFilters] = useState({date: '', time: '', partySize: ''});
    const [tables, setTables] = useState([]);
    const [timeSlots, setTimeSlots] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        async function loadData() {
            const slots = await fetchTimeSlots();
            setTimeSlots(slots);

            // Show general layout
            const allTables = await fetchTables({});
            setTables(allTables);
            setLoading(false);
        }

        loadData();
    }, []);

    const handleSearch = () => {
        if (filters.date && filters.time && filters.partySize) {
            const searchParams = new URLSearchParams(filters).toString();
            navigate(`/reserve?${searchParams}`);
        } else {
            alert("Please select Date, Time, and Party Size first.");
        }
    };

    return (
        <div className="page landing-page">
            <div className="hero-section">
                <h1>Find Your Perfect Table</h1>
                <p>Browse our restaurant plan and book a table for your next meal.</p>
                <FilterBar
                    filters={filters}
                    onChange={setFilters}
                    timeSlots={timeSlots}
                />
                <button className="btn-primary cta-button" onClick={handleSearch}>
                    Check Availability
                </button>
            </div>

            <div className="layout-preview">
                <h2>Restaurant Floor Plan</h2>
                {loading ? (
                    <div>Loading plan...</div>
                ) : (
                    <TableLayout tables={tables} mode="view"/>
                )}
            </div>
        </div>
    );
}

export default LandingPage;
