import Application from "../../../model/application";

interface Props {
  application: Application;
}

const ApplicationRow = ({ application }: Props) => {

  return (
    <tr className={"table-custom"}>
      <td>{application.offer!.title}</td>
      <td>{application.offer!.employer.organisation}</td>
      <td>
          {application.student!.firstName} {application.student!.lastName}
      </td>
    </tr>
  );
};

export default ApplicationRow;
