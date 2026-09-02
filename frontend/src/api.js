const BASE = "/api";

export async function getCustomers() {
    const res = await fetch(`${BASE}/customers`);
    return res.json();
}

export async function createCustomer(data) {
    const res = await fetch(`${BASE}/customers`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });
    return res.json();
}

export async function updateCustomer(id, data) {
    const res = await fetch(`${BASE}/customers/${id}`, {
        method: "PUT",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(data),
    });
    return res.json();
}

export async function deleteCustomer(id) {
    await fetch(`${BASE}/customers/${id}`, { method: "DELETE" });
}

// ROOMS

export async function getRoomsAvailable(checkIn, checkOut, guests) {
    const res = await fetch(
        `${BASE}/rooms/available?checkIn=${checkIn}&checkOut=${checkOut}&guests=${guests}`);
    return res.json();
}

export async function getRooms() {
    const res = await fetch(`${BASE}/rooms`);
    return res.json();
}

export async function getRoom(id) {
    const res = await fetch(`${BASE}/rooms/${id}`);
    return res.json();
}

export async function createRoom(data) {
    const res = await fetch(`${BASE}/rooms`,
        {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        });
    return res.json();
}

export async function updateRoom(data, id) {
    const res = await fetch(`${BASE}/rooms/${id}`,
        {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        });
    return res.json();
}

export async function deleteRoom(id) {
    const res = await fetch(`${BASE}/rooms/${id}`, { method: "DELETE" });
    return res.json();
}

// BOOKINGS

export async function getBookings() {
    const res = await fetch(`${BASE}/bookings`);
    return res.json();
}

export async function getBooking(id) {
    const res = await fetch(`${BASE}/bookings/${id}`);
    return res.json();
}

export async function createBooking(data) {
    const res = await fetch(`${BASE}/bookings`,
        {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        });
    return res.json();
}

export async function updateBooking(data, id) { //BUG? see bookingcontrollers update booking, compare params
    const res = await fetch(`${BASE}/bookings/${id}`,
        {
            method: "PUT",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(data),
        });
    return res.json();
}

export async function deleteBooking(id) {
    const res = await fetch(`${BASE}/bookings/${id}`, { method: "DELETE" });
    return res.json();
}