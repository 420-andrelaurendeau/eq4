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
        <div className="container my-3 g-0">
            <h2>Your CVs</h2>
            <div className="card">
                <div className="card-body">
                    {cvs.length > 0 ? (
                        <ul className="list-group">
                            {cvs.map((cv) => (
                                <li key={cv.id} className="list-group-item">
                                    <a href={`link-to-cv/${cv.id}`}>{cv.fileName}</a>
                                </li>
                            ))}
                        </ul>
                    ) : (
                        <p>No CVs found</p>
                    )}
                </div>
            </div>
        </div>
    );
}

export default CvList;
