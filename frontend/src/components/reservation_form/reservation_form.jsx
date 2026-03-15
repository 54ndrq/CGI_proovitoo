import {useState} from 'react';
import './reservation_form.css';

function ReservationForm({table, filters, timeSlots, onSubmit, onCancel}) {
    const [name, setName] = useState('');
    const [phone, setPhone] = useState('');
    const [date] = useState(filters.date ?? '');
    const [time] = useState(filters.time ?? '');
    const [partySize] = useState(filters.partySize ?? '1');
    const [submitting, setSubmitting] = useState(false);

    async function handleSubmit(e) {
        e.preventDefault();
        setSubmitting(true);
        await onSubmit({
            tableId: table.id,
            customerName: name,
            date,
            time,
            partySize: Number(partySize),
            phone,
        });
        setSubmitting(false);
    }

    return (
        <div className="reservation-overlay" onClick={onCancel}>
            <form
                className="reservation-form"
                onClick={e => e.stopPropagation()}
                onSubmit={handleSubmit}
            >
                <h2>Reserve Table {table.id}</h2>
                <p className="reservation-form-info">
                    {table.capacity} seats &middot; {table.location}
                </p>

                <div className="form-field">
                    <label htmlFor="res-name">Name</label>
                    <input
                        id="res-name"
                        type="text"
                        required
                        value={name}
                        onChange={e => setName(e.target.value)}
                        placeholder="Your name"
                    />
                </div>

                <div className="form-field">
                    <label htmlFor="res-phone">Phone</label>
                    <input
                        id="res-phone"
                        type="tel"
                        required
                        value={phone}
                        onChange={e => setPhone(e.target.value)}
                        placeholder="Phone number"
                    />
                </div>

                <div className="form-row">
                    <div className="form-field">
                        <label>Date</label>
                        <div className="static-value">{date}</div>
                    </div>
                    <div className="form-field">
                        <label>Time</label>
                        <div className="static-value">{time}</div>
                    </div>
                    <div className="form-field">
                        <label>Guests</label>
                        <div className="static-value">{partySize}</div>
                    </div>
                </div>

                <div className="form-actions">
                    <button type="button" className="btn-secondary btn-cancel" onClick={onCancel}>
                        Cancel
                    </button>
                    <button type="submit" className="btn-primary btn-submit" disabled={submitting}>
                        {submitting ? 'Reserving...' : 'Confirm Reservation'}
                    </button>
                </div>
            </form>
        </div>
    );
}

export default ReservationForm;
