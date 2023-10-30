import Application from "../../../model/application";
import {useTranslation} from "react-i18next";
import {Student} from "../../../model/user";

interface Props {
    application: Application;
    student: Student;
}
const ManagerApplicationRow = ({application, student}: Props) => {
    const {t} = useTranslation();

    return (
        <tr>
            <td>{application.offer!.title}</td>
            <td>{student.firstName} {student.lastName}</td>
            <td>
                {t(`applicationsList.row.status.${application.applicationStatus}`)}
            </td>
        </tr>
    );
};

export default ManagerApplicationRow;