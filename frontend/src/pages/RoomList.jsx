import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getRooms, deleteRoom } from "../api";

export default function RoomList() {
    const [rooms, setRooms] = useState([]);

    const load = () => getRooms().then(data => setRooms(data));
    useEffect(() => { load(); }, []);

    return (
        <div>
            <h1>Rooms</h1>
            <Link to="/rooms/new">New room</Link>
            <table>
                <tbody>
                {rooms.map(r => (
                    <tr key={r.id}>
                        <td>Room</td>
                        <td><Link to={`/rooms/${r.id}/edit`}>Edit</Link></td>
                        <td><button onClick={() => deleteRoom(r.id).then(load)}>Delete</button></td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}