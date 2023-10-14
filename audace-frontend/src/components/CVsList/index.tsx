import React, { useState, useEffect } from 'react';
import {CV} from "../../model/cv";
import {getCvsByStudentId} from "../../services/studentApplicationService";
import {getUserId} from "../../services/authService";

function CvList() {
    const [cvs, setCvs] = useState<CV[]>([]); // Initialize state for CVs
    const studentId = getUserId();

    useEffect(() => {
        if (studentId) {
            // Fetch CVs for the current student
            getCvsByStudentId(Number(studentId))
                .then((response) => {
                    setCvs(response.data);
                })
                .catch((error) => {
                    console.error('Error fetching CVs:', error);
                });
        }
    }, [studentId]);

    return (
        <div>
            <h2>Your CVs</h2>
            {cvs.length > 0 ? (
                <ul>
                    {cvs.map((cv) => (
                        <li key={cv.id}>
                            <a href={`link-to-cv/${cv.id}`}>{cv.fileName}</a>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>No CVs found</p>
            )}
        </div>
    );
}

export default CvList;
