import {useEffect, useState} from "react";
import {StudentsByInternshipFoundStatus} from "../../model/user";
import {getStudentsByInternshipStatus} from "../../services/managerService";
import {Form, Table} from "react-bootstrap";
import ManagerStudentByInternshipStatusRow from "./ManagerStudentByInternshipStatusRow";

const ManagerStudentByInternshipStatusList = () => {
    const [studentsByInternshipStatus, setStudentsByInternshipStatus] = useState<StudentsByInternshipFoundStatus>();
    const [selectedOption, setSelectedOption] = useState("studentsWithPendingResponse");

    useEffect(() => {
        getStudentsByInternshipStatus(1).then((res) => {
            setStudentsByInternshipStatus(res);
            console.log(studentsByInternshipStatus);
        });
    }, []);

    const handleDropdownChange = (event: any) => {
        setSelectedOption(event.target.value);
    };

    const currentTypeOfStudentObject = studentsByInternshipStatus
        ? studentsByInternshipStatus[selectedOption]
        : undefined;


    return (
        <>
            {/*<div className={"row"} style={{ padding: "16px 0", display: "flex", justifyContent: "flex-end", alignItems: "center" }}>*/}
            {/*    <h3 className={"col"}>Students by Internship</h3>*/}
            {/*    <Form className={"col"}>*/}
            {/*        <Form.Group controlId="searchText" style={{ margin: 0 }}>*/}
            {/*            <Form.Control*/}
            {/*                type="text"*/}
            {/*                placeholder={t("applicationsList.SearchPlaceholder")}*/}
            {/*                value={searchText}*/}
            {/*                onChange={(e) => setSearchText(e.target.value)}*/}
            {/*                className="custom-search-input"*/}
            {/*            />*/}
            {/*        </Form.Group>*/}
            {/*    </Form>*/}
            {/*</div>*/}

            <select value={selectedOption} onChange={handleDropdownChange}>
                <option value="studentsWithPendingResponse">Pending</option>
                <option value="studentsWithAcceptedResponse">Accepted</option>
                <option value="studentsWithRefusedResponse">Refused</option>
                <option value="studentsWithoutApplications">No applications</option>
            </select>

            {currentTypeOfStudentObject!.students.length > 0 ? (
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
                        {currentTypeOfStudentObject?.students.map((student) => (
                            <ManagerStudentByInternshipStatusRow key={student.id} student={student} status={currentTypeOfStudentObject?.status}/>
                        ))}
                        </tbody>
                    </Table>
                </div>
             ) : (
                 <p>No student</p>
             )}
        </>
    )
}

export default ManagerStudentByInternshipStatusList