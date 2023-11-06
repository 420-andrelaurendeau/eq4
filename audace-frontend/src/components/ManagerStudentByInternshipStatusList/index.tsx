import {useEffect, useState} from "react";
import {StudentsByInternshipFoundStatus} from "../../model/user";
import {getStudentsByInternshipStatus} from "../../services/managerService";
import {Form, Table} from "react-bootstrap";
import ManagerStudentByInternshipStatusRow from "./ManagerStudentByInternshipStatusRow";

const ManagerStudentByInternshipStatusList = () => {
    const [studentsByInternshipStatus, setStudentsByInternshipStatus] = useState<StudentsByInternshipFoundStatus>();
    const currentTypeOfStudent = "studentsWithPendingResponse";
    const currentTypeOfStudentObject = studentsByInternshipStatus?.studentsWithPendingResponse;

    useEffect(() => {
        getStudentsByInternshipStatus(1).then((res) => {
            setStudentsByInternshipStatus(res);
            console.log(studentsByInternshipStatus);
        });
    }, []);


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

            {/*{filteredApplications.length > 0 ? (*/}
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
                        {/*{filteredApplications.map((application) => (*/}
                        {/*    <ManagerApplicationRow key={application.id} application={application}/>*/}
                        {/*))}*/}
                        {currentTypeOfStudentObject?.students.map((student) => (
                            <ManagerStudentByInternshipStatusRow key={student.id} student={student} status={currentTypeOfStudentObject?.status}/>
                        ))}
                        </tbody>
                    </Table>
                </div>
            {/* ) : (*/}
            {/*     <p>No student</p>*/}
            {/* )}*/}
        </>
    )
}

export default ManagerStudentByInternshipStatusList