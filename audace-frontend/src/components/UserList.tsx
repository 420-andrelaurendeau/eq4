
import React, { useEffect, useState } from 'react';

const UserList: React.FC = () => {
    const [users, setUsers] = useState<any[]>([]);

    useEffect(() => {
        fetch('http://localhost:8081/users')
            .then((response) => response.json())
            .then((data) => setUsers(data));
    }, []);

    return (
        <div>
            <h1>User List</h1>
            <ul>
                {users.map((user) => (
                    <li key={user.id}>{user.username}</li>
                ))}
            </ul>
        </div>
    );
};

export default UserList;
