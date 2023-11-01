import Application from "../../../model/application";

interface Props {
  application: Application;
}
const ManagerApplicationRow = ({ application }: Props) => {

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