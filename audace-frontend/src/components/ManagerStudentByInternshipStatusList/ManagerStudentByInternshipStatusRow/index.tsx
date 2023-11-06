import { useTranslation } from "react-i18next";
import { useNavigate } from "react-router-dom";
import {Student} from "../../../model/user";

interface Props {
    // application: Application;
    student: Student;
    status: string;
}
const ManagerStudentByInternshipStatusRow = ({student, status}: Props) => {
    const navigate = useNavigate();
    const { t } = useTranslation();

    return (
        <tr>
            <td>a</td>
            <td>b</td>
            <td>
                c
            </td>
            <td>
                {status}
            </td>
        </tr>
    );
};

export default ManagerStudentByInternshipStatusRow;