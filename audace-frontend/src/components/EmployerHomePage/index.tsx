import React from "react";
import { useParams } from "react-router-dom";

const EmployerHomePage = () => {
    const { userId } = useParams();

    return (
        <div>
            <h1>Employer {userId}</h1>
        </div>
    );
};

export default EmployerHomePage;