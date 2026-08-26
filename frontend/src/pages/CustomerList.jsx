import { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { getCustomers, deleteCustomer } from "../api";

export default function CustomerList() {
    const [customers, setCustomers] = useState([]);

    const load = () => getCustomers().then(setCustomers);
    useEffect(() => { load(); }, []);

    return (
        <div>
            <h1>Customers</h1>
            <Link to="/customers/new">+ New customer</Link>
            <table>
                <tbody>
                {customers.map(c => (
                    <tr key={c.id}>
                        <td>{c.firstName} {c.lastName}</td>
                        <td>{c.email}</td>
                        <td><Link to={`/customers/${c.id}/edit`}>Edit</Link></td>
                        <td><button onClick={() => deleteCustomer(c.id).then(load)}>Delete</button></td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}