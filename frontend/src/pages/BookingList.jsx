import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import {getBooking, deleteBooking, getBookings} from "../api";

export default function BookingList() {
    const [bookings, setBooking] = useState([]);

    const load = () => getBookings().then(data => setBooking(data));
    useEffect(() => { load(); }, []);

    return (
        <div>
            <h1>Bookings</h1>
            <Link to="/bookings/new">New booking</Link>
            <table>
                <tbody>
                {bookings.map(b => (
                    <tr key={b.id}>
                        <td><Link to={`/bookings/${b.id}/edit`}>Edit</Link></td>
                        <td><button onClick={() => deleteBooking(b.id).then(load)}>Delete</button></td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}