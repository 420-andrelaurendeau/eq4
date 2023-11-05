import {useEffect, useState} from "react";
import {StudentsByInternshipFoundStatus, StudentsWithAcceptedResponse, StudentsWithInternship, StudentsWithNoApplications, StudentsWithPendingResponse, StudentsWithRefusedResponse} from "../../model/user";
import {getStudentsByInternshipStatus, getStudentsByInternshipStatusTest} from "../../services/managerService";

interface Props {
    departmentId: number;
}

const ManagerStudentByInternshipStatusList = ({departmentId}: Props) => {

    const [studentsByInternshipStatus, setStudentsByInternshipStatus] = useState<StudentsByInternshipFoundStatus>();
    const [studentsWithAccepted, setStudentsWithAccepted] = useState<StudentsWithAcceptedResponse>();
    const [studentsWithRefused, setStudentsWithRefused] = useState<StudentsWithRefusedResponse>();
    const [studentsWithPending, setStudentsWithPending] = useState<StudentsWithPendingResponse>();
    const [studentsWithNoApplications, setStudentsWithNoApplications] = useState<StudentsWithNoApplications>();
    const [studentsWithInternship, setStudentsWithInternship] = useState<StudentsWithInternship>();

    useEffect(() => {
        fetchStudentsByInternshipStatus(1);
        getStudentsByInternshipStatusTest(1).then((res) => {
            setStudentsByInternshipStatus(res.data);
            console.log(studentsByInternshipStatus);
        });
    }, []);

    const fetchStudentsByInternshipStatus = async (departmentId: number) => {
        try {

            const studentsByInternshipStatusRes = await getStudentsByInternshipStatus(departmentId);
            setStudentsWithInternship(studentsByInternshipStatusRes.data.studentsWithInternship);
            setStudentsWithAccepted(studentsByInternshipStatusRes.data.studentsWithAcceptedResponse);
            setStudentsWithRefused(studentsByInternshipStatusRes.data.studentsWithRefusedResponse);
            setStudentsWithPending(studentsByInternshipStatusRes.data.studentsWithPendingResponse);
            setStudentsWithNoApplications(studentsByInternshipStatusRes.data.studentsWithoutApplications);

        } catch (err: any) {
            console.log(
                "Students by internship status fetching error: " + err.response.data
            );
        }
    };

    return (
        <div>
            ManagerStudentByInternshipStatusList
        </div>
    )
}

export default ManagerStudentByInternshipStatusList