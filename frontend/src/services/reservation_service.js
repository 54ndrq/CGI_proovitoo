const API_BASE = '/api';

export async function fetchTables(filters = {}) {
    const params = new URLSearchParams();
    if (filters.date) params.append('date', filters.date);
    if (filters.time) params.append('time', filters.time);
    if (filters.partySize) params.append('partySize', filters.partySize);
    
    // Attribute filters
    if (filters.isByTheWindow) params.append('isByTheWindow', 'true');
    if (filters.isPrivate) params.append('isPrivate', 'true');
    if (filters.smokingAllowed) params.append('smokingAllowed', 'true');

    const response = await fetch(`${API_BASE}/tables?${params}`, {
        headers: {'Cache-Control': 'no-cache'}
    });
    return await response.json();
}

export async function fetchTimeSlots() {
    const response = await fetch(`${API_BASE}/time-slots`);
    return await response.json();

}

export async function fetchReservations() {
    const response = await fetch(`${API_BASE}/reservations`);
    return await response.json();
}

export async function createReservation(reservation) {
    const response = await fetch(`${API_BASE}/reservations`, {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(reservation),
    });

    if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || 'Reservation failed');
    }

    return await response.json();
}

export async function cancelReservation(id) {
    await fetch(`${API_BASE}/reservations/${id}`, {method: 'DELETE'});
}

export async function fetchRecommendations(filters = {}) {
  const params = new URLSearchParams();
  if (filters.date) params.append('date', filters.date);
  if (filters.time) params.append('time', filters.time);
  if (filters.partySize) params.append('partySize', filters.partySize);
  
  // Attribute filters
  if (filters.isByTheWindow) params.append('isByTheWindow', 'true');
  if (filters.isPrivate) params.append('isPrivate', 'true');
  if (filters.smokingAllowed) params.append('smokingAllowed', 'true');

  try {
    const response = await fetch(`${API_BASE}/recommendations?${params}`);
    if (response.status === 204) return null;
    return await response.json();
  } catch {
    return null;
  }
}
