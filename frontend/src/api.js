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
        `${BASE}/rooms/available?checkIn=${checkIn}&checkOut=${checkOut}&guests=${guests}
        `);
    return res.json();
}

export async function getRoomsAll() {
    const res = await fetch(`${BASE}/rooms`);
    return res.json();
}

export async function getRoom(id) {
    const res = await fetch(`${BASE}/rooms/`);
    return res.json();
}