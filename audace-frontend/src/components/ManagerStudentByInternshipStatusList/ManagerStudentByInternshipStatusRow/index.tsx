import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import {Student} from "../../../model/user";

interface Props {
    student: Student;
    status: string;
}
const ManagerStudentByInternshipStatusRow = ({student, status}: Props) => {
    const navigate = useNavigate();
    const { t } = useTranslation();

    return (
        <tr>
            <td>{student.lastName}, {student.firstName}</td>
            <td>{student.studentNumber}</td>
            <td>
                {student.department!.name}
            </td>
            <td>
                {status}
            </td>
        </tr>
    );
};

export default ManagerStudentByInternshipStatusRow;