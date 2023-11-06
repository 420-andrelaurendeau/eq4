import { useEffect, useState } from "react";
import { Student, StudentsByInternshipFoundStatus } from "../../model/user";
import { getStudentsByInternshipStatus } from "../../services/managerService";
import { Form, Table } from "react-bootstrap";
import ManagerStudentByInternshipStatusRow from "./ManagerStudentByInternshipStatusRow";

const ManagerStudentByInternshipStatusList = () => {
    const [studentsByInternshipStatus, setStudentsByInternshipStatus] = useState<
        StudentsByInternshipFoundStatus
    >();
    const [selectedOption, setSelectedOption] = useState("studentsWithPendingResponse");
    const [searchText, setSearchText] = useState("");
    const [filteredStudents, setFilteredStudents] = useState<Student[]>([]);

    useEffect(() => {
        getStudentsByInternshipStatus(1).then((res) => {
            setStudentsByInternshipStatus(res);
            filterStudents(searchText, selectedOption);
        });
    }, [searchText, selectedOption]);

    const handleDropdownChange = (event: any) => {
        setSelectedOption(event.target.value);
        setFilteredStudents([]);
        filterStudents(searchText, event.target.value);
    };

    const filterStudents = (text: string, tab: string) => {
        if (!studentsByInternshipStatus) return;

        const filtered = studentsByInternshipStatus[tab].students.filter((student) => {
            const lowerCaseText = text.toLowerCase();
            return (
                student.firstName!.toLowerCase().includes(lowerCaseText) ||
                student.studentNumber.toLowerCase().includes(lowerCaseText) ||
                student.department!.name.toLowerCase().includes(lowerCaseText) ||
                student.lastName!.toLowerCase().includes(lowerCaseText)
            );
        });
        setFilteredStudents(filtered);
    };

    return (
        <>
            <select value={selectedOption} onChange={handleDropdownChange}>
                <option value="studentsWithPendingResponse">Pending</option>
                <option value="studentsWithAcceptedResponse">Accepted</option>
                <option value="studentsWithRefusedResponse">Refused</option>
                <option value="studentsWithoutApplications">No applications</option>
            </select>

            <Form.Control
                type="text"
                placeholder="Search by name, number, or department"
                value={searchText}
                onChange={(e) => {
                    setSearchText(e.target.value);
                    filterStudents(e.target.value, selectedOption);
                }}
            />

            {filteredStudents.length > 0 ? (
                <div style={{ overflow: "auto", maxHeight: "18.5rem" }}>
                    <Table className="table-custom" striped bordered hover size="sm">
                        <thead className="table-custom">
                        <tr>
                            <th>Name</th>
                            <th>Number</th>
                            <th>Department</th>
                            <th>Status</th>
                        </tr>
                        </thead>
                        <tbody className="table-custom">
                        {filteredStudents.map((student) => (
                            <ManagerStudentByInternshipStatusRow key={student.id} student={student} status={studentsByInternshipStatus![selectedOption].status} />
                        ))}
                        </tbody>
                    </Table>
                </div>
            ) : (
                <p>No student</p>
            )}
        </>
    );
};

export default ManagerStudentByInternshipStatusList;
