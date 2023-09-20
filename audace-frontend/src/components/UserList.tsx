
import React, { useEffect, useState } from 'react';
import service from "../DataService/service";
import {User} from "../model/user";

const UserList: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);

    useEffect(() => {
        service.getAllUsers()
            .then(response => {
                setUsers(response.data)
            })
    }, []);

    return (
            <ul>
                {users.map((user) => (
                    <li key={user.id}>
                        <div className={"container-fluid mw-100"} style={{background: '#ccc', width: '35%', borderRadius: '5px'}}>
                            <div className="d-flex justify-content-between p-2">
                                <div className="d-inline-block align-self-center">{user.email}</div>
                                <div className="d-inline-block btn btn-success fw-bold">Sign in</div>
                            </div>
                        </div>
                    </li>
                ))}
            </ul>
    );
};

export default UserList;

