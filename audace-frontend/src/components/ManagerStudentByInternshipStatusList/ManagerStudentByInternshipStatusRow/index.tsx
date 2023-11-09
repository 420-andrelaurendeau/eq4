import { useTranslation } from "react-i18next";
import { Student } from "../../../model/user";

interface Props {
    student: Student;
    status: string;
}

const ManagerStudentByInternshipStatusRow = ({ student, status }: Props) => {
    const { t } = useTranslation();

    return (
        <tr>
            <td>{student.lastName}, {student.firstName}</td>
            <td>{student.studentNumber}</td>
            <td>{student.department!.name}</td>
            <td>{t(`studentsByInternship.row.statusValues.${status}`)}</td>
        </tr>
    );
};

export default ManagerStudentByInternshipStatusRow;
