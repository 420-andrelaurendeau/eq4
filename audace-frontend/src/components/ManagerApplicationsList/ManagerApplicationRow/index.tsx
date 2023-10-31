import Application from "../../../model/application";
import {useTranslation} from "react-i18next";

interface Props {
    application: Application;
}
const ManagerApplicationRow = ({application}: Props) => {
    const {t} = useTranslation();

    return (
        <tr>
            <td>{application.offer!.title}</td>
            <td>{application.offer!.employer.organisation}</td>
            <td>
                {application.cv?.student.firstName} {application.cv?.student.lastName}
            </td>
        </tr>
    );
};

export default ManagerApplicationRow;