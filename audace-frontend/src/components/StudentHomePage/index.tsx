import React from "react";
import { useParams } from "react-router-dom";
import FileUploader from "../FileUploader";

const StudentHomePage = () => {
    const { userId } = useParams();

    return (
        <div>
            <h1>Student {userId}</h1>
            <FileUploader />
        </div>
    );
};

export default StudentHomePage;