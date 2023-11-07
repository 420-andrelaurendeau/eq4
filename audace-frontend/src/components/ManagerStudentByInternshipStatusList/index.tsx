import { useEffect, useState } from "react";
import { Student, StudentsByInternshipFoundStatus } from "../../model/user";
import { getStudentsByInternshipStatus } from "../../services/managerService";
import { Col, Form, Row, Table } from "react-bootstrap";
import ManagerStudentByInternshipStatusRow from "./ManagerStudentByInternshipStatusRow";
import { useTranslation } from "react-i18next";

const ManagerStudentByInternshipStatusList = () => {
    const [studentsByInternshipStatus, setStudentsByInternshipStatus] = useState<
        StudentsByInternshipFoundStatus
    >();
    const { t } = useTranslation();
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
            <Row style={{ paddingBottom: "10px" }}>
                <Col>
                    <h3>{t("studentsByInternship.title")}</h3>
                </Col>
                <Col className="d-flex">
                    <Col style={{ flex: "1" }}>
                        <select className="select-custom" value={selectedOption} onChange={handleDropdownChange}>
                            <option value="studentsWithPendingResponse">{t("studentsByInternship.row.statusValues.PENDING")}</option>
                            <option value="studentsWithAcceptedResponse">{t("studentsByInternship.row.statusValues.ACCEPTED")}</option>
                            <option value="studentsWithRefusedResponse">{t("studentsByInternship.row.statusValues.REFUSED")}</option>
                            <option value="studentsWithoutApplications">{t("studentsByInternship.row.statusValues.NO_APPLICATIONS")}</option>
                        </select>
                    </Col>
                    <Col style={{ flex: "2" }}>
                        <Form.Control
                            className="custom-search-input"
                            type="text"
                            placeholder={t("studentsByInternship.searchBarPlaceholder")}
                            value={searchText}
                            onChange={(e) => {
                                setSearchText(e.target.value);
                                filterStudents(e.target.value, selectedOption);
                            }}
                        />
                    </Col>
                </Col>
            </Row>


            {filteredStudents.length > 0 ? (
                <div style={{ overflow: "auto", maxHeight: "18.5rem" }}>
                    <Table className="table-custom" striped bordered hover size="sm">
                        <thead className="table-custom">
                            <tr>
                                <th>{t("studentsByInternship.row.nameTh")}</th>
                                <th>{t("studentsByInternship.row.studentNumberTh")}</th>
                                <th>{t("studentsByInternship.row.departmentTh")}</th>
                                <th>{t("studentsByInternship.row.statusTh")}</th>
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
                <p>{t("studentsByInternship.noStudents")}</p>
            )}
        </>
    );
};

export default ManagerStudentByInternshipStatusList;
