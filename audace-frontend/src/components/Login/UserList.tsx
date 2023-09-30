
import React, { useEffect, useState } from 'react';
import service from "../../DataService/service";
import {User, UserType} from "../../model/user";
import {Link, useNavigate} from "react-router-dom";

const UserList: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        service.getAllUsers()
            .then(response => {
                setUsers(response.data)
            })
    }, []);

    const handleSignInClick = (user: User) => {
        if (user.type === "student") {
            navigate(`/student/${user.id}`);
        } else if (user.type === "employer") {
            navigate(`/employer/${user.id}`);
        }
    };


    return (
            <ul style={{ listStyleType: 'none' }}>
                {users.map((user) => (
                    <li className={"pb-2"} key={user.id}>
                        <div className={"container-fluid mw-100"} style={{background: '#ccc', width: '35%', borderRadius: '5px'}}>
                            <div className="d-flex justify-content-between p-2">
                                <div className="d-inline-block align-self-center">{user.email}</div>
                                <div className="d-inline-block btn btn-success fw-bold" onClick={() => handleSignInClick(user)}>Sign in</div>
                            </div>
                        </div>
                    </li>
                ))}
            </ul>
    );
};

export default UserList;

