
import React, { useEffect, useState } from 'react';
import service from "../DataService/service";
import { User } from "../model/user"

const UserList: React.FC = () => {
    const [users, setUsers] = useState<any[]>([]);

    useEffect(() => {
        service.getAllUsers()
            .then(response => {
                setUsers(response.data)
            })
    }, []);

    return (
        <div className={"container vh-100"}>
            <h1 className={"m-5"}>Login (temporaire)</h1>
            <div className={"container-fluid mw-100"} style={{background: '#ccc', width: '35%', borderRadius: '5px'}}>
                <div className="d-flex justify-content-between p-2">
                    <div className="d-inline-block align-self-center" style={{}}>ID</div>
                    <div className="d-inline-block btn btn-success">+</div>
                </div>

            </div>
            <ul>
                {users.map((user) => (
                    <li key={user.id}>{user.email}</li>
                ))}
            </ul>
        </div>
    );
};

export default UserList;

