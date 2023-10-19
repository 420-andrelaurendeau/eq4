import React, { useEffect, useState } from 'react';
import { User } from "../../model/user";
import { useNavigate } from "react-router-dom";
import { ListGroup, Button, Container } from 'react-bootstrap';
import {getAllUsers} from "../../services/loginService";

const UserList: React.FC = () => {
    const [users, setUsers] = useState<User[]>([]);
    const navigate = useNavigate();

    useEffect(() => {
        getAllUsers()
            .then(response => {
                setUsers(response.data);
            });
    }, []);

    const handleSignInClick = (user: User) => {
        sessionStorage.setItem('user', JSON.stringify(user));
        if (user.type === "student") {
            navigate(`/student/${user.id}`);
        } else if (user.type === "employer") {
            navigate(`/employer/${user.id}`);
        } else if (user.type === "manager") {
            navigate(`/manager/${user.id}`);
        }
    };

    return (
        <ListGroup>
            {users.map((user) => (
                <ListGroup.Item key={user.id}>
                    <Container fluid style={{ background: '#ccc', width: '35%', borderRadius: '5px' }}>
                        <div className="d-flex justify-content-between p-2">
                            <div className="d-inline-block align-self-center">{user.email}</div>
                            <Button variant="success" className="fw-bold" onClick={() => handleSignInClick(user)}>
                                Sign in
                            </Button>
                        </div>
                    </Container>
                </ListGroup.Item>
            ))}
        </ListGroup>
    );
}

export default UserList;
