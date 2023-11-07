import { useTranslation } from "react-i18next";
import { Student } from "../../../model/user";

interface Props {
    student: Student;
    status: string;
}

const ManagerStudentByInternshipStatusRow = ({ student, status }: Props) => {
    const { t } = useTranslation();

    const getStatusTranslation = (status: string) => {
        switch (status) {
            case "PENDING":
                return t("studentsByInternship.row.statusValues.PENDING");
            case "ACCEPTED":
                return t("studentsByInternship.row.statusValues.ACCEPTED");
            case "REFUSED":
                return t("studentsByInternship.row.statusValues.REFUSED");
            case "INTERN":
                return t("studentsByInternship.row.statusValues.INTERN");
            case "NO_APPLICATIONS":
                return t("studentsByInternship.row.statusValues.NO_APPLICATIONS");
            default:
                return status;
        }
    };

    return (
        <tr>
            <td>{student.lastName}, {student.firstName}</td>
            <td>{student.studentNumber}</td>
            <td>{student.department!.name}</td>
            <td>{getStatusTranslation(status)}</td>
        </tr>
    );
};

export default ManagerStudentByInternshipStatusRow;
