import React from "react";
import { useParams } from "react-router-dom";

const StudentHomePage = () => {
    const { userId } = useParams();

    return (
        <div>
            <h1>Student {userId}</h1>
        </div>
    );
};

export default StudentHomePage;