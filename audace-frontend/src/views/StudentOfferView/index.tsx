import { Container } from "react-bootstrap";
import { Student } from "../../model/user";
import { Department } from "../../model/department";
import StudentOffersList from "../../components/StudentOffersList";

const tempDepartment: Department = {
    id: 1,
    name: "",
    code: "",
}

const tempStudent: Student = {
    id: 1,
    email: "",
    password: "",
    studentNumber: "1",
    department: tempDepartment,
}

const StudentOfferView = () => {
    return (
        <Container>
            <h1>Student Offer View</h1>
            <StudentOffersList student={tempStudent}/>
        </Container>
    );
};

export default StudentOfferView;